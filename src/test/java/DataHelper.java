import com.github.javafaker.Faker;
import lombok.Value;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {

    }

    @Value
    public static class CardInfo {
        String cardNum;
        String month;
        String year;
        String cardholder;
        String cvc;
    }

    //    public static CardInfo getCardInfo() {
//        return new CardInfo();
//
//    }
    public static String generateDate() {

        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }
    public static String generateMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static CardInfo getCardInfo() {
        Faker faker = new Faker(new Locale("en"));
        String cardNum = faker.business().creditCardNumber();
        String month = generateMonth();
        String year = generateDate();


        String cardholder = faker.name().fullName();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));

        return new CardInfo(cardNum, month, year, cardholder, cvc);
    }

    public static CardInfo getCardInfo(String card) {
        Faker faker = new Faker(new Locale("en"));
        String cardNum = card;
        String month = generateMonth();
        String year = generateDate();


        String cardholder = faker.name().fullName();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));

        return new CardInfo(cardNum, month, year, cardholder, cvc);
    }
    public static CardInfo getCardInfo(String card, String month) {
        Faker faker = new Faker(new Locale("en"));
        String cardNum = card;
        String monthNum = month;
        String year = generateDate();


        String cardholder = faker.name().fullName();
        String cvc = String.valueOf(faker.number().numberBetween(100, 999));

        return new CardInfo(cardNum, month, year, cardholder, cvc);
    }
}

