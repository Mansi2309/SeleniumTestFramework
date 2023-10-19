package base.utils;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import configs.AutoConfigConstants;

public class WebDriverFactory {

	WebDriver driver;
	public static String browserType = System.getProperty("browser");

	@SuppressWarnings("deprecation")
	public WebDriver createBrowser() {
		try {
			if (AutoConfigConstants.browserType.equalsIgnoreCase(AutoConfigConstants.chrome)
					&& (AutoConfigConstants.operatingSystemType.equalsIgnoreCase(AutoConfigConstants.window))) {
				final String driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator
                        + "ChromeDriver.exe";
                System.setProperty("webdriver.chrome.driver", driverPath);
                final ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*"); 
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--disable-extensions");
                options.addArguments("test-type");
                options.addArguments("--start-maximized");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--start-maximized");
                options.addArguments("--lang=en-us");
                options.setExperimentalOption("useAutomationExtension", false);
                options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation")); 
                options.setPageLoadStrategy(PageLoadStrategy.NONE);
                options.addArguments("--disable-features=VizDisplayCompositor");
                options.setExperimentalOption("useAutomationExtension", false);
                final Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("password_manager_enabled", false);
                prefs.put("download.default_directory",
                        System.getProperty("user.dir") + File.separator + "downloadFiles" + File.separator);
                options.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(options);
                driver.get(AutoConfigConstants.environmentUrl);
			}

		} catch (final Exception e) {
			System.out
					.println(" Caught Exception in the launching " + AutoConfigConstants.browserType + " browser" + e);
		}
		return driver;
	}

}
