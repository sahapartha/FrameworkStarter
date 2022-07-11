package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
	private WebDriver driver;
	private String propPath = System.getProperty("user.dir") + "/src/main/java/resources/browserData.properties";
	private Properties prop;
	private String browser;
	private String currentPath = System.getProperty("user.dir");

	public WebDriver initializeDriver(boolean implicitWaitSwitch, long implicitWaitTime, boolean maxWindow)
			throws IOException {

		// Importing data from browserdata.properties
		prop = new Properties();
		FileInputStream propFile = new FileInputStream(propPath);
		prop.load(propFile);
		browser = prop.getProperty("browser");
		String browserDriverPath = currentPath + prop.getProperty("driverPath");

		// Choosing the driver
		if (browser.equals("chrome")) {
			driver = WebDriverManager.chromedriver().cachePath(browserDriverPath).create();
		} else if (browser.equals("firefox")) {
			driver = WebDriverManager.firefoxdriver().cachePath(browserDriverPath).create();
		} else if (browser.equals("IE")) {
			driver = WebDriverManager.iedriver().cachePath(browserDriverPath).create();
		}

		// Implicit Wait
		if (implicitWaitSwitch == true && implicitWaitTime > 0) {
			this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

			System.out.println("Implicit Wait is on. Duration: " + implicitWaitTime);
		}

		// Max Window
		if (maxWindow == true) {
			driver.manage().window().maximize();
		}

		return driver;
	}

	public String getPropValue(String key) {
		// Add LOG for not found URL
		String propValue = prop.getProperty(key);

		if (key.contains("Path"))
			return currentPath + propValue;
		else
			return propValue;

	}

	public boolean checkLinkHealth(String link) {
		HttpURLConnection conn = null;
		int responseCode = 404;
		try {
			conn = (HttpURLConnection) new URL(link).openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect();
			responseCode = conn.getResponseCode();
		} catch (Exception e) {
			// log the exception
		} finally {
			conn.disconnect();
		}
		if (responseCode < 400)
			return true;
		else
			return false;
	}

	public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/screenshots/" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}
}
