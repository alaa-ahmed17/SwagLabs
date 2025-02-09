package features.f1Authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class F1_logout {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators for elements on the logout page
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    // Constructor
    public F1_logout(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait in the constructor
    }

    // Method to log out
    public void logout() {
        try {
            // Wait for the menu button to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
            System.out.println("Menu button clicked.");

            // Wait for the logout link to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
            System.out.println("Logout link clicked.");
        } catch (Exception e) {
            // Handle any errors during the logout process
            System.err.println("Error during logout: " + e.getMessage());
            throw e; // Rethrow or log as needed
        }
    }
}
