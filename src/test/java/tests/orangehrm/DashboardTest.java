package tests.orangehrm;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.utils.BasePage;
import base.utils.BaseTest;
import dataProviders.OrangeHRM;
import pages.orangehrm.DashboardPage;
import pages.orangehrm.LoginPage;

public class DashboardTest extends BaseTest{
	
	@BeforeTest
	public void beforeTest() throws Exception {
		launchOrangeHRMApplication();
		getInstanceOfWebPages(LoginPage.class).loginApplication();
	}
	
	@Test(description = "dashboard", dataProviderClass = OrangeHRM.class, dataProvider = "addEmployee", enabled = true, alwaysRun = true)
	public void verifyAddEmplyeeInPim(String firstName, String middleName, String lastName, String empId) throws Exception{
		DashboardPage dashboardPage = getInstanceOfWebPages(DashboardPage.class);
		Thread.sleep(8000);
		
		//navigate to PIM Module
		dashboardPage.navigateToPimModule();
		Thread.sleep(8000);
		getCustomeVerification().verifyTrue(getSoftAssert(), getDriver().getCurrentUrl().contains("pim"), "Successfully not navigate to PIM Module");
		Thread.sleep(8000);
		
		Boolean isPersonalDetailsUrlPresent = dashboardPage.addEmployee(firstName, middleName, lastName, empId);
		getCustomeVerification().verifyTrue(getSoftAssert(), isPersonalDetailsUrlPresent, "Employee details does not save successfully");
		
		getCustomeVerification().assertAll(getSoftAssert());
	}
	
	/*@AfterTest
	public void afterTest() {
		getDriver().quit();
	}*/

}
