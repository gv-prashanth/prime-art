package com.vadrin.primeart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PrimeArtApplicationRunner implements ApplicationRunner {

	@Autowired
	PrimalityTester primalityTester;

	@Autowired
	WebDriver driver;

	@Value("${prime-art.alpertron.url}")
	private String alpertronUrl;

	@Value("${prime-art.input.image}")
	private String inputFile;

	@Value("${prime-art.output.folder}")
	private String outputFolder;

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtApplicationRunner.class);

	String input = "";
	int rows = 0;
	int cols = 0;

	@Override
	public void run(ApplicationArguments args) throws IOException {
		System.out.println("Begin processing the image");
		readInputFile();
		final String inputExcludingLast = input.substring(0, input.length() - 7);
		int incremental = 100000;
		driver.get(alpertronUrl);
		while (incremental < 999999) {
			String thisInput = inputExcludingLast + Integer.toString(incremental) + "1";
			String result = primalityTester.test(thisInput);
			logger.info(result + " :" + thisInput);
			if (result.equalsIgnoreCase("PRIME")) {
				writeOutput(thisInput);
			}
			incremental++;
		}
		driver.close();
		driver.quit();
		System.out.println("Finished processing the image");
	}

	private void writeOutput(String output) throws IOException {
		FileWriter fw = new FileWriter(outputFolder + "\\" + (new Timestamp(System.currentTimeMillis())).getTime() + ".txt");
		String[] outputArray = formatOutput(output);
		for(int i=0;i<outputArray.length;i++) {
			fw.write(outputArray[i]);
			fw.write(System.lineSeparator());
		}
		fw.close();
	}

	private String[] formatOutput(String output) {
		String[] toReturn = new String[rows];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = output.substring(i*(cols), (i*(cols)) + cols);
		}
		return toReturn;
	}

	private void readInputFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String ln;
		while ((ln = br.readLine()) != null) {
			this.input = this.input + ln;
			rows++;
			cols = ln.length();
		}
		System.out.println(rows + " rows and " + cols + " columns");
		br.close();
	}

}