package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WSCartPage {
    private final SelenideElement checkNameItem = $("a.product-name");
    private final SelenideElement summCheck = $("span.product-subtotal");
    private final SelenideElement quantityCheck = $("input.qty-input");


    public WSCartPage checkItemName(String itemName){
        checkNameItem.shouldHave(text(itemName));

        return this;

    }
    public WSCartPage checkSumm(String itemQuantity, String itemPrice){
        summCheck.shouldHave(text(String.valueOf(
                Float.parseFloat(itemPrice) * Float.parseFloat(itemQuantity))));

        return this;

    }
    public WSCartPage checkQuantity(String itemQuantity){
        String itemQuantityInCart = quantityCheck.getAttribute("value");
        assertEquals(itemQuantity, itemQuantityInCart);

        return this;

    }
}
