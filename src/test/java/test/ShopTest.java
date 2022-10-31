package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataHelp;
import data.DataToBase;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.SelectionPage;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {

    final String approvedCard = "4444444444444441";
    final String declinedCard = "4444444444444442";

    final String approvedStatus = "APPROVED";
    final String declineStatus = "DECLINED";
    final String debit = "payment_entity";
    final String credit = "credit_request_entity";
    private DataHelp valid = new DataHelp();
    private String validMonth = valid.generateMonth(0);
    private String validYear = valid.generateYear(0);
    private String validCardholder = valid.generateCardholder();
    private String validCVC = valid.generateCVC();

    @BeforeAll
    public static void setAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {

        Configuration.holdBrowserOpen = false;
        Configuration.headless = true;

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

    //тесты без кредита
    //тесты positive================
    @Test

    public void shouldTestValidData() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, validCVC);

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page.successBuy();
        Assertions.assertEquals(approvedStatus, DataToBase.selection(debit));
    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithoutCreditDeclinedCard() {
        var info = Card.setCardInfo(declinedCard, validMonth, validYear, validCardholder, validCVC);

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page.errorBuy();
        Assertions.assertEquals(declineStatus, DataToBase.selection(debit));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCardholderRus() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "Иван Иванов", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderDigits() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "123", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderSymbols() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "?!", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderEmpty() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, " ", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getCardholderSubEmptyField()
                .shouldBe(visible);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestYearExpiredCard() {
        var info = Card.setCardInfo(approvedCard, validMonth, valid.generateYear(-1), validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getExpiredYear()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestYearIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, validMonth, "1", validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectYearFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestYearIncorrectExpiration() {
        var info = Card.setCardInfo(approvedCard, validMonth, valid.generateYear(6), validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectExpirationYear()
                .shouldBe(visible);

    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestMonthIncorrectExpiration() {
        var info = Card.setCardInfo(approvedCard, valid.generateMonth(-1), validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonthIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, "1", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonthEmpty() {
        var info = Card.setCardInfo(approvedCard, "", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }
    @Test

    public void shouldTestMonth_00() {
        var info = Card.setCardInfo(approvedCard, "00", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestMonth_13() {
        var info = Card.setCardInfo(approvedCard, "13", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        String result = page
                .getMonthField()
                .getValue();
        Assertions.assertEquals("", result);
    }

    //    //тесты поля cvc===========
    @Test
    public void shouldTestCVCIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "20");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        page
                .getIncorrectCVCFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCVCInputLetters() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "qwe");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSymbols() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "!");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSpace() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, " ");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputFourDigits() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "1234");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("123", result);
    }


    //Тесты с кредитом//
//positive=======
    @Test

    public void shouldTestValidDataWithCredit() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page.successBuy();
        Assertions.assertEquals(approvedStatus, DataToBase.selection(credit));
    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithCreditDeclinedCard() {
        var info = Card.setCardInfo(declinedCard, validMonth, validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page.errorBuy();
        Assertions.assertEquals(declineStatus, DataToBase.selection(credit));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCreditCardholderRus() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "Иван Иванов", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderDigits() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "123", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderSymbols() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, "?!", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCardholderEmpty() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, " ", validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getCardholderSubEmptyField()
                .shouldBe(visible);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestCreditYearExpiredCard() {
        var info = Card.setCardInfo(approvedCard, validMonth, valid.generateYear(-1), validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getExpiredYear()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditYearIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, validMonth, "1", validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectYearFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditYearIncorrectExpiration() {
        var info = Card.setCardInfo(approvedCard, validMonth, valid.generateYear(6), validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectExpirationYear()
                .shouldBe(visible);

    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestCreditMonthIncorrectExpiration() {
        var info = Card.setCardInfo(approvedCard, valid.generateMonth(-1), validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonthIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, "1", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }


    @Test

    public void shouldTestCreditMonthEmpty() {
        var info = Card.setCardInfo(approvedCard, "", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonth_00() {
        var info = Card.setCardInfo(approvedCard, "00", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditMonth_13() {
        var info = Card.setCardInfo(approvedCard, "13", validYear, validCardholder, validCVC);
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        String result = page
                .getMonthField()
                .getValue();
        Assertions.assertEquals("", result);
    }

    //    //тесты поля cvc===========
    @Test
    public void shouldTestCreditCVCIncorrectFormat() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "20");
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        page
                .getIncorrectCVCFormat()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCreditCVCInputLetters() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "qwe");
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSymbols() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "!");
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSpace() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, " ");
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputFourDigits() {
        var info = Card.setCardInfo(approvedCard, validMonth, validYear, validCardholder, "1234");
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(info);
        String result = page
                .getCvcCode()
                .getValue();
        Assertions.assertEquals("123", result);
    }


}
