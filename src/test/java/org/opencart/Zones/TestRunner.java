package org.opencart.Zones;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "org.opencart.Zones", // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        tags ="@testal"// Reporting options
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
