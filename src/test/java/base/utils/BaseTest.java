package base.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import configs.AutoConfigConstants;

public class BaseTest {
	private CustomeVerification customeVerification;
	public SoftAssert softAssert;
	public SoftAssertion softAsser;
	private WebDriver driver;
	private BasePage basePage;
	
	/*
	 * public SoftAssert getSoftAssert() { softAssert =
	 * PageFactory.initElements(getDriver(), SoftAssert.class); return
	 * softAssert; // return softAssert; }
	 */

	public SoftAssert getSoftAssert() {
		return softAssert;
	}

	public SoftAssertion getSoftAssertion() {
		return softAsser;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void launchOrangeHRMApplication() {
		driver = new WebDriverFactory().createBrowser();
		BasePage.log("Launched " + AutoConfigConstants.browserType + " browser");
		BasePage.log("Environment : " + AutoConfigConstants.environmentUrl);
	}
	public BasePage getBasePage() {
		if (basePage == null) {
			basePage = new BasePage(getDriver());
		}
		return basePage;
	}

	


	public BasePage getBasePagewithoutdriver() {
		if (basePage == null) {
			basePage = new BasePage();
		}
		return basePage;
	}

	public CustomeVerification getCustomeVerification() {
		if (customeVerification == null) {
			customeVerification = new CustomeVerification();
		}
		return customeVerification;
	}





	@BeforeMethod
	public void beforeMethod() {
		this.softAssert = new SoftAssert();
		// This is will be use full when we log assertions into log file
		// Use soft assertion object if you want to log assertions into your log
		// file
		this.softAsser = new SoftAssertion();

	}


	/**
	 * Method Used to take ScreenShot For Failed Test Case
	 *
	 * @param driver
	 * @param screenshotname
	 */
	public static void captureScreenshot(WebDriver driver, String screenshotname) {
		try {
			final String timestamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
			final String timeStamp1 = (timestamp.replaceAll("/", "_")).replaceAll(":", "_").replace(" ", "_");
			final TakesScreenshot screenshot = (TakesScreenshot) driver;
			final File source = screenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./Screenshots/" + screenshotname + timeStamp1 + ".png"));
			System.out.println("Screenshot Taken");
		} catch (final Exception excep) {
			System.out.println("Throwing exception while taking screenshot" + excep.getMessage());
		}
	}

	@BeforeSuite()
	@Parameters("datafile")
	public void beforeSuite(@Optional String fileName) {
		if (new File("./Screenshots/").exists()) {
			try {
				FileUtils.cleanDirectory(new File("./Screenshots/"));
			} catch (final IOException e) {
				System.out.println("screen shot folder is missing");
				e.printStackTrace();
			}
		}
	}

	public <TPage extends BasePage> TPage getInstanceOfWebPages(Class<TPage> pageClass) throws Exception {
		try {
			// Initialize the Page with its elements and return it.
			return PageFactory.initElements(driver, pageClass);
		} catch (final Exception e) {
			e.printStackTrace();
				throw e;
			
		}
	}

	// JAVA Generics to Create and return a any New Class Instance
	public <T> T getInstanceUtilsClass(Class<T> utilsClassName) throws Exception {

		T objectOfTheClass;
		try {
			objectOfTheClass = utilsClassName.newInstance();
		} catch (final Exception e) {
			e.printStackTrace();
			throw e;
		}
		return objectOfTheClass;
	}

	


}
