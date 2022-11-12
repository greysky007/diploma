package data;

import lombok.Value;

@Value
public class Card {
    private String cardNum;
    private String month;
    private String year;
    private String cardholder;
    private String cvc;

}

