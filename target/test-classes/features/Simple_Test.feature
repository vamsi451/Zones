Feature: Test Submit and Reset Functionality

  Scenario: Submit button displays entered text
    Given I open the test page
    When I enter "Hello, Selenium!" in the text field
    And I click the submit button
    Then I should see "You entered: Hello, Selenium!" in the result div
@test
  Scenario: Reset button clears the input and result
    Given I open the test page
    When I enter "Reset Test" in the text field
    And I click the reset button
    Then the text field should be empty
    And the result div should be empty
    And the character count should be "Character count: 0"
