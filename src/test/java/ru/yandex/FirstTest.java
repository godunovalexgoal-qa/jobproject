package ru.yandex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.*;

import static com.codeborne.selenide.Selenide.*;

public class FirstTest {
    @Test
    @DisplayName("Проверить, что цена обучения 47 000 рублей")
    @Tag("POSITIVE")
    void mentoringPriceShouldBe47kTest () {
//        Configuration.pageLoadTimeout = 100000;
//        Configuration.timeout = 100000;
//        Configuration.holdBrowserOpen = true;
        YandexSearchPage yandexSearchPage = new YandexSearchPage();
        YandexSearchResultsPage yandexSearchResultsPage = new YandexSearchResultsPage();
        WelcomePage welcomePage = new WelcomePage();
        PaymountPage paymountPage = new PaymountPage();

        open("https://ya.ru/");
        yandexSearchPage
                .search("bulgakov qa")
                .submit();

        sleep(3000);

        yandexSearchResultsPage
                .closeDefaultBrowserSelectWindow()
                .openLink("ivanbulgakovqa.ru");

//        sleep(3000);
        switchTo().window(1);
        sleep(3000);

        welcomePage
                .clickPrice()
                .submitSearch()
                .payDo();

        switchTo().window(2);
        sleep(3000);

        paymountPage
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
                .setSearchQuery("Тестировщик")
                .startSearch()
                .verifyTextOnPage("Тестировщик");

    }

}
