package pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageObj {
	// Instances
	private WebDriver driver;

	// Locators
	By login = By.cssSelector("a[href*='sign_in']");
	By closePopup = By.xpath("//button[contains(text(),'NO THANKS')]");
	By allContentLinks = By.cssSelector("#content h3 a");

	public HomePageObj(WebDriver driver) {
		this.driver = driver;
		closePopup();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public WebElement getLoginBtn() {
		return driver.findElement(login);
	}

	public String getLoginLink() {
		return driver.findElement(login).getAttribute("href");
	}

	public List<WebElement> getAllContentLinks() {
		return driver.findElements(allContentLinks);
	}

	public void closePopup() {
		WebElement closePopupBtn = driver.findElement(closePopup);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(closePopupBtn));
		driver.findElement(closePopup).click();
		wait.until(ExpectedConditions.invisibilityOf(closePopupBtn));
	}
}
