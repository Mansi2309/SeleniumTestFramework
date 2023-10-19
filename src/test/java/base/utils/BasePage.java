package base.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class BasePage {

	protected WebDriver driver;
	private SoftAssert softAssert;
	CustomeVerification customeVerification;

	public BasePage() {

	}

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getWebdriver() {
		return driver;
	}

	public Actions getActions() {
		return new Actions(this.driver);
	}

	public void click(By locator) {
		driver.findElement(locator).click();
	}

	public void clear(By locator) {
		driver.findElement(locator).clear();
	}

	public void type(By locator, String value) {
		driver.findElement(locator).sendKeys(value);
	}

	public String getText(By locator) {
		return driver.findElement(locator).getText();
	}

	public String getAttribute(By locator, String Attribute) {
		return driver.findElement(locator).getAttribute(Attribute);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void moveMouseHere(By locator) {
		final Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(locator)).build().perform();

	}

	public void moveMouseToElement(WebElement element) {
		final Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}

	public void safeClick(By locator) {
		try {
			final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			driver.findElement(locator).click();
		} catch (final WebDriverException e) {
			final Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(locator)).perform();
			action.click().perform();
		}
	}

	public void safeType(By locator, String value) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);
	}

	public void safeClear(By locator) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		driver.findElement(locator).clear();
	}

	public void safeCheck(By locator) {
		if (driver.findElement(locator).getAttribute("checked") == null) {
			driver.findElement(locator).click();
		}
	}

	public void safeUnCheck(By locator) {
		if (driver.findElement(locator).getAttribute("checked") == null) {
		} else {
			driver.findElement(locator).click();
		}
	}

	public void doubleClick(WebElement web) {
		getActions().doubleClick(web).build().perform();
		log("Double Clicked on Element");

	}

	public void doubleClick(By locator) {
		getActions().doubleClick(driver.findElement(locator)).build().perform();
		log("Double Clicked on Element");

	}

	public void clickElementWithJavaScript(By locator) {
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(locator));
	}

	public void clickHiddenElement(By locator) {
		final String elementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight));";
		((JavascriptExecutor) driver).executeScript(elementIntoMiddle, driver.findElement(locator));

		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(locator));
	}

	public String getElementValueUsingJavaScript(String script) {
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		final String elementValue = executor.executeScript(script).toString();
		return elementValue;
	}

	public void doubleClickElementUsingJavaScript(By locator) {
		final WebElement web = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", web);
	}

	public void doubleClickElementUsingJavaScript(WebElement web) {
		((JavascriptExecutor) driver).executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", web);
	}

	public void scrollIntoView(By by) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		final WebElement element = driver.findElement(by);
		jse.executeScript("arguments[0].scrollIntoView();", element);
		waitForPage(10);
	}

	public void scrollDown() {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0, 100)");
		waitForPage(10);
	}

	public void safeTypeByJavaScript(By by, String value) {
		final WebElement wb = driver.findElement(by);
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + value + "';", wb);
	}

	public void clickElementUsingJavaScript(By locator) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(locator));
	}

	public void waitForPage(int sec) {
		final JavascriptExecutor je = (JavascriptExecutor) driver;
		final int waitTime = sec * 1000;
		int counter = 0;
		counter = 0;
		Long ajaxCount = (long) -1;
		do {
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e1) {
				e1.printStackTrace();
			}
			counter += 1000;
			try {
				ajaxCount = (Long) ((je)).executeScript("return window.dwr.engine._batchesLength");
			} catch (final WebDriverException e) {

			}
		} while (ajaxCount.intValue() > 0 && counter < waitTime);
	}

	public void waitForElementPresent(By locator) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// wait until text on element is some Text
	public void waitForElementUntilText(By locator, String strText) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, strText));
	}

	public boolean isElementPresent(By by) {
		boolean value;
		try {
			driver.findElement(by).isDisplayed();
			value = true;
		} catch (final NoSuchElementException e) {
			value = false;
		}
		return value;
	}

	public boolean isElementSelected(By by) {
		boolean value;
		try {
			driver.findElement(by).isSelected();
			value = true;
		} catch (final Exception e) {
			value = false;
		}
		return value;
	}

	public boolean isDisplayed(By locater) {
		final boolean i = getWebdriver().findElement(locater).isDisplayed();
		log("Locater is Displayed");
		return i;
	}

	public void waitForElementPresence(By locator, int sec) {
		waitForPage(60);
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public String formaterFor2Digi(double value) {
		final double d = value;
		final DecimalFormat f = new DecimalFormat("#0.00");
		return f.format(d);
	}

	public String getNumberWithoutcomma(String word) {
		String result = "";
		if (word.contains(",")) {
			try {
				final String arr[] = word.split(",");
				for (int i = 0; i < arr.length; i++) {
					result = result + arr[i].trim();
				}

			} catch (final ArrayIndexOutOfBoundsException e) {
				System.out.println("Array out of bound for " + word);
			}
		} else {
			result = word;
		}

		return result;
	}

	public String trimTo2digits(double value) {
		final DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.DOWN); // Note this extra step
		return (df.format(value));
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	/*
	 * @param number
	 *
	 * @param formate
	 *
	 * @return numberWithComma Please pass format line "#,###.00" #,### #,##.##
	 * #,##.00
	 */
	public String getNumberWithComma(String number, String format) {
		final double amount = Double.parseDouble(number);
		final DecimalFormat formatter = new DecimalFormat(format);
		final String numberWithComma = formatter.format(amount);
		return numberWithComma;
	}

	public static void log(String message) {
		Reporter.log(message, true);
	}

	public CustomeVerification getCustomeVerification() {
		if (customeVerification == null) {
			customeVerification = new CustomeVerification();
		}
		return customeVerification;
	}

	/**
	 * @return the softAssert
	 */
	public SoftAssert getSoftAssert() {
		softAssert = PageFactory.initElements(getWebdriver(), SoftAssert.class);
		return softAssert;
	}

	public String getRandomNumber() {
		final String chars = "345678910";
		String ret = "";
		final int length = chars.length();
		for (int i = 0; i < length; i++) {
			ret += chars.split("")[(int) (Math.random() * (length - 1))];
		}
		return ret;
	}

	// get todaysDate in required Format
	public String getPresentDateInReqFormate(String formate) {
		final DateFormat dateFormat = new SimpleDateFormat(formate);
		final Date date = new Date();
		return dateFormat.format(date);
	}

	// Get ISO Date In Required Format
	public String getFormattedDate(String date, String inpFormat, String outFormat) throws ParseException {
		final ISO8601DateFormat df = new ISO8601DateFormat();
		final Date dateFormat = df.parse(date);
		final SimpleDateFormat inputDateFormat = new SimpleDateFormat(inpFormat);
		inputDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		final SimpleDateFormat outputDateFormat = new SimpleDateFormat(outFormat);
		final String formatedDate = outputDateFormat.format(dateFormat);
		return formatedDate;
	}

	public String getFormateDate(String date, String inpFormat, String outFormat) {
		String outputDate = null;
		try {
			final DateFormat df1 = new SimpleDateFormat(inpFormat); // for
			// parsing
			// //
			// input//mm-dd-yyyy
			final DateFormat df2 = new SimpleDateFormat(outFormat); // yyyy/mm/dd
			final Date d = df1.parse(date);
			outputDate = df2.format(d);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputDate;
	}

	public String getRandomAlphaNumericString(String size) {
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789_,@#$%*)(!^";
		String ret = "";
		final int length = chars.length();
		for (int i = 0; i < Integer.parseInt(size); i++) {
			ret += chars.split("")[(int) (Math.random() * (length - 1))];
		}
		return ret;
	}

	// Convert String Date In Required Format
	public Date convertStringDateInReqFormat(String date, String format) throws ParseException {
		final DateFormat formatter = new SimpleDateFormat(format);
		final Date reqFormattedDate = formatter.parse(date);
		return reqFormattedDate;
	}

	//
	public boolean verifyDateAfterOrEqualsToAnotherDate(String inputDate1, String inputDate2, String format)
			throws ParseException {
		boolean verifyDateAfter = false;
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		final Date date1 = formatter.parse(inputDate1);
		final Date date2 = formatter.parse(inputDate2);
		log("Date1" + formatter.format(date1));
		log("Date2" + formatter.format(date2));
		// equals() returns true if both the dates are equal
		// after() will return true if and only if date1 is after date 2
		if ((date1.after(date2)) || (date1.equals(date2))) {
			verifyDateAfter = true;
			log("Date1 is after Date2");
		}
		return verifyDateAfter;
	}

	public boolean verifyDateBeforeOrEqualsToAnotherDate(String inputDate1, String inputDate2, String format)
			throws ParseException {
		boolean verifyDateAfter = false;
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		final Date date1 = formatter.parse(inputDate1);
		final Date date2 = formatter.parse(inputDate2);
		log("Date1" + formatter.format(date1));
		log("Date2" + formatter.format(date2));
		// equals() returns true if both the dates are equal
		// after() will return true if and only if date1 is after date 2
		if ((date1.before(date2)) || (date1.equals(date2))) {
			verifyDateAfter = true;
			log("Date1 is Before Date2");
		}
		return verifyDateAfter;
	}

	public void tabOutFromField(By locator) {
		driver.findElement(locator).sendKeys(Keys.TAB);
	}

	// Add Days to Given Date
	public String addNumberOfDaysToDate(String date, String formate, int days) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat(formate);
		final Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, +days);
		final String dt = sdf.format(c.getTime());
		return dt;
	}

	public String getCurrentDate(String format) {
		String currentDate = null;
		final DateFormat sdf = new SimpleDateFormat(format);
		final Date date = new Date();
		currentDate = sdf.format(date);
		return currentDate;
	}

	// Substract Days to Given Date
	public String substractNumberOfDaysToDate(String date, String formate, int days) throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat(formate);
		final Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(date));
		c.add(Calendar.DATE, -days);
		final String dt = sdf.format(c.getTime());
		return dt;
	}

	public void scrollIntoView(WebElement element) {
		final JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView();", element);
		waitForPage(10);
	}

	public void uploadFileUsingRobotClass(String filePath) {
		final StringSelection stringSelection = new StringSelection(filePath);
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (final AWTException e) {
			e.printStackTrace();
		}
		robot.delay(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(150);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public void deleteFile(String fileNamePath, String Startwith) {
		final File folder = new File(fileNamePath);
		final File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				final String file = listOfFiles[i].getName();
				if (file.startsWith(Startwith)) {
					listOfFiles[i].delete();
				}
			}
		}
	}

	public String getFile(String Startwith) {
		waitForPage(330);
		final String fileNamePath = System.getProperty("user.dir") + "\\downloadFiles\\";
		final File folder = new File(fileNamePath);
		final File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				final String file = listOfFiles[i].getName();
				if (file.startsWith(Startwith)) {
					log("Expected File found :" + file);
					return fileNamePath + file;
				}
			}
		}
		return null;
	}

	public String getDateInRequiredTimeZone(String date, String currentFormate, String reqformate, String timeZone)
			throws ParseException {
		final SimpleDateFormat sdfmt1 = new SimpleDateFormat(currentFormate);
		final SimpleDateFormat sdfmt2 = new SimpleDateFormat(reqformate);
		sdfmt2.setTimeZone(TimeZone.getTimeZone(timeZone));
		final java.util.Date dDate = sdfmt1.parse(date);
		final String strOutput = sdfmt2.format(dDate);
		return strOutput;
	}

	@SuppressWarnings("deprecation")
	public void waitForElementAbsence(By locator, long seconds) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	// verify given date is present between to dates
	public boolean isDateInBetweenIncludingEndPoints(String minDate, String maxDate, String currentDate,
			String givenFormat) throws ParseException {
		final Date mininumDateFormat = new SimpleDateFormat(givenFormat).parse(minDate);
		final Date maximumDateFormat = new SimpleDateFormat(givenFormat).parse(maxDate);
		final Date currentDateFormat = new SimpleDateFormat(givenFormat).parse(currentDate);

		return !(currentDateFormat.before(mininumDateFormat) || currentDateFormat.after(maximumDateFormat));
	}

	public void safeClickByAction(By locator) {
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(locator));
		final WebElement element = driver.findElement(locator);
		final Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
	}

	public String getDecimalPrecision(double number) {

		final DecimalFormat df = new DecimalFormat("#0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		return (df.format(number));
	}

	public void openNewPageInNewTab(By locator) {
		final WebElement wb = driver.findElement(locator);
		wb.sendKeys(Keys.TAB);
		wb.sendKeys(Keys.ENTER);
		waitForPage(60);
	}

	public void threadSleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForElementPresence(By locator) {
		waitForPage(60);
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public String getRandomNumberDoubleDigit() {
		final double rand = Math.random() * 100;
		final int rand_int = (int) rand;
		final String rand_str = String.valueOf(rand_int);
		return rand_str;
	}

	public String getRandomCharString(int size) {
		final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String ret = "";
		final int length = chars.length();
		for (int i = 0; i < size; i++) {
			ret += chars.split("")[(int) (Math.random() * (length - 1))];
		}
		return ret;
	}

	public boolean isReadOnly(By locator) {
		final boolean i = driver.findElement(locator).isEnabled();
		return i;
	}

	public String getRandomNumberFourDigit() {
		final double rand = Math.random() * 10000;
		final int rand_int = (int) rand;
		final String rand_str = String.valueOf(rand_int);
		return rand_str;
	}

	public boolean isElementEnabled(By locator) {
		boolean value;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		try {
			final JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0]", driver.findElement(locator));
			value = true;
		} catch (final Exception e) {
			value = false;
			log("Element Not Enabled");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return value;

	}

	// converts quarter EG: 3Q20 to 2020-Q3 Eg
	public String convertDifferentQuarterFormats(String inputValue) throws ParseException {
		final String format[] = inputValue.split("Q");
		final String year = getFormateDate(format[1], "YY", "YYYY").toString();
		final String outputFormat = year + "-Q" + format[0];
		return outputFormat;
	}

	public String getRandomNo() {
		final double rand = Math.random() * 10;
		final int rand_int = (int) rand * 10;
		final String rand_str = String.valueOf(rand_int);
		return rand_str;
	}

//	Move Horizontal Scroll bar to Right
	public void moveScrollBarToRight(By horizontalScrollBar) {
		final WebElement element = driver.findElement(horizontalScrollBar);
		element.click();
		final int size = (element.getSize().width / 2);
		log("Horozontal Scrollbar Width/2 is :" + size);
		getActions().moveToElement(element);
		getActions().moveByOffset(size, 0).click().perform();
	}

//	Move Horizontal Scroll bar to Left
	public void moveScrollBarToLeft(By horizontalScrollBar) {
		final WebElement element = driver.findElement(horizontalScrollBar);
		element.click();
		final int size = -(element.getSize().width / 2);
		log("Horozontal Scrollbar Width/2 is : " + size);
		getActions().moveToElement(element).clickAndHold();
		getActions().moveByOffset(size, 0).click().release().build().perform();
	}

	// To Zoom In Browser to get maximum elements
	public void zoomInBrowser(int zoomInPercentage) {
		final String zoomInJS = "document.body.style.zoom='" + zoomInPercentage + "%'";
		final JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(zoomInJS);
	}

	// To Get the Element
	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	// Open Module in a new Tab
	public BasePage openModuleInNewTab(String moduleName) {
		waitForPage(100);
		getActions().keyDown(Keys.LEFT_CONTROL)
				.click(driver.findElement(By.xpath("//li//a[contains(text(),'" + moduleName + "')]/..")))
				.keyUp(Keys.LEFT_CONTROL).build().perform();
		return this;
	}

	// Switch to next Tab
	public BasePage switchToNextTab() throws Exception {
		waitForPage(100);
		final Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		return this;
	}

	public boolean isEleEnabled(By locator) {
		return driver.findElement(locator).isEnabled();
	}

	public void scrollDown(By locator) {
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", getElement(locator));
		final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	@SuppressWarnings("unused")
	public String readPropertiesFile(String fileLocation, String name) {
		Properties properties = null;
		String val = null;
		try {
			final File file = new File(fileLocation);
			if (file == null) {
				throw new FileNotFoundException();
			}
			FileInputStream in = new FileInputStream(fileLocation);
			Properties props = new Properties();
			props.load(in);
			val = props.getProperty(name);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		log("The read entity is " + val);
		return val;
	}

	@SuppressWarnings({ "unused", "null" })
	public void writePropertiesFile(String filePath, String name, String value) {
		// Properties prop = null;
		try {
			Properties prop = new Properties();
			Boolean blnLoopStatus = true;
			prop.load(new FileInputStream(filePath));
			while (blnLoopStatus) {
				if (prop.getProperty("Editlock").equalsIgnoreCase("off")) {
					prop.setProperty("Editlock", "on");
					prop.store(new FileOutputStream(filePath), null);
					prop.setProperty(name, value);
					prop.setProperty("Editlock", "off");
					prop.store(new FileOutputStream(filePath), null);
					break;
				} else {
					prop.load(new FileInputStream(filePath));
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String format2Digi(String value) {
		return new DecimalFormat("#0.00").format(Double.parseDouble(value));
	}

	public String formatToNoDigit(String value) {
		return new DecimalFormat("#").format(Double.parseDouble(value));
	}

	// This will format digits to no decimal and upto n number of decimal
	public String formatDigits(String value, int decimals) {

		if (value != null && value.length() > 0) {
			StringBuilder formateValue = new StringBuilder(decimals + 2);
			if (decimals == 0) {
				return new DecimalFormat("#").format(Double.parseDouble(value));
			}
			formateValue.append("#0.");
			for (int i = 0; i < decimals; i++) {
				formateValue.append("0");
			}
			return new DecimalFormat(formateValue.toString()).format(Double.parseDouble(value));
		} else {
			return "";
		}
	}

	// for key down arrow press
	public BasePage downArrow() throws AWTException {
		waitForPage(100);
		final Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		return this;
	}

	// for key up arrow press
	public BasePage upArrow() throws AWTException {
		waitForPage(100);
		final Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_UP);
		return this;
	}

	public String getHiddenElementTextJavaScriptExecutor(By locator) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		String str = (String) jse.executeScript("return arguments[0].innerHTML", element);
		return str;
	}

	public void navigateTo(String url) {
		driver.navigate().to(url);
		log("Navigate to----->" + url);
	}

}
