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

public class simple1 {
    private SelfHealingDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver and wrap with SelfHealingDriver
        WebDriver delegate = new FirefoxDriver();
        driver = SelfHealingDriver.create(delegate);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the HTML page
        driver.get("file:///Users/testvagrant/Documents/Zones/src/test/java/org/opencart/Zones/simple2.html");
    }

    @Test
    public void testSubmitButton() {
        // Ensure JavaScript has been executed
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("return document.readyState").equals("complete");

        // Enter text into the input field using XPath
        WebElement inputField = driver.findElement(By.xpath("//input[@id='text-input']"));
        inputField.sendKeys("Hello, Selenium!");

        // Assert that the character count updates correctly
        WebElement charCountDiv = driver.findElement(By.xpath("//div[@id='char-count']"));
        String charCountText = charCountDiv.getText();
        Assert.assertTrue(charCountText.contains("Character count: 16"), "Character count did not update correctly.");

        // Click the submit button using XPath
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit-btn']"));
        submitButton.click();

        // Wait for the result to be displayed and verify the result
        WebElement resultDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='result']")));
        String resultText = resultDiv.getText();
        Assert.assertEquals(resultText, "You entered: Hello, Selenium!", "The result text is incorrect.");
    }

    @Test
    public void testResetButton() {
        // Enter text into the input field using XPath
        WebElement inputField = driver.findElement(By.xpath("//input[@id='text-input']"));
        inputField.sendKeys("Reset Test");

        // Click the reset button using XPath
        WebElement resetButton = driver.findElement(By.xpath("//button[@id='reset-btn']"));
        resetButton.click();

        // Assert that the input field is cleared
        String inputText = inputField.getAttribute("value");
        Assert.assertEquals(inputText, "", "The input field was not cleared.");

        // Assert that the result div is cleared
        WebElement resultDiv = driver.findElement(By.xpath("//div[@id='result']"));
        String resultText = resultDiv.getText();
        Assert.assertEquals(resultText, "", "The result text was not cleared.");

        // Assert that the character count is reset
        WebElement charCountDiv = driver.findElement(By.xpath("//div[@id='char-count']"));
        String charCountText = charCountDiv.getText();
        Assert.assertTrue(charCountText.contains("Character count: 0"), "Character count did not reset correctly.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
