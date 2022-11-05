package com;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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


public class Case1
{
    ChromeDriver driver;
    WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(Case1.class);

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
                "{\"data\":\"95a35c11-7114-4936-917f-d98613c47389\",\"expiration\":1699164221982,\"creation\":1667628221982}");

        SessionStorage sessionStorage = ((WebStorage) driver).getSessionStorage();
        sessionStorage.setItem("yt-remote-session-app", "{\"data\":\"youtube-desktop\",\"creation\":1667628222564}");

        LocalStorage localStorageOnurBuldu = ((WebStorage) driver).getLocalStorage();
        localStorageOnurBuldu.setItem("feature_discovery_data",
                "{\"add-to-favorites\":{\"count\":1,\"displayedAt\":1666694107010},\"extended\":true}");

        SessionStorage sessionStorageOnurBuldu = ((WebStorage) driver).getSessionStorage();
        sessionStorageOnurBuldu.setItem("feature_discovery_displayed", "true");

        //driver.switchTo().frame(1);
        //driver.findElement(By.cssSelector(".intermediate-element >img")).click();
        //driver.switchTo().defaultContent();

        driver.navigate().refresh();
        logger.info("Testboxa baglanıldı");
    }

    @Tag("SEARCH_TESTS")
    @DisplayName("SEARCH RESULT TEST")
    @Test
    public void searchResultTestCase1()
    {
        try
        {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title='Otomobil']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("all-classifieds-link"))).click();


            String allClassifiedsText = driver.findElement(By.cssSelector("a.phdef[title='Tüm İlanlar']")).getText();
            logger.info(allClassifiedsText);

            String sahibindenText = driver.findElement(By.cssSelector("a.phdef[title='Sahibinden']")).getText();
            logger.info(sahibindenText);

            String gallerryText = driver.findElement(By.cssSelector(" a.phdef[title='Galeriden']")).getText();
            logger.info(gallerryText);

            String officialSellerText = driver.findElement(By.cssSelector("a.phdef[title='Yetkili Bayiden']")).getText();
            logger.info(officialSellerText);

            String classifiedTitleName = driver.findElement(By.className("searchResultsTitleFullWidth")).getText();
            logger.info(classifiedTitleName);

            String searchResultText = driver.findElement(By.cssSelector(".classifiedTitle")).getText();
            logger.info(searchResultText);

            String searchResultKmText = driver.findElement(By.cssSelector("tbody tr:nth-child(1) td:nth-child(7)")).getText();
            logger.info(searchResultKmText);

            String searchResultYearText = driver.findElement(By.cssSelector(".searchResultsAttributeValue")).getText();
            logger.info(searchResultYearText);

            String searchResultPriceText = driver.findElement(By.cssSelector(".searchResultsPriceValue")).getText();
            logger.info(searchResultPriceText);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".classifiedTitle"))).click();

            String searchResultClassifiedIdText = driver.findElement(By.id("classifiedId")).getText();
            logger.info(searchResultClassifiedIdText);

            Assertions.assertAll
                    (
                            () -> Assertions.assertEquals("Tüm İlanlar", allClassifiedsText),
                            () -> Assertions.assertEquals("Sahibinden", sahibindenText),
                            () -> Assertions.assertEquals("Galeriden", gallerryText),
                            () -> Assertions.assertEquals("Yetkili Bayiden", officialSellerText),
                            () -> Assertions.assertEquals("İlan Başlığı", classifiedTitleName)

                    );

            String URL = driver.getCurrentUrl();
            String classifieldId = findValue("#classifiedId", "getText", driver);
            Assertions.assertTrue(URL.contains(classifieldId));
            logger.info("İlan detay sayfasının urldeki ilan no ile ilan detaydaki ilan no'nun aynı olduğu görüldü");

            String classifiedInfoListText = driver.findElement(By.xpath("//ul[@class='classifiedInfoList']/li[5]")).getText();
            logger.info(classifiedInfoListText);

        }

        catch (Exception exception)
        {

            File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            logger.error("Test fail ekran görüntüsü ektedir");
        }

    }

    private static String findValue(String css, String type, WebDriver driver)
    {
        WebElement a = driver.findElement(By.cssSelector(css));

        if (type == "getText")
        {
            return (a.getText());
        }

        return (a.getAttribute("title"));

    }

    @AfterEach
    public void afterEach()
    {
        driver.quit();
        logger.info("Chromedriver kapandı");
    }

}

