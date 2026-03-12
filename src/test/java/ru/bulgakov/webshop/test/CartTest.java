package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bulgakov.webshop.pages.*;
import ru.bulgakov.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.*;
import static java.util.Locale.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class CartTest {
  private final AuthSteps authSteps = new AuthSteps();

  @BeforeEach
  void beforeEach() {
    Configuration.browserSize = "1920x1080";

    authSteps.registerNewUser();
  }

  @Test
  void addItemToCartTest() {
    WSItemCard wsItemCard = open(WEB_SHOP_URL, WSWelcomePage.class)
        .hoverBar()
        .selectDesctop()
        .chooseItem(0);

    String itemName = wsItemCard.getProductName();
    String itemPrice = wsItemCard.getProductPrice();
    String itemQuantity = "2";
    int processorIndex = 0; // 0 = slow, 1 = medium, 2 = fast

    WSCartPage cartPage = wsItemCard
        .selectProcessor(processorIndex)
        .setQuantity(itemQuantity)
        .addToCart()
        .checkNotificationBar()
        .closeNotificationBar()
        .checkQuantity(itemQuantity)
        .goToCart();

    String expectedSum = String.format(
        US,
        "%.2f",
        Float.parseFloat(itemPrice) * Float.parseFloat(itemQuantity)
    );

    assertEquals(itemName, cartPage.getItemName());
    assertEquals(expectedSum, cartPage.getSubtotal());
    assertEquals(itemQuantity, cartPage.getQuantity());
  }
}
