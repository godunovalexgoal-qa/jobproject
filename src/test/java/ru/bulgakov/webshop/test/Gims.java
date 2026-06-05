package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.bulgakov.webshop.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;
import static java.time.Duration.ofSeconds;

public class Gims extends TestBase {

    // 🔧 Вызываем один раз перед тестом
    @Test
    @DisplayName("Тест прогона до нужного вопроса")
    @Tags({@Tag("UI"), @Tag("positive")})
    void studyGimsTest() {
        // 🎯 Настройки для стабильной работы в Selenoid
        Configuration.pageLoadStrategy = "eager"; // не ждать полной загрузки ресурсов
        Configuration.browserCapabilities.setCapability("selenoid:options",
                java.util.Map.of(
                        "enableVNC", false,
                        "enableLog", true,
                        "sessionTimeout", "5m"
                ));

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage"); // обход проблем с /dev/shm
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        Configuration.browserCapabilities.merge(options);

        String numberList = System.getProperty("QUESTION_NUMBER", "8");
        System.out.println(">>> Начало теста. Целевой вопрос: " + numberList);

        open("https://digital.mchs.gov.ru/gims/simulator");
        $$(".form-check-label").last().click();
        $("#gims_simulator_form_send").shouldBe(visible, ofSeconds(20)).click();
        sleep(3000);

        $$(".answers-label").first().click();
        clickAnswerButton();
        clickAnswerButton();

        while (!$("[data-name='question-number']").shouldBe(visible).getText().equals(numberList)) {
            sleep(3000);
            $$(".answers-label").first().click();
            clickAnswerButton();
            clickAnswerButton();

            // 🔄 Каждые 100 вопросов — мягкая перезагрузка страницы (сброс памяти)
            try {
                int q = Integer.parseInt($("[data-name='question-number']").getText());
                if (q % 100 == 0 && q > 0) {
                    System.out.println("🔄 Перезагрузка страницы после вопроса " + q);
                    refresh();
                    $("[data-name='question-number']").shouldBe(visible, ofSeconds(15));
                }
            } catch (Exception ignored) {}
        }
        $("[data-name='question-number']").shouldHave(text(numberList));

        System.out.println(">>> Тест успешно завершён!");
    }

    private void clickAnswerButton() {
        int timeout = 15;
        try {
            String qNum = $("[data-name='question-number']").getText();
            int num = Integer.parseInt(qNum);
            if (num > 700) timeout = 30;
        } catch (Exception e) {}

        int retry = 0;
        int maxRetry = 3;

        while (retry < maxRetry) {
            try {
                $(".button-step")
                        .shouldBe(visible, ofSeconds(timeout))
                        .shouldBe(enabled, ofSeconds(timeout))
                        .click();
                return;
            } catch (Exception e) {
                retry++;
                System.out.println("⚠ Попытка клика #" + retry + " не удалась");
                sleep(500);
                if (retry < maxRetry) {
                    $$(".answers-label").first().click();
                }
            }
        }
        try {
            $(".button-step").shouldBe(visible).click();
        } catch (Exception e) {
            System.err.println("✗ Не удалось кликнуть после " + maxRetry + " попыток");
            throw e;
        }
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ЗАВЕРШЕНИЕ ТЕСТА: " + testName);
        System.out.println("=".repeat(60));
        try {
            String currentUrl = WebDriverRunner.url();
            System.out.println("✓ Текущий URL: " + currentUrl);
        } catch (Exception e) {
            System.err.println("✗ Ошибка: " + e.getMessage());
        }
        System.out.println("=".repeat(60) + "\n");
    }
}