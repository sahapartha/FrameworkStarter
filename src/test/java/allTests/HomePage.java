package allTests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObject.HomePageObj;
import resources.Base;

public class HomePage extends Base {
	private WebDriver driver;
	public static Logger log= LogManager.getLogger(HomePage.class.getName());
	HomePageObj homePageObj = null;
	@BeforeTest
	public void driverSetup() throws IOException {
		// initializeDriver(implicitWaitSwitch, implicitWaitTime, maxWindow)
		driver = initializeDriver(false, 0, false);
		log.info("Driver is initialized");
		driver.get(getPropValue("homepageURL"));
		log.info("Navigated to HomePage");
		homePageObj= new HomePageObj(driver);
	}

	@Test
	public void validateLoginLink() throws MalformedURLException, IOException {
		String loginLink = homePageObj.getLoginLink();
		log.debug("Validating the Login Link URL");
		Assert.assertTrue(checkLinkHealth(loginLink));
	}
	@Test
	public void validateAllContectLink() throws MalformedURLException, IOException {
		List<WebElement> allContentLinks= homePageObj.getAllContentLinks();	
		log.debug("Validating all the link in Content Section");
		for(WebElement link: allContentLinks) {
			Assert.assertTrue(checkLinkHealth(link.getAttribute("href")));
		}
	}

	@AfterTest
	public void tearDown() {
		driver.close();
	}

}
