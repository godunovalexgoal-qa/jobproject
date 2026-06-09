package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PaymountPage {
    private final ElementsCollection finalPrice = $$(".styles-module-scss-module__t92_WG__price");

    @Step("Проверить итоговую стоимость курса {fPrice}")
    public PaymountPage CheckPrice47K(String fPrice){
        finalPrice.last().$("h2").shouldBe(text(fPrice));

        return this;
    }

}
