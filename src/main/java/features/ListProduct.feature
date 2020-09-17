Feature: List Product Name 



Scenario: List product name and add the discounted product to the cart 
	Given User is loaded into the home page http://automationpractice.com/index.php 
	Then Print the name of the products 
	When Add the product with 20% discount 
	Then Verifying the product is added to the cart 
	
Scenario: Sort the products and verify 
	Given Navigate to the page http://automationpractice.com/index.php?id_category=3&controller=category 
	Then Sort the given products based on Price 
	
Scenario: Toggle colors to verify image and verify logo
	Given Navigate to the page http://automationpractice.com/index.php?id_product=5&controller=product 
	Then Toggle between colors and verify 
	When Click on the logo
	Then Verify loading of homepage
	
Scenario: Verify total price for the products added to the cart
	Given Navigate to the page http://automationpractice.com/index.php
	Then Add to the cart the same product 5 times
	When Go to cart
	Then Verify the price match
	
	