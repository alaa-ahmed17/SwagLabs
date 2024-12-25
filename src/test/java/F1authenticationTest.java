import helpers.jsonDataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import features.F1_authentication;
import config.Config;
import testData.LoginData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static helpers.jsonDataReader.getLoginData;

public class F1authenticationTest {
    public WebDriver driver;
    public F1_authentication auth;

    @BeforeTest
    public void setup(){
        driver=new ChromeDriver();

        auth =new F1_authentication(driver);
        driver.manage().window().maximize();
    }
    @BeforeMethod
    public void setupMethod(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.get(Config.baseUrl);
        //driver.manage().window().fullscreen();
    }

    @DataProvider(name = "loginValidDataProvider")
    public Object[][] loginDataValidProvider() {
        LoginData loginData = getLoginData();
        List<LoginData.User> validUsers = loginData.getUsers("valid");
        List<Object[]> data=new ArrayList<>();
        for (LoginData.User user : validUsers) {
            data.add(new Object[]{user.getUsername(), user.getPassword()});
        }
        return data.toArray(new Object[0][]);
    }

    @DataProvider(name = "loginInvalidDataProvider")
    public Object[][] loginDataInvalidProvider() {
        LoginData loginData = getLoginData();
        List<LoginData.User> validUsers = loginData.getUsers("valid");
        List<Object[]> data=new ArrayList<>();
        for (LoginData.User user : validUsers) {
            data.add(new Object[]{user.getUsername(), user.getPassword()});
        }

        return data.toArray(new Object[0][]);
    }
    @Test(priority=1, dataProvider = "loginValidDataProvider" )
    public void validlogin(String username, String password) {
        auth.loginToTheSite(username, password);
        assert auth.isLoginSuccess() : "Login failed for valid credentials!";
    }

    @Test(priority = 2, dataProvider = "loginInvalidDataProvider" )
    public void invalidData(String username, String password) {
        auth.loginToTheSite(username, password);
        assert (Objects.equals(auth.loginassertion().getText(),"Epic sadface: Username and password do not match any user in this service")) : "Login succeeded with invalid password!";
    }


    @Test(priority = 3)
    public void blankUsernameAndPassword() {
        auth.loginToTheSite("", "");
        assert (Objects.equals(auth.loginassertion().getText(),"Epic sadface: Username is required"))  : "Login succeeded with blank username and password!";
    }

    @Test(priority = 6)
    public void validPasswordWithBlankUsername() {
        auth.loginToTheSite("", "secret_sauce");
        assert (Objects.equals(auth.loginassertion().getText(),"Epic sadface: Username is required")) : "Login succeeded with blank username!";
    }

    @Test(priority = 7)
    public void validUsernameWithBlankPassword() {
        auth.loginToTheSite("standard_user", "");
        assert (Objects.equals(auth.loginassertion().getText(),"Epic sadface: Password is required"))  : "Login succeeded with blank password!";
    }

    @Test(priority = 8)
    public void lockedOutUser() {
        F1_authentication auth = new F1_authentication(driver);
        auth.loginToTheSite("locked_out_user", "secret_sauce");
        assert (Objects.equals(auth.loginassertion().getText(),"Epic sadface: Sorry, this user has been locked out.")) : "Locked-out user was able to log in!";
    }
//    @AfterTest
//    public void finshproject(){
//        //driver.quit();
//    }


}
