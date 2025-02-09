package features;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class F5_overview {
    private WebDriver driver;
    // Constructor

    public F5_overview(WebDriver driver) {
        this.driver = driver;
    }
    // Locators
    private By pageTitle = By.className("title");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By itemPrices = By.className("inventory_item_price");
    private By cartItems = By.className("cart_item");  // All items in the checkout
    private By itemNames = By.className("inventory_item_name");  // Names of items

    // Verify user is on Checkout Step Two
    public boolean isOnCheckoutStepTwo() {
        return driver.findElement(pageTitle).getText().equals("Checkout: Overview");
    }


    // Click Finish button
    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    // Click Cancel button
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public void goToCheckoutOverview() {
        driver.get("https://www.saucedemo.com/checkout-step-two.html");
    }

    // Get subtotal price
    public double getSubtotal() {
        String text = driver.findElement(subtotalLabel).getText().replace("Item total: $", "");
        return Double.parseDouble(text);
    }

    // Get tax amount
    public double getTax() {
        String text = driver.findElement(taxLabel).getText().replace("Tax: $", "");
        return Double.parseDouble(text);
    }

    // Get total price
    public double getTotalPrice() {
        String text = driver.findElement(totalLabel).getText().replace("Total: $", "");
        return Double.parseDouble(text);
    }
    public double calculateExpectedSubtotal() {
        List<WebElement> prices = driver.findElements(itemPrices);
        double subtotal = 0.0;
        for (WebElement price : prices) {
            subtotal += Double.parseDouble(price.getText().replace("$", ""));
        }
        return subtotal;
    }


    /** ✅ Get the number of items in checkout */
    public int getNumberOfItemsInCheckout() {
        return driver.findElements(cartItems).size();
    }

    /** ✅ Get the name of the first item in the checkout */
    public String getFirstItemName() {
        return driver.findElement(itemNames).getText();
    }

    /** ✅ Click the first item to navigate to its details page */
    public void clickOnFirstItem() {
        driver.findElement(itemNames).click();
    }
    /** ✅ Get all item names in checkout */
    public List<WebElement> getAllItemNames() {
        return driver.findElements(itemNames);
    }

    /** ✅ Get all item prices in checkout */
    public List<WebElement> getAllItemPrices() {
        return driver.findElements(itemPrices);
    }

}
