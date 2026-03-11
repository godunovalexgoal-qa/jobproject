package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class YandexSearchPage {
    private final SelenideElement searchInput = $("#text");
    private final SelenideElement submitButton = $("[type=submit]");

    public YandexSearchPage search(String query) {
        searchInput.setValue(query);

        return this;

    }
    public YandexSearchPage submit() {
        submitButton.click();

        return this;

    }

}
