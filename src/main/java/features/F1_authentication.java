package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class F1_authentication {
    public WebDriver driver ;
    public F1_authentication(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginSuccess(){
        return driver.findElement(By.id("inventory_container")).isDisplayed();    }

    public WebElement loginassertion() {
        return driver.findElement(By.xpath("//h3[@data-test=\"error\"]"));
    }

    public void loginToTheSite (String username, String password)
    {
        final WebElement  userField = driver.findElement(By.id("user-name"));
        final WebElement passField= driver.findElement(By.id("password"));
        final WebElement buttonlogin = driver.findElement(By.id("login-button"));
        userField.sendKeys(username);
        //Thread.sleep(2000);  // Add a 2-second wait to ensure the page is fully loaded
        passField.sendKeys(password);
        buttonlogin.click();
    }
}
