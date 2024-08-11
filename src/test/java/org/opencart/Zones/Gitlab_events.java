package org.opencart.Zones;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;



import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gitlab_events{

    public static void main(String[] args) {
        // Set up Firefox WebDriver (adjust the path to geckodriver accordingly)
        //System.setProperty("webdriver.gecko.driver", "/path/to/geckodriver");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit wait
        
        try {
            // Navigate to the GitLab Events page
            driver.get("https://about.gitlab.com/events/");
            
            // Find all elements containing event dates and names
            List<WebElement> eventCards = driver.findElements(By.className("event-card"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy"); // Adjust pattern based on actual format
            
            // Get today's date
            LocalDate today = LocalDate.now();
            
            for (WebElement card : eventCards) {
                try {
                    // Extract date and event name
                    String dateText = card.findElement(By.className("event-card__dates")).getText();
                    String eventName = card.findElement(By.className("slp-text-align-left")).getText(); // Adjust based on actual class for event names

                    // Use regular expression to extract dates
                    Pattern pattern = Pattern.compile("(\\w+ \\d{1,2}, \\d{4})");
                    Matcher matcher = pattern.matcher(dateText);
                    
                    while (matcher.find()) {
                        String extractedDate = matcher.group(1); // Extracted date
                        try {
                            // Parse the extracted date
                            LocalDate eventDate = LocalDate.parse(extractedDate, formatter);
                            
                            // Check if the event date is more than 2 days from today
                            if (eventDate.isAfter(today.plusDays(2))) {
                                // Print event name
                                System.out.println("Event Name: " + eventName + ", Date: " + eventDate);
                                
                                // Click the event link
                                WebElement joinButton = card.findElement(By.className("slp-btn-primary")); // Adjust based on actual class for join button
                                
                                // Open link in a new tab
                                String joinButtonUrl = joinButton.getAttribute("href");
                                String script = "window.open(arguments[0], '_blank');";
                                ((JavascriptExecutor) driver).executeScript(script, joinButtonUrl);
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Date parsing error for text: " + extractedDate);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error processing card: " + e.getMessage());
                }
            }
        } finally {
            // Close the WebDriver
            //driver.quit();
        }
    }
}
