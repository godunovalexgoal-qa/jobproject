package ru.bulgakov.webshop.test;

import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.WSLoginPage;
import ru.bulgakov.webshop.pages.WSRegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.*;

public class LoginTest extends TestBase {
    private static final Faker faker = new Faker();
    private String email;
    private String password;

    @Nested
    public class PositiveTests {
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
        @Tags({@Tag("UI"), @Tag("positive")})
        @Severity(SeverityLevel.CRITICAL)
        @Epic("ВебШоп")
        @Feature("Тестовый сайт интернет магазина")
        @Story("Тестирование авторизации пользователя")
        @Issue("Bag-125")
        @Description("Проверка возможности пользователя осуществить авторизацию")
        @Owner("alex_god")
        @Link(name = "TASK-124", url = "https://...")
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

    @ParameterizedTest(name = "Авторизация с невалидным email: {0}")
    @CsvFileSource(resources = "/email.csv")
    @Tags({@Tag("UI"), @Tag("negative")})
    @Severity(SeverityLevel.BLOCKER)
    @Epic("ВебШоп")
    @Feature("Тестовый сайт интернет магазина")
    @Story("Тестирование валидации email")
    @Issue("Bag-125")
    @Description("Проверка валации email при осуществлении авторизации пользователя")
    @Owner("alex_god")
    @Link(name = "TASK-125", url = "https://...")
    void invalidEmailLoginTest(String email) {
        open(WEB_SHOP_LOGIN_URL, WSLoginPage.class)
                .enterEmail(email)
                .submitLogin()
                .verifyEmailValidation();
    }


}


