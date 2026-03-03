package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiPage {
    private final SelenideElement getSeach = $(".lang1");
    private final SelenideElement findValue = $("[type=search]");
    private final SelenideElement button = $("#searchButton");
    private final SelenideElement scText = $(".mw-parser-output");

    public WikiPage submit(){
        getSeach.click();

        return this;
    }
    public WikiPage findText(String query){
        findValue.setValue(query);;

        return this;
    }
    public WikiPage startSearch(){
        button.click();

        return this;
    }
    public WikiPage scanPage(String sText){
        scText.shouldBe(text(sText));

        return this;
    }
}
