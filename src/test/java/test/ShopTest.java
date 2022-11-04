package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataHelp;
import data.DataToBase;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
    private String validMonth = DataHelp.generateMonth(0);
    private String validYear = DataHelp.generateYear(0);
    private String validCardholder = DataHelp.generateCardholder();
    private String validCVC = DataHelp.generateCVC();

    @BeforeAll
    public static void setAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {

        Configuration.holdBrowserOpen = false;
        Configuration.headless = false;

        open("http://localhost:8080");

    }

    @AfterEach
    public void del() {
        DataToBase.delInfoFromTables();
    }

    @AfterAll

    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void buy() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, validCVC);
        var page = new MainPage();
        page.ordinaryBuy().purchaseSuccess(info);
        Assertions.assertEquals(DataToBase.APPROVED_STATUS, DataToBase.selection(DataToBase.DEBIT));

    }

    //тесты без кредита
    //тесты positive================
    @Test

    public void shouldTestValidData() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, validCVC);
        var page = new MainPage();
        page.ordinaryBuy().purchaseSuccess(info);
        Assertions.assertEquals(DataToBase.APPROVED_STATUS, DataToBase.selection(DataToBase.DEBIT));

    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithoutCreditDeclinedCard() {
        var info = Card.setCardInfo(DataHelp.DECLINED_CARD, validMonth, validYear, validCardholder, validCVC);
        var page = new MainPage();
        page.ordinaryBuy().purchaseError(info);
        Assertions.assertEquals(DataToBase.DECLINE_STATUS, DataToBase.selection(DataToBase.DEBIT));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCardholderRus() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "Иван Иванов", validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);
    }

    @Test

    public void shouldTestCardholderDigits() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "123", validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderSymbols() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "?!", validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderEmpty() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, " ", validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestYearExpiredCard() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(-1), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getExpiredYear()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestYearIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, "1", validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectYearFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestYearIncorrectExpiration() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(6), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationYear()
                .shouldBe(visible);

    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestMonthIncorrectExpiration() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, DataHelp.generateMonth(-1), validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonthIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "1", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonthEmpty() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonth_00() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "00", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonth_13() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "13", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getMonthField()
                .getValue();
        Assertions.assertEquals("", result);
    }


    //    //тесты поля cvc===========
    @Test
    public void shouldTestCVCIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "20");
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectCVCFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCVCInputLetters() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "qwe");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSymbols() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "!");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSpace() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, " ");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputFourDigits() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "1234");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("123", result);
    }


    //Тесты с кредитом//
//positive=======
    @Test

    public void shouldTestValidDataWithCredit() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchaseSuccess(info);
        Assertions.assertEquals(DataToBase.APPROVED_STATUS, DataToBase.selection(DataToBase.CREDIT));
    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithCreditDeclinedCard() {
        var info = Card.setCardInfo(DataHelp.DECLINED_CARD, validMonth, validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchaseError(info);
        Assertions.assertEquals(DataToBase.DECLINE_STATUS, DataToBase.selection(DataToBase.CREDIT));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCreditCardholderRus() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "Иван Иванов", validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderDigits() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "123", validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderSymbols() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, "?!", validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderEmpty() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, " ", validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubEmptyField()
                .shouldBe(visible);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestCreditYearExpiredCard() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(-1), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getExpiredYear()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditYearIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, "1", validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectYearFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditYearIncorrectExpiration() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(6), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationYear()
                .shouldBe(visible);

    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestCreditMonthIncorrectExpiration() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, DataHelp.generateMonth(-1), validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonthIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "1", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }


    @Test

    public void shouldTestCreditMonthEmpty() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonth_00() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "00", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonth_13() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, "13", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getMonthField()
                .getValue();

        Assertions.assertEquals("", result);
    }

    //    //тесты поля cvc===========
    @Test
    public void shouldTestCreditCVCIncorrectFormat() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "20");
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectCVCFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCVCInputLetters() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "qwe");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSymbols() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "!");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSpace() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, " ");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputFourDigits() {
        var info = Card.setCardInfo(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "1234");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("123", result);
    }
}



