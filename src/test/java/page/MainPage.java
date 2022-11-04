package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private SelenideElement buttonBuy = $x("//span[text() = 'Купить']");
    private SelenideElement buttonWithCredit = $x("//span[text() = 'Купить в кредит']");

    public OrdinaryPurchasePage ordinaryBuy() {
        buttonBuy.click();
        return new OrdinaryPurchasePage();
    }

    public CreditPurchasePage creditBuy() {
        buttonWithCredit.click();
        return new CreditPurchasePage();
    }
}
