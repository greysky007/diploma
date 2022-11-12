package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.Card;
import data.DataHelp;
import data.DataToBase;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;


import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
    private String validMonth = DataHelp.generateMonth(0);
    private String validYear = DataHelp.generateYear(0);
    private String validCardholder = DataHelp.generateCardholder();
    private String invalidCardholderName = DataHelp.generateInvalidCardholder();
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


    //тесты без кредита
    //тесты positive================
    @Test

    public void shouldTestValidData() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, validCVC);
        var page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .successMessage();
        Assertions.assertEquals(DataToBase.APPROVED_STATUS, DataToBase.selection(DataToBase.DEBIT));

    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithoutCreditDeclinedCard() {
        Card info = new Card(DataHelp.DECLINED_CARD, validMonth, validYear, validCardholder, validCVC);
        var page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .errorMessage();
        Assertions.assertEquals(DataToBase.DECLINE_STATUS, DataToBase.selection(DataToBase.DEBIT));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCardholderRus() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, invalidCardholderName, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);

    }

    @Test

    public void shouldTestCardholderDigits() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, DataHelp.generateDigit(), validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCardholderSymbols() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, DataHelp.generateSymbol(), validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCardholderEmpty() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, " ", validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestYearExpiredCard() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(-1), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getExpiredYear(DataHelp.EXPIRED_MSG);


    }

    @Test

    public void shouldTestYearIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, "1", validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectYearFormat(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestYearIncorrectExpiration() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(6), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationYear(DataHelp.INVALID_EXPIRATION_MSG);

    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestMonthIncorrectExpiration() {
        Card info = new Card(DataHelp.APPROVED_CARD, DataHelp.generateMonth(-1), validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationMonth(DataHelp.INVALID_EXPIRATION_MSG);


    }

    @Test

    public void shouldTestMonthIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, "1", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectMonthFormat(DataHelp.WRONG_FORMAT_MSG);

    }

    @Test

    public void shouldTestMonthEmpty() {
        Card info = new Card(DataHelp.APPROVED_CARD, "", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectMonthFormat(DataHelp.WRONG_FORMAT_MSG);

    }

    @Test

    public void shouldTestMonth_00() {
        Card info = new Card(DataHelp.APPROVED_CARD, "00", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectExpirationMonth(DataHelp.INVALID_EXPIRATION_MSG);


    }

    @Test

    public void shouldTestMonth_13() {
        Card info = new Card(DataHelp.APPROVED_CARD, "13", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getMonthField();

        Assertions.assertEquals("", result);
    }


    //    //тесты поля cvc===========
    @Test
    public void shouldTestCVCIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "20");
        MainPage page = new MainPage();
        page.ordinaryBuy()
                .purchase(info)
                .getIncorrectCVCFormat(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCVCInputLetters() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, DataHelp.generateLetter());
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSymbols() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, DataHelp.generateSymbol());
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputSpace() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, " ");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCVCInputFourDigits() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "1234");
        MainPage page = new MainPage();
        String result = page
                .ordinaryBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("123", result);
    }


    //Тесты с кредитом//
//positive=======
    @Test

    public void shouldTestValidDataWithCredit() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .successMessage();
        Assertions.assertEquals(DataToBase.APPROVED_STATUS, DataToBase.selection(DataToBase.CREDIT));
    }

    //Негативные тесты===========
    @Test

    public void shouldTestWithCreditDeclinedCard() {
        Card info = new Card(DataHelp.DECLINED_CARD, validMonth, validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .errorMessage();
        Assertions.assertEquals(DataToBase.DECLINE_STATUS, DataToBase.selection(DataToBase.CREDIT));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCreditCardholderRus() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, invalidCardholderName, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditCardholderDigits() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, DataHelp.generateDigit(), validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditCardholderSymbols() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, DataHelp.generateSymbol(), validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubErrorInput(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditCardholderEmpty() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, " ", validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getCardholderSubEmptyField(DataHelp.EMPTY_FIELD_MSG);


    }

    //тесты поля Год===============
    @Test

    public void shouldTestCreditYearExpiredCard() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(-1), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getExpiredYear(DataHelp.EXPIRED_MSG);


    }

    @Test

    public void shouldTestCreditYearIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, "1", validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectYearFormat(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditYearIncorrectExpiration() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, DataHelp.generateYear(6), validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationYear(DataHelp.INVALID_EXPIRATION_MSG);


    }

    //    //тесты поля месяц===========
    @Test

    public void shouldTestCreditMonthIncorrectExpiration() {
        Card info = new Card(DataHelp.APPROVED_CARD, DataHelp.generateMonth(-1), validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationMonth(DataHelp.INVALID_EXPIRATION_MSG);


    }

    @Test

    public void shouldTestCreditMonthIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, "1", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectMonthFormat(DataHelp.WRONG_FORMAT_MSG);


    }


    @Test

    public void shouldTestCreditMonthEmpty() {
        Card info = new Card(DataHelp.APPROVED_CARD, "", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectMonthFormat(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditMonth_00() {
        Card info = new Card(DataHelp.APPROVED_CARD, "00", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectExpirationMonth(DataHelp.INVALID_EXPIRATION_MSG);


    }

    @Test

    public void shouldTestCreditMonth_13() {
        Card info = new Card(DataHelp.APPROVED_CARD, "13", validYear, validCardholder, validCVC);
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getMonthField();


        Assertions.assertEquals("", result);
    }

    //    //тесты поля cvc===========
    @Test
    public void shouldTestCreditCVCIncorrectFormat() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "20");
        MainPage page = new MainPage();
        page.creditBuy()
                .purchase(info)
                .getIncorrectCVCFormat(DataHelp.WRONG_FORMAT_MSG);


    }

    @Test

    public void shouldTestCreditCVCInputLetters() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, DataHelp.generateLetter());
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSymbols() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, DataHelp.generateSymbol());
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputSpace() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, " ");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("", result);
    }

    @Test

    public void shouldTestCreditCVCInputFourDigits() {
        Card info = new Card(DataHelp.APPROVED_CARD, validMonth, validYear, validCardholder, "1234");
        MainPage page = new MainPage();
        String result = page
                .creditBuy()
                .purchase(info)
                .getCvcCode();

        Assertions.assertEquals("123", result);
    }
}



