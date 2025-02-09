import config.Config;
import features.f1Authentication.F1_login;
import features.f2Products.F2_HomePage;
import features.F6_menu;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Objects;

public class F6menuTest {
    private WebDriver driver;
    private F1_login auth;
    private F6_menu menu;
    private F2_HomePage product;

    @BeforeTest
    public void setup(){
        driver= Config.setup();
        driver.get(Config.baseUrl);
        auth = new F1_login(driver);
        auth.loginToTheSite("standard_user", "secret_sauce");
        menu=new F6_menu(driver);
        product=new F2_HomePage(driver);
    }
    @BeforeMethod
    public void eachMethod(){
        if(menu.isMenuClicked()){
            menu.clickCloseMenu();
        }

    }

    @Test(priority =1)
    public void menuDisplayTest(){
        assert menu.isMenuIconDisplay():"The menu is not displayed";
    }

    @Test(priority = 2)
    public void clickMenuTest() {
        menu.clickMenuIcon();
        assert menu.isMenuClicked() : "The menu is not clicked";
    }

    @Test(priority = 3)
    public void EachItemDisplayedTest() {
        menu.clickMenuIcon();
        List<String> actualMenuItems = menu.getItems();
        List<String> expectedMenuItems = List.of(
                "All Items",
                "About",
                "Logout",
                "Reset App State"
        );
        // Compare the two lists
        assert actualMenuItems.equals(expectedMenuItems) : "Menu items do not match. Expected: " + expectedMenuItems + ", but got: " + actualMenuItems;
    }

    @DataProvider
    public Object[][] navigation(){
        return new Object[][]{
                {"All Items", Config.baseUrl+"inventory.html"},
                {"About", "https://saucelabs.com/"},
                {"Logout", Config.baseUrl},
                {"Reset App State", Config.baseUrl+"inventory.html"} // Reset App State may not change URL, but should reset app
        };
    }

    @Test(dataProvider = "navigation",priority =4)
    public void clickItemTest(String item, String expectedUrl){
        menu.clickMenuIcon();
        menu.clickItemOnMenu(item);
        assert Objects.equals(driver.getCurrentUrl(),expectedUrl);
        // Reset state (if necessary)
        if("About".equals(item)){
            driver.navigate().back();
        }
        if ("Logout".equals(item)) {
            auth.loginToTheSite("standard_user", "secret_sauce");
        }if ("Reset App State".equals(item)) {
            driver.navigate().refresh();
        }
    }

    @Test(priority = 5)
    public void checkResetAppState(){
        if(product.getCartItemCount()<=0){
            product.addToCart("Sauce Labs Backpack");
        }
        menu.clickMenuIcon();
        menu.clickItemOnMenu("Reset App State");
        driver.navigate().refresh();
        assert product.isProductRemovedFromCart("Sauce Labs Backpack");
        assert product.getCartItemCount()==0;
    }


    @Test(priority = 6)
    public void closeMenuTest(){
        menu.clickMenuIcon();
        menu.clickCloseMenu();
        assert menu.isClosedMenuIconClicked():"the closed icon isn't worked";
    }


}
