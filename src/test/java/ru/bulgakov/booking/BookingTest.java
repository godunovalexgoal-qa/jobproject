package ru.bulgakov.booking;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.bulgakov.booking.dto.AuthRequest;
import ru.bulgakov.booking.dto.AuthResponse;
import ru.bulgakov.booking.dto.BookingDTO;
import ru.bulgakov.booking.dto.BookingDTO.BookingDates;
import ru.bulgakov.booking.dto.CreateBookingResponse;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.bulgakov.booking.dto.BookingNegativeTest.createBookingRequest;

public class BookingTest {
    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";
    private static final Faker faker = new Faker();
    private static final String USER = "admin", PASSWORD = "password123";

    private final BookingApiClient bookingClient = new BookingApiClient();

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    void authTest() {
        Response resp = bookingClient.auth(USER, PASSWORD);

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    @Test
    void createBookingTest() {
        Response resp = bookingClient.createBooking(buildBookingRequest());
        assertThat(resp.getStatusCode()).isEqualTo(200);

        CreateBookingResponse createBookingResponse = resp.as(CreateBookingResponse.class);
        assertThat(createBookingResponse.getBookingid()).isNotNull();
        assertThat(createBookingResponse.getBooking().getTotalprice()).isEqualTo(1000);
        assertThat(createBookingResponse.getBooking().getBookingdates().getCheckin()).isEqualTo("2026-01-01");
        assertThat(createBookingResponse.getBooking().getDepositpaid()).isFalse();
    }

    @Test
    void updateBookingTest() {
        Response createResp = bookingClient.createBooking(buildBookingRequest());
        assertThat(createResp.getStatusCode()).isEqualTo(200);

        BookingDTO bookingDTO = buildBookingRequest();
        Response updateResponse = bookingClient
                .updateBooking(bookingDTO, createResp.as(CreateBookingResponse.class).getBookingid());
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDto = updateResponse.as(BookingDTO.class);
        assertThat(updatedBookingDto.equals(bookingDTO)).isTrue();
    }

    private static BookingDTO buildBookingRequest() {
        return BookingDTO.builder()
                .firstname(faker.name().firstName())
                .lastname(faker.name().lastName())
                .totalprice(faker.number().numberBetween(1000, 10000))
                .depositpaid(faker.bool().bool())
                .bookingdates(BookingDates.builder()
                        .checkin("2026-01-01")
                        .checkout("2027-01-01")
                        .build())
                .additionalneeds(faker.videoGame().title())
                .build();
    }
}