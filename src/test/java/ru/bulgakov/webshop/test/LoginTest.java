package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.WSLoginPage;
import ru.bulgakov.webshop.pages.WSRegistrationPage;
import ru.bulgakov.webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_REGISTRATION_URL;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class LoginTest extends TestBase {
    private static final Faker faker = new Faker();
    private String email;
    private String password;
    @BeforeEach
    void beforeEach(){
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
    @DisplayName("Тест Log in")
    @Tag("positive")
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

    @ParameterizedTest
    @DisplayName("Проверка формы на невалидные данные")
    @CsvFileSource(resources = "/email.csv")
    @Tag("negative")
    void invalidEmailLoginTest(String email) {
        open(WEB_SHOP_URL, WSLoginPage.class)
                .buttonLogin()
                .checkLoginPageOpened()
                .enterEmail(email)
                .submitLogin()
                .verifyEmailValidation();

    }


}


