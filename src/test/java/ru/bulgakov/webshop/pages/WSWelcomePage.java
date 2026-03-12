package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSWelcomePage {
    private final SelenideElement registerButton = $("a.ico-register"),
            selectDecst = $(byText("Desktops"));
    private final ElementsCollection hoveredBar = $$("ul.top-menu li a");

    public WSRegistrationPage openRegistration(){
        registerButton.click();

        return new WSRegistrationPage();
    }

    public WSWelcomePage hoverBar(){
        hoveredBar.get(1).hover();

        return this;
    }

    public WSCatalogPage selectDesctop(){
        selectDecst.click();

        return new WSCatalogPage();
    }
}
