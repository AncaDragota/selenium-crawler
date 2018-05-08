package com.ancadragota.crawler.test.main;

import org.testng.annotations.DataProvider;

public class TyreDataProvider {

    private static Object[][] tyreDetails;

    @DataProvider
    public static Object[][] getTyreDetails() {
        return tyreDetails;
    }

    public static void setTyreDetails(Object[][] newTyreDetails) {
        tyreDetails = newTyreDetails;
    }
}
