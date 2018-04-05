
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DamoclesSTest {

    static String username = "jqchang";
    static String password = "testtest";
    static String googleAuth = "652051";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Google Authenticator Code: ");
        googleAuth = sc.next();

        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("localhost:3000");

        System.out.println("Starting Test");

        WebElement user = driver.findElement(By.id("username"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement code = driver.findElement(By.id("code"));
        WebElement loginBtn = driver.findElement(By.className("btn"));
        user.clear();
        pass.clear();
        code.clear();
        user.sendKeys(username);
        pass.sendKeys(password);
        code.sendKeys(googleAuth);
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        loginBtn.click();

        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // get all the links after logging in
        java.util.List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of links: " + links.size());
        for (int i = 0; i < links.size(); i = i + 1) {
            System.out.println(i + " " + links.get(i).getText());
        }
        WebElement link = null;
        for (int i = 0; i < links.size(); i++) {
            System.out.println("*** Navigating to" + " " + links.get(i).getAttribute("href"));
            if (links.get(i).getAttribute("href") == null)
                continue;
            if ((links.get(i).getText()).equals("Logout")) {
                link = links.get(i);
                continue;
            }
            boolean staleElementLoaded = true;
            while (staleElementLoaded) {
                try {
                    driver.navigate().to(links.get(i).getAttribute("href"));
                    try {
                        Thread.sleep(3000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    driver.navigate().back();
                    links = driver.findElements(By.tagName("a"));
                    System.out.println("*** Navigated to" + " " + links.get(i).getAttribute("href"));
                    staleElementLoaded = false;
                } catch (StaleElementReferenceException e) {
                    staleElementLoaded = true;
                }
            }
        }
        driver.get("localhost:3000/profile");
        java.util.List<WebElement> refresh = driver.findElements(By.tagName("a"));
        for (int i = 0; i < refresh.size(); i = i + 1) {
            link = refresh.get(i);
            if ((link.getText()).equals("Logout")) {
                try {
                    System.out.println("Logging out");
                    try{
                        driver.navigate().to(link.getAttribute("href"));
                    }catch (StaleElementReferenceException e){
                        
                    }
                    
                } catch (Exception e) {
                    System.out.println("This is not a proper URL to connect to " + links.get(i).getAttribute("href"));
                }
            }
        }

        System.out.println("Testing pages that should not be reached");
        System.out.println("Trying localhost:3000/facerec");
        driver.navigate().to("localhost:3000/facerec");
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Should be redirected to http://localhost:3000/, is now at " + driver.getCurrentUrl());
        System.out.println("Trying localhost:3000/faceadd");
        driver.navigate().to("localhost:3000/faceadd");
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Should be redirected to http://localhost:3000/, is now at " + driver.getCurrentUrl());
        System.out.println("Trying localhost:3000/profile");
        driver.navigate().to("localhost:3000/profile");
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Should be redirected to http://localhost:3000/, is now at " + driver.getCurrentUrl());
        System.out.println("Trying localhost:3000/adminprofile");
        driver.navigate().to("localhost:3000/adminprofile");
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Should be redirected to http://localhost:3000/, is now at " + driver.getCurrentUrl());
        System.out.println("Test Ended");
        driver.close();

    }
}