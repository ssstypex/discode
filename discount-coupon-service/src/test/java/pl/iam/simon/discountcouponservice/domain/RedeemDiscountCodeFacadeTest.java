package pl.iam.simon.discountcouponservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.iam.simon.discountcouponservice.domain.service.RedeemDiscountCodeFacade;
import pl.iam.simon.discountcouponservice.domain.service.RedeemDiscountCodePolicy;
import pl.iam.simon.discountcouponservice.domain.model.Country;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValue;
import pl.iam.simon.discountcouponservice.domain.model.UserId;
import pl.iam.simon.discountcouponservice.domain.port.in.RedeemDiscountCodeInput;
import pl.iam.simon.discountcouponservice.domain.port.out.RedeemDiscountCodeDataSourceProvider;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RedeemDiscountCodeFacadeTest {

    @Mock
    private RedeemDiscountCodeDataSourceProvider dataSourceProvider;

    @Mock
    private RedeemDiscountCodePolicy policy;

    @InjectMocks
    private RedeemDiscountCodeFacade redeemDiscountCodeUseCase;


    @Test
    void ShouldThrowException_WhenCodeNotFound() {
        //given
        given(dataSourceProvider.findByCodeAndCountry(any(), any())).willReturn(Optional.empty());

        //when + then
        assertThrows(Exception.class, () -> redeemDiscountCodeUseCase.canBeUsed(
                new RedeemDiscountCodeInput(new DiscountCodeValue("MAJ30"), Country.POLAND, new UserId(UUID.randomUUID()))));
    }

    //TODO

}