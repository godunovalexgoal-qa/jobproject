package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSRegistrationPage {
    private final SelenideElement maleGenderRadio = $("input#gender-male");
    private final SelenideElement pageTitle = $("div.page-title");
    private final SelenideElement firstNameInput = $("input#FirstName");
    private final SelenideElement lastNameInput = $("input#LastName");
    private final SelenideElement emailInput = $("input#Email");
    private final SelenideElement passwordInput = $("input#Password");
    private final SelenideElement passwordConfirmInput = $("input#ConfirmPassword");
    private final SelenideElement submitRegisterButton = $("input#register-button");
    private final SelenideElement resultText = $("div.result");
    private final ElementsCollection headerLinks = $$("div.header-links ul li a");


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
        pageTitle.shouldHave(text("Register"));

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
        resultText.shouldHave(text("Your registration completed"));

        return this;

    }
    public WSRegistrationPage cheekUserLogeedIn(String email){
        headerLinks.get(0).shouldHave(text(email));

        return this;

    }
}
