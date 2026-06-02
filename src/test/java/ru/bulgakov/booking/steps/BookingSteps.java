package ru.bulgakov.booking.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import net.datafaker.Faker;
import ru.bulgakov.booking.BookingApiClient;
import ru.bulgakov.booking.dto.BookingDTO;
import ru.bulgakov.booking.dto.CreateBookingResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class BookingSteps {
    private static final Faker faker = new Faker();
    private final BookingApiClient bookingClient = new BookingApiClient();

    public CreateBookingResponse createBooking() {
        return createBooking(randomBooking());
    }

    public CreateBookingResponse createBooking(BookingDTO booking) {
        Response createResp = bookingClient.createBooking(booking);
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        return createResp.as(CreateBookingResponse.class);
    }
    @Step("Проверить соответствие всех полей в ответе")
    public static void bookingsShouldBeEqual(BookingDTO expected, BookingDTO actual) {
        assertAll(
                () -> assertThat(actual.getFirstname())
                        .as("firstName отличается от ожидаемого")
                        .isEqualTo(expected.getFirstname()),
                () -> assertThat(actual.getLastname())
                        .as("lastName отличается от ожидаемого")
                        .isEqualTo(expected.getLastname()),
                () -> assertThat(actual.getTotalprice())
                        .as("totalPrice отличается от ожидаемого")
                        .isEqualTo(expected.getTotalprice()),
                () -> assertThat(actual.getDepositpaid())
                        .as("depositpaid отличается от ожидаемого")
                        .isEqualTo(expected.getDepositpaid()),
                () -> assertThat(actual.getAdditionalneeds())
                        .as("additionalneeds отличается от ожидаемого")
                        .isEqualTo(expected.getAdditionalneeds()),
                () -> assertThat(actual.getBookingdates())
                        .as("bookingdates равен null")
                        .isNotNull(),
                () -> assertThat(actual.getBookingdates().getCheckin())
                        .as("checkin отличается от ожидаемого")
                        .isEqualTo(expected.getBookingdates().getCheckin()),
                () -> assertThat(actual.getBookingdates().getCheckout())
                        .as("checkout отличается от ожидаемого")
                        .isEqualTo(expected.getBookingdates().getCheckout())
        );
    }

    public static BookingDTO randomBooking() {
        return BookingDTO.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1000, 10000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDTO.BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds(faker.videoGame().title())
                .build();
    }
}
