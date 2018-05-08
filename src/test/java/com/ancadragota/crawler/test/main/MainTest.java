package com.ancadragota.crawler.test.main;

import com.ancadragota.crawler.test.page.HomePage;
import com.ancadragota.crawler.test.page.SearchResultPage;
import com.ancadragota.crawler.test.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private WebDriver driver;

    private FileWriter writer = null;
    private File csvFile = null;

    @BeforeTest
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver_win32_2.34\\chromedriver");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get("https://www.reifen.com/");

        Utilities.waitForUnconditionalTime(1000);

        try {
            csvFile = new File("csvCrawler.csv");
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }
            writer = new FileWriter(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test(priority = 1)
    public void getAllTyresDimensions() {

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        TyreDataProvider.setTyreDetails(homePage.crawlAllTyreDimensions());

    }

    @Test(priority = 2, dataProvider = "getTyreDetails", dataProviderClass = TyreDataProvider.class)
    public void getAllTyresResultForSearch(String width, String profile, String size) {

        driver.get("https://www.reifen.com/");

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.searchForTireDimensions(width, profile, size);

        try {
            writer = new FileWriter(csvFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchResultPage searchResultPage = PageFactory.initElements(driver, SearchResultPage.class);
        searchResultPage.crawlSearchResults(writer, width, profile, size);
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterTest
    public void tearDown() {

        Utilities.waitForUnconditionalTime(3000);

        driver.close();
        driver.quit();

    }

}
