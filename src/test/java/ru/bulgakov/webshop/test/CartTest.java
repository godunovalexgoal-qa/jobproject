package ru.bulgakov.webshop.test;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.bulgakov.webshop.TestBase;
import ru.bulgakov.webshop.pages.*;
import ru.bulgakov.webshop.steps.AuthSteps;

import static com.codeborne.selenide.Selenide.*;
import static ru.bulgakov.webshop.config.Config.WEB_SHOP_URL;

public class CartTest extends TestBase {
    private final AuthSteps authSteps = new AuthSteps();

    @BeforeEach
    void beforeEach(){
//       Configuration.holdBrowserOpen = true;
       authSteps.registerNewUser();
    }

    @Test
    @DisplayName("Тест полного цикла покупки")
//    @Tags({@Tag("UI"), @Tag("positive")})
    @Severity(SeverityLevel.NORMAL)
    @Epic("ВебШоп")
    @Feature("Тестовый сайт интернет магазина")
    @Story("Тестирование пользовательского пути (Покупка товара)")
    @Issue("Bag-125")
    @Description("Проверка возможности пользователя осуществить покупку товара в магазине")
    @Owner("alex_god")
    @Link(name = "TASK-123", url = "https://...")
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
