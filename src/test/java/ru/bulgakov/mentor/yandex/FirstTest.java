package ru.bulgakov.mentor.yandex;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ru.bulgakov.mentor.pages.*;
import ru.bulgakov.webshop.TestBase;

import static com.codeborne.selenide.Selenide.*;

public class FirstTest extends TestBase {
    @Test
    @DisplayName("Проверить, что цена обучения 47 000 рублей")
    @Tags({@Tag("UI"), @Tag("positive")})
    @Severity(SeverityLevel.TRIVIAL)
    @Epic("Яндекс")
    @Feature("Булгаков")
    @Story("Хочу вкатиться Булгакой QA")
    @Issue("Bag-123")
    @Description("Поиск страницы ментора для установления стоимости курса")
    @Owner("alex_god")
    @Link(name = "TASK-121", url = "https://...")
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
    @Tags({@Tag("UI"), @Tag("negative")})
    @Severity(SeverityLevel.MINOR)
    @Epic("Вики")
    @Feature("Поиск на википедии")
    @Story("Поиск информации о тестировщике")
    @Issue("Bag-124")
    @Description("Поиск страницы с подробной информацией о тестировщике")
    @Owner("alex_god")
    @Link(name = "TASK-122", url = "https://...")
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
