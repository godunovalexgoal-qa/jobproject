package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class WelcomePage {
    private final ElementsCollection price = $$(".t-menu__list li");
    private final SelenideElement just = $x("/html/body/div[1]/div[42]/div/div/div[32]/div/a");
    private final SelenideElement pay = $(byText("Бегу оплачивать"));

    public WelcomePage clickPrice(){
        sleep(3000);
        switchTo().window(1);
        sleep(3000);
        price.last().click();

        return this;

    }

    public WelcomePage justDoIt(){
        just.click();

        return this;

    }

    public PaymountPage payDo(){
        pay.click();;

        return new PaymountPage();

    }




}
