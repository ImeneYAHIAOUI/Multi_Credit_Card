# Created by Sourour GAZZEH at 2/28/2023
Feature: making changes to the gift catalog
  # Enter feature description here

  Scenario:  add gift to catalog
    Given a shop with an empty gift list
    When the shop adds a gift to the catalog
    Then the gift is added to the catalog

  Scenario: add an existing gift to catalog
    Given a shop with a gift list
    When shop includes an existing gift in its catalog.
    Then the gift is not added to the catalog because it already exists

  Scenario: remove gift from catalog
    Given a shop with a gift list
    When the shop remove a gift from the catalog
    Then the gift is removed from the catalog

  Scenario: remove an existing gift to catalog
    Given a shop with an empty gift list
    When shop removes a non-existent gift from its catalog.
    Then the gift is not removed from the catalog because it does not exist








