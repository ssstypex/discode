package pl.iam.simon.discountcouponservice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.iam.simon.discountcouponservice.domain.service.CreateDiscountCodeFacade;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCode;
import pl.iam.simon.discountcouponservice.domain.port.out.CreateDiscountCodeDataSourceProvider;


@ExtendWith(MockitoExtension.class)
class CreateDiscountCodeFacadeTest {

    @Mock
    private CreateDiscountCodeDataSourceProvider createDiscountCodeDataSourceProvider;

    @InjectMocks
    private CreateDiscountCodeFacade createDiscountCodeFacade;

    private DiscountCode testDiscountCode;

    @BeforeEach
    void init() {
//        testDiscountCode = new DiscountCode();
    }

    @Test
    void Should_CreateDiscountCode_When() {
//        given(createDiscountCodeDataSourceProvider.createDiscountCode(any()));
    }
}