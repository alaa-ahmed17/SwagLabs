import config.Config;
import features.F4_checkout;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import features.f2Products.F3_cart;
import features.f2Products.F2_productPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import features.F5_overview;

import static java.lang.Math.round;

public class F5_overviewTest {
    private WebDriver driver;
    private  F5_overview checkoutOverview;
    private F3_cart cart;
    private  F2_HomePage homepage;
    private  String productName ;
    @BeforeTest
    public void setUp() {
        driver = Config.setup();
        F1_login auth = new F1_login(driver);
        checkoutOverview=new F5_overview(driver);
        cart=new F3_cart(driver);
        F5_overview checkoutOverview=new F5_overview(driver);
        homepage =new  F2_HomePage(driver);
        driver.get(Config.baseUrl);
        auth.loginToTheSite("standard_user", "secret_sauce");
        homepage.addToCart(homepage.getFirstProduct());
        productName = homepage.getFirstProduct();
        checkoutOverview.goToCheckoutOverview();
    }

    @Test(priority = 1)
    public void testUserIsOnCheckoutStepTwo() {
        Assert.assertTrue(checkoutOverview.isOnCheckoutStepTwo(), "User is NOT on Checkout: Overview page!");
    }

    @Test(priority = 2)
    public void testSubtotalPrice() {
        double expectedSubtotal = checkoutOverview.calculateExpectedSubtotal();
        double actualSubtotal = checkoutOverview.getSubtotal();
        Assert.assertEquals(actualSubtotal, expectedSubtotal, "Subtotal price is incorrect!");
    }

    @Test(priority = 3)
    public void testTaxCalculation() {
        double expectedTax = Math.round(checkoutOverview.calculateExpectedSubtotal() * 0.08 * 100.0) / 100.0;
        double actualTax = checkoutOverview.getTax();
        Assert.assertEquals(actualTax, expectedTax, "Tax calculation is incorrect!");
    }
    @Test(priority = 4)
    public void testTotalPrice() {
        double expectedTotal = checkoutOverview.calculateExpectedSubtotal() + checkoutOverview.getTax();
        double actualTotal = checkoutOverview.getTotalPrice();
        Assert.assertEquals(actualTotal, expectedTotal, "Total price is incorrect!");
    }

    @Test(priority = 5)
    public void testClickFinish() {
        checkoutOverview.clickFinish();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete"), "Finish button did not navigate to the confirmation page!");
    }

    @Test(priority = 6)
    public void testClickCancel() {
        checkoutOverview.clickCancel();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Cancel button did not return to the cart page!");
    }

    @Test(priority = 7)
    public void testNumberOfItemsInCheckout() {
        int expectedItemCount = cart.getCartItemCount();
        int actualItemCount = checkoutOverview.getNumberOfItemsInCheckout();
        Assert.assertEquals(actualItemCount, expectedItemCount, "Item count does not match cart!");
    }
    @Test(priority = 8)
    public void testClickingItemRedirectsToDetails() {
        driver.get("https://www.saucedemo.com/inventory.html");
        if(homepage.getCartButtonState(productName).equals("Add to cart")){
            homepage.addToCart(productName);
        }
        checkoutOverview.goToCheckoutOverview();
        String itemName = checkoutOverview.getFirstItemName();
        checkoutOverview.clickOnFirstItem();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"), "Item page did not open!");
        Assert.assertEquals(productName, itemName, "Wrong product page opened!");
    }


    @BeforeMethod
    public void checkoutPage() {
         checkoutOverview.goToCheckoutOverview();
    }

//    @AfterTest
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}

