package dataProviders;

import java.io.File;

import org.testng.annotations.DataProvider;

import excelHandlers.ExcelUtilities;

public class OrangeHRM {
	/*@DataProvider(name = "assignLeaveForm")
	public static Object[][] assignLeaveForm() {
		final Object[][] objReturn = ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator
				+ "TestData" + File.separator + "OrangeHRM_TestData.xls", "OrangeHRM_TestData1", "assignLeaveForm");

		return objReturn;
	}*/
	@DataProvider(name = "addEmployee")
	public static Object[][] assignLeaveForm() {
		final Object[][] objReturn = ExcelUtilities.getTableArray(System.getProperty("user.dir") + File.separator
				+ "TestData" + File.separator + "OrangeHRM_TestData.xls", "OrangeHRM_TestData1", "addEmployee");
		return objReturn;
	}
}
