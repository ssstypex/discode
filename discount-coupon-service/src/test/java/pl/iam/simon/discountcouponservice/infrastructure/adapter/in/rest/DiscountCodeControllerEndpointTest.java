package pl.iam.simon.discountcouponservice.infrastructure.adapter.in.rest;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.iam.simon.discountcouponservice.domain.model.DiscountCodeValidationError;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeEntity;
import pl.iam.simon.discountcouponservice.infrastructure.adapter.out.persistence.DiscountCodeRepository;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = "classpath:db/migration/V50__insert_test_discount_coupon.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DiscountCodeControllerEndpointTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        Dotenv dotenv = Dotenv.configure().directory("../").ignoreIfMissing().load();
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("ipify.api-key", () -> dotenv.get("IPIFY_API_KEY"));
    }

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Test
    void ShouldAllowOnlyOneRedemptionToSuccess_WhenOneUsageLeft() throws InterruptedException {
        // given
        int threads = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threads);
        AtomicInteger successfulResponses = new AtomicInteger(0);

        // when
        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                try {
                    startLatch.await();
                    ResponseEntity<String> response = restTemplate.postForEntity(
                            "http://localhost:" + randomServerPort + "/v1/discount-codes/PL/MAJ30MULTI/redeem",
                            null,
                            String.class
                    );
                    if (response.getStatusCode().is2xxSuccessful()) {
                        successfulResponses.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        executor.shutdown();

        //then
        Optional<DiscountCodeEntity> entity = transactionTemplate.execute(status ->
                discountCodeRepository.findByCodeAndCountryCode("MAJ30MULTI", "PL"));
        assertThat(successfulResponses.get()).isEqualTo(3);
        assertThat(entity.get().getCurrentUsages()).isEqualTo(entity.get().getMaxUsages());
    }

    @Test
    void ShouldReturn201_WhenCreateRequestIsSuccessful(){
        // given
        CreateDiscountCodeDTO request = new CreateDiscountCodeDTO("SUMMER20", "PL", 100);

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + randomServerPort + "/v1/discount-codes",
                request,
                Void.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void ShouldReturn409_WhenTryToCreateAndDiscountCodeAlreadyExists() {
        // given
        CreateDiscountCodeDTO request = new CreateDiscountCodeDTO("MAJ30", "PL", 100);

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + randomServerPort + "/v1/discount-codes",
                request,
                String.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void ShouldProperlyPersistDiscountCode_WhenCreateRequestIsValid() {
        // given
        CreateDiscountCodeDTO request = new CreateDiscountCodeDTO("MAJ40", "PL", 100);

        // when
        restTemplate.postForEntity("http://localhost:" + randomServerPort + "/v1/discount-codes", request, Void.class
        );

        // then
        Optional<DiscountCodeEntity> entity = transactionTemplate.execute(status ->
                discountCodeRepository.findByCodeAndCountryCode("MAJ40", "PL"));

        assertTrue(entity.isPresent());
        assertThat(entity.get().getCode()).isEqualTo("MAJ40");
        assertThat(entity.get().getCountryCode()).isEqualTo("PL");
        assertNotNull(entity.get().getCreatedAt());
    }

    @Test
    void ShouldReturn400WithErrors_WhenCreateRequestIsInvalid() {
        // given
        CreateDiscountCodeDTO request = new CreateDiscountCodeDTO("MAJ40", "PLL", 0);

        // when
        ResponseEntity<DiscountCodeExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "http://localhost:" + randomServerPort + "/v1/discount-codes", request, DiscountCodeExceptionHandler.ErrorResponse.class);
        System.out.println(response.getBody());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().errors().size()).isEqualTo(2);
    }


    @Test
    void ShouldReturn200_WhenRedeemRequestIsSuccessful() {

        // when
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "http://localhost:" + this.randomServerPort + "/v1/discount-codes/PL/MAJ30/redeem",
                null,
                Void.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void ShouldPersistUsageIncrementation_WhenRedeemRequestIsSuccessful() {
        // when
        restTemplate.postForEntity(
                "http://localhost:" + this.randomServerPort + "/v1/discount-codes/PL/MAJ30/redeem",
                null,
                Void.class
        );

        // then
        DiscountCodeEntity entity = transactionTemplate.execute(status ->
                discountCodeRepository.findByCodeAndCountryCode("MAJ30", "PL")).orElseThrow();
        assertThat(entity.getCurrentUsages()).isEqualTo(10);
    }

    @Test
    void ShouldReturn404_WhenWhenTryToRedeemAndDiscountCodeDoesNotExist() {
        // when
        ResponseEntity<DiscountCodeExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "http://localhost:" + this.randomServerPort + "/v1/discount-codes/PL/MAJ40/redeem",
                null,
                DiscountCodeExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().errors().getFirst()).isEqualTo(DiscountCodeValidationError.UNKNOWN.getMessage());
    }

    @Test
    void ShouldReturn422_WhenTryToRedeemAndCountryDoesNotMatch() {
        // when
        ResponseEntity<DiscountCodeExceptionHandler.ErrorResponse> response = restTemplate.postForEntity(
                "http://localhost:" + this.randomServerPort + "/v1/discount-codes/DE/MAJ30/redeem",
                null,
                DiscountCodeExceptionHandler.ErrorResponse.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody().errors().getFirst()).isEqualTo(DiscountCodeValidationError.WRONG_COUNTRY.getMessage());
    }
}