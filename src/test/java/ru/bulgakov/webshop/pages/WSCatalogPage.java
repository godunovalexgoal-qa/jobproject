package ru.bulgakov.webshop.pages;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class WSCatalogPage {
    private final ElementsCollection selectItem = $$("div.product-grid div");

    public WSItemCard chooseItem(int index){
        selectItem.get(index).click();

        return new WSItemCard();
    }
}