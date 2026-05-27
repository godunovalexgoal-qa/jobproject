package ru.bulgakov.booking.dto;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.bulgakov.booking.dto.BookingDTO.BookingDates;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingNegativeTest {
    private static final String BOOKING_URL = "https://restful-booker.herokuapp.com";

    @BeforeAll
    static void setUp() {
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    static Stream<Arguments> invalidAuthCases() {
        return Stream.of(
                Arguments.of("admin", "wrongPassword", "Неверный пароль"),
                Arguments.of("invalidUser", "password123", "Неверный логин"),
                Arguments.of("admin", "", "Пустой пароль"),
                Arguments.of("", "password123", "Пустой логин"),
                Arguments.of(null, null, "Пустое body {}")
        );
    }

    static Stream<Arguments> invalidBookingCases() {
        return Stream.of(
                Arguments.of(
                        createBookingWithoutFirstname(),
                        "Отсутствует обязательное поле firstname"
                ),
                Arguments.of(
                        createBookingWithoutLastname(),
                        "Отсутствует обязательное поле lastname"
                ),
                Arguments.of(
                        createBookingWithNegativePrice(),
                        "Отрицательная цена totalprice"
                ),
                Arguments.of(
                        createBookingWithInvalidDate(),
                        "Неверный формат даты checkin"
                ),
                Arguments.of(
                        createBookingWithCheckoutBeforeCheckin(),
                        "Дата выезда раньше даты заезда"
                )
        );
    }

    @ParameterizedTest(name = "{index} => {2}")
    @MethodSource("invalidAuthCases")
    @DisplayName("Негативные тесты авторизации")
    void shouldRejectInvalidAuthData(String username, String password, String description) {
        AuthRequest requestBody = AuthRequest.builder()
                .username(username)
                .password(password)
                .build();

        String reason = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().path("reason");

        assertThat(reason)
                .as("Сценарий '%s': ожидается ошибка авторизации", description)
                .isEqualTo("Bad credentials");
    }

    @Test
    @DisplayName("Сценарий 6: Отправка запроса без body")
    void shouldNotReturnTokenWhenBodyIsMissing() {
        String reason = given()
                .contentType(ContentType.JSON)
                .when()
                .post(BOOKING_URL + "/auth")
                .then()
                .statusCode(200)
                .extract().path("reason");

        assertThat(reason).isEqualTo("Bad credentials");
    }

    @ParameterizedTest(name = "{index} => {1}")
    @MethodSource("invalidBookingCases")
    @DisplayName("Негативные тесты создания бронирования")
    void shouldRejectInvalidBookingData(BookingDTO booking, String scenarioName) {
        var response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        String responseBody = response.asString();
        int statusCode = response.getStatusCode();

        Integer bookingId = extractBookingIdSafely(responseBody);

        if (scenarioName.contains("Отсутствует обязательное поле")) {
            assertThat(statusCode)
                    .as("Сценарий '%s': при отсутствии обязательных полей ожидается 500", scenarioName)
                    .isEqualTo(500);

            assertThat(responseBody)
                    .as("Сценарий '%s': ответ должен содержать текст ошибки", scenarioName)
                    .contains("Internal Server Error");
            return;
        }

        if (scenarioName.contains("Отрицательная цена")) {
            assertThat(statusCode)
                    .as("Сценарий '%s': API вернул статус", scenarioName)
                    .isEqualTo(200);

            assertThat(bookingId)
                    .as("Сценарий '%s': API создал бронирование с отрицательной ценой (БАГ)", scenarioName)
                    .isNotNull();
        }

        if (scenarioName.contains("Неверный формат даты")) {
            assertThat(statusCode)
                    .as("Сценарий '%s': API вернул статус", scenarioName)
                    .isEqualTo(200);

            assertThat(bookingId)
                    .as("Сценарий '%s': API создал бронирование с невалидной датой (БАГ)", scenarioName)
                    .isNotNull();

            assertThat(responseBody)
                    .as("Сценарий '%s': API должен был исказить невалидную дату", scenarioName)
                    .contains("NaN");
        }

        if (scenarioName.contains("Дата выезда раньше даты заезда")) {
            assertThat(statusCode)
                    .as("Сценарий '%s': API вернул статус", scenarioName)
                    .isEqualTo(200);

            assertThat(bookingId)
                    .as("Сценарий '%s': API создал бронирование с перепутанными датами (БАГ)", scenarioName)
                    .isNotNull();
        }
    }

    @Test
    @DisplayName("Сценарий 6: Пустое body {}")
    void shouldRejectEmptyBookingBody() {
        var response = given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post(BOOKING_URL + "/booking")
                .then()
                .extract().response();

        String responseBody = response.asString();
        int statusCode = response.getStatusCode();

        assertThat(statusCode).isEqualTo(500);
        assertThat(responseBody).contains("Internal Server Error");
    }

    public static BookingDTO createBookingRequest() {
        BookingDTO booking = new BookingDTO();
        booking.setFirstname("Barack");
        booking.setLastname("Obama");
        booking.setTotalprice(1000);
        booking.setDepositpaid(false);
        booking.setBookingdates(new BookingDates("2026-01-01", "2027-01-01"));
        booking.setAdditionalneeds("newspaper");
        return booking;
    }

    private static BookingDTO createBookingWithoutFirstname() {
        BookingDTO booking = createBookingRequest();
        booking.setFirstname(null);
        return booking;
    }

    private static BookingDTO createBookingWithoutLastname() {
        BookingDTO booking = createBookingRequest();
        booking.setLastname(null);
        return booking;
    }

    private static BookingDTO createBookingWithNegativePrice() {
        BookingDTO booking = createBookingRequest();
        booking.setTotalprice(-500);
        return booking;
    }

    private static BookingDTO createBookingWithInvalidDate() {
        BookingDTO booking = createBookingRequest();
        booking.setBookingdates(new BookingDates("не-дата", "2027-01-01"));
        return booking;
    }

    private static BookingDTO createBookingWithCheckoutBeforeCheckin() {
        BookingDTO booking = createBookingRequest();
        booking.setBookingdates(new BookingDates("2027-01-01", "2026-01-01"));
        return booking;
    }

    private static Integer extractBookingIdSafely(String responseBody) {
        try {
            return new JsonPath(responseBody).getInt("bookingid");
        } catch (Exception e) {
            return null;
        }
    }
}