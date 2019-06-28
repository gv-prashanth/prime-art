package com.vadrin.primeart.services.prime;

import java.math.BigInteger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AlpertronPrimalityServiceImpl implements PrimalityService {

	private WebDriver driver;

	private static final String PRIME = "PRIME";
	private static final String COMPOSITE = "COMPOSITE";
	private static final String UNDETERMINED = "UNDETERMINED";

	@Value("${prime-art.alpertron.url}")
	private String alpertronUrl;
	
	@Value("${prime-art.drivers.chrome}")
	private String chromeDriverLocation;

	public void loadDriver() {
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		driver = new ChromeDriver();
		driver.get(alpertronUrl);
	}

	public void destroyDriver() {
		driver.close();
		driver.quit();
	}
	
	private boolean isPrime(String input) {
		try {
			WebElement stop = driver.findElement(By.xpath("//*[@id=\"stop\"]"));
			stop.click();
		} catch (ElementNotVisibleException ex) {

		}

		WebElement inputBox = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"value\"]")));
		inputBox.clear();
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", inputBox, input);
		WebElement factor = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"factor\"]")));
		factor.click();

		try {
			WebElement compositeResult = (new WebDriverWait(driver, 10)).until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/p[@class=\"blue\"]")));
			if (compositeResult.getText().toLowerCase().contains("digits) =")) {
				//return COMPOSITE;
				return false;
			}
		} catch (TimeoutException ex) {

		} catch (StaleElementReferenceException ex) {

		}

		try {
			WebElement primeResult = (new WebDriverWait(driver, 20))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/ul/li")));
			if (primeResult.getText().toLowerCase().contains("prime")) {
				//return PRIME;
				return true;
			} else {
				//return COMPOSITE;
				return false;
			}
		} catch (TimeoutException ex) {
			//return UNDETERMINED;
			return false;
		}
	}

	@Override
	public String nextPrime(String input) {
		loadDriver();
		BigInteger b = new BigInteger(input);
		while(!isPrime(b.toString())){
			b = b.add(new BigInteger("1"));
		}
		destroyDriver();
		return b.toString();
	}

}
