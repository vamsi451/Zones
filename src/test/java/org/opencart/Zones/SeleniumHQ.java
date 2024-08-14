package org.opencart.Zones;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SeleniumHQ {
    private WebDriver driver;
    private WebDriverWait wait;
    private ConfigReader configReader;
    private BrowserManager browserManager;

    @BeforeClass
    public void setUp() {
        // Initialize ConfigReader and BrowserManager
        configReader = new ConfigReader("Config.properties");
        browserManager = new BrowserManager();
        driver = browserManager.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to the Selenium GitHub page and validate the URL
        String url = configReader.getProperty("github.selenium.url");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("URL property is not set or is empty in the configuration file.");
        }
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url, "URL did not match!");
    }

    @Test
    public void testPullRequestTitles() {
        // Click on the "Pull Requests" tab and assert that it is selected
        WebElement pullRequestsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='pull-requests-tab']")));
        pullRequestsTab.click();
        //Assert.assertTrue(pullRequestsTab.getAttribute("aria-selected").equals("true"), "Pull Requests tab was not selected!");

        boolean hasNextPage = true;
        int pageCount = 0;

        while (hasNextPage) {
            // Fetch all pull request titles on the current page and assert the list is not empty
            List<WebElement> pullRequestTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("a.Link--primary.v-align-middle.no-underline.h4.js-navigation-open.markdown-title")
            ));
            Assert.assertTrue(pullRequestTitles.size() > 0, "No pull requests found on this page.");

            // Print out all pull request titles and assert that each title is not empty
            System.out.println("Pull request titles on page " + (pageCount + 1) + ":");
            for (WebElement titleElement : pullRequestTitles) {
                String titleText = titleElement.getText();
                Assert.assertFalse(titleText.isEmpty(), "Found an empty pull request title!");
                System.out.println(titleText);
            }

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
        browserManager.quitDriver();
    }
}
