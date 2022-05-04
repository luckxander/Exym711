package automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.HttpSessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


@SpringBootTest
public class StepsDefinitionCode {
	
	public WebDriver dr = null;
	static String URL_login = "https://exym-vnext-teamtest2.azurewebsites.net/";
	static HttpSessionId stored = null;

	
	 //Browser SetUp and Login
	 ////////////////////////////////////////////////////////////////////////////////////////////////////
	 
    @Before
    public void setUp() throws Throwable { 
    	   System.out.println("Running setUp ...");
		   System.setProperty("webdriver.chrome.driver","C:/Users/luckx/OneDrive/Documents/workspace-spring-tool-suite-4-4.13.1.RELEASE/exym425/chromedriver.exe");
	       
		   dr = new ChromeDriver();
	       dr.manage().window().maximize();
	       dr.get(URL_login); 
		   
	       if(stored == null) {
			   HttpSessionId session = new HttpSessionId();
			   TimeUnit.SECONDS.sleep(6);
			   WebDriverWait wait = new WebDriverWait(dr, 10);
			   wait.until(ExpectedConditions.textToBePresentInElement(dr.findElement(By.xpath("//body[@id='kt_body']/app-root/ng-component/div/div/div/div/div/tenant-change/span")),"Current tenant"));

			   String currentText = dr.findElement(By.cssSelector("#kt_body > app-root > ng-component > div > div > div.d-flex.flex-center.flex-column.flex-column-fluid.p-10.pb-lg-20 > div > div > tenant-change > span")).getText();
			   //System.out.println("Current text => "+currentText);
			   if(currentText.intern() == "Current tenant: Not selected ( Change )") {
				  
				  //switch to default 
				  dr.findElement(By.linkText("Change")).click();
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.xpath("//*[@id=\"kt_body\"]/app-root/ng-component/div/div/div[1]/div/div/tenant-change/span/tenantchangemodal/div/div/div/form/div[2]/div[1]/div/label/input")).click();
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.cssSelector("#tenancyNameInput")).sendKeys("default");
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.xpath("//*[@id=\"kt_body\"]/app-root/ng-component/div/div/div[1]/div/div/tenant-change/span/tenantchangemodal/div/div/div/form/div[3]/button[2]")).click();
			   
				  //click openIDconnect
				  TimeUnit.SECONDS.sleep(6);
				  dr.findElement(By.linkText("OpenIdConnect")).click();
				  
				  //user credentials 
				  TimeUnit.SECONDS.sleep(3);
				  dr.findElement(By.cssSelector("#signInName")).sendKeys("BettyWhite@mailinator.com");
				  dr.findElement(By.cssSelector("#password")).sendKeys("Password22!");
				  dr.findElement(By.cssSelector("#next")).click();
				  				  
			   }
			   
			   stored = session;
			   TimeUnit.SECONDS.sleep(3);
			}
    }
    
    /////////////////////////////////////////////////////////////////////////////
	//End Browser SetUp and Login

	
	@Given("I am a clinician user")
	public void i_am_a_clinician_user() throws Throwable {
		assertNotEquals(stored,null);
	}

	@When ("I go to the main page Exym vNext portal")
	public void i_go_to_the_main_page_Exym_vNext_portal() throws Throwable {
		TimeUnit.SECONDS.sleep(6);
		String expected_notes = dr.findElement(By.xpath("//*[@id=\"kt_wrapper\"]/div[2]/app-landing-dashboard/div/div/sub-header/div/div/div[1]/h5")).getText();
	    String current_notes ="Dashboard";
	    assertEquals(current_notes,expected_notes);

	}

	@And ("I click on a client name in the client table view on the dashboard I am taken to new client details page")
	public void click_clients_name() throws Throwable {
		TimeUnit.SECONDS.sleep(3);
		dr.findElement(By.xpath("//*[@id=\"pr_id_5\"]/div/table/tbody/tr[1]/td[1]/div/div[1]/a")).click();
		
	}
	
	@Then ("I should see a photo icon in the top left corner of the page")
	public void see_foto_icon() throws Throwable {
		TimeUnit.SECONDS.sleep(4);
		//Fetching the image source
      	WebElement image = dr.findElement(By.xpath("//*[@id=\"kt_wrapper\"]/div[2]/ng-component/div/div/div[1]/div/div/div/div/client-header-detail/div/div[1]/img"));
      	System.out.println("The image source is : "+image.getAttribute("src"));
		
	}
	
	@And ("I should see to right of the photo icon the first and last name of my client")
	public void first_las_name() throws Throwable {
		String name = dr.findElement(By.xpath("//*[@id=\"kt_wrapper\"]/div[2]/ng-component/div/div/div[1]/div/div/div/div/client-header-detail/div/div[2]/div[1]/div[1]/p[1]")).getText();
		//System.out.println("Name : "+name);
		String single_name[] = name.split(" ");
		//System.out.println("first_name : "+single_name[0]);
		//System.out.println("last_name : "+single_name[1]);
	    assertNotEquals(single_name[0], null);
	    assertNotEquals(single_name[1], null);
	}
	
	@And ("I can click on Dashboard and return back to the dashboard page")
	public void click_dashboard() throws Throwable {
		TimeUnit.SECONDS.sleep(3);
		dr.findElement(By.xpath("//*[@id=\"#kt_aside_menu\"]/div/a/span[3]")).click();
	}
	
    @After //tearDown() (close browser)
    public void tearDown() throws Exception {
    	 TimeUnit.SECONDS.sleep(3);
        System.out.println("Running tearDown ...");
        dr.close();
    }
}
