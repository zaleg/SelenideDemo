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
    public void ukrNetEmailBoxLoginTest () throws InterruptedException, IOException {
        // ChromeDriver initialization
        ChromeDriverManager.getInstance().setup();
        // Setting Browser type instead Firefox by default
        Configuration.browser = "chrome";
        // Setting start URL
        open("https://www.ukr.net/");
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1920, 1080));
        // Interaction with elements
        $(By.name("Login")).setValue("zaleg");
        $(By.name("Password")).setValue("incorrect_pass");
        $(By.xpath("//*[@id=\"user-login-form\"]//button")).click();
        // Error message validation
        System.out.println("Browser window size is: " + WebDriverRunner.getWebDriver().manage().window().getSize()); // test dimension changed.
        validateMessage();
    }

    @Step("Message validation.")
    private void validateMessage() throws IOException {
        screenshot();
        $(By.className("error-text")).shouldBe(visible)
                .has(text("Неправильно вказано логін чи пароль. Спробуйте знову."));
    }

    @Attachment(type = "image/png")
    private byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }
}
