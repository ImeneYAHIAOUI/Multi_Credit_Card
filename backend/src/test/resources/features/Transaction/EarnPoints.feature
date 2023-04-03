# Created by Sourour GAZZEH at 2/26/2023
Feature: earns points
  # Enter feature description here
  Background:
    Given a client has an account in the system

  Scenario: client earn points when he makes a purchase
    When the client makes a valid purchase
    Then the client earns points

  Scenario: client doesn't earn points when he makes a purchase
    When the client makes an invalid purchase
    Then the client doesn't earn points








