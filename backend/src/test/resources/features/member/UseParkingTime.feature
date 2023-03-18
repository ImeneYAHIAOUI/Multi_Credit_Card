# Created by imene at 3/3/2023
Feature: use parking time

  Background:
    Given a member

    Scenario: VFP member
      When the member is a vfp
      And they want to use parking time
      Then they get a positive response

    Scenario: Regular member
      When the member is regular
      And they want to use parking time
      Then they get a negative response