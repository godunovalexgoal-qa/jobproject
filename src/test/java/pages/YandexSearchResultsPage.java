package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class YandexSearchResultsPage {
    private final SelenideElement closeWindow = $(".DistributionButtonClose");

    public YandexSearchResultsPage closeDefaultBrowserSelectWindow() {
        sleep(3000);
        closeWindow.click();

        return this;

    }
    public WelcomePage openLink(String websiteName) {
        $(byText(websiteName)).click();

        return new WelcomePage();

    }
}
