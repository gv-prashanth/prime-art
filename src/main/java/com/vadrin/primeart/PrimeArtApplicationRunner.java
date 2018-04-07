package com.vadrin.primeart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PrimeArtApplicationRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Begin processing the image");
		System.setProperty("webdriver.chrome.driver", "D:\\Projects\\stsworkspace\\prime-art\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.alpertron.com.ar/ECM.HTM");
		driver.findElement(By.id("value")).sendKeys("7");
		driver.findElement(By.id("factor")).click();
		WebElement status = (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"result\"]/ul/li")));
		System.out.println(status.getText());
		driver.close();
		System.out.println("Finished processing the image");
	}

}