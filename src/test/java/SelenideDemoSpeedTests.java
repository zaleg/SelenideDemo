import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideDemoSpeedTests {

    @Description("Speedtest UI test.")
    @Test
    public void speedTestValidation () throws InterruptedException, IOException {
        // ChromeDriver initialization
        ChromeDriverManager.getInstance().setup();
        // Setting Browser type instead Firefox by default
        Configuration.browser = "chrome";
        Configuration.timeout = 80000;
        // Setting start URL
        open("http://beta.speedtest.net/");
        validateResults();
    }

    @Step("Message validation.")
    private void validateResults() throws IOException {
        $(By.className("start-text")).shouldBe(visible);
        screenshot();
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }
}
