# Created by Sourour GAZZEH at 2/26/2023
Feature: change shop hours
  # Enter feature description here

  Background:
    Given a shop who wants to change his planning
  Scenario: change shop hours for specific day
    When The shop modifies its operating hours to be open monday between from 8 am and  8 pm
    Then the shop can change the shop hours for Monday

  Scenario: cannot change shop hours for specific day
    When The shop modifies its operating hours to be open monday between from 3 pm and  8 am
    Then the shop cannot change its operating hours to be open monday between from 3 pm and  8 am

  Scenario:  include an additional workday.
   When The shop include an additional workday
   Then the shop can  include an additional workday







