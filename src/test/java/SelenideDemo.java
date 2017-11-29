import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideDemo {

    @Description("Simple Selenide UI test.")
    @Test
    public void speedTestValidation () throws InterruptedException, IOException {
        // ChromeDriver initialization
        ChromeDriverManager.getInstance().setup();
        // Setting Browser type instead Firefox by default
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        // Setting start URL
        open("http://beta.speedtest.net/");
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        // Interaction with elements
        $(By.xpath("//*[@alt='Close']")).shouldBe(visible).click(); // close pop-up.
        $(By.className("start-text")).shouldBe(visible).click(); // Press 'GO'.
        // Speedtest results validation
        System.out.println("Browser window size is: " + WebDriverRunner.getWebDriver().manage().window().getSize()); // test dimension changed.
        validateResults();
    }

    @Step("Message validation.")
    private void validateResults() throws IOException {
        $(By.xpath("//*[@data-result-id='true']")).waitUntil(visible, 60000);
        screenshot();
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }
}
