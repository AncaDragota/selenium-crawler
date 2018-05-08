package com.ancadragota.crawler.test.page;

import com.ancadragota.crawler.test.utils.CSVUtils;
import com.ancadragota.crawler.test.utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchResultPage {

    private WebDriver driver;

    @FindBys({@FindBy(className = "secondHeaderStyle")})
    private List<WebElement> brandElements;
    @FindBys({@FindBy(className = "inset")})
    private List<WebElement> detailsElements;
    @FindBys({@FindBy(className = "price")})
    private List<WebElement> priceElements;

    public SearchResultPage(WebDriver driver) {
        this.driver = driver;
        Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"containerBenefits\"]/div[3]/p/a/span"));
    }

    public void crawlSearchResults(FileWriter writer, String width, String profile, String size) {
        crawlResultsForCurrentPage(writer, width, profile, size);

        int pageID = 2;
        while (driver.findElements(By.id("btnPage" + pageID)).size() != 0) {
            driver.findElement(By.id("btnPage" + pageID)).click();
            Utilities.waitForUnconditionalTime(3000);
            brandElements = driver.findElements(By.className("secondHeaderStyle"));
            detailsElements = driver.findElements(By.className("inset"));
            priceElements = driver.findElements(By.className("price"));
            Utilities.waitForUnconditionalTime(1000);
            crawlResultsForCurrentPage(writer, width, profile, size);
            pageID++;
        }
    }

    private void crawlResultsForCurrentPage(FileWriter writer, String sWidth, String sProfile, String sSize) {
        Utilities.waitForUnconditionalTime(1000);

        for (int j = 0; j < brandElements.size() && j < detailsElements.size(); j++) {
            Utilities.waitForUnconditionalTime(100);

            String brand = brandElements.get(j).getText().trim();

            String details = detailsElements.get(j).getText().trim();
            String width = sWidth;
            if (!details.contains(width)) {
                width = "width";
            }
            String profile = sProfile;
            if (!details.contains(profile)) {
                profile = "profile";
            }
            String size = sSize;
            if (!details.contains(size)) {
                size = "size";
            }

            String profile_name = size.equals(sSize) ? details.substring(details.indexOf(size) + 1).substring(details.indexOf(" ") + 1).trim() : "profile_name";

            String price = priceElements.get(j).getText().trim();
            price = price.substring(0, price.length() - 3) + "EUR";

            try {
                CSVUtils.writeLine(writer, Arrays.asList(width, profile, size, brand, profile_name, price));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
