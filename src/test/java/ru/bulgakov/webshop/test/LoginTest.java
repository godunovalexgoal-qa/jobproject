package ru.bulgakov.webshop.test;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.pages.WSLoginPage;
import ru.bulgakov.webshop.pages.WSRegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_REGISTRATION_URL;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class LoginTest {
    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @BeforeEach
    void beforeAll(){
        password = faker.harryPotter().character() + faker.number().positive();
        email = faker.internet().emailAddress();
        open(WEB_SHOP_REGISTRATION_URL, WSRegistrationPage.class)
                .register(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        email,
                        password)
                .cheekUserLogeedIn(email);

        clearBrowserLocalStorage();
        clearBrowserCookies();
    }

    @Test
    void successLoginTest(){
        open(WEB_SHOP_URL, WSLoginPage.class)
                .buttonLogin()
                .checkLoginPageOpened()
                .enterEmail(email)
                .enterPassword(password)
                .checkRememberMe()
                .submitLogin()
                .cheekUserLoggedIn(email);
    }
}