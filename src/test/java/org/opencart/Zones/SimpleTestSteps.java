package org.opencart.Zones;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.epam.healenium.SelfHealingDriver;

import java.time.Duration;

public class SimpleTestSteps {

    private SelfHealingDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriver delegate = new FirefoxDriver();
        driver = SelfHealingDriver.create(delegate);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("I open the test page")
    public void iOpenTheTestPage() {
        String filePath = "file:///Users/testvagrant/Documents/Zones/src/test/java/org/opencart/Zones/simple2.html";
        driver.get(filePath);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("return document.readyState").equals("complete");
    }

    @When("I enter {string} in the text field")
    public void iEnterInTheTextField(String text) {
        WebElement inputField = driver.findElement(By.xpath("//input[@id='text-input']"));
        inputField.sendKeys(text);
    }

    @When("I click the submit button")
    public void iClickTheSubmitButton() {
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit-btn']"));
        submitButton.click();
    }

    @Then("I should see {string} in the result div")
    public void iShouldSeeInTheResultDiv(String expectedText) {
        WebElement resultDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='result']")));
        String resultText = resultDiv.getText();
        Assert.assertEquals(resultText, expectedText, "The result text is incorrect.");
    }

    @When("I click the reset button")
    public void iClickTheResetButton() {
        WebElement resetButton = driver.findElement(By.xpath("//button[@id='reset-btn']"));
        resetButton.click();
    }

    @Then("the text field should be empty")
    public void theTextFieldShouldBeEmpty() {
        WebElement inputField = driver.findElement(By.xpath("//input[@id='text-input']"));
        String inputText = inputField.getAttribute("value");
        Assert.assertEquals(inputText, "", "The input field was not cleared.");
    }

    @Then("the result div should be empty")
    public void theResultDivShouldBeEmpty() {
        WebElement resultDiv = driver.findElement(By.xpath("//div[@id='result']"));
        String resultText = resultDiv.getText();
        Assert.assertEquals(resultText, "", "The result text was not cleared.");
    }

    @Then("the character count should be {string}")
    public void theCharacterCountShouldBe(String expectedCount) {
        WebElement charCountDiv = driver.findElement(By.xpath("//div[@id='char-count']"));
        String charCountText = charCountDiv.getText();
        Assert.assertTrue(charCountText.contains(expectedCount), "Character count did not reset correctly.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
