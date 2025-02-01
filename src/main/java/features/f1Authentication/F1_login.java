package features.f1Authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class F1_login {

    private final WebDriver driver;

    // Constructor
    public F1_login(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Verifies if login is successful by checking the presence of the inventory container.
     *
     * @return true if login is successful, false otherwise.
     */
    public boolean isLoginSuccess() {
        try {
            return driver.findElement(By.id("inventory_container")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fetches the error message element after a failed login attempt.
     *
     * @return WebElement of the error message.
     */
    public WebElement getLoginError() {
        return driver.findElement(By.xpath("//h3[@data-test='error']"));
    }

    /**
     * Logs in to the site using the provided username and password.
     *
     * @param username the username for login.
     * @param password the password for login.
     */
    public void loginToTheSite(String username, String password) {
        try {
            enterText(By.id("user-name"), username);
            enterText(By.id("password"), password);
            clickElement(By.id("login-button"));
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    // Utility Methods

    /**
     * Helper method to send keys to an input field.
     *
     * @param locator By locator of the input field.
     * @param text    Text to send to the input field.
     */
    private void enterText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Helper method to click a web element.
     *
     * @param locator By locator of the element.
     */
    private void clickElement(By locator) {
        driver.findElement(locator).click();
    }
}
