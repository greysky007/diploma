import com.github.javafaker.Faker;
import lombok.Value;

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

class DataHelp {
    public String generateYear(int years) {

        String year = LocalDate.now().plusYears(years).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public String generateMonth(int months) {
        String month = LocalDate.now().plusMonths(months).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public String generateCardholder() {
        Faker faker = new Faker(new Locale("en"));
        String cardholder = faker.name().fullName();
        return cardholder;
    }

    public String generateCVC() {
        Faker faker = new Faker(new Locale("en"));
        String cvc = String.valueOf(faker.number().digits(3));
        return cvc;
    }
}