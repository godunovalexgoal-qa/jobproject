package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class YandexSearchResultsPage {
    private final SelenideElement closeWindow = $(".DistributionButtonClose");

    public YandexSearchResultsPage closeDefaultBrowserSelectWindow() {
        closeWindow.click();

        return this;

    }
    public YandexSearchResultsPage openLink(String websiteName) {
        $(byText(websiteName)).click();

        return this;

    }
}
