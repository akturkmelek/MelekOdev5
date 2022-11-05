package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Case2
{
    ChromeDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(Case2.class);

    @BeforeEach
    public void connectToTestbox() throws InterruptedException

    {

        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.MINUTES);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.MINUTES);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.MINUTES);

        driver.get("https://sahibinden.com");

        List<Cookie> cookieList = new ArrayList<>();
        cookieList.add(new Cookie("testBox", "165", ".sahibinden.com", "/", null));
        cookieList.add(new Cookie("tbSite", "x", ".sahibinden.com", "/", null));
        cookieList.forEach(cookie -> driver.manage().addCookie(cookie));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".onetrust-close-btn-handler"))).click();

        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        localStorage.setItem("yt-remote-device-id",
                "{\"data\":\"836acd5e-336d-437c-b95d-f52eabc02204\",\"expiration\":1699227236937,\"creation\":1667691236937}");

        SessionStorage sessionStorage = ((WebStorage) driver).getSessionStorage();
        sessionStorage.setItem("yt-remote-session-app", "{\"data\":\"youtube-desktop\",\"creation\":1667691238008}");


        logger.info("Testboxa baglanıldı");


    }

    @Tag("SEARCH_TESTS")
    @ParameterizedTest
    @CsvSource({
            "iPhone 12 Mini,#search_cats ul li.cl4,1",
            "PlayStation 5,#search_cats ul li.cl3 div a h2,2",
            "Koşu Bandı,#search_cats ul li.cl3 div a h2,3",
            "Elektrikli Isıtıcı,#search_cats ul li.cl3 div a h2,4",
            "Toyota,#search_cats ul li.cl2 div a h2,5"})

    public void categoryTestCase2()
    {
        try
        {


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'iPhone 12 Mini')]"))).click();

            String categoryText = driver.findElement(By.xpath("//a[contains(text(),'iPhone 12 Mini')]")).getText();
            logger.info(categoryText);
        }
        catch (Exception exception)
        {
            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            logger.error("Test fail ekran görüntüsü ektedir");
        }



    }

    @AfterEach
    public void afterEach ()
    {
        driver.quit();
        logger.info("Chromedriver kapandı");
    }
}
