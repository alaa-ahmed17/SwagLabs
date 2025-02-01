package f1AuthenticationTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import features.f1Authentication.F1_login;
import config.Config;
import testData.LoginData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static helpers.jsonDataReader.getLoginData;

public class F1authenticationTest {

    private WebDriver driver;
    private F1_login auth;

    @BeforeTest
    public void setup() {
        driver=Config.setup();
        auth = new F1_login(driver);
    }

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get(Config.baseUrl);
    }

    @DataProvider(name = "loginValidDataProvider")
    public Object[][] loginValidDataProvider() {
        return convertUsersToData(getLoginData().getUsers("valid"));
    }

    @DataProvider(name = "loginInvalidDataProvider")
    public Object[][] loginInvalidDataProvider() {
        return convertUsersToData(getLoginData().getUsers("invalid"));
    }

    @DataProvider(name = "loginLockedDataProvider")
    public Object[][] loginLockedDataProvider() {
        return convertUsersToData(getLoginData().getUsers("locked"));
    }

    // Test Cases

    @Test(priority = 1, dataProvider = "loginValidDataProvider")
    public void testValidLogin(String username, String password) {
        auth.loginToTheSite(username, password);
        assert auth.isLoginSuccess() : "Login failed for valid credentials!";
    }

    @Test(priority = 2, dataProvider = "loginInvalidDataProvider")
    public void testInvalidLogin(String username, String password) {
        auth.loginToTheSite(username, password);
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Username and password do not match any user in this service") : "Invalid login error message is incorrect!";
    }
    @Test(priority = 3, dataProvider = "loginLockedDataProvider")
    public void testLockedLogin(String username, String password) {
        auth.loginToTheSite(username, password);
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Sorry, this user has been locked out.") : "locked login error message is incorrect!Epic sadface: Sorry, this user has been locked out.";
    }
    @Test(priority = 4)
    public void testBlankUsernameAndPassword() {
        auth.loginToTheSite("", "");
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Username is required") : "Error message for blank credentials is incorrect!";
    }

    @Test(priority = 5)
    public void testBlankUsernameWithValidPassword() {
        auth.loginToTheSite("", "secret_sauce");
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Username is required") : "Error message for blank username is incorrect!";
    }

    @Test(priority = 6)
    public void testBlankPasswordWithValidUsername() {
        auth.loginToTheSite("standard_user", "");
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Password is required") : "Error message for blank password is incorrect!";
    }

    @Test(priority = 7)
    public void testLockedOutUser() {
        auth.loginToTheSite("locked_out_user", "secret_sauce");
        assert Objects.equals(auth.getLoginError().getText(), "Epic sadface: Sorry, this user has been locked out.") : "Locked-out user error message is incorrect!";
    }

    // Helper Methods

    private Object[][] convertUsersToData(List<LoginData.User> users) {
        List<Object[]> data = new ArrayList<>();
        for (LoginData.User user : users) {
            data.add(new Object[]{user.getUsername(), user.getPassword()});
        }
        return data.toArray(new Object[0][]);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
