# Created by Sourour GAZZEH at 70/4/2023
Feature: Create admin account
  # Enter feature description here

  Background:
    Given a admin who wants to create an account

  Scenario: valid admin
    When the admin submits the name "John doe"
    And admin mail is "John.Doe@mail.com"
    And admin password is "password"
    And admin birth date is "11/04/2001"
    Then this admin account is created

  Scenario: missing name
    And admin mail is "John.Doe@mail.com"
    And admin password is "password"
    And admin birth date is "11/04/2001"
    Then this admin account is not created because of missing information

  Scenario: missing mail
    When the admin submits the name "John doe"
    And admin password is "password"
    And admin birth date is "11/04/2001"
    Then this admin account is not created because of missing information


  Scenario: missing password
    When the admin submits the name "John doe"
    And admin mail is "John.Doe@mail.com"
    And admin birth date is "11/04/2001"
    Then this admin account is not created because of missing information

  Scenario: missing birth date
    When the admin submits the name "John doe"
    And admin mail is "John.Doe@mail.com"
    And admin password is "password"
    Then this admin account is not created because of missing information


  Scenario: existing mail
    When the admin submits the name "John doe"
    And admin mail is "John.Doe@mail.com"
    And admin password is "password"
    And admin birth date is "11/04/2001"
    And this admin tries to create an account with the same mail
    Then this admin account is not created because they are already a admin






