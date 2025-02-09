import config.Config;
import features.F4_checkout;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import features.f2Products.F3_cart;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class F4_checkoutTest {
    private WebDriver driver;
    private F4_checkout checkout;

    @BeforeTest
    public void setUp() {
        driver = Config.setup();
        F1_login auth = new F1_login(driver);
        checkout = new F4_checkout(driver);
        driver.get(Config.baseUrl);
        auth.loginToTheSite("standard_user", "secret_sauce");
        checkout.goOnCheckoutPageAfterLogin();
    }
    @Test(priority = 1)
    public void CheckGoToCheckoutpage(){
        assert  driver.findElement(By.className("title")).getText().equals("Checkout: Your Information");
    }

    @Test(priority = 2)
    public void enterValidData(){
        checkout.setFirstName("test");
        checkout.setSecondName("test");
        checkout.setPostalCode("5112");
        checkout.clickOnContinue();
        Assert.assertEquals(driver.getCurrentUrl(),"https://www.saucedemo.com/checkout-step-two.html",
                "User is not navigated to the checkout step two page!");
    }

    @Test(priority = 3)
    public void testCheckoutWithoutFirstName() {
        checkout.setSecondName("Doe");
        checkout.setPostalCode("12345");
        checkout.clickOnContinue();

        // Verify error message
        Assert.assertTrue(checkout.isErrorMessageDisplayed(), "Error message is not displayed!");
        Assert.assertEquals(checkout.readErrorMassage(), "Error: First Name is required");
    }

    @Test(priority = 4)
    public void testCheckoutWithoutLastName() {
        checkout.setFirstName("John");
        checkout.setPostalCode("12345");
        checkout.clickOnContinue();

        // Verify error message
        Assert.assertTrue(checkout.isErrorMessageDisplayed(), "Error message is not displayed!");
        Assert.assertEquals(checkout.readErrorMassage(), "Error: Last Name is required");
    }

    @Test(priority = 5)
    public void testCheckoutWithoutPostalCode() {
        checkout.setFirstName("John");
        checkout.setSecondName("Doe");
        checkout.clickOnContinue();

        // Verify error message
        Assert.assertTrue(checkout.isErrorMessageDisplayed(), "Error message is not displayed!");
        Assert.assertEquals(checkout.readErrorMassage(), "Error: Postal Code is required");
    }

    @Test(priority = 5)
    public void testCancelButton() {
        checkout.clickOnCancel();

        // Verify navigation back to cart page
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html",
                "Cancel button did not navigate back to the cart!");
    }
    @AfterMethod
    public void checkoutPage() {
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



