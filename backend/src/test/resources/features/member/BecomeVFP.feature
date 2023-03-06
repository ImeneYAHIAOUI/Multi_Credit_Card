# Created by imene at 3/3/2023
Feature: become a vfp member
  # Enter feature description here

  Background:
    Given a member with name "john", mail "doe", password "password" and birthdate "11/04/2001"
  Scenario: Becoming a vfp member
    When the member statue is regular
    And the member makes 3 purchases
    When the member account status gets updated
    Then the member statue becomes regular