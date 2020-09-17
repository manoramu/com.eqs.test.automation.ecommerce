package runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src/main/java/features"
		, glue = "stepdefinition"
		)
public class TestRunner extends AbstractTestNGCucumberTests {
	private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	
	public static WebDriver getDriver() {
		return drivers.get();
	}
	
	@BeforeClass
    @Parameters({"browser"})
    public void setUpClass(String browser) {
		WebDriver driver = null;
		String path = System.getProperty("user.dir");
        if(browser.equalsIgnoreCase("chrome")) {
        	System.setProperty("webdriver.chrome.driver", path + 
    				"//src//drivers//chromedriver.exe");
    		driver = new ChromeDriver();
        } else if(browser.equalsIgnoreCase("firefox")) {
        	System.setProperty("webdriver.gecko.driver", path + 
        			"//src//drivers//geckodriver.exe");
    		driver = new FirefoxDriver();
        } else if(browser.equalsIgnoreCase("IE")) {
        	System.setProperty("webdriver.ie.driver", path +
        			"//src//drivers//IEDriverServer.exe");
    		driver = new InternetExplorerDriver();
    		driver.manage().window().maximize();
        }
        drivers.set(driver);
        super.setUpClass();
    }
	
	@AfterClass
    public void tearDownClass() {
		getDriver().quit();
		drivers.remove();
        super.tearDownClass();
    }
	
}