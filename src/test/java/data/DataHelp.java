package data;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataHelp {

    public final String APPROVED_CARD = "4444444444444441";
    public final String DECLINED_CARD = "4444444444444442";

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
