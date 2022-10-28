import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

public class ShopTest {

    final String approvedCard = "4444444444444441";
    final String declinedCard = "4444444444444442";

    final String approvedStatus = "APPROVED";
    final String declineStatus = "DECLINED";
    final String debit = "payment_entity";
    final String credit = "credit_request_entity";
    Card fake = new Card();
    String validMonth = fake.generateMonth(0);
    String validYear = fake.generateYear(0);
    String validCardholder = fake.generateCardholder();
    String validCVC = fake.generateCVC();

    @BeforeEach
    public void setUp() {

        Configuration.holdBrowserOpen = true;

        open("http://localhost:8080");


    }

    @AfterEach
    public void del() {
        DataToBase.delInfoFromTables();
    }


    //Позитивные тесты с кредитом
//    @Test
//    public void shouldTestWithCreditApprovedCard() {
//
//        new SelectionPage().buyWithCredit(DataHelper.getCardInfo(approvedCard));
//
//        Assertions.assertEquals(approvedStatus, DataToBase.selection(credit));
//
//    }

//    @Test
//    public void shouldTestWithCreditDeclinedCard() {
//        SelectionPage page = new SelectionPage();
//        page.buyWithCredit(DataHelper.getCardInfo(declinedCard));
//        Assertions.assertEquals(declineStatus, DataToBase.selection(credit));
//    }
    //Позитивные тесты дебитовые
//    @Test
//    public void shouldTestWithoutCreditApprovedCard() {
//        SelectionPage page = new SelectionPage();
//        page.buyWithoutCredit(DataHelper.getCardInfo(approvedCard));
//        Assertions.assertEquals(approvedStatus, DataToBase.selection(debit));
//
//    }

    @Test

    public void shouldTestWithoutCreditDeclinedCard() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(declinedCard, validMonth, "22", "ivan", "123");
        page.successBuy();
        Assertions.assertEquals(declineStatus, DataToBase.selection(debit));
    }

    //тесты positive================
    @Test

    public void shouldTestValidData() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, validYear, validCardholder, validCVC);
        page.successBuy();
        Assertions.assertEquals(approvedStatus, DataToBase.selection(debit));
    }

    //тесты полей имени================
    @Test

    public void shouldTestCardholderRus() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, validYear, "Иван Иванов", validCVC);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderDigits() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, validYear, "123", validCVC);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderSymbols() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, validYear, "?!", validCVC);
        page
                .getCardholderSubErrorInput()
                .shouldBe(visible);

    }

    @Test

    public void shouldTestCardholderEmpty() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, validYear, " ", validCVC);
        page
                .getCardholderSubEmptyField()
                .shouldBe(visible);

    }

    //тесты поля Год===============
    @Test

    public void shouldTestYearExpiredCard() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, fake.generateYear(-1), validCardholder, validCVC);
        page
                .getExpiredYear()
                .shouldBe(visible);

    }
    @Test

    public void shouldTestYearIncorrectFormat() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, "1", validCardholder, validCVC);
        page
                .getIncorrectYearFormat()
                .shouldBe(visible);

    }
    @Test

    public void shouldTestYearIncorrectExpiration() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, validMonth, fake.generateYear(6), validCardholder, validCVC);
        page
                .getIncorrectExpirationYear()
                .shouldBe(visible);

    }
    //тесты поля месяц===========
    @Test

    public void shouldTestMonthIncorrectExpiration() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, fake.generateMonth(-1), validYear, validCardholder, validCVC);
        page
                .getIncorrectExpirationMonth()
                .shouldBe(visible);

    }
    @Test

    public void shouldTestMonthIncorrectFormat() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, "1", validYear, validCardholder, validCVC);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }
    @Test

    public void shouldTestMonthEmpty() {

        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(approvedCard, "", validYear, validCardholder, validCVC);
        page
                .getIncorrectMonthFormat()
                .shouldBe(visible);

    }

    /////
//    @Test
//    public void shouldTestBorderCredit() {
//        SelectionPage page = new SelectionPage();
//        DataHelper.CardInfo = new DataHelper.CardInfo("dsfs","dsfs","dsfs","dsfs","dsfs");
//        page.buyWithoutCredit(DataHelper.getCardInfo(declinedCard));
//    }

////////
//    @Test
//    public void shouldTestWithoutCredit() {
//        SelectionPage page = new SelectionPage();
//        page.buyWithoutCredit(DataHelper.getCardInfo());
//
//    }

//    @Test
//    public void shouldTestWithCredit() {
//        SelectionPage page = new SelectionPage();
//        page.buyWithCredit(DataHelper.getCardInfo());
//    }


}
