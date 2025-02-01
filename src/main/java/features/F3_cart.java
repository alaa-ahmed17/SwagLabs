package features.f2Products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class F3_cart {
    private final WebDriver driver;

    // Locators
    private static final By CART_ITEMS = By.className("cart_item");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private static final By REMOVE_BUTTONS = By.className("cart_button");

    public F3_cart(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to Cart Page
    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }


    // Check if a specific product is in the cart
    public boolean isProductInCart(String productName) {
        List<WebElement> cartItems = driver.findElements(CART_ITEMS);
        for (WebElement item : cartItems) {
            String itemName = item.findElement(By.className("inventory_item_name")).getText();
            if (itemName.equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    // Remove a product from the cart
    public void removeProduct(String productName) {
        List<WebElement> cartItems = driver.findElements(CART_ITEMS);
        for (WebElement item : cartItems) {
            String itemName = item.findElement(By.className("inventory_item_name")).getText();
            if (itemName.equalsIgnoreCase(productName)) {
                item.findElement(REMOVE_BUTTONS).click();
                break;
            }
        }
    }

    // Click Checkout Button
    public void clickCheckout() {
        driver.findElement(CHECKOUT_BUTTON).click();
    }

    // Click Continue Shopping Button
    public void clickContinueShopping() {
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
    }
}
