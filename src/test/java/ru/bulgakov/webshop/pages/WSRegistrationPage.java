package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSRegistrationPage {
    private final ElementsCollection headerLinks = $$("div.header-links ul li a");
    private final SelenideElement maleGenderRadio = $("input#gender-male"),
            pageTitle = $("div.page-title"),
            firstNameInput = $("input#FirstName"),
            lastNameInput = $("input#LastName"),
            emailInput = $("input#Email"),
            passwordInput = $("input#Password"),
            passwordConfirmInput = $("input#ConfirmPassword"),
            submitRegisterButton = $("input#register-button"),
            resultText = $("div.result");

    private final String REGISTRATION_COMPLETED_MESSAGE = "Your registration completed",
                         REGISTER_OPENED_TEXT = "Register";

    public WSRegistrationPage register(String firstName,String lastName,String email,String password){
        selectMaleGender()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .enterEmail(email)
                .enterPassword(password)
                .enterPasswordConfirm(password)
                .submitRegistration()
                .cheekRegistrationCompleted();

        return this;
    }

    public WSRegistrationPage verifyRegistrationOpened(){
        pageTitle.shouldHave(text(REGISTER_OPENED_TEXT));

        return this;
    }

    public WSRegistrationPage selectMaleGender(){
        maleGenderRadio.click();

        return this;
    }

    public WSRegistrationPage enterFirstName(String firstName){
        firstNameInput.setValue(firstName);

        return this;
    }

    public WSRegistrationPage enterLastName(String lastName){
        lastNameInput.setValue(lastName);

        return this;
    }

    public WSRegistrationPage enterEmail(String email){
        emailInput.setValue(email);

        return this;
    }

    public WSRegistrationPage enterPassword(String password){
        passwordInput.setValue(password);;

        return this;
    }

    public WSRegistrationPage enterPasswordConfirm(String password){
        passwordConfirmInput.setValue(password);;

        return this;
    }

    public WSRegistrationPage submitRegistration(){
        submitRegisterButton.click();

        return this;
    }

    public WSRegistrationPage cheekRegistrationCompleted(){
        resultText.shouldHave(text(REGISTRATION_COMPLETED_MESSAGE));

        return this;
    }

    public WSRegistrationPage cheekUserLogeedIn(String email){
        headerLinks.get(0).shouldHave(text(email));

        return this;
    }
}
