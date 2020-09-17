package stepdefinition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import runner.TestRunner;

public class ListProductStepDef {
	protected WebDriver driver = TestRunner.getDriver();
	
	private void addToCart(String xPathOfItemToAdd) {
		WebElement element = driver.findElement(By.xpath(xPathOfItemToAdd + "/ancestor::li[contains(@class,'ajax_block_product')]//a[contains(@class,'ajax_add_to_cart_button')]"));
		this.clickHiddenElement(element);
	}
	
	private void clickHiddenElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
	}
	

	@Given("^User is loaded into the home page (.*)$")
	public void user_is_loaded_into_the_home_page(String URL) {
		driver.get(URL);
	}

	@Given("^Navigate to the page (.*)$")
	public void navigate_to_the_page(String URL) {
		driver.navigate().to(URL);
	}

	@When("^Add the product with 20% discount$")
	public void add_the_product_with_discount() {
		this.addToCart("//ul[contains(@class,'product_list') and contains(@class,'active')]//div[contains(@class, 'right-block')]//span[contains(@class,'price-percent-reduction') and contains(text(),'-20%')]");
	}
	
	@When("^Click on the logo$")
	public void click_on_the_logo() {
		driver.findElement(By.cssSelector("img.logo")).click();
	}

	@Then("^Print the name of the products$")
	public void print_the_name_of_the_products() {
		List<WebElement> list = driver.findElements(By.cssSelector("ul.product_list.active a.product-name"));
		list.stream().forEach(webElement -> {
			System.out.println(webElement.getText());
			Reporter.log(webElement.getText() + "<br>");
		});
	}

	@Then("^Sort the given products based on Price$")
	public void sort_the_given_products_based_on_price() {
		List<WebElement> priceListElementBeforeSort = driver.findElements(By.cssSelector("div.right-block span.price.product-price"));
		List<Float> priceListOnSort = new ArrayList<Float>();
		priceListElementBeforeSort.stream().forEach(webElement -> {
			priceListOnSort.add(Float.parseFloat(webElement.getText().replace("$", "")));
		});
		priceListOnSort.sort(Comparator.naturalOrder());
		
		
		Select dropdown = new Select(driver.findElement(By.cssSelector("select#selectProductSort.selectProductSort.form-control")));
		dropdown.selectByVisibleText("Price: Lowest first");
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//ul[contains(@class,'product_list')]//img[contains(@src,'loader')]")));
		List<WebElement> priceListElementAfterSort = driver.findElements(By.cssSelector("div.right-block span.price.product-price"));
		List<Float> priceListAfterSort = new ArrayList<Float>();
		priceListElementAfterSort.stream().forEach(webElement -> {
			priceListAfterSort.add(Float.parseFloat(webElement.getText().replace("$", "")));
		});
		Assert.assertEquals(priceListAfterSort, priceListOnSort);
	}

	@Then("^Verifying the product is added to the cart$")
	public void verifying_the_product_is_added_to_the_cart() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div.layer_cart_product h2")));
		String message = element.getText();
		Assert.assertEquals("Product successfully added to your shopping cart", message);

	}
	
	@Then("^Toggle between colors and verify$")
	public void toggle_between_colors_and_verify() {
		HashMap<String,String> colorUrlMap = new HashMap<String,String>();
		colorUrlMap.put("Black", "http://automationpractice.com/img/p/1/5/15-large_default.jpg");
		colorUrlMap.put("Orange", "http://automationpractice.com/img/p/1/4/14-large_default.jpg");
		colorUrlMap.put("Blue", "http://automationpractice.com/img/p/1/3/13-large_default.jpg");
		colorUrlMap.put("Yellow", "http://automationpractice.com/img/p/1/2/12-large_default.jpg");
		
		colorUrlMap.forEach((key, value) -> {
			driver.findElement(By.xpath("//ul[@id='color_to_pick_list']//a[contains(@name,'" + key + "')]")).click();
			Assert.assertEquals(value,driver.findElement(By.cssSelector("div#image-block img#bigpic")).getAttribute("src"));
			System.out.println(key + "-->" + driver.findElement(By.cssSelector("div#image-block img#bigpic")).getAttribute("src"));
		});
	
		
	}
	
	@Then("^Verify loading of homepage$")
	public void verify_loading_of_homepage()
	{
		Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php");
	}
	
	@Then("^Add to the cart the same product 5 times$")
	public void add_to_the_cart_the_same_product_5_times() throws InterruptedException
	{
		if(driver.findElement(By.cssSelector("span.ajax_cart_quantity")).getText() != "0") {
			this.go_to_cart();
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("i.icon-trash")).click();
			Thread.sleep(1000);
			this.click_on_the_logo();
			Thread.sleep(3000);
		}
		for(Integer i = 0; i < 5; i++) {
			this.addToCart("//ul[contains(@class,'product_list') and contains(@class,'active')]//div[contains(@class, 'right-block')]//span[contains(@class,'product-price') and contains(text(),'$50.99')]");
			this.verifying_the_product_is_added_to_the_cart();
			driver.findElement(By.cssSelector("div.button-container span.continue")).click();
			Thread.sleep(1000);
		}
	}
	
	@When("^Go to cart$")
	public void go_to_cart()
	{
		driver.findElement(By.cssSelector("div.shopping_cart a[title='View my shopping cart']")).click();
	}
	
	@Then("^Verify the price match$")
	public void verify_the_price_match()
	{
		Float actualTotalPrice = Float.parseFloat(driver.findElement(By.cssSelector("#total_product")).getText().replace("$", ""));
		Float expectedTotalPrice = (float) 254.95;
		Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
	}


//	@After()
//	public void closeBrowser() {
//		driver.quit();
//	}

}
