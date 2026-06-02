package ru.bulgakov.booking;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.bulgakov.booking.config.BookingConfig;
import ru.bulgakov.booking.dto.AuthResponse;
import ru.bulgakov.booking.dto.BookingDTO;
import ru.bulgakov.booking.dto.BookingId;
import ru.bulgakov.booking.dto.CreateBookingResponse;
import ru.bulgakov.booking.steps.BookingSteps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static ru.bulgakov.booking.config.BookingApiConfig.getBookingConfig;
import static ru.bulgakov.booking.steps.BookingSteps.randomBooking;

public class BookingTest extends BaseApiTest {
    private static final Faker faker = new Faker();
    private static final BookingConfig CFG = getBookingConfig();

    private final BookingApiClient bookingClient = new BookingApiClient();
    private final BookingSteps bookingSteps = new BookingSteps();

    @BeforeAll
    static void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.filters(new AllureRestAssured());
    }

    @Test
    void authTest() {
        Response resp = bookingClient.auth(CFG.username(), CFG.password());

        assertThat(resp.statusCode()).isEqualTo(200);
        assertThat(resp.as(AuthResponse.class).getToken()).isNotNull();
    }

    @Test
    void createBookingTest() {
        BookingDTO bookingDTO = randomBooking();
        Response resp = bookingClient.createBooking(bookingDTO);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        CreateBookingResponse createBookingResp = resp.as(CreateBookingResponse.class);
        assertThat(createBookingResp.getBookingid()).isNotNull();
        BookingSteps.bookingsShouldBeEqual(bookingDTO, createBookingResp.getBooking());
    }

    @Test
    void getBookingTest() {
        CreateBookingResponse booking = bookingSteps.createBooking();

        Response resp = bookingClient.getBooking(booking.getBookingid());
        assertThat(resp.getStatusCode()).isEqualTo(200);

        BookingSteps.bookingsShouldBeEqual(booking.getBooking(), resp.as(BookingDTO.class));
    }

    @Test
    void updateBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        BookingDTO bookingDTO = randomBooking();
        Response resp = bookingClient.updateBooking(bookingDTO, bookingId);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDto = resp.as(BookingDTO.class);
        BookingSteps.bookingsShouldBeEqual(bookingDTO, updatedBookingDto);
    }

    @Test
    void partialUpdateBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        BookingDTO bookingDTO = new BookingDTO(faker.football().players(), faker.number().numberBetween(10001, 12000), "2026-02-01");

        Response resp = bookingClient.partialUpdateBooking(bookingDTO, bookingId);
        assertThat(resp.getStatusCode()).isEqualTo(200);

        BookingDTO updatedBookingDto = resp.as(BookingDTO.class);
        assertThat(bookingDTO.getFirstname()).isEqualTo(updatedBookingDto.getFirstname());
        assertThat(bookingDTO.getTotalprice()).isEqualTo(updatedBookingDto.getTotalprice());
        assertThat(bookingDTO.getBookingdates().getCheckin()).isEqualTo(updatedBookingDto.getBookingdates().getCheckin());
    }

    @Test
    void deleteBookingTest() {
        Integer bookingId = bookingSteps.createBooking().getBookingid();

        Response deleteResp = bookingClient.deleteBooking(bookingId);
        assertThat(deleteResp.getStatusCode()).isEqualTo(201);

        Response getResp = bookingClient.getBooking(bookingId);
        assertThat(getResp.getStatusCode()).isEqualTo(404);
    }

    @Test
    void getBookingsByLastName() {
        int bookingQuantity = 5;
        String lastName = faker.name().lastName();

        List<Integer> bookingIds = new ArrayList<>();
        for (int i = 0; i < bookingQuantity; i++) {
            BookingDTO bookingDTO = randomBooking();
            bookingDTO.setLastname(lastName);

            Integer bookingId = bookingSteps.createBooking(bookingDTO).getBookingid();
            bookingIds.add(bookingId);
        }

        Response resp = bookingClient.getBookings(Map.of("lastname", lastName));
        assertThat(resp.getStatusCode()).isEqualTo(200);

        List<BookingId> bookings = resp.as(new TypeRef<List<BookingId>>() {});
        assertThat(bookings)
                .hasSize(bookingQuantity)
                .doesNotContainNull()
                .doesNotHaveDuplicates()
                .extracting(booking -> booking.bookingid())
                .containsExactlyInAnyOrderElementsOf(bookingIds);
    }
}