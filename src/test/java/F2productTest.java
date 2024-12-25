import config.Config;
import features.F1_authentication;
import features.F2_product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class F2productTest {
    private WebDriver driver;
    private F1_authentication auth;
    private F2_product productPage;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        auth = new F1_authentication(driver);
        productPage = new F2_product(driver);

        driver.manage().window().maximize();
        driver.get(Config.baseUrl);
        auth.loginToTheSite("standard_user", "secret_sauce");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider
    public Object[] productProvider() {
        return new Object[]{
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket",
                "Sauce Labs Onesie",
                "Test.allTheThings() T-Shirt (Red)"
        };
    }

    @Test(dataProvider = "productProvider")
    public void testAddToCart(String productName) {
        int initialCount = productPage.getCartItemCount();
        productPage.addToCart(productName);
        Assert.assertTrue(productPage.isProductAddedToCart(productName), "Add-to-cart action failed!");
        Assert.assertEquals(productPage.getCartItemCount(), initialCount + 1, "Cart count mismatch after adding a product.");
    }

    @Test(dataProvider = "productProvider")
    public void testRemoveFromCart(String productName) {
        //productPage.addToCart(productName); // Ensure product is in the cart
        int initialCount = productPage.getCartItemCount();
        productPage.removeFromCart(productName);
        Assert.assertTrue(productPage.isProductRemovedFromCart(productName), "Remove-from-cart action failed!");
        Assert.assertEquals(productPage.getCartItemCount(), initialCount - 1, "Cart count mismatch after removing a product.");
    }

    @Test
    public void testSortByName() {
        Assert.assertTrue(productPage.sortProductsByName(true), "Sorting by name (A to Z) failed!");
        Assert.assertTrue(productPage.sortProductsByName(false), "Sorting by name (Z to A) failed!");
    }

    @Test
    public void testSortByPrice() {
        Assert.assertTrue(productPage.sortProductsByPrice(true), "Sorting by price (low to high) failed!");
        Assert.assertTrue(productPage.sortProductsByPrice(false), "Sorting by price (high to low) failed!");
    }
}
