package org.opencart.Zones;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SeleniumHQ {
    public static void main(String[] args) {

        // Initialize WebDriver (Firefox)
      //  System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Navigate to the GitHub Selenium repository
            driver.get("https://github.com/SeleniumHQ/selenium");
            System.out.println("Navigated to GitHub Selenium repository.");

            // Click on the "Pull Requests" tab
            System.out.println("Waiting for Pull Requests tab to be clickable...");
            WebElement pullRequestsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='pull-requests-tab']")));
            pullRequestsTab.click();
            System.out.println("Clicked on Pull Requests tab.");

            boolean hasNextPage = true;

            while (hasNextPage) {
                // Fetch all pull request titles on the current page
                System.out.println("Fetching pull request titles...");
                List<WebElement> pullRequestTitles = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("a.Link--primary.v-align-middle.no-underline.h4.js-navigation-open.markdown-title")
                ));

                // Print out all pull request titles
                System.out.println("Pull request titles on this page:");
                for (WebElement titleElement : pullRequestTitles) {
                    System.out.println(titleElement.getText());
                }

                try {
                    // Click on the "Next" page button
                    System.out.println("Attempting to click Next page button...");
                    WebElement nextPageButton = driver.findElement(By.xpath("//div[@class='paginate-container d-none d-sm-flex flex-sm-justify-center']//a[@aria-label='Next page'][normalize-space()='Next']"));
                    nextPageButton.click();

                    // Wait for the new page to load
                    wait.until(ExpectedConditions.stalenessOf(pullRequestTitles.get(0)));
                    // Optional sleep to ensure all content is loaded
                    Thread.sleep(1000);
                    System.out.println("Navigated to the next page.");

                } catch (Exception e) {
                    // No more pages to navigate
                    System.out.println("No more pages to navigate.");
                    hasNextPage = false;
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred:");
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
