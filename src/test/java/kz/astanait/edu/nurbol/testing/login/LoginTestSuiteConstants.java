package kz.astanait.edu.nurbol.testing.login;

import org.openqa.selenium.By;

public class LoginTestSuiteConstants {

    static final By USERNAME_INPUT_SELECTOR = By.xpath("//*[@id=\"username\"]");
    static final By PASSWORD_INPUT_SELECTOR = By.xpath("//*[@id=\"password\"]");
    static final By LOGIN_BUTTON_SELECTOR = By.xpath("/html/body/div[2]/div/div/form/button");
    static final By FLASH_SELECTOR = By.cssSelector("#flash");
}
