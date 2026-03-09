Feature: eBay Monopoly Search and Purchase Flow

  Background:
    Given I navigate to "https://www.ebay.com/"

  @homepage @smoke
  Scenario: Verify eBay homepage loads correctly
    Then the eBay homepage should be loaded correctly

  @search @smoke
  Scenario: Search for Monopoly in Toys and Hobbies category
    When I select "Toys & Hobbies" from the category dropdown
    And  I search for "Monopoly Board Game"
    Then the first result title should contain "Monopoly"
    And  the first result should display a price

  @details 
  Scenario: Verify item detail page content matches search results
    When I select "Toys & Hobbies" from the category dropdown
    And  I search for "Monopoly Board Game"
    And  I click on the first result
    Then the detail page title should contain "Monopoly"
    And  the detail page price should match the search results price

  @shipping
  Scenario: Verify item can be shipped to Bulgaria
    When I select "Toys & Hobbies" from the category dropdown
    And  I search for "Monopoly Board Game"
    And  I click on the first result
    When I click the See details link
    Then the shipping popup should confirm shipping to "Bulgaria"

  @cart
  Scenario: Add 2 items to cart and verify cart page
    When I select "Toys & Hobbies" from the category dropdown
    And  I search for "Monopoly Board Game"
    And  I click on the first result
    When I select quantity "2"
    And  I click Add to cart
    Then the URL should contain "cart.ebay.com"
    And  the cart quantity should show "2"
    And  the cart should display the price for 2 items