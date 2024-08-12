package org.opencart.Zones;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class SonarQube {
    public static void main(String[] args) {
        // Set the path to the Firefox driver executable
        //System.setProperty("webdriver.gecko.driver", "/path/to/geckodriver");

        // Initialize the Firefox WebDriver
        WebDriver driver = new FirefoxDriver();

        try {
            // Navigate to the SonarQube "What's New" page
            driver.get("https://www.sonarsource.com/products/sonarqube/whats-new/");
            try {
                Thread.sleep(30000); // 30000 milliseconds = 30 seconds
            } catch (InterruptedException e) {
                // Handle the InterruptedException
                System.err.println("Thread sleep was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }

            // Locate the elements containing the dates using CSS selectors for compound class names
            List<WebElement> dateElements = driver.findElements(By.cssSelector(".css-yqzwap.e1lgj9y93"));

            // Locate the elements containing the feature names using CSS selectors for compound class names
            List<WebElement> featureElements = driver.findElements(By.cssSelector(".css-swfhcj.e1lgj9y92"));

            // Check if the lists are of the same size
            if (dateElements.size() != featureElements.size()) {
                System.out.println("The number of dates and features do not match.");
                return;
            }

            // Print the dates with their corresponding features
            System.out.println("Dates and Feature Names:");
            for (int i = 0; i < dateElements.size(); i++) {
                String date = dateElements.get(i).getText();
                String feature = featureElements.get(i).getText();
                System.out.println("Date: " + date + " - Feature: " + feature);
            }
    


        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
