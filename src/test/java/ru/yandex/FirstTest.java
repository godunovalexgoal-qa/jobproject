package ru.yandex;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.WikiPage;
import pages.YandexSearchPage;
import pages.YandexSearchResultsPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FirstTest {
    @Test
    @DisplayName("Проверить, что цена обучения 47 000 рублей")
    @Tag("POSITIVE")
    void mentoringPriceShouldBe47kTest () {
//        Configuration.pageLoadTimeout = 100000;
//        Configuration.timeout = 100000;
//        Configuration.holdBrowserOpen = true;
        open("https://ya.ru/", YandexSearchPage.class)
                .search("bulgakov qa")
                .submit()
                .closeDefaultBrowserSelectWindow()
                .openLink("ivanbulgakovqa.ru")
                .clickPrice()
                .justDoIt()
                .payDo()
                .CheckPrice47K("₽ 47 000.00");

    }
    @Test
    @DisplayName("Учебный поиск на странице")
    @Tag("POSITIVE")
    void findNewTask () {
        //        Configuration.pageLoadTimeout = 100000;
        //        Configuration.timeout = 100000;
        //        Configuration.holdBrowserOpen = true;
        open("https://www.wikipedia.org/", WikiPage.class)
                .submit()
                .findText("Тестировщик")
                .startSearch()
                .scanPage("Тестировщик");

    }

}
