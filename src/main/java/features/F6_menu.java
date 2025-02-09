package features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class F6_menu {

    private final WebDriver driver;
    public F6_menu(WebDriver driver){
        this.driver=driver;
    }

    public boolean isMenuIconDisplay(){
        return driver.findElement(By.className("bm-icon")).isDisplayed();
    }

    public void clickMenuIcon(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("react-burger-menu-btn")));
        driver.findElement(By.id("react-burger-menu-btn")).click();
    }

    public boolean isMenuClicked(){
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-hidden='false']")));
        //return driver.findElement(By.xpath("//*[@aria-hidden='false']")).isDisplayed();
        WebElement menuElement = driver.findElement(By.xpath("//div[@class='bm-menu-wrap']")); // Replace with the parent element of the menu
        String ariaHidden = menuElement.getAttribute("aria-hidden");
        return "false".equals(ariaHidden); // If aria-hidden is "false", menu is clicked
    }

    public List<String> getItems(){
        List<String> allItems=new ArrayList<>();
        //List<WebElement> itemsName=driver.findElements(By.className("bm-item"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("bm-item")));
        List<WebElement> itemsName = driver.findElements(By.className("bm-item"));
        for (WebElement item:itemsName){
            allItems.add(item.getText());
        }
        return allItems;
    }

    public void clickItemOnMenu(String item){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//a[contains(text(),'"+item+"')]")));
        driver.findElement(By.xpath("//a[contains(text(),'"+item+"')]")).click();
        //driver.findElement(By.xpath("//*[@text()='"+item+"']")).click();

    }

    public boolean verifyNavigation(String item, String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        return Objects.equals(actualUrl, expectedUrl);
    }

    public void clickCloseMenu(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("react-burger-cross-btn")));
        driver.findElement(By.id("react-burger-cross-btn")).click();
    }

    public boolean isClosedMenuIconClicked() {
        WebElement menuElement = driver.findElement(By.xpath("//div[@class='bm-menu-wrap']")); // Replace with the parent element of the menu
        String ariaHidden = menuElement.getAttribute("aria-hidden");
        return "true".equals(ariaHidden); // If aria-hidden is "false", menu is clicked    }
    }

}
