package ru.bulgakov.mentor.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WikiPage {
    private final SelenideElement searchInputField = $(".lang1");
    private final SelenideElement searchInput = $("[type=search]");
    private final SelenideElement button = $("#searchButton");
    private final SelenideElement scText = $(".mw-parser-output");

    @Step("Выбрать русскую версию")
    public WikiPage submit(){
        searchInputField.click();

        return this;
    }
    @Step("Ввести в поисковую стороку значение {query}")
    public WikiPage setSearchQuery(String query){
        searchInput.setValue(query);;

        return this;
    }
    @Step("Нажать на кнопку поиск")
    public WikiPage startSearch(){
        button.click();

        return this;
    }
    @Step("Проверить наличение на странице искомых данных {sText}")
    public WikiPage verifyTextOnPage(String sText){
        scText.shouldBe(text(sText));

        return this;
    }
}
