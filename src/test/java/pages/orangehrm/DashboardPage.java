package pages.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.fasterxml.jackson.core.base.GeneratorBase;

import base.utils.BasePage;

public class DashboardPage extends BasePage{

	WebDriver driver;
	public By collapsedMenuIcon = By.xpath("//i[contains(@class,'bi-chevron-right')]");
	public By pimModule = By.xpath("//a[contains(@href,'viewPim')]");
	public By addEmployeeTab = By.xpath("//a[contains(text(),'Add Employee')]");
	public By empFirstName = By.xpath("//input[@name='firstName']");
	public By empMiddleName = By.xpath("//input[@name='middleName']");
	public By empLastName = By.xpath("//input[@name='lastName']");
	public By employeeId = By.xpath("//label[text()='Employee Id']/../..//input");
	public By saveButton = By.xpath("//button[@type='submit']");
	
	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public void navigateToPimModule() {
		waitForPage(30);
		if(isElementPresent(collapsedMenuIcon)) {
			click(collapsedMenuIcon);
		}
		click(pimModule);
	}
	
	public Boolean addEmployee(String firstName, String middleName, String lastName, String empId) {
		waitForPage(60);
		waitForElementPresence(addEmployeeTab);
		click(addEmployeeTab);
		waitForPage(60);
		waitForElementPresence(empFirstName);
		safeType(empFirstName, firstName);
		safeType(empMiddleName, middleName);
		safeType(empLastName, lastName);
		safeClear(employeeId);
		safeType(employeeId, empId);
		click(saveButton);
		waitForPage(300);
		Boolean isPersonalDetailsUrlPresent = driver.getCurrentUrl().contains("PersonalDetails");
		log("url: " + isPersonalDetailsUrlPresent);
		return isPersonalDetailsUrlPresent;
	}
	
}
