import com.codeborne.selenide.Configuration;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.codeborne.selenide.Selenide.open;

public class ShopTest {
    String approvedCard = "4444444444444441";
    String declinedCard = "4444444444444442";

    String approvedCardStatus = "APPROVED";
    String declineCardStatus = "DECLINE";
    String debit = "payment_entity";
    String credit = "credit_request_entity";

    @BeforeEach
    public void delTable() {
        DataToBase.delInfoFromTables();
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");

    }


    //Позитивные тесты
    @Test
    public void shouldTestWithCreditApprovedCard() {

        new SelectionPage().buyWithCredit(DataHelper.getCardInfo(approvedCard));
        Assertions.assertEquals(approvedCardStatus, DataToBase.selection(credit));

    }

    @Test
    public void shouldTestWithCreditDeclinedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithCredit(DataHelper.getCardInfo(declinedCard));

    }

    @Test
    public void shouldTestWithoutCreditApprovedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo(approvedCard));
        Assertions.assertEquals(approvedCardStatus, DataToBase.selection(debit));

    }

    @Test
    public void shouldTestWithoutCreditDeclinedCard() {
        SelectionPage page = new SelectionPage();
        page.buyWithoutCredit(DataHelper.getCardInfo(declinedCard));

    }

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
