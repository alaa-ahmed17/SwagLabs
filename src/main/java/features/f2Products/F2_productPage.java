package features.f2Products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class F2_productPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productName = By.className("inventory_details_name");
    private final By productDescription = By.className("inventory_details_desc");
    private final By productPrice = By.className("inventory_details_price");
    private final By statusButton = By.className("btn_inventory");
    private final By addToCartButton = By.xpath("//button[text()='Add to cart']");
    private final By removeButton = By.xpath("//button[text()='Remove']");
    private final By backToProductsButton = By.id("back-to-products");
    private final String homePageURL = "https://www.saucedemo.com/inventory.html";

    public F2_productPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
    }

    public String getProductDescription() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productDescription)).getText();
    }

    public void clickAddToCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public boolean isAddToCartButtonVisible() {
        return !driver.findElements(addToCartButton).isEmpty();
    }

    public void clickRemoveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }

    public boolean isRemoveButtonVisible() {
        return !driver.findElements(removeButton).isEmpty();
    }

    public void clickBackToProductsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton)).click();
    }

    public boolean isBackToProductsButtonVisible() {
        return !driver.findElements(backToProductsButton).isEmpty();
    }

    public boolean isBackToProductsClicked() {
        return driver.getCurrentUrl().equals(homePageURL);
    }

    public String getCartButtonState() {
        try {
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(statusButton));
            return button.getText();
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
