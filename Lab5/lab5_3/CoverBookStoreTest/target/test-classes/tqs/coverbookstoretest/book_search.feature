Feature: Search for books in the online library

  Scenario: Search for an existing book by title
    Given the user is on the bookstore homepage
    When the user searches for "Harry Potter"
    Then at least one book containing "Harry Potter" should be displayed

  Scenario: Search for a book by author
    Given the user is on the bookstore homepage
    When the user searches for books by "J.K. Rowling"
    Then at least one book by "J.K. Rowling" should be displayed

  Scenario: Search for a non-existent book
    Given the user is on the bookstore homepage
    When the user searches for "Unknown Book 12345"
    Then no search results should be displayed

 