package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WelcomePage {
    private final ElementsCollection price = $$(".t-menu__list li");
    private final SelenideElement searchButton = $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a");
    private final SelenideElement pay = $(byText("Бегу оплачивать"));

    public WelcomePage clickPrice(){
        price.last().click();

        return this;

    }

    public WelcomePage submitSearch(){
        searchButton.click();

        return this;

    }

    public WelcomePage payDo(){
        pay.click();;

        return this;

    }




}
