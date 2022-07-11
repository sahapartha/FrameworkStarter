package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageObj {
	//Instances
	private WebDriver driver;
	
	//Locators
	private By usernameField = By.cssSelector("#user_email");
	private By passwordField = By.cssSelector("#user_password");
	private By submitBtn= By.cssSelector("div[class='form-group text-center'] input[name='commit']");
	
	public LoginPageObj(WebDriver driver){
		this.driver=driver;
	}
	public WebElement getUsernameField() {
		return driver.findElement(usernameField);
	}
	public WebElement getPasswordField() {
		return driver.findElement(passwordField);
	}
	public WebElement getLoginBtn() {
		return driver.findElement(submitBtn);
	}
}
