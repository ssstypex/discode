package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.iam.simon.discountcouponservice.domain.exception.DiscountCodeValidationException;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicyError;
import pl.iam.simon.discountcouponservice.domain.validator.DiscountCodePolicyValidationResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiscountCodeController.class)
class DiscountCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateDiscountCodeUseCase createDiscountCodeUseCase;

    @MockitoBean
    private RedeemDiscountCodeUseCase redeemDiscountCodeUseCase;

    @Test
    void ShouldReturn201_WhenCreateEndpointCalledAndDiscountCodeIsCreated() throws Exception {
        //given
        CreateDiscountCodeDTO input = new CreateDiscountCodeDTO("MAJ30", "PL", 100);

        //when
        ResultActions result = mockMvc.perform(post("/v1/discount-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        //then
        result.andExpect(status().isCreated());
    }

    @Test
    void ShouldVerifyIfUseCaseCodeWasPerformed_WhenCreateEndpointCalled() throws Exception {
        //given
        CreateDiscountCodeDTO input = new CreateDiscountCodeDTO("MAJ30", "PL", 100);

        //when
        mockMvc.perform(post("/v1/discount-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        //then
        verify(createDiscountCodeUseCase, times(1)).createDiscountCode(any());
    }


    @Test
    void ShouldReturn400_WhenCreatedEndpointCalledWithMissingRequiredField() throws Exception {
        //given
        CreateDiscountCodeDTO input = new CreateDiscountCodeDTO(null, "PL", 100);

        //when
        ResultActions result = mockMvc.perform(post("/v1/discount-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void ShouldReturn400_WhenCreateEndpointCalledWithInvalidCountry() throws Exception {
        //given
        CreateDiscountCodeDTO input = new CreateDiscountCodeDTO("MAJ", "PLL", 100);

        //when
        ResultActions result = mockMvc.perform(post("/v1/discount-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        //then
        result.andExpect(status().isBadRequest());
    }

    @Test
    void ShouldReturn200WithoutErrorsInResponse_WhenValidationEndpointCalledAndDiscountCodeIsValid() throws Exception {
        //given
        given(redeemDiscountCodeUseCase.canBeUsed(any()))
                .willReturn(DiscountCodePolicyValidationResult.valid());

        //when
        ResultActions result = mockMvc.perform(
                get("/v1/discount-codes/PL/MAJ30/validate"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", hasSize(0)));
    }

    @Test
    void ShouldReturn200WithErrorsInResponse_WhenValidationEndpointCalledAndDiscountCodeIsInvalid() throws Exception {
        //given
        given(redeemDiscountCodeUseCase.canBeUsed(any()))
                .willReturn(DiscountCodePolicyValidationResult.invalid(List.of(
                        new DiscountCodePolicyError(DiscountCodeValidationError.USAGE_LIMIT),
                        new DiscountCodePolicyError(DiscountCodeValidationError.USER_USAGE_LIMIT))
                ));

        //when
        ResultActions result = mockMvc.perform(
                get("/v1/discount-codes/PL/MAJ30/validate"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    void ShouldReturn422_WhenValidationEndpointCalledAndCodeUsageLimitExceeded() throws Exception {
        //given
        willThrow(new DiscountCodeValidationException(DiscountCodeValidationError.USAGE_LIMIT))
                .given(redeemDiscountCodeUseCase).redeemDiscountCode(any());

        //when
        ResultActions result = mockMvc.perform(
                post("/v1/discount-codes/PL/MAJ30/redeem"));

        //then
        result.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0]").value(DiscountCodeValidationError.USAGE_LIMIT.getMessage()));
    }

    @Test
    void ShouldReturn422_WhenValidationEndpointCalledAndUserHasWrongCountry() throws Exception {
        //given
        willThrow(new DiscountCodeValidationException(DiscountCodeValidationError.WRONG_COUNTRY))
                .given(redeemDiscountCodeUseCase).redeemDiscountCode(any());

        //when
        ResultActions result = mockMvc.perform(
                post("/v1/discount-codes/PL/MAJ30/redeem"));

        //then
        result.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0]").value(DiscountCodeValidationError.WRONG_COUNTRY.getMessage()));
    }

    @Test
    void ShouldReturn404_WhenValidationEndpointCalledAndDiscountCodeNotFound() throws Exception {
        //given
        willThrow(new DiscountCodeValidationException(DiscountCodeValidationError.UNKNOWN))
                .given(redeemDiscountCodeUseCase).redeemDiscountCode(any());

        //when
        ResultActions result = mockMvc.perform(post("/v1/discount-codes/PL/MAJ30/redeem"));

        //then
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0]").value(DiscountCodeValidationError.UNKNOWN.getMessage()));
    }

}