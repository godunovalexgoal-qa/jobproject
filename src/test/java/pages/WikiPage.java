package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiPage {
    private final SelenideElement searchInputField = $(".lang1");
    private final SelenideElement searchInput = $("[type=search]");
    private final SelenideElement button = $("#searchButton");
    private final SelenideElement scText = $(".mw-parser-output");

    public WikiPage submit(){
        searchInputField.click();

        return this;
    }
    public WikiPage setSearchQuery(String query){
        searchInput.setValue(query);;

        return this;
    }
    public WikiPage startSearch(){
        button.click();

        return this;
    }
    public WikiPage verifyTextOnPage(String sText){
        scText.shouldBe(text(sText));

        return this;
    }
}
