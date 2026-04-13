package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.WSWelcomePage;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class RegistrationTest extends TestBase {
    private static final Faker faker = new Faker();

    @Test
    @DisplayName("Тест регистрации")
//    @Tag("positive")
    @Severity(SeverityLevel.CRITICAL)
    @Epic("ВебШоп")
    @Feature("Тестовый сайт интернет магазина")
    @Story("Тестирование регистрации")
    @Issue("Bag-125")
    @Description("Проверка возможности пользователя осуществить регистрацию")
    @Owner("alex_god")
    @Link(name = "TASK-126", url = "https://...")
    void registrationTest() {
//        Configuration.holdBrowserOpen = true;
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
