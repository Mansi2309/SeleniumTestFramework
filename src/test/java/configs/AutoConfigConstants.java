package configs;

public class AutoConfigConstants {
	public static String browserType = AutoConfiguration.initAutomatioProperties().getProperty("BrowserType");
	public static String environmentUrl = AutoConfiguration.initEnvironmentProperties().getProperty("EnvironmentUrl");
	public static String operatingSystemType = AutoConfiguration.initAutomatioProperties()
			.getProperty("OperatingSystem");
	public static String window = "window";
	public static String chrome = "Chrome";
	public static String userName = AutoConfiguration.initEnvironmentProperties().getProperty("username");
	public static String passWord = AutoConfiguration.initEnvironmentProperties().getProperty("password");
}
