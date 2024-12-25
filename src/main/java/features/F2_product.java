package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.*;

public class F2_product {
    private final WebDriver driver;

    // Common selectors
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By PRODUCT_SORT_DROPDOWN = By.className("product_sort_container");

    public F2_product(WebDriver driver) {
        this.driver = driver;
    }

    // Add a product to the cart
    public void addToCart(String product) {
        String productId = formatProductId(product);
        driver.findElement(By.id("add-to-cart-" + productId)).click();
    }

    // Verify if add-to-cart action was successful
    public boolean isProductAddedToCart(String product) {
        String productId = formatProductId(product);
        return driver.findElement(By.id("remove-" + productId)).isDisplayed();
    }

    // Remove a product from the cart
    public void removeFromCart(String product) {
        String productId = formatProductId(product);
        driver.findElement(By.id("remove-" + productId)).click();
    }

    // Verify if the product was removed from the cart
    public boolean isProductRemovedFromCart(String product) {
        String productId = formatProductId(product);
        return driver.findElement(By.id("add-to-cart-" + productId)).isDisplayed();
    }

    // Get the number of items in the cart
    public int getCartItemCount() {
        try {
            WebElement badge = driver.findElement(CART_BADGE);
            return Integer.parseInt(badge.getText().trim());
        } catch (Exception e) {
            return 0; // Assume cart is empty if badge is not visible
        }
    }

    // Sort products by name in ascending or descending order
    public boolean sortProductsByName(boolean ascending) {
        selectSortOption(ascending ? "Name (A to Z)" : "Name (Z to A)");
        List<String> actualNames = getProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);

        if (ascending) {
            Collections.sort(expectedNames);
        } else {
            expectedNames.sort(Collections.reverseOrder());
        }

        return actualNames.equals(expectedNames);
    }

    // Sort products by price in ascending or descending order
    public boolean sortProductsByPrice(boolean ascending) {
        selectSortOption(ascending ? "Price (low to high)" : "Price (high to low)");
        List<Double> actualPrices = getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);

        if (ascending) {
            Collections.sort(expectedPrices);
        } else {
            expectedPrices.sort(Collections.reverseOrder());
        }

        return actualPrices.equals(expectedPrices);
    }

    // Private helper methods
    private void selectSortOption(String option) {
        WebElement dropdown = driver.findElement(PRODUCT_SORT_DROPDOWN);
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    private List<String> getProductNames() {
        List<WebElement> nameElements = driver.findElements(By.className("inventory_item_name"));
        List<String> names = new ArrayList<>();
        for (WebElement element : nameElements) {
            names.add(element.getText().trim());
        }
        return names;
    }

    private List<Double> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String priceText = element.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    private String formatProductId(String productName) {
        return productName.replace(" ", "-").toLowerCase(Locale.ROOT);
    }
}
