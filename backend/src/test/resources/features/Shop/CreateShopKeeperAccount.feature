# Created by Sourour GAZZEH at 2/26/2023
Feature: Create shop keeper account
  # Enter feature description here

  Background:
    Given a shop who wants to create an account

  Scenario: valid shop
    When the shop submits the name "John doe"
    And the mail is "John.Doe@mail.com"
    And the password is "password"
    And the birth date is "11/04/2001"
    Then this shop keeper account is created

  Scenario: missing name
    And the mail is "John.Doe@mail.com"
    And the password is "password"
    And the birth date is "11/04/2001"
    Then this shop keeper account is not created because of missing information

  Scenario: missing mail
    When the shop submits the name "John doe"
    And the password is "password"
    And the birth date is "11/04/2001"
    Then this shop keeper account is not created because of missing information


  Scenario: missing password
    When the shop submits the name "John doe"
    And the mail is "John.Doe@mail.com"
    And the birth date is "11/04/2001"
    Then this shop keeper account is not created because of missing information

  Scenario: missing birth date
    When the shop submits the name "John doe"
    And the mail is "John.Doe@mail.com"
    And the password is "password"
    Then this shop keeper account is not created because of missing information

  Scenario: underage shop
    When the shop submits the name "John doe"
    And the mail is "Joohn.Doe@mail.com"
    And the password is "password"
    And the birth date is "11/04/2010"
    Then this shop keeper account is not created because they are under age

  Scenario: existing mail
    When the shop submits the name "John doe"
    And the mail is "John.Doe@mail.com"
    And the password is "password"
    And the birth date is "11/04/2001"
    And this shop tries to create an account with the same mail
    Then this shop keeper account is not created because they are already a member






