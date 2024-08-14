package org.opencart.Zones;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserManager {
    private WebDriver driver;

    public BrowserManager() {
        // Set up WebDriver Manager
        WebDriverManager.firefoxdriver().setup();
        // Initialize the WebDriver instance
        driver = new FirefoxDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
