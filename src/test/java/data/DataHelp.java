package data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@UtilityClass
public class DataHelp {

    public final String APPROVED_CARD = "4444444444444441";
    public final String DECLINED_CARD = "4444444444444442";
    public final String WRONG_FORMAT_MSG = "Неверный формат";
    public final String INVALID_EXPIRATION_MSG = "Неверно указан срок действия карты";
    public final String EMPTY_FIELD_MSG = "Поле обязательно для заполнения";
    public final String EXPIRED_MSG = "Истёк срок действия карты";

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

    public String generateInvalidCardholder() {
        Faker faker = new Faker(new Locale("ru", "russia"));
        String cardholder = faker.name().fullName();
        return cardholder;
    }

    public String generateCVC() {
        Faker faker = new Faker(new Locale("en"));
        String cvc = String.valueOf(faker.number().digits(3));
        return cvc;
    }

    public String generateSymbol() {
        String[] symbol = {"!", "?", "%", "@", "#", "&", "^"};
        Random random = new Random();
        int randomNumber = random.nextInt(symbol.length);
        String result = symbol[randomNumber];
        return result;

    }

    public String generateDigit() {
        Random random = new Random();
        int tmp = random.nextInt(10);
        String result = String.valueOf(tmp);
        return result;
    }

    public String generateLetter() {
        String[] symbol = {"a", "b", "c", "d", "e", "f", "g"};
        Random random = new Random();
        int randomLetter = random.nextInt(symbol.length);
        String result = symbol[randomLetter];
        return result;

    }


}
