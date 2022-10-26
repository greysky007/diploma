import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SelectionPage {
    private SelenideElement buttonBuy = $x("//span[text() = 'Купить']");
    private SelenideElement buttonWithCredit = $x("//span[text() = 'Купить в кредит']");
    private SelenideElement numberField = $x("//span[text() = 'Номер карты']/..//input");
    private SelenideElement monthField = $x("//span[text() = 'Месяц']/..//input");
    private SelenideElement yearField = $x("//span[text() = 'Год']/..//input");
    private SelenideElement cardHolder = $x("//span[text() = 'Владелец']/..//input");
    private SelenideElement cvcCode = $x("//span[text() = 'CVC/CVV']/..//input");
    private SelenideElement buttonNext = $x("//span[text()='Продолжить']/../..");
    private ElementsCollection success =
            $$x("//div[contains(@class, 'notification_status')]");
    private SelenideElement suc = $x("//div[contains(@class, 'notification_status_ok')]");
    private SelenideElement error = $x("//div[contains(@class, 'notification_status_error')]");

    public void buyWithoutCredit(DataHelper.CardInfo info) {
        buttonBuy.click();
        numberField.setValue(info.getCardNum());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cardHolder.setValue(info.getCardholder());
        cvcCode.setValue(info.getCvc());
        buttonNext.click();
        suc.shouldBe(visible, Duration.ofSeconds(15));
        error.shouldBe(hidden, Duration.ofSeconds(0, 5));
        //success.exclude(hidden).last().shouldBe(visible, Duration.ofSeconds(10));

    }

    public void buyWithCredit(DataHelper.CardInfo info) {
        buttonWithCredit.click();
        numberField.setValue(info.getCardNum());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cardHolder.setValue(info.getCardholder());
        cvcCode.setValue(info.getCvc());
        buttonNext.click();
        suc.shouldBe(visible, Duration.ofSeconds(15));
        error.shouldBe(hidden, Duration.ofSeconds(0, 5));

    }


}


