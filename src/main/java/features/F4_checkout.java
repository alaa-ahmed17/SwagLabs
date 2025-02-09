package features;

import features.f2Products.F3_cart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class F4_checkout {
    private WebDriver driver = null;
    private final F3_cart cart;
    // Locators
    private static final By FirstName = By.id("first-name");
    private static final By SecondName = By.id("last-name");
    private static final By postalCode = By.id("postal-code");
    private static final By cancelButton = By.id("cancel");
    private static final By continueButton = By.id("continue");
    private static final By errorMessage =By.cssSelector("h3[data-test='error']");

    public F4_checkout(WebDriver driver) {
        this.driver = driver;
        cart =new F3_cart(driver);
    }
    public void setFirstName(String name){
        enterText(FirstName,name);
    }
    public void setSecondName(String name){
        enterText(SecondName,name);
    }
    public void setPostalCode(String code){
        enterText(postalCode,code);
    }
    public String readErrorMassage(){
        return driver.findElement(errorMessage).getText();
    }

    public void clickOnContinue(){
        driver.findElement(continueButton).click();
    }
    public void clickOnCancel(){
        driver.findElement(cancelButton).click();
    }
    public void goOnCheckoutPageAfterLogin(){
        cart.goToCart();
        cart.clickCheckout();
    }
    private void enterText(By locator, String text) {
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    public boolean isErrorMessageDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }
}
