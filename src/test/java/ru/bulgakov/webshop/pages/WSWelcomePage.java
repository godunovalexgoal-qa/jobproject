package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSWelcomePage {
    private final SelenideElement registerButton = $("a.ico-register");
    private final ElementsCollection hoveredBar = $$("ul.top-menu li a");
    private final SelenideElement selectDecst = $(byText("Desktops"));

    @Step("Открыть страницу регистрации")
    public WSRegistrationPage openRegistration(){
        registerButton.click();

        return new WSRegistrationPage();

    }
    @Step("Навести на хедер")
    public WSWelcomePage hoverBar(){
        hoveredBar.get(1).hover();

        return this;

    }
    @Step("Выбрать категорию")
    public WSWelcomePage selectDesctop(){
        selectDecst.click();

        return this;

    }

}
