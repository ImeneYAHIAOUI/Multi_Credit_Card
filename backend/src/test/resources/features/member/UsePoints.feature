# Created by Sourour GAZZEH at 2/26/2023
Feature: use points
  # Enter feature description here
  Background:
    Given a client has an account
  Scenario: client can use points
    Given the client has 150 points
    When the client uses points to obtain a gift
    Then the client gets the gift

    Scenario: client can't use points
      Then the client is unable to obtain a gift because he does not have enough points.
      And  the client doesn't get the gift

  Scenario: client can't use points because he hasn't the status required
    Then the client is unable to obtain a gift because he hasn't the status required
    And  the client doesn't get the gift
  Scenario: client can't use points because he doesn't make a purchase before
    Then the client is unable to obtain a gift because he doesn't make a purchase before
    And  the client doesn't get the gift








