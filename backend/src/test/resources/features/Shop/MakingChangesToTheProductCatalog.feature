# Created by Sourour GAZZEH at 2/28/2023
Feature: making changes to the product catalog
  # Enter feature description here

  Scenario:  add product to catalog
    Given a shop with an empty product list
    When the shop adds a product to the catalog
    Then the product is added to the catalog

  Scenario: add an existing product to catalog
    Given a shop with a product list
    When shop includes an existing product in its catalog.
    Then the product is not added to the catalog because it already exists

  Scenario: remove product from catalog
    Given a shop with a product list
    When the shop remove a product from the catalog
    Then the product is removed from the catalog

  Scenario: remove an existing product to catalog
    Given a shop with an empty product list
    When shop removes a non-existent product from its catalog.
    Then the product is not removed from the catalog because it does not exist








