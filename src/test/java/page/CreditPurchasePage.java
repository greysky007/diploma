package page;

import com.codeborne.selenide.SelenideElement;
import data.Card;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

@Getter
public class CreditPurchasePage {
    private SelenideElement numberField = $x("//span[text() = 'Номер карты']/..//input");
    private SelenideElement monthField = $x("//span[text() = 'Месяц']/..//input");
    private SelenideElement yearField = $x("//span[text() = 'Год']/..//input");
    private SelenideElement cardHolder = $x("//span[text() = 'Владелец']/..//input");
    private SelenideElement cvcCode = $x("//span[text() = 'CVC/CVV']/..//input");
    private SelenideElement buttonNext = $x("//span[text()='Продолжить']/../..");

    private SelenideElement cardholderSubEmptyField =
            $x("//span[@class = 'input__top' and text() = 'Владелец']/..//span[text()='Поле обязательно для заполнения']");
    private SelenideElement cardholderSubErrorInput =
            $x("//span[@class = 'input__top' and text() = 'Владелец']/..//span[text()='Неверный формат имени']");


    private SelenideElement incorrectExpirationMonth =
            $x("//span[@class = 'input__top' and text() = 'Месяц']/..//span[text()='Неверно указан срок действия карты']");
    private SelenideElement incorrectMonthFormat =
            $x("//span[@class = 'input__top' and text() = 'Месяц']/..//span[text()='Неверный формат']");

    private SelenideElement incorrectExpirationYear =
            $x("//span[@class = 'input__top' and text() = 'Год']/..//span[text()='Неверно указан срок действия карты']");
    private SelenideElement incorrectYearFormat =
            $x("//span[@class = 'input__top' and text() = 'Год']/..//span[text()='Неверный формат']");
    private SelenideElement expiredYear =
            $x("//span[@class = 'input__top' and text() = 'Год']/..//span[text()='Истёк срок действия карты']");


    private SelenideElement incorrectCVCFormat =
            $x("//span[@class = 'input__top' and text() = 'CVC/CVV']/..//span[text()='Неверный формат']");


    private SelenideElement successMessage = $x("//div[contains(@class, 'notification_status_ok')]");
    private SelenideElement errorMessage = $x("//div[contains(@class, 'notification_status_error')]");

    public OrdinaryPurchasePage purchase(Card info) {
        numberField.setValue(info.getCardNum());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cardHolder.setValue(info.getCardholder());
        cvcCode.setValue(info.getCvc());
        buttonNext.click();
        return new OrdinaryPurchasePage();
    }

    public SelenideElement purchaseSuccess(Card info) {
        numberField.setValue(info.getCardNum());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cardHolder.setValue(info.getCardholder());
        cvcCode.setValue(info.getCvc());
        buttonNext.click();
        return successMessage.shouldBe(visible, Duration.ofSeconds(15));

    }

    public SelenideElement purchaseError(Card info) {
        numberField.setValue(info.getCardNum());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        cardHolder.setValue(info.getCardholder());
        cvcCode.setValue(info.getCvc());
        buttonNext.click();
        return errorMessage.shouldBe(visible, Duration.ofSeconds(15));

    }
}

