package com.vadrin.primeart;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//@Component
public class PrimeSequenceApplicationRunner implements ApplicationRunner {

	@Autowired
	PrimalityTester primalityTester;

	@Autowired
	WebDriver driver;

	@Value("${prime-art.alpertron.url}")
	private String alpertronUrl;

	@Value("${prime-art.output.folder}")
	private String outputFolder;

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtApplicationRunner.class);

	@Override
	public void run(ApplicationArguments args) throws IOException {
		System.out.println("Begin printing the prime sequence");
		BigInteger thisInput = new BigInteger("1000000000000");
		driver.get(alpertronUrl);
		FileWriter fw = new FileWriter(
				outputFolder + "\\" + (new Timestamp(System.currentTimeMillis())).getTime() + ".txt");
		while (thisInput.compareTo(new BigInteger("9999999999999")) < 0) {
			String result = primalityTester.test(thisInput.toString());
			logger.info(result + " :" + thisInput);
			if (result.equalsIgnoreCase("PRIME")) {
				fw.write(thisInput.toString());
				fw.write(System.lineSeparator());
				fw.flush();
			}
			thisInput = thisInput.add(new BigInteger("1"));
		}
		driver.close();
		driver.quit();
		fw.close();
		System.out.println("Finished processing the image");
	}

}
