import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideDemo {
    @Test
    public void ukrNetEmailBoxLoginTest () throws InterruptedException {
        // ChromeDriver initialization
        ChromeDriverManager.getInstance().setup();
        // Setting Browser type instead Firefox by default
        Configuration.browser = "chrome";
        // Setting start URL
        open("https://www.ukr.net/");
        // Interaction with elements
        $(By.name("Login")).setValue("zaleg");
        $(By.name("Password")).setValue("incorrect_pass");
        $(By.xpath("//*[@id=\"user-login-form\"]//button")).click();
        // Error message validation
        System.out.println("Browser window sixe is: " + WebDriverRunner.getWebDriver().manage().window().getSize());
        $(By.className("error-text")).shouldHave(text("Неправильно вказано логін чи пароль. Спробуйте знову."));
    }
}
