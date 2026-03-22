package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WelcomePage {
    private final ElementsCollection price = $$(".t-menu__list li");
    private final SelenideElement searchButton = $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a");
    private final SelenideElement pay = $(byText("Бегу оплачивать"));

    @Step("Нажать на кнопку стоимость в хедере")
    public WelcomePage clickPrice(){
        price.last().click();

        return this;

    }
    @Step("Нажать на кнопку хочу вкатиться")
    public WelcomePage submitSearch(){
        searchButton.click();

        return this;

    }
    @Step("Нажать на кнопку бегу оплачивать")
    public WelcomePage payDo(){
        pay.click();;

        return this;

    }




}
