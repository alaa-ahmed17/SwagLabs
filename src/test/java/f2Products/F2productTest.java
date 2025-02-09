package f2Products;

import config.Config;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import features.f2Products.F2_productPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class F2productTest {
    private WebDriver driver;
    private F2_HomePage homePage;
    private F2_productPage product;
    private String productName;
    private String stateProductOnHomePage;

    @BeforeTest
    public void setUp() {
        driver = Config.setup();
        F1_login auth = new F1_login(driver);
        homePage = new F2_HomePage(driver);
        product = new F2_productPage(driver);

        driver.get(Config.baseUrl);
        auth.loginToTheSite("standard_user", "secret_sauce");

        productName = homePage.getFirstProduct();
        stateProductOnHomePage = homePage.getCartButtonState(productName);
        homePage.clickOnProduct(productName);
    }

    @Test(priority = 0)
    public void testStatusWhenClick() {
        // Assert that product status on home page matches product details page
        Assert.assertEquals(stateProductOnHomePage, product.getCartButtonState());
    }

    @Test(priority = 1)
    public void testProductDetailsDisplayed() {
        Assert.assertNotNull(product.getProductName(), "Product title is not displayed!");
        Assert.assertNotNull(product.getProductDescription(), "Product description is not displayed!");
        Assert.assertNotNull(product.getProductPrice(), "Product price is not displayed!");
    }

    @Test(priority = 2)
    public void testAddToCart() {
        if (product.getCartButtonState().equals("Remove")) {
            product.clickRemoveButton();
        }
        int countBefore = homePage.getCartItemCount();
        product.clickAddToCartButton();

        Assert.assertTrue(product.isRemoveButtonVisible(), "Remove button should be visible after adding.");
        int countAfter = homePage.getCartItemCount();
        Assert.assertEquals(countAfter, countBefore + 1, "Cart count did not increase.");
    }

    @Test(priority = 3)
    public void testRemoveFromCart() {
        if (product.getCartButtonState().equals("Add to Cart")) {
            product.clickAddToCartButton();
        }

        int countBefore = homePage.getCartItemCount();
        product.clickRemoveButton();

        Assert.assertTrue(product.isAddToCartButtonVisible(), "Add to Cart button should be visible after removing.");
        int countAfter = homePage.getCartItemCount();
        Assert.assertEquals(countAfter, countBefore - 1, "Cart count did not decrease.");
    }

    @Test(priority = 4)
    public void testBackToHomePage() {
        Assert.assertTrue(product.isBackToProductsButtonVisible(), "Back to Products button is not visible!");
        product.clickBackToProductsButton();
        Assert.assertTrue(product.isBackToProductsClicked(), "User is not redirected to home page!");
    }
}
