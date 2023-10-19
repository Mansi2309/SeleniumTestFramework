package base.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class CustomeVerification extends BaseTest{
	public void verifyEquals(SoftAssert softAssert, String actual, String expected) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyEqualsListString(SoftAssert softAssert, List<String> actual, List<String> expected,
			String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
	}

	public void verifyEqualsListInteger(SoftAssert softAssert, List<Integer> actual, List<Integer> expected,
			String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEquals(SoftAssert softAssert, Double actual, Double expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyEquals(SoftAssert softAssert, Long actual, Long expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyEquals(SoftAssert softAssert, int actual, int expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "" + message + " \t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyNotEquals(SoftAssert softAssert, int actual, int expected, String message) {
		softAssert.assertNotEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	// Added by vishal
	public void verifyNotEquals(SoftAssert softAssert, Double actual, Double expected, String message) {
		softAssert.assertNotEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "\n");
	}

	public void verifyEquals(SoftAssert softAssert, String actual, String expected, String message) {
		softAssert.assertEquals(actual.trim(), expected.trim(),
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyEquals(SoftAssert softAssert, boolean actual, boolean expected) {
		softAssert.assertEquals(actual, expected, "Actual Value : " + actual + " Expected Value : " + expected + "\n");
	}

	public void verifyEquals(SoftAssert softAssert, boolean actual, boolean expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		Reporter.log("Actual  : " + actual + " Expected  " + expected);
	}
	
	public void verifyEquals(SoftAssert softAssert, boolean actual, String expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		Reporter.log("Actual  : " + actual + " Expected  " + expected);
	}

	public void verifyTrue(SoftAssert softAssert, boolean actual, String message) {
		softAssert.assertTrue(actual, message + "\n");
	}

	public void verifyTrue(SoftAssert softAssert, List<Integer> actual, List<Integer> expected, String message) {
		softAssert.assertTrue(actual.equals(expected), message + "\n");

	}

	public void verifyFalse(SoftAssert softAssert, boolean actual, String message) {
		softAssert.assertFalse(actual, message + "\n");
	}

	public void verifyNotEquals(SoftAssert softAssert, String actual, String expected, String message) {
		softAssert.assertNotEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
	}

	public void verifyNull(SoftAssert softAssert, String actual, String message) {
		softAssert.assertNull(actual, message + "\n");
	}

	public void verifyNotNull(SoftAssert softAssert, String actual, String message) {
		softAssert.assertNotNull(actual, message + "\n");
	}

	public void assertAll(SoftAssert softAssert) {
		softAssert.assertAll();
	}

	public void assertAll(SoftAssertion softAssert, String fileName) {
		softAssert.assertAll(fileName);
	}

	public void verifyTrue(SoftAssert softAssert, boolean string) {
		softAssert.assertTrue(string);
	}

	public void verifyEqualsWithDoubleAndString(SoftAssert softAssert, String actual, Double double1, String message) {
		softAssert.assertEquals(String.valueOf(actual), double1,
				"Actual Value : " + actual + " Expected Value : " + double1 + "\t\n");
	}

	public void verifyEqualsWithIntAndString(SoftAssert softAssert, int actual, String expected, String message) {
		softAssert.assertEquals(String.valueOf(actual), expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "\t\n");
	}

	public void verifyArrayEquals(SoftAssert softAssert, String[] actual, String[] expected, String message) {

		for (int i = 0; i < actual.length; i++) {
			softAssert.assertEquals(actual[i].trim(), expected[i].trim(),
					"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		}
	}

	public void verifyArrayEquals(SoftAssert softAssert, int[] actual, int[] expected, String message) {
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "\n");
		for (int i = 0; i < actual.length; i++) {
			softAssert.assertEquals(actual[i], expected[i],
					"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
			Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "\n");
		}

	}

	public void verifyEqualsWithoutLog(SoftAssert softAssert, String actual, String expected, String message) {
		softAssert.assertEquals(actual.trim(), expected.trim(),
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEqualsWithoutLog(SoftAssert softAssert, long actual, long expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEqualsWithoutLog(SoftAssert softAssert, int actual, int expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEqualsWithoutLog(SoftAssert softAssert, boolean actual, boolean expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEqualsWithoutLog(SoftAssert softAssert, Double actual, Double expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected : " + expected + " " + message + "\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "\n");
	}

	public void verifyEquals(SoftAssert softAssert, int actual, int expected) {
		// TODO Auto-generated method stub
		softAssert.assertEquals(actual, expected, "Actual Value : " + actual + " Expected : " + expected + "\n");
	}

	public void verifyEqualsListStringWithoutLog(SoftAssert softAssert, List<String> actual, List<String> expected,
			String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		// Reporter.log("Actual Value : " + actual + " Expected Value : " + expected +
		// "+Message : " + message +
		// "\t\n");
	}

	public void verifyTrueWithLog(SoftAssert softAssert, boolean actual, String message) {

		softAssert.assertTrue(actual, message + "\n");
	}

	// Validate - Assert Equals for integer values
	public void verifyEqualsIntegerCount(SoftAssert softAssert, int actual, int expected, String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
	}


	public void verifyNotEquals(SoftAssert softAssert, Long actual, Long expected, String message) {
		softAssert.assertNotEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected);
	}

	public void verifyEquals(SoftAssertion softAssert, String actual, String expected, String message) {
		softAssert.assertEquals(actual.trim(), expected.trim(),message + "\n");
	}

	public void verifyEquals(SoftAssertion softAssert, Double actual, Double expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}

	public void verifyEquals(SoftAssertion softAssert, Long actual, Long expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}

	public void verifyEquals(SoftAssertion softAssert, Integer actual, Integer expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}

	public void verifyEquals(SoftAssertion softAssert, Float actual, Float expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}
	public void verifyEquals(SoftAssertion softAssert, Date actual, Date expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}
	public void verifyEquals(SoftAssertion softAssert, Boolean actual, Boolean expected, String message) {
		softAssert.assertEquals(actual, expected, message + "\n");
	}

	public void verifyNotNull(SoftAssertion softAssert, Object actual, String message) {
		softAssert.assertNotNull(actual, message + "\n");
	}
	
	public void verifyNotEqualsListString(SoftAssert softAssert, List<String> actual, List<String> expected,
			String message) {
		softAssert.assertNotEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
	}
	
	public void verifyEqualsMapStringString(SoftAssert softAssert, Map<String, String> actual, Map<String, String> expected,
			String message) {
		softAssert.assertEquals(actual, expected,
				"Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
		Reporter.log("Actual Value : " + actual + " Expected Value : " + expected + "+Message  : " + message + "\t\n");
	}
}
