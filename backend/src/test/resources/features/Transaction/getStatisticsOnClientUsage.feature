# Created by Sourour GAZZEH at 2/26/2023
Feature: get statistics on client usage
  # Enter feature description here
  Background:
    Given A customer has a valid account with numerous transactions

  Scenario:  get statistics on client usage application
    When the client has made at least two transactions using the application
    Then the usage statistics for the client will display a count of the two transactions.

  Scenario:  get statistics on client usage application at shop
    When the client has made at least one transactions using the application at shop with name "Sephora"
    Then the usage statistics for the client will display a count of the one transaction.








