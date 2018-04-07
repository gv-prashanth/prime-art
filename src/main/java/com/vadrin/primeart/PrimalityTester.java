package com.vadrin.primeart;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PrimalityTester {

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtApplicationRunner.class);
	
	public void test(String input) {
		//System.setProperty("webdriver.chrome.driver","D:\\Projects\\stsworkspace\\prime-art\\drivers\\chromedriver.exe");
		//WebDriver driver = new ChromeDriver();
		System.setProperty("phantomjs.binary.path", "D:\\Projects\\stsworkspace\\prime-art\\drivers\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.get("https://www.alpertron.com.ar/ECM.HTM");
		driver.findElement(By.id("value")).sendKeys(input);
		driver.findElement(By.id("factor")).click();
		for(int i=0;i<10;i++) {
			try {
				WebElement status = (new WebDriverWait(driver, 1))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/ul/li")));
				System.out.println(status.getText());
				logger.info("PRIME: "+input);
				break;
			} catch (TimeoutException ex) {
				try {
					WebElement blue = (new WebDriverWait(driver, 1))
							.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/p[1]")));
					System.out.println();
					logger.info("COMPOSITE: "+input);
					break;
				} catch (TimeoutException e) {

				}
			}
			if(i==29) {
				System.out.println(input + " cant be determined");
				logger.info("UNDETERMINED: "+input);
			}else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		driver.close();
	}
}
