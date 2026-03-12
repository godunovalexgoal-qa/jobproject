package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class WSCartPage {
    private final SelenideElement itemNameLabel = $("a.product-name"),
            subtotalLabel = $("span.product-subtotal"),
            quantityInput = $("input.qty-input");

    public String getItemName() {
        return itemNameLabel.getText();
    }

    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    public String getQuantity() {
        return quantityInput.getAttribute("value");
    }
}