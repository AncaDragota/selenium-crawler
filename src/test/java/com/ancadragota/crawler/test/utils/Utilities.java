package com.ancadragota.crawler.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.concurrent.TimeUnit;

/**
 * Utilities class used to store common actions that are not need to be tied with a specific test class
 */
public class Utilities {

    /**
     * Use Fluent Conditional Wait for an element to be visible
     * @param driver WebDriver used
     * @param locator element to be located by locator
     */
    public static void waitForElementByLocator(WebDriver driver, final By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                //how much to wait
                .withTimeout(10, TimeUnit.SECONDS)
                // frequency of check
                .pollingEvery(1, TimeUnit.SECONDS)
                // ignore exception
                .ignoring(NoSuchElementException.class);

//        wait.until(driver1 -> driver1.findElement(locator));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Use Unconditional Wat to wait a certain amount of time
     * @param millis how much time to wait in milliseconds
     */
    public static void waitForUnconditionalTime(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
