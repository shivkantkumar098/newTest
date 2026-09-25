package com.javaAT.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static volatile DriverManager instance;
    private WebDriver driver;

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (instance == null) {
            synchronized (DriverManager.class) {
                if (instance == null) {
                    instance = new DriverManager();
                }
            }
        }
        return instance;
    }

    public void startDriver(String browser) {
        if (driver != null) {
            throw new IllegalStateException("WebDriver is already started");
        }
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver has not been started");
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
