package allTests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObject.HomePageObj;
import pageObject.LoginPageObj;
import resources.Base;

public class LoginPage extends Base {
	WebDriver driver;
	public static Logger log= LogManager.getLogger(LoginPage.class.getName());
	@BeforeTest
	public void driverSetup() throws IOException {
		driver = initializeDriver(true, 5, true);
		log.info("Driver is initialized");
		if (driver.getCurrentUrl().contains("sign_in") != true) {
			driver.get(getPropValue("homepageURL"));
			log.info("Navigated to HomePage");
			HomePageObj homePageObj = new HomePageObj(driver);
			homePageObj.getLoginBtn().click();
			log.debug("Clicked on the Login");
		}
	}

	@Test(dataProvider = "getUserCredentials")
	public void validateUserCred(String userStatus, String username, String password) {
		LoginPageObj loginPageObj = new LoginPageObj(driver);
	
		loginPageObj.getUsernameField().sendKeys(username);
		log.debug("Entered Username");
		loginPageObj.getPasswordField().sendKeys(password);
		log.debug("Entered Password");
		loginPageObj.getLoginBtn().click();	
		log.debug("Clicked on Submit Btn");
	}

	@AfterTest
	public void tearDown() {
		 driver.close();
	}

	@DataProvider
	public Object[][] getUserCredentials() throws Throwable {
		// Resize the 2d array
		Object[][] userCredentials = new Object[4][3];
		
		// Fetch the data from excel
		FileInputStream userCredExcelFile = new FileInputStream(getPropValue("userCredPath"));
		XSSFWorkbook workBook = new XSSFWorkbook(userCredExcelFile);

		// Fetch desired sheet
		XSSFSheet sheet = workBook.getSheet("userCred");

		// Iterate through the rows
		log.debug("Fetching all data from Excel and adding to Object Array.");
		Iterator<Row> allRows = sheet.rowIterator(); // ALl the row
		int rowIndex = 0;
		int cellIndex = 0;
		Row dataRow = allRows.next(); // Ignoring the first row
		while (allRows.hasNext()) {
			cellIndex = 0;
			dataRow = allRows.next();
			// All the cell
			Iterator<Cell> allCells = dataRow.cellIterator();
			Cell dataValue = allCells.next();   // Ignoring the first cell userId
			while (allCells.hasNext()) {
				// start saving the value into the object array
				dataValue = allCells.next();
				if (dataValue.getCellType() == CellType.STRING) {
					userCredentials[rowIndex][cellIndex] = dataValue.getStringCellValue().toString();
					//System.out.println(dataValue.getStringCellValue().toString());
				} else {
					userCredentials[rowIndex][cellIndex] = NumberToTextConverter
							.toText(dataValue.getNumericCellValue());
					//System.out.println(NumberToTextConverter.toText(dataValue.getNumericCellValue()));
				}
				cellIndex++;
			}
			rowIndex++;
		}

		// Closing the excel
		workBook.close();
		return userCredentials;
	}

}
