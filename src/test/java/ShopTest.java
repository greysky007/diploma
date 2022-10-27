import com.codeborne.selenide.Configuration;

import org.junit.Assert;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Selenide.open;

public class ShopTest {

    final String approvedCard = "4444444444444441";
    final String declinedCard = "4444444444444442";

    final String approvedStatus = "APPROVED";
    final String declineStatus = "DECLINE";
    final String debit = "payment_entity";
    final String credit = "credit_request_entity";

    @BeforeEach
    public void setUp() {

        Configuration.holdBrowserOpen = false;

        open("http://localhost:8080");


    }

    @AfterEach
    public void del() {
        DataToBase.delInfoFromTables();
    }


    //Позитивные тесты с кредитом
    @Test
    public void shouldTestWithCreditApprovedCard() {

        new SelectionPage().buyWithCredit(DataHelper.getCardInfo(approvedCard));

        Assertions.assertEquals(approvedStatus, DataToBase.selection(credit));

    }

    @Test
    public void shouldTestWithCreditDeclinedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(DataHelper.getCardInfo(declinedCard));
        Assertions.assertEquals(declineStatus, DataToBase.selection(credit));
    }
    //Позитивные тесты дебитовые
    @Test
    public void shouldTestWithoutCreditApprovedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo(approvedCard));
        Assertions.assertEquals(approvedStatus, DataToBase.selection(debit));

    }

    @Test
    public void shouldTestWithoutCreditDeclinedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo(declinedCard));
        Assertions.assertEquals(declinedCard, DataToBase.selection(debit));
    }

    /////
    @Test
    public void shouldTestBorderCredit() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo(declinedCard));
    }

////////
    @Test
    public void shouldTestWithoutCredit() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo());

    }

    @Test
    public void shouldTestWithCredit() {
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(DataHelper.getCardInfo());
    }


}
