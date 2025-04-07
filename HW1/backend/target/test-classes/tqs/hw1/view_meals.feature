Feature: View meals and weather forecast for selected restaurant

  Scenario: User selects a restaurant and date to view planned meals and weather forecast
    Given the user navigates to the homepage
    When the user selects a restaurant from the dropdown
    And the user selects a date for the meals
    And the user confirms the selection
    Then the meals for the selected restaurant and date should be displayed
    And the weather forecast for the selected date should be shown for the chosen city

#  Scenario: User makes a reservation for a meal
#    Given the user navigates to the homepage
#    When the user selects a restaurant from the dropdown
#    And the user selects a date for the meals
#    And the user confirms the selection
#    And the user clicks on a meal to make a reservation
#    Then a confirmation modal should be displayed
#    When the user confirms the reservation
#    Then the success modal should be displayed with a reservation code
