
package org.opencart.Zones;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.healenium.SelfHealingDriver;

public class GitlabEvents {
    private SelfHealingDriver driver;
    private WebDriverWait wait;
    private ConfigReader configReader;

    @BeforeClass
public void setUp() {
    // Initialize ConfigReader
    configReader = new ConfigReader("Config.properties");

    // Get the GitLab events URL
    String url = configReader.getProperty("gitlab.events.url");
    if (url == null || url.isEmpty()) {
        throw new IllegalStateException("URL property is not set or is empty.");
    }

    // Set up Firefox WebDriver
    WebDriver Delegate = new FirefoxDriver();
    driver = SelfHealingDriver.create(Delegate);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Navigate to the GitLab Events page
    driver.get(url);
}

    

    @Test 
    public void testEventDates() {
        // Find all elements containing event dates and names
        List<WebElement> eventCards = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("event-card")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");

        // Get today's date
        LocalDate today = LocalDate.now();

        boolean eventFound = false;  // Flag to track if any valid event was found

        for (WebElement card : eventCards) {
            try {
                // Extract date and event name
                String dateText = card.findElement(By.className("event-card__dates")).getText();
                String eventName = card.findElement(By.className("slp-text-align-left")).getText();

                // Use regular expression to extract dates
                Pattern pattern = Pattern.compile("(\\w+ \\d{1,2}, \\d{4})");
                Matcher matcher = pattern.matcher(dateText);

                while (matcher.find()) {
                    String extractedDate = matcher.group(1);
                    try {
                        // Parse the extracted date
                        LocalDate eventDate = LocalDate.parse(extractedDate, formatter);

                        // Check if the event date is more than 2 days from today
                        if (eventDate.isAfter(today.plusDays(2))) {
                            // Print event name
                            System.out.println("Event Name: " + eventName + ", Date: " + eventDate);

                            // Click the event link
                            WebElement joinButton = card.findElement(By.className("slp-btn-primary"));
                            String joinButtonUrl = joinButton.getAttribute("href");

                            // Open link in a new tab
                            String script = "window.open(arguments[0], '_blank');";
                            ((JavascriptExecutor) driver).executeScript(script, joinButtonUrl);

                            eventFound = true;  // Set flag to true if a valid event is found
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Date parsing error for text: " + extractedDate);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing card: " + e.getMessage());
            }
        }

        // Assert that at least one valid event was found
        Assert.assertTrue(eventFound, "No valid events found that are more than 2 days away from today.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
