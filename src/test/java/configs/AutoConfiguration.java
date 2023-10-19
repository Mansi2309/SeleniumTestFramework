package configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.util.Strings;

public class AutoConfiguration {
	@SuppressWarnings("unused")
	  public static Properties initAutomatioProperties() {
	    Properties properties = null;
	    try {
	      final File file = new File(
	          System.getProperty("user.dir") + File.separator + "Env_configs" + File.separator + "Automation.Properties");
	      if (file == null) {
	        throw new FileNotFoundException();
	      }
	      final FileInputStream fileInput = new FileInputStream(file);
	      properties = new Properties();
	      properties.load(fileInput);
	      final String envVal = System.getProperty("Env");
	      if (!Strings.isNullOrEmpty(envVal)) {
	        properties.setProperty("Environment", envVal);
	      }

	      final String tRun = System.getProperty("TRun");
	      if (!Strings.isNullOrEmpty(tRun)) {
	        properties.setProperty("TestRail", tRun);
	      }
	      
	      final String bType = System.getProperty("BType");
	      if (!Strings.isNullOrEmpty(bType)) {
	        properties.setProperty("BrowserType", bType);
	      }

	      fileInput.close();
	    } catch (final Exception e) {
	      e.printStackTrace();
	    }
	    return properties;
	  }
	
	@SuppressWarnings("unused")
	  public static Properties initEnvironmentProperties() {
	    Properties properties = null;
	    try {
	      final File file = new File(System.getProperty("user.dir") + File.separator + "Environments" + File.separator
	          + initAutomatioProperties().getProperty("Environment") + ".Properties");
	      if (file == null) {
	        throw new FileNotFoundException();
	      }
	      final FileInputStream fileInput = new FileInputStream(file);
	      properties = new Properties();
	      properties.load(fileInput);
	      fileInput.close();
	    } catch (final Exception e) {
	      e.printStackTrace();
	    }
	    return properties;
	  }
}
