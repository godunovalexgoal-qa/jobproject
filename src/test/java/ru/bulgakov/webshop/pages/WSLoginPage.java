package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSLoginPage {
    private final SelenideElement loginButton =  $("a.ico-login");
    private final SelenideElement pageTitle = $("div.page-title");
    private final SelenideElement emailInput = $("input#Email");
    private final SelenideElement passwordInput = $("input#Password");
    private final SelenideElement rememberMeCheckbox = $("input#RememberMe");
    private final SelenideElement buttonLogin = $("input.login-button");
    private final ElementsCollection headerLinks = $$("div.header-links ul li a");

    @Step("Нажать кнопку Log in")
    public WSLoginPage buttonLogin(){
        loginButton.click();

        return this;

    }
    @Step("Проверить, что открылась страница авторизации {text}")
    public WSLoginPage checkLoginPageOpened(){
        pageTitle.shouldHave(text("Welcome, Please Sign In!"));

        return this;

    }
    @Step("Ввести электронную почту {email}")
    public WSLoginPage enterEmail(String email){
        emailInput.setValue(email);

        return this;

    }
    @Step("Ввести пароль {password}")
    public WSLoginPage enterPassword(String password){
        passwordInput.setValue(password);

        return this;

    }
    @Step("Нажать кнопку запомнить меня")
    public WSLoginPage checkRememberMe(){
        rememberMeCheckbox.click();

        return this;

    }
    @Step("Нажать кнопку войти")
    public WSLoginPage submitLogin(){
        buttonLogin.click();

        return this;

    }
    @Step("Проверить проверить валидность электронной почты")
    public WSLoginPage verifyEmailValidation(){
        $("span.field-validation-error").shouldBe(visible);

        return this;

    }
    @Step("Проверить наличие логина пользователя в хедере {email}")
    public WSLoginPage cheekUserLoggedIn(String email){
        headerLinks.get(0).shouldHave(text(email));

        return this;

    }
}
