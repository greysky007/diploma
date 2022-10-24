import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class SelectionPage {
    private SelenideElement buttonBuy = $x("//span[text() = 'Купить']");
    private SelenideElement buttonWithCredit = $x("//span[text() = 'Купить в кредит']");

}
