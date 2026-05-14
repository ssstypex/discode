package pl.iam.simon.discountcouponservice.infrastructure.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.iam.simon.discountcouponservice.domain.port.in.CreateDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeUseCase;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest.CreateDiscountCodeDTO;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest.DiscountCodeController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void ShouldReturn201_WhenDiscountCodeIsCreated() throws Exception {
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
    void ShouldVerifyIfUseCaseCodeWasPerformed_WhenEndpointCalled() throws Exception {
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
    void ShouldReturn400_WhenRequiredFieldIsMissing() throws Exception {
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
    void ShouldReturn400_WhenInvalidCountry() throws Exception {
        //given
        CreateDiscountCodeDTO input = new CreateDiscountCodeDTO("MAJ", "PLL", 100);

        //when
        ResultActions result = mockMvc.perform(post("/v1/discount-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        //then
        result.andExpect(status().isBadRequest());
    }
}