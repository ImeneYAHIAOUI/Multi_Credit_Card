# Created by Sourour GAZZEH at 2/26/2023
Feature: Loading money to a card online
  # Enter feature description here
  Background:
    Given a client has a valid account in the system

  Scenario: Successfully loading money to a card
    When the client load card with money
    Then the money should be successfully loaded to the card
    And the card balance increases.

  Scenario: Loading money to a card that is declined by the bank
    Then the bank should decline the card loading
    And the system should indicate that the loading was unsuccessful
    And the card balance should remain the same










