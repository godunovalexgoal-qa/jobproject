package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class RegistrationTest {
    private static final Faker faker = new Faker();

    @Test
    void registrationTest() {
        Configuration.holdBrowserOpen = true;

        String password = faker.harryPotter().character() + faker.number().positive();
        String email = faker.internet().emailAddress();

        open(WEB_SHOP_URL, WSWelcomePage.class)
                .openRegistration()
                .verifyRegistrationOpened()
                .selectMaleGender()
                .enterFirstName(faker.name().firstName())
                .enterLastName(faker.name().lastName())
                .enterEmail(email)
                .enterPassword(password)
                .enterPasswordConfirm(password)
                .submitRegistration()
                .cheekRegistrationCompleted()
                .cheekUserLogeedIn(email);
    }
}