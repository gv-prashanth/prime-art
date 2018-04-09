package com.vadrin.primeart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrimeArtApplication {

	@Value("${prime-art.drivers.chrome}")
	private String chromeDriverLocation;

	public static void main(String[] args) {
		SpringApplication.run(PrimeArtApplication.class, args);
	}

	@Bean
	public WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		return new ChromeDriver();
	}

}
