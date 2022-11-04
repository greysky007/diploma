package data;

import lombok.Value;

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


}

