package f2Products;

import config.Config;
import features.F6_menu;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class F2homePageTest {
    private WebDriver driver;
    private F2_HomePage homePage;
    private F6_menu menu;
    private F1_login auth;
    @BeforeTest
    public void setUp() {
        driver = Config.setup();
        auth = new F1_login(driver);
        homePage = new F2_HomePage(driver);
        menu =new F6_menu(driver);
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
        int initialCount = homePage.getCartItemCount();
        homePage.addToCart(productName);
        Assert.assertTrue(homePage.isProductAddedToCart(productName), "Add-to-cart action failed!");
        Assert.assertEquals(homePage.getCartItemCount(), initialCount + 1, "Cart count mismatch after adding a product.");
    }

    @Test(dataProvider = "productProvider")
    public void testRemoveFromCart(String productName) {
        int initialCount = homePage.getCartItemCount();
        homePage.removeFromCart(productName);
        Assert.assertTrue(homePage.isProductRemovedFromCart(productName), "Remove-from-cart action failed!");
        Assert.assertEquals(homePage.getCartItemCount(), initialCount - 1, "Cart count mismatch after removing a product.");
    }

    @Test
    public void testSortByName() {
        Assert.assertTrue(homePage.sortProductsByName(true), "Sorting by name (A to Z) failed!");
        Assert.assertTrue(homePage.sortProductsByName(false), "Sorting by name (Z to A) failed!");
    }

    @Test
    public void testSortByPrice() {
        Assert.assertTrue(homePage.sortProductsByPrice(false), "Sorting by price (high to low) failed!");
        Assert.assertTrue(homePage.sortProductsByPrice(true), "Sorting by price (low to high) failed!");
    }

    @Test(dataProvider = "productProvider")
    public void testProductStatusAfterLogout(String productName) {
        // Step 1: Login and Add to Cart
        if(homePage.getCartButtonState(productName).equals("Add to cart")){
            homePage.addToCart(productName);
        }
        Assert.assertTrue(homePage.isProductAddedToCart(productName), "Product was not added to cart!");

        // Step 2: Logout
        menu.clickMenuIcon();
        menu.clickItemOnMenu("Logout");

        // Step 3: Login Again
        auth.loginToTheSite("standard_user", "secret_sauce");

        // Step 4: Verify Product Status After Login
        Assert.assertTrue(homePage.isProductAddedToCart(productName), "Product status did not persist after logout!");
    }
}