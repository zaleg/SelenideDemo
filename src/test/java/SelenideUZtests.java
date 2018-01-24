import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.google.common.io.Files;
import org.openqa.selenium.By;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideUZtests {
    private static final String stationFrom = "Рівне";
    private static final String stationTo = "Львів";
    private static final int futureDays = 4;

    @Description("Booking train tickets UI test.")
    @Test
    public void availableRailwayTicketsTest() throws InterruptedException, IOException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, futureDays);
        String date = dateFormat.format(calendar.getTime());

        // Setting Browser type instead Firefox by default
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        // Setting start URL
        open("https://booking.uz.gov.ua/");
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        // Interaction with elements
        $(By.xpath("//input[@name='from-title']")).shouldBe(visible).setValue(stationFrom).
                $(By.xpath("//li[contains(text(), '" + stationFrom + "')]")).shouldBe(visible).click(); // Set station from.
        $(By.xpath("//input[@name='to-title']")).shouldBe(visible).setValue(stationTo).
                $(By.xpath("//li[contains(text(), '" + stationTo + "')]")).shouldBe(visible).click(); // Set station to.
        Selenide.executeJavaScript("jQuery(document.getElementsByName('date-hover'))" +
                ".removeAttr('readonly')"); // make date input field editable.
        $(By.name("date-hover")).val(date).pressTab().click();
        $("td.current > a").click(); // select highlighted date from datepicker.
        $(By.xpath("//button[@type='submit']")).click(); // Press search button.
        validateResults();
    }

    @Step("Trains list validation.")
    private void validateResults() throws IOException {
        $(By.id("train-list")).shouldBe(visible).scrollTo();
        screenshot();
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }
}