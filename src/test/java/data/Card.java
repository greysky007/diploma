package data;

import com.github.javafaker.Faker;
import lombok.Value;
import page.SelectionPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Value
public class Card {
    private String cardNum;
    private String month;
    private String year;
    private String cardholder;
    private String cvc;

    public static Card setCardInfo(String cardNum, String month, String year, String cardholder, String cvc) {

        return new Card(cardNum, month, year, cardholder, cvc);

    }
    public static SelectionPage qqq(){
        return new SelectionPage();
    }

}

