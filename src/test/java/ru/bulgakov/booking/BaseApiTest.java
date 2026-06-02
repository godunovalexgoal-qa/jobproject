package ru.bulgakov.booking;

import org.junit.jupiter.api.BeforeAll;

import static ru.bulgakov.booking.util.RestAssuredSpec.setUpRestAssured;

public class BaseApiTest {

    @BeforeAll
    static void setUp() {
        setUpRestAssured();
    }
}
