package org.opencart.Zones;

import java.time.Duration;

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

public class simple {
    private SelfHealingDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and wrap with SelfHealingDriver
        WebDriver delegate = new FirefoxDriver();
        driver = SelfHealingDriver.create(delegate);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the HTML page
        driver.get("file:///Users/testvagrant/Zones/src/test/java/org/opencart/Zones/simple.html");
    }

    @Test
    public void testSubmitButton() {
        // Ensure JavaScript has been executed (if needed)
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("return document.readyState").equals("complete");

        // Enter text into the input field
        WebElement inputField = driver.findElement(By.id("text-input"));
        inputField.sendKeys("Hello, Selenium!");

        // Click the submit button
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        // Wait for the result to be displayed
        WebElement resultDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));

        // Verify the result
        String resultText = resultDiv.getText();
        Assert.assertEquals(resultText, "You entered: Hello, Selenium!", "The result text is incorrect.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
