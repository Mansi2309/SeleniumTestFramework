package pages.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.utils.BasePage;
import configs.AutoConfigConstants;

public class LoginPage extends BasePage{
	WebDriver driver;
	public LoginPage(WebDriver driver) {
	    super(driver);
	    this.driver = driver;
	  }

	  By username = By.xpath("//input[@placeholder='Username']");
	  By password = By.xpath("//input[@placeholder='Password']");
	  By logIn = By.xpath("//button[@type='submit']");
	  
	  public LoginPage loginApplication() {
		    waitForPage(50000);
		    waitForElementPresence(username);
		    safeType(username, AutoConfigConstants.userName);
		    waitForPage(10);
		    waitForElementPresence(password);
		    safeClick(password);
		    safeType(password, AutoConfigConstants.passWord);
		    waitForPage(1000);
		    safeClick(logIn);
		    return this;
		  }

		  public LoginPage loginApplication(String userName, String passWord) {
		    waitForPage(50000);
		    waitForElementPresence(username);
		    safeType(username, userName);
		    waitForPage(10);
		    waitForElementPresence(password);
		    safeClick(password);
		    safeType(password, passWord);
		    safeClick(logIn);
		    waitForPage(800);
		    return this;
		  }

}
