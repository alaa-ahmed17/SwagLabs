package features.f2Products;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

public class F2_HomePage {
    private final WebDriver driver;

    // Common selectors
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By PRODUCT_SORT_DROPDOWN = By.className("product_sort_container");

    public F2_HomePage(WebDriver driver) {
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
        return isButtonVisible(By.id("remove-" + productId));
    }

    // Remove a product from the cart
    public void removeFromCart(String product) {
        String productId = formatProductId(product);
        driver.findElement(By.id("remove-" + productId)).click();
    }

    // Verify if the product was removed from the cart
    public boolean isProductRemovedFromCart(String product) {
        String productId = formatProductId(product);
        return isButtonVisible(By.id("add-to-cart-" + productId));
    }

    // Check the cart button state (either Add to Cart or Remove)
    public String getCartButtonState(String productId) {
        String formattedId = formatProductId(productId);
        if (isButtonVisible(By.id("add-to-cart-" + formattedId))) {
            return "Add to cart";
        } else if (isButtonVisible(By.id("remove-" + formattedId))) {
            return "Remove";
        }
        return "Unknown";
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
        return sortProducts("Name", ascending);
    }

    // Sort products by price in ascending or descending order
    public boolean sortProductsByPrice(boolean ascending) {
        return sortProducts("Price", ascending);
    }

    // Private helper methods
    private boolean sortProducts(String criterion, boolean ascending) {
        String option = (criterion.equals("Name")) ?
                (ascending ? "Name (A to Z)" : "Name (Z to A)") :
                (ascending ? "Price (low to high)" : "Price (high to low)");

        selectSortOption(option);
        List<?> actualItems = criterion.equals("Name") ? getProductNames() : getProductPricesAsString();
        List<?> expectedItems = new ArrayList<>(actualItems);
        if (ascending) {
            if (criterion.equals("Name")) {
                Collections.sort((List<String>) expectedItems);
            } else {
                Collections.sort((List<Double>) expectedItems);
            }
        } else {
            if (criterion.equals("Name")) {
                expectedItems.sort(Collections.reverseOrder());
            } else {
                expectedItems.sort(Collections.reverseOrder());
            }
        }
        return actualItems.equals(expectedItems);
    }

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

    private List<Double> getProductPricesAsString() {
        List<WebElement> priceElements = driver.findElements(By.className("inventory_item_price"));
        List<Double> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String priceText = element.getText().replace("$", "").trim();
            // Parse the price as a Double for proper numerical sorting
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    private boolean isButtonVisible(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    private String formatProductId(String productName) {
        return productName.replace(" ", "-").toLowerCase(Locale.ROOT);
    }

    // Get first product name (for testing)
    public String getFirstProduct() {
        return driver.findElement(By.className("inventory_item_name")).getText();
    }

    public void clickOnProduct(String product) {
        driver.findElement(By.xpath("//*[contains(text(), '" + product + "')]")).click();
    }
}
