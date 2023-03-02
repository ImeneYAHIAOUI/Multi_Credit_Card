# Created by Imene at 2/16/2023
Feature: create a new account
  # Enter feature description here

  Background:
    Given a member who wants to create an account

  Scenario: valid member
    When the member submits the name "John doe"
    And the mail "John.Doe@mail.com"
    And the password "password"
    And the birth date "11/04/2001"
    Then this account is created

    Scenario: missing name
      And the mail "John.Doe@mail.com"
      And the password "password"
      And the birth date "11/04/2001"
      Then this account is not created because of missing information

    Scenario: missing mail
      When the member submits the name "John doe"
      And the password "password"
      And the birth date "11/04/2001"
      Then this account is not created because of missing information


    Scenario: missing password
      When the member submits the name "John doe"
      And the mail "John.Doe@mail.com"
      And the birth date "11/04/2001"
      Then this account is not created because of missing information

    Scenario: missing birth date
      When the member submits the name "John doe"
      And the mail "John.Doe@mail.com"
      And the password "password"
      Then this account is not created because of missing information

  Scenario: underage member
    When the member submits the name "John doe"
    And the mail "John.Doe@mail.com"
    And the password "password"
    And the birth date "11/04/2010"
    Then this account is not created because they are under age

    Scenario: existing mail
        When the member submits the name "John doe"
      And the mail "John.Doe@mail.com"
      And the password "password"
      And the birth date "11/04/2001"
      And this member tries to create an account with the same mail
      Then this account is not created because they are already a member

