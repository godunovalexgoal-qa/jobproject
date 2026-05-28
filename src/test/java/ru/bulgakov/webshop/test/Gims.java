package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import ru.bulgakov.webshop.TestBase;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class Gims extends TestBase {

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ЗАВЕРШЕНИЕ ТЕСТА: " + testName);
        System.out.println("=".repeat(60));

        try {
            // Всегда выводим URL
            String currentUrl = WebDriverRunner.url();
            System.out.println("✓ Текущий URL: " + currentUrl);

            // Делаем скриншот (даже если тест прошёл - для отладки)
            String screenshot = Selenide.screenshot("test_" + testName.replaceAll("[^a-zA-Z0-9]", "_"));
            System.out.println("✓ Скриншот сохранён: " + screenshot);

        } catch (Exception e) {
            System.err.println("✗ Ошибка при получении информации: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(60) + "\n");
    }

    @Test
    @DisplayName("Тест прогона до нужного вопроса")
    @Tags({@Tag("UI"), @Tag("positive")})
    void studyGimsTest() {
//        Configuration.browser = "chrome"; // выбор браузера
//        Configuration.holdBrowserOpen = true;
        String numberList = System.getProperty("QUESTION_NUMBER", "8");
//        String numberList = "100";

        System.out.println(">>> Начало теста. Целевой вопрос: " + numberList);

        open("https://digital.mchs.gov.ru/gims/simulator");
        $$(".form-check-label").last().click();
        $("#gims_simulator_form_send").shouldBe(visible).click();
        sleep(3000);
        $$(".answers-label").first().click();
        $(".button-step").shouldBe(visible).shouldBe(enabled, ofSeconds(10)).click();
        $(".button-step").shouldBe(visible).shouldBe(enabled, ofSeconds(10)).click();
        while (!$("[data-name='question-number']").shouldBe(visible).getText().equals(numberList)) {
            sleep(3000);
            $$(".answers-label").first().click();
            $(".button-step").shouldBe(visible).shouldBe(enabled, ofSeconds(10)).click();
            $(".button-step").shouldBe(visible).shouldBe(enabled, ofSeconds(10)).click();
        }
        $("[data-name='question-number']").shouldHave(text(numberList));

        System.out.println(">>> Тест успешно завершён!");
    }
}