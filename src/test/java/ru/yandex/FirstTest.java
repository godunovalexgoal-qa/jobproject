package com.youtube;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FirstTest {
    @Test
    void mentoringPriceShouldBe47kTest () {
//        Configuration.holdBrowserOpen = true;
        open("https://ya.ru/");
        $("#text").setValue("bulgakov qa");
        $("[type=submit]").click();
        sleep(3000);
        $(".DistributionButtonClose").click();
        $(byText("ivanbulgakovqa.ru")).click();
        sleep(3000);
        switchTo().window(1);
        sleep(3000);
        $$(".t-menu__list li").last().click();
        $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a").click();
        $(byText("Бегу оплачивать")).click();
        switchTo().window(2);
        sleep(3000);
        $$(".styles-module-scss-module__t92_WG__price").last().$("h2").shouldBe(text("₽ 47 000.00"));

    }
    @Test
    void findNewTask () {
//        Configuration.holdBrowserOpen = true;
        open("https://www.wikipedia.org/");
        $(".lang1").click();
        $("[type=search]").setValue("Тестировщик");
        $("#searchButton").click();
        $(".mw-parser-output").shouldBe(text("Тестировщик"));

    }

}
