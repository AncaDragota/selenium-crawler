package com.ancadragota.crawler.test.page;

import com.ancadragota.crawler.test.utils.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"drpTyreTypeCloneSB\"]")
    private WebElement vehicleCategory;

    @FindBy(xpath = "//*[@id=\"drpTyreWidthSB\"]")
    private WebElement widthElement;
    @FindBy(xpath = "//*[@id=\"drpTyreCrossSectionSB\"]")
    private WebElement profileElement;
    @FindBy(xpath = "//*[@id=\"drpTyreDiameterSB\"]")
    private WebElement sizeElement;

    @FindBy(xpath = "//*[@id=\"content-groesse\"]/div[3]/span[2]/button")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"content-groesse\"]/div[3]/span[2]/button"));
    }

    public Object[][] crawlAllTyreDimensions() {
        selectVehicleCategory("Car");

        ArrayList<Object[]> arrayList = new ArrayList<>();

        widthElement.click();
        Utilities.waitForUnconditionalTime(100);

        //------------------------debug------------------------
        /*int startIndex = 5;
        int maxRepetitions = 0;
        int repeated = 0;*/
        //-----------------------------------------------------

        List<WebElement> allWidth = getAllOfElement(widthElement);
        Select widthDropDown = new Select(widthElement);
        for (WebElement widthElem : allWidth) {
            //------------------------debug------------------------
            /*repeated++;
            if (repeated < startIndex) continue;*/
            //-----------------------------------------------------

            Utilities.waitForUnconditionalTime(500);
            widthDropDown.selectByVisibleText(widthElem.getText());
            Utilities.waitForUnconditionalTime(600);
            profileElement = driver.findElement(By.xpath("//*[@id=\"drpTyreCrossSectionSB\"]"));
            Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"drpTyreCrossSectionSB\"]"));
            Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"drpTyreDiameterSB\"]"));

            profileElement.click();
            Utilities.waitForUnconditionalTime(100);

            List<WebElement> allProfile = getAllOfElement(profileElement);
            Select profileDropDown = new Select(profileElement);
            for (WebElement profileElem : allProfile) {
                Utilities.waitForUnconditionalTime(500);
                profileDropDown.selectByVisibleText(profileElem.getText());
                Utilities.waitForUnconditionalTime(600);
                sizeElement = driver.findElement(By.xpath("//*[@id=\"drpTyreDiameterSB\"]"));
                Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"drpTyreDiameterSB\"]"));

                sizeElement.click();
                Utilities.waitForUnconditionalTime(100);

                List<WebElement> allSize = getAllOfElement(sizeElement);
                Select sizeDropDown = new Select(sizeElement);
                for (WebElement sizeElem : allSize) {
                    Utilities.waitForUnconditionalTime(500);
                    sizeDropDown.selectByVisibleText(sizeElem.getText());
                    Utilities.waitForUnconditionalTime(600);

                    Object[] searchEntry = new Object[]{
                            widthDropDown.getFirstSelectedOption().getText(),
                            profileDropDown.getFirstSelectedOption().getText(),
                            sizeDropDown.getFirstSelectedOption().getText()
                    };
                    arrayList.add(searchEntry);
                    Utilities.waitForUnconditionalTime(100);
                }
            }

            //------------------------debug------------------------
            /*if (repeated ==  startIndex + maxRepetitions) break;*/
            //-----------------------------------------------------
        }

        Object[][] arrayObject = new Object[arrayList.size()][];
        for (int i = 0; i < arrayObject.length; i++) {
            arrayObject[i] = arrayList.get(i);
        }

        return arrayObject;
    }

    public void searchForTireDimensions(String width, String profile, String size) {
        Utilities.waitForElementByLocator(driver, By.xpath("//*[@id=\"content-groesse\"]/div[3]/span[2]/button"));
        selectVehicleCategory("Car");
        Utilities.waitForUnconditionalTime(500);
        selectWidth(width);
        Utilities.waitForUnconditionalTime(1000);
        selectProfile(profile);
        Utilities.waitForUnconditionalTime(1000);
        selectSize(size);
        Utilities.waitForUnconditionalTime(1000);
        searchButton.click();
    }

    private void selectVehicleCategory(String typeForCategory) {
        Select vehicleCategoryDropDown = new Select(vehicleCategory);
        vehicleCategoryDropDown.selectByValue(typeForCategory);
        Utilities.waitForUnconditionalTime(500);
    }

    private List<WebElement> getAllOfElement(WebElement element) {
        Select elementDropDown = new Select(element);
        return elementDropDown.getOptions();
    }

    private void selectWidth(String width) {
        Select widthDropDown = new Select(widthElement);
        widthDropDown.selectByVisibleText(width);
        widthElement.click();
    }

    private void selectProfile(String profile) {
        Select profileDropDown = new Select(profileElement);
        profileDropDown.selectByVisibleText(profile);
        profileElement.click();
    }

    private void selectSize(String size) {
        Select sizeDropDown = new Select(sizeElement);
        sizeDropDown.selectByValue(size);
        sizeElement.click();
    }
}
