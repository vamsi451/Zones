package org.opencart.Zones;
//import com.epam.healenium.SelfHealingDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.healenium.driver.InitDriver;
//import java.util.Properties;

public class SeleniumHQ {
    private WebDriver driver;
    private WebDriverWait wait;
   //private Properties healeniumProperties;

    @BeforeClass
    public void setUp() {
        // Load Healenium properties
       // healeniumProperties = new Properties();
      

        // Initialize SelfHealingDriver with ChromeDriver
        driver = InitDriver.getDriver(); // Assuming InitDriver initializes SelfHealingDriver
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the URL and validate it
        String url = "https://github.com/seleniumhq/selenium/pulls"; // Example URL
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url, "URL did not match!");

        // Take a screenshot of the initial page
        takeScreenshot("initial_page.png");
    }

    @Test
    public void testPullRequestTitles() {
        // Click on the "Pull Requests" tab and assert that it is selected
        WebElement pullRequestsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='pull-requests-tab']")));
        pullRequestsTab.click();

        boolean hasNextPage = true;
        int pageCount = 0;

        while (hasNextPage) {
            // Fetch all pull request titles on the current page and assert the list is not empty
            List<WebElement> pullRequestTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("a.Link--primary.v-align-middle.no-underline.h4.js-navigation-open.markdown-title")
            ));
            Assert.assertTrue(!pullRequestTitles.isEmpty(), "No pull requests found on this page.");

            // Print out all pull request titles and assert that each title is not empty
            System.out.println("Pull request titles on page " + (pageCount + 1) + ":");
            for (WebElement titleElement : pullRequestTitles) {
                String titleText = titleElement.getText();
                Assert.assertFalse(titleText.isEmpty(), "Found an empty pull request title!");
                System.out.println(titleText);
            }

            // Take a screenshot of the current page
            takeScreenshot("page_" + (pageCount + 1) + ".png");

            try {
                // Attempt to click the "Next" page button and assert that it worked
                WebElement nextPageButton = driver.findElement(By.xpath("//div[@class='paginate-container d-none d-sm-flex flex-sm-justify-center']//a[@aria-label='Next page'][normalize-space()='Next']"));
                nextPageButton.click();
                wait.until(ExpectedConditions.stalenessOf(pullRequestTitles.get(0)));
                pageCount++;
            } catch (Exception e) {
                // No more pages to navigate, assert that the loop ends here
                System.out.println("No more pages to navigate.");
                hasNextPage = false;
            }
        }

        // Assert that at least one page of pull requests was processed
        Assert.assertTrue(pageCount > 0, "No pages of pull requests were processed.");
    }

    @AfterClass
    public void tearDown() {
        // Take a final screenshot before closing the browser
        takeScreenshot("final_page.png");
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenshot(String fileName) {
        // Get the screenshot path from the configuration file
        String screenshotPath = "screenshots"; // Example path
        File screenshotFile = new File(screenshotPath, fileName);

        // Take the screenshot and save it to the specified location
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Ensure that the directory exists
            Files.createDirectories(screenshotFile.getParentFile().toPath());

            // Save the screenshot to the specified file
            Files.copy(screenshot.toPath(), screenshotFile.toPath());
            System.out.println("Screenshot saved to: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
