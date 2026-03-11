package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.pages.*;
import ru.bulgakov.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class CartTest {
    private final AuthSteps authSteps = new AuthSteps();
    @BeforeEach
    void beforeEach(){
       Configuration.holdBrowserOpen = true;
       authSteps.registerNewUser();
    }
    @Test
    void addItemToCartTest(){
        WSItemCard wsItemCard = new WSItemCard();
        WSCatalogPage wsCatalogPage = new WSCatalogPage();
        WSCartPage wsCartPage = new WSCartPage();
        WSWelcomePage wsWelcomePage = new WSWelcomePage();

        open(WEB_SHOP_URL);
        wsWelcomePage
                .hoverBar()
                .selectDesctop();
        wsCatalogPage
                .chooseItem();

        String itemName = wsItemCard.getProductName();
        String itemPrice = wsItemCard.getProductPrice();
        String itemQuantity = "2";
//        int processorIndex = 0; // 0 = slow, 1 = medium, 2 = fast


        wsItemCard
                .selectProcessor(0)
                .setQuantity(itemQuantity)
                .addToCart()
                .checkNotificationBar()
                .closeNotificationBar()
                .checkQuantity(itemQuantity)
                .goToCart();
        wsCartPage
                .checkItemName(itemName)
                .checkSumm(itemQuantity,itemPrice)
                .checkQuantity(itemQuantity);


    }
}
