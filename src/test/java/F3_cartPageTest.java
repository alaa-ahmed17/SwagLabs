import config.Config;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import features.f2Products.F3_cart;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class F3_cartPageTest {
    private WebDriver driver;
    private F2_HomePage homePage;
    private F3_cart cartPage;

    @BeforeTest
    public void setUp() {
        driver = Config.setup();
        F1_login auth = new F1_login(driver);
        homePage = new F2_HomePage(driver);
        cartPage = new F3_cart(driver);
        driver.get(Config.baseUrl);
        auth.loginToTheSite("standard_user", "secret_sauce");
    }

    @AfterTest
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

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

    @Test(dataProvider = "productProvider",priority = 1)
    public void testAddToCart(String productName) {
        homePage.addToCart(productName);  // Add product from home page
        cartPage.goToCart();  // Go to cart page

        // Verify the product is in the cart
        Assert.assertTrue(cartPage.isProductInCart(productName), "Product not found in the cart!");

        // Navigate back to home page to allow adding another product
        cartPage.clickContinueShopping();

        // Verify we are back on the home page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Failed to return to home page!");
    }


    @Test(dataProvider = "productProvider",priority = 2)
    public void testRemoveFromCart(String productName) {
        if(homePage.getCartButtonState(productName).equals("Add to cart")){
            homePage.addToCart(productName);
        }

        cartPage.goToCart();
        int initialCount = homePage.getCartItemCount();
        cartPage.removeProduct(productName);
        Assert.assertFalse(cartPage.isProductInCart(productName), "Product was not removed!");
        Assert.assertEquals(homePage.getCartItemCount(), initialCount - 1, "Cart count mismatch after removing a product.");

        // Navigate back to home page to allow adding another product
        cartPage.clickContinueShopping();

        // Verify we are back on the home page
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Failed to return to home page!");
    }

    @Test(priority =3)
    public void testCheckoutButton() {
        cartPage.goToCart();
        cartPage.clickCheckout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"), "Checkout button did not navigate correctly!");
    }


}
