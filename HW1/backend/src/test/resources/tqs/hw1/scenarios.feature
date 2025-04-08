Feature: View meals and weather forecast for selected restaurant

  Scenario: User selects a restaurant and date to view planned meals and weather forecast
    Given the user navigates to the homepage
    When the user selects a restaurant from the dropdown
    And the user selects a date for the meals
    And the user confirms the selection
    Then the meals for the selected restaurant and date should be displayed
    And the weather forecast for the selected date should be shown for the chosen city

  Scenario: User makes a reservation for a meal
    Given the user navigates to the homepage
    When the user selects a restaurant from the dropdown
    And the user selects a date for the meals
    And the user confirms the selection
    And the user clicks on a meal to make a reservation
    Then a confirmation modal should be displayed
    When the user confirms the reservation
    Then the success modal should be displayed with a reservation code

  Scenario: Food services worker verifies a reservation
    Given the admin navigates to the "admin" page
    And there is at least one reservation with status "Pendente"
    When the admin clicks to mark the reservation as used
    Then the reservation status should change to "Usada"
    And the verify button should no longer be visible

  Scenario: The user checks the active reservation and cancels it
    Given the user navigates to the "My Reservations" page
    When the user sees the list of active reservations
    And the user chooses to cancel a reservation
    And the user confirms the cancellation
    Then the reservation should be removed from the list
    And a success message should be displayed