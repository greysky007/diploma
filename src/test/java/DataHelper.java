import com.github.javafaker.Faker;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static DataHelper.CardInfo CardInfo;

    public DataHelper() {

    }

    @Getter
    @Setter

    public class CardInfo {
        private String cardNum;
        private String month;
        private String year;
        private String cardholder;
        private String cvc;


        public void generateYear() {

            setYear(LocalDate.now().format(DateTimeFormatter.ofPattern("yy")));
        }

        public void generateMonth() {
            setMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));

        }

        public void generateCardholder() {
            Faker faker = new Faker(new Locale("en"));
            String cardholder = faker.name().fullName();
            setCardholder(cardholder);

        }

        public void generateCVC() {
            Faker faker = new Faker(new Locale("en"));
            String cvc = String.valueOf(faker.number().digits(3));
            setCvc(cvc);
        }
    }

    //    public static CardInfo getCardInfo() {
//        return new CardInfo();
//
//    }


//    public static CardInfo getCardInfo() {
//        Faker faker = new Faker(new Locale("en"));
//        String cardNum = faker.business().creditCardNumber();
//        String month = Card.generateMonth();
//        String year = Card.generateYear();
//        String cardholder = faker.name().fullName();
//        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
//
//        return new CardInfo(cardNum, month, year, cardholder, cvc);
//    }

//    public static CardInfo getCardInfo(String card) {
//        Faker faker = new Faker(new Locale("en"));
//        String cardNum = card;
//        String month = Card.generateMonth();
//        String year = Card.generateYear();
//
//
//        String cardholder = faker.name().fullName();
//        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
//
//        return new CardInfo(cardNum, month, year, cardholder, cvc);
//    }

//    public static CardInfo getCardInfo(String card, String month) {
//        Faker faker = new Faker(new Locale("en"));
//        String cardNum = card;
//        String monthNum = month;
//        String year = generateYear();
//
//
//        String cardholder = faker.name().fullName();
//        String cvc = String.valueOf(faker.number().numberBetween(100, 999));
//
//        return new CardInfo(cardNum, month, year, cardholder, cvc);
//    }


//    @Value
//    public static class CardNumber {
//        private String number;
//
//
//        public static CardNumber getApprovedCardNumber() {
//            return new CardNumber("4444 4444 4444 4441");
//        }
//
//        public static CardNumber getDeclinedCardNumber() {
//            return new CardNumber("4444 4444 4444 4442");
//        }
//
//
//    }
}


