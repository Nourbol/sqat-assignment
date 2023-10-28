package kz.astanait.edu.nurbol.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class TestCases {

    private WebDriver driver;

    @BeforeClass
    void setUp() {
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass
    void tearDown() {
        driver.quit();
    }

    @Test
    void testSearch() {
        driver.get("https://dev.to/");

        driver.findElement(By.xpath("/html/body/header/div/div[1]/form/div/div/input"))
                .sendKeys("N");
        driver.findElement(By.xpath("/html/body/header/div/div[1]/form/div/div/button"))
                .click();

        By searchResultsHeadingSelector = By.xpath("/html/body/div[7]/div/main/div/div[1]/h1");

        String expectedResultStart = "Search results";
        String actualResult = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(visibilityOfElementLocated(searchResultsHeadingSelector))
                .getText();

        Assert.assertTrue(actualResult.startsWith(expectedResultStart));
    }

    @Test
    void testLoginAndLogout() {
        driver.get("https://the-internet.herokuapp.com/login");

        Actions actions = new Actions(driver);
        WebElement usernameInput = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        actions.sendKeys(usernameInput, "tomsmith");

        WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        actions.sendKeys(passwordInput, "SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/button"));
        actions.click(loginButton);

        actions.perform();

        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(250));
        wait.until(visibilityOfElementLocated(By.linkText("Logout"))).click();

        String expectedMessage = "You logged out of the secure area!";
        String actualFlashMessage = wait.until(visibilityOfElementLocated(By.cssSelector("#flash")))
                .getText();

        Assert.assertTrue(actualFlashMessage.contains(expectedMessage));
    }

    @Test
    void testFlightBooking() {
        driver.get("https://blazedemo.com");

        new Select(driver.findElement(By.name("fromPort")))
                .selectByVisibleText("Boston");
        new Select(driver.findElement(By.name("toPort")))
                .selectByVisibleText("New York");

        driver.findElement(By.xpath("/html/body/div[3]/form/div/input")).click();

        By chooseFlightSelector = By.xpath("/html/body/div[2]/table/tbody/tr[1]/td[1]/input");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(visibilityOfElementLocated(chooseFlightSelector))
                .click();

        Actions actions = new Actions(driver);

        new Select(driver.findElement(By.id("cardType"))).selectByVisibleText("Diner's Club");

        actions.sendKeys(driver.findElement(By.id("inputName")), "Nur Bot")
                .sendKeys(driver.findElement(By.id("address")), "123 Mangelik El")
                .sendKeys(driver.findElement(By.id("city")), "Astana")
                .sendKeys(driver.findElement(By.id("state")), "CA")
                .sendKeys(driver.findElement(By.id("zipCode")), "12345")
                .sendKeys(driver.findElement(By.id("creditCardNumber")), "1234567890123456")
                .sendKeys(driver.findElement(By.id("creditCardMonth")), "12")
                .sendKeys(driver.findElement(By.id("creditCardYear")), "2025")
                .sendKeys(driver.findElement(By.id("nameOnCard")), "Nur Bot")
                .click(driver.findElement(By.cssSelector("input[value=\"Purchase Flight\"]")))
                .perform();

        Assert.assertEquals("BlazeDemo Confirmation", driver.getTitle());
    }
}