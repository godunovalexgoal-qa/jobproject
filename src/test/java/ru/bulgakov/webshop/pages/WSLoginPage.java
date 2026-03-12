package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSLoginPage {
    private final SelenideElement loginButton = $("a.ico-login"),
            pageTitle = $("div.page-title"),
            emailInput = $("input#Email"),
            passwordInput = $("input#Password"),
            rememberMeCheckbox = $("input#RememberMe"),
            buttonLogin = $("input.login-button");
    private final ElementsCollection headerLinks = $$("div.header-links ul li a");

    public WSLoginPage buttonLogin(){
        loginButton.click();

        return this;
    }

    public WSLoginPage checkLoginPageOpened(){
        pageTitle.shouldHave(text("Welcome, Please Sign In!"));

        return this;
    }

    public WSLoginPage enterEmail(String email){
        emailInput.setValue(email);

        return this;
    }

    public WSLoginPage enterPassword(String password){
        passwordInput.setValue(password);

        return this;
    }

    public WSLoginPage checkRememberMe(){
        rememberMeCheckbox.click();

        return this;
    }

    public WSLoginPage submitLogin(){
        buttonLogin.click();

        return this;
    }

    public WSLoginPage cheekUserLoggedIn(String email){
        headerLinks.get(0).shouldHave(text(email));

        return this;
    }
}
