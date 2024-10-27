package TestCases;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utilities.AppUtils;


public class AppTest extends AppUtils{
	
	@Parameters({"title"})
	@Test(priority = 1, enabled = false)
	public void validateLoginPageTitile(String pageTitle) {
		if(driver.getTitle().equalsIgnoreCase(pageTitle)) {
			Reporter.log("Valid Page Title: "+pageTitle, true);
		}
		else {
			Reporter.log("Invalid Page Title");
		}
		
	}
	
	@Parameters({"UserName", "Password", "PageTitle"})
	@Test(priority = 2, enabled = false)
	public void loginTest(String userName,String Password,String PageTitle) {
		
		driver.findElement(By.xpath(p.getProperty("objUserName"))).sendKeys(userName);
		driver.findElement(By.xpath(p.getProperty("objPassword"))).sendKeys(Password);
		driver.findElement(By.xpath(p.getProperty("objSignIn"))).click();
		
		if(driver.getTitle().contains(PageTitle)) {
			Reporter.log("Login Successfull", true);
			driver.findElement(By.xpath(p.getProperty("objUserSettings"))).click();
			driver.findElement(By.xpath(p.getProperty("objUserLogout"))).click();
		}
		else {
			String errorMessage=driver.findElement(By.xpath(p.getProperty("objErrorMessage"))).getText();
			Reporter.log(errorMessage, true);
		}
	}
	
	@Parameters({"date", "from", "to", "airlineName", "PassengerName", "FlightClass", "Tickets"})
	@Test
	public void bookFlightTicket(String date, String from, String to, String airlineName, String PassengerName, String FlightClass, String Tickets) throws Throwable {
		driver.findElement(By.xpath(p.getProperty("objSearchDate"))).sendKeys(date);
		Select flightFrom = new Select(driver.findElement(By.xpath(p.getProperty("objFlightFrom"))));
		flightFrom.selectByVisibleText(from);
		Select flightTo = new Select(driver.findElement(By.xpath(p.getProperty("objFlightTo"))));
		flightTo.selectByVisibleText(to);
		driver.findElement(By.xpath(p.getProperty("objSearchFlightsButton"))).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(p.getProperty("objSearchResultsClose"))));
		WebElement ftable;
		WebElement ftableBody;
		List<WebElement> trows;
		List<WebElement> tcols;
		String fname;
		try {
			if(driver.findElement(By.xpath(p.getProperty("objFlightTable"))).isDisplayed()) {
				ftable = driver.findElement(By.xpath(p.getProperty("objFlightTable")));
				ftableBody=ftable.findElement(By.tagName("tbody"));
				trows=ftableBody.findElements(By.tagName("tr"));
				int rowCount = trows.size();
				boolean IsAirlineNamePresent = false;
				Reporter.log("Number of rows present in Flight Search Results Table: ", rowCount, true);
				for(int i=1; i<trows.size(); i++) {
					tcols=trows.get(i).findElements(By.tagName("td"));
					fname=tcols.get(0).getText();
					if(fname.equalsIgnoreCase(airlineName)) {
						tcols.get(8).click();
						IsAirlineNamePresent = true;
						Thread.sleep(3000);
						break;
					}
				}
				if(IsAirlineNamePresent==true) {
					Reporter.log("Airline Present In FLight Search Results Table", true);
					driver.findElement(By.xpath(p.getProperty("objPassengerName"))).sendKeys(PassengerName);
					
					if(FlightClass.equalsIgnoreCase("First Class")) {
						driver.findElement(By.xpath(p.getProperty("objFlightFirstClass"))).click();
					}
					else if(FlightClass.equalsIgnoreCase("Business")) {
						driver.findElement(By.xpath(p.getProperty("objFlightBusinessClass"))).click();
					}
					else {
						driver.findElement(By.xpath(p.getProperty("objFlightEconomyClass"))).click();
					}
					driver.findElement(By.xpath(p.getProperty("objTickets"))).clear();
					driver.findElement(By.xpath(p.getProperty("objTickets"))).sendKeys(Tickets);
					driver.findElement(By.xpath(p.getProperty("objInsertOrder"))).click();
				}
				else {
					Reporter.log("Airline Name not Present in Table", true);
					driver.findElement(By.xpath(p.getProperty("objCloseFlightSearchResults"))).click();
				}
			}
	 		else {
	 			Reporter.log("No Flights", true);
	 			driver.findElement(By.xpath(p.getProperty("objCloseFlightSearchResults"))).click();
	 		}
			
		} catch (Exception e) {
			if(driver.findElement(By.xpath(p.getProperty("objFlightTable"))).isDisplayed()) {
				ftable = driver.findElement(By.xpath(p.getProperty("objFlightTable")));
				ftableBody=ftable.findElement(By.tagName("tbody"));
				trows=ftableBody.findElements(By.tagName("tr"));
				int rowCount = trows.size();
				boolean IsAirlineNamePresent = false;
				Reporter.log("Number of rows present in Flight Search Results Table: ", rowCount, true);
				for(int i=1; i<trows.size(); i++) {
					tcols=trows.get(i).findElements(By.tagName("td"));
					fname=tcols.get(0).getText();
					if(fname.equalsIgnoreCase(airlineName)) {
						tcols.get(8).click();
						IsAirlineNamePresent = true;
						Thread.sleep(3000);
						break;
					}
				}
				if(IsAirlineNamePresent==true) {
					Reporter.log("Airline Present In FLight Search Results Table", true);
					driver.findElement(By.xpath(p.getProperty("objPassengerName"))).sendKeys(PassengerName);
					
					if(FlightClass.equalsIgnoreCase("First Class")) {
						driver.findElement(By.xpath(p.getProperty("objFlightFirstClass"))).click();
					}
					else if(FlightClass.equalsIgnoreCase("Business")) {
						driver.findElement(By.xpath(p.getProperty("objFlightBusinessClass"))).click();
					}
					else {
						driver.findElement(By.xpath(p.getProperty("objFlightEconomyClass"))).click();
					}
					driver.findElement(By.xpath(p.getProperty("objTickets"))).clear();
					driver.findElement(By.xpath(p.getProperty("objTickets"))).sendKeys(Tickets);
					driver.findElement(By.xpath(p.getProperty("objInsertOrder"))).click();
					
				}
				else {
					Reporter.log("Airline Name nit Present in Table", true);
					driver.findElement(By.xpath(p.getProperty("objCloseFlightSearchResults"))).click();
				}
			}
	 		else {
	 			Reporter.log("No Flights", true);
	 			driver.findElement(By.xpath(p.getProperty("objCloseFlightSearchResults"))).click();
	 		}
		}
		
		
		
		
	}
	
}













