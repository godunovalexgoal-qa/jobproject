package ru.bulgakov.webshop.test;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class Gims extends TestBase {
    @Test
    @DisplayName("Тест прогона до нужного вопроса")
    @Tags({@Tag("UI"), @Tag("positive")})
    void studyGimsTest() {
//        Configuration.browser = "chrome"; // выбор браузера
//        Configuration.holdBrowserOpen = true;
        String numberList = System.getProperty("QUESTION_NUMBER", "8");
//        String numberList = "100";

        open("https://digital.mchs.gov.ru/gims/simulator");
        $$(".form-check-label").last().click();
        $("#gims_simulator_form_send").shouldBe(visible).click();
        sleep(3000);
        $$(".answers-label").first().click();
        $(".button-step").shouldBe(visible).click();
        $(".button-step").shouldBe(visible).click();
        while (!$("[data-name='question-number']").shouldBe(visible).getText().equals(numberList)) {
            sleep(3000);
            $$(".answers-label").first().click();
            $(".button-step").shouldBe(visible).click();
            $(".button-step").shouldBe(visible).click();
        }
        $("[data-name='question-number']").shouldHave(text(numberList));

        System.out.println("Текущий URL: " + WebDriverRunner.url());

    }
}