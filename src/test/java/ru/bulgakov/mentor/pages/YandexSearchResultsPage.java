package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class YandexSearchResultsPage {
    private final SelenideElement closeWindow = $(".DistributionButtonClose");

    @Step("Закрыть окно выбора браузера по умолчанию")
    public YandexSearchResultsPage closeDefaultBrowserSelectWindow() {
        closeWindow.click();

        return this;

    }
    @Step("Найти и нажать на название сайта {websiteName}")
    public YandexSearchResultsPage openLink(String websiteName) {
        $(byText(websiteName)).click();

        return this;

    }
}
