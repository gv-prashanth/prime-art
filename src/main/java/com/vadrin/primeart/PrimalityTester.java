package com.vadrin.primeart;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrimalityTester {

	@Autowired
	private WebDriver driver;

	private static final String PRIME = "PRIME";
	private static final String COMPOSITE = "COMPOSITE";
	private static final String UNDETERMINED = "UNDETERMINED";

	public String test(String input) {

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
				return COMPOSITE;
			}
		} catch (TimeoutException ex) {

		}

		try {
			WebElement primeResult = (new WebDriverWait(driver, 20))
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/ul/li")));
			if (primeResult.getText().toLowerCase().contains("prime")) {
				return PRIME;
			} else {
				return COMPOSITE;
			}
		} catch (TimeoutException ex) {
			return UNDETERMINED;
		}

	}

}
