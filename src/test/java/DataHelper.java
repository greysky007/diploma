import com.github.javafaker.Faker;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {

    }

    @Value
    public static class CardInfo {
        String cardNum;
        int month;
        int year;
        String cardholder;
        int cvc;
    }

//    public static CardInfo getCardInfo() {
//        return new CardInfo();
//
//    }

    public static CardInfo getCardInfo() {
        Faker faker = new Faker(new Locale("en"));
        String cardNum = faker.business().creditCardNumber();
        int month = faker.number().numberBetween(0, 12);
        int year = LocalDate.now().getYear();
        String cardholder = faker.name().fullName();
        int cvc = faker.number().numberBetween(100, 999);

        return new CardInfo(cardNum, month, year, cardholder, cvc);
    }
}

