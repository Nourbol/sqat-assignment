package kz.astanait.edu.nurbol.testing.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SuccessfulLoginTestCase {

    private static final String VALID_LOGIN = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";

    private WebDriver driver;

    @BeforeClass
    void setUp() {
        Reporter.log("Opening browser");
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

    @Test
    void testLoginWithSuccessfulCredentials() {
        driver.get("https://the-internet.herokuapp.com/login");

        Reporter.log("Entering values in login form");

        Actions actions = new Actions(driver);
        WebElement usernameInput = driver.findElement(LoginTestSuiteConstants.USERNAME_INPUT_SELECTOR);
        actions.sendKeys(usernameInput, VALID_LOGIN);

        WebElement passwordInput = driver.findElement(LoginTestSuiteConstants.PASSWORD_INPUT_SELECTOR);
        actions.sendKeys(passwordInput, VALID_PASSWORD);

        WebElement loginButton = driver.findElement(LoginTestSuiteConstants.LOGIN_BUTTON_SELECTOR);

        Reporter.log("Clicking login button");
        actions.click(loginButton);

        actions.perform();

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(250));

        String expectedMessage = "You logged into a secure area!";
        String actualFlashMessage = wait.until(visibilityOfElementLocated(LoginTestSuiteConstants.FLASH_SELECTOR))
                .getText();

        Assert.assertTrue(actualFlashMessage.contains(expectedMessage));
    }
}