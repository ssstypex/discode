package pl.iam.simon.discountcouponservice;

import org.springframework.boot.SpringApplication;

public class TestDiscountCouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(DiscountCouponServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
