package ru.bulgakov.webshop.test;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.bulgakov.webshop.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;
import static java.time.Duration.ofSeconds;

public class Gims extends TestBase {

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
            System.err.println("✗ Ошибка при получении информации: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(60) + "\n");
    }

    @Test
    @DisplayName("Тест прогона до нужного вопроса")
    @Tags({@Tag("UI"), @Tag("positive")})
    void studyGimsTest() {
        String numberList = System.getProperty("QUESTION_NUMBER", "8");

        System.out.println(">>> Начало теста. Целевой вопрос: " + numberList);

        open("https://digital.mchs.gov.ru/gims/simulator");
        $$(".form-check-label").last().click();
        $("#gims_simulator_form_send").shouldBe(visible).click();
        sleep(3000);

        $$(".answers-label").first().click();
        clickAnswerButton();
        clickAnswerButton();

        while (!$("[data-name='question-number']").shouldBe(visible).getText().equals(numberList)) {
            sleep(3000);
            $$(".answers-label").first().click();
            clickAnswerButton();
            clickAnswerButton();
        }
        $("[data-name='question-number']").shouldHave(text(numberList));

        System.out.println(">>> Тест успешно завершён!");
    }

    /**
     * 🔑 1. Адаптивные таймауты + 2. Retry + 3. Проверка выбора ответа
     */
    private void clickAnswerButton() {
        // Определяем номер текущего вопроса для адаптивных таймаутов
        int timeout = 15; // базовый таймаут
        try {
            String qNum = $("[data-name='question-number']").getText();
            int num = Integer.parseInt(qNum);
            // 🔑 1. Адаптивные таймауты: для поздних вопросов увеличиваем
            if (num > 700) timeout = 30;
        } catch (Exception e) {
            // Если не удалось получить номер — используем базовый таймаут
        }

        // 🔑 2. Retry логика: пробуем до 3 раз, если кнопка не активируется
        int retry = 0;
        int maxRetry = 3;

        while (retry < maxRetry) {
            try {
                // 🔑 3. Проверка: ждём enabled перед кликом
                $(".button-step")
                        .shouldBe(visible, ofSeconds(timeout))
                        .shouldBe(enabled, ofSeconds(timeout))
                        .click();

                // Если клик прошёл без ошибки — выходим из цикла retry
                return;

            } catch (Exception e) {
                retry++;
                System.out.println("⚠ Попытка клика #" + retry + " не удалась, пробую ещё...");
                sleep(500);

                // Перед повторной попыткой ещё раз кликаем по ответу (на случай, если сбросился)
                if (retry < maxRetry) {
                    $$(".answers-label").first().click();
                }
            }
        }

        // Если все попытки исчерпаны — пробую кликнуть в любом случае (последняя надежда)
        try {
            $(".button-step").shouldBe(visible).click();
        } catch (Exception e) {
            System.err.println("✗ Не удалось кликнуть по кнопке после " + maxRetry + " попыток");
            throw e;
        }
    }
}