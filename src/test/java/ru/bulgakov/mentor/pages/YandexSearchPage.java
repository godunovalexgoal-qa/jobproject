package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class YandexSearchPage {
    private final SelenideElement searchInput = $("#text");
    private final SelenideElement submitButton = $("[type=submit]");

    @Step("Ввести название сайта в поисковую строку {query}")
    public YandexSearchPage search(String query) {
        searchInput.setValue(query);

        return this;

    }
    @Step("Нажать кнопку поиск")
    public YandexSearchPage submit() {
        submitButton.click();

        return this;

    }

}
