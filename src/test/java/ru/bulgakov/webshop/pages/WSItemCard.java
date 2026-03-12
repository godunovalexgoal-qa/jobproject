package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WSItemCard {
    private final ElementsCollection selectBrutto = $$("dl dd ul li");
    private final SelenideElement amountInput = $(".qty-input"),
            addToCartButton = $("#add-to-cart-button-72"),
            notificationBar = $("div#bar-notification"),
            submitCloseNotificationBar = $("span.close"),
            cartQuantityLabel = $("span.cart-qty"),
            cartIconButton = $("a.ico-cart");

    public String getProductName(){
        return $("[itemprop=name]").getText();

    }

    public String getProductPrice(){
        return $("[itemprop=price]").getText();

    }

    public WSItemCard selectProcessor(int index) {
        // index 0 = slow, 1 = medium, 2 = fast
        selectBrutto.get(index).$$("li input").get(0).click();

        return this;
    }

    public WSItemCard setQuantity(String itemQuantity){
        amountInput.setValue(itemQuantity);

        return this;

    }

    public WSItemCard addToCart(){
        addToCartButton.click();

        return this;

    }

    public WSItemCard checkNotificationBar(){
        notificationBar.shouldBe(visible);

        return this;

    }

    public WSItemCard closeNotificationBar(){
        submitCloseNotificationBar.click();

        return this;

    }

    public WSItemCard checkQuantity(String itemQuantity){
        cartQuantityLabel.shouldHave(text("(" + itemQuantity + ")"));

        return this;

    }

    public WSCartPage goToCart(){
        cartIconButton.click();

        return new WSCartPage();

    }
}
