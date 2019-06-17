package com.vadrin.primeart.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.primeart.models.Art;
import com.vadrin.primeart.services.ImageFormattingService;
import com.vadrin.primeart.services.PrimalityService;

@RestController
public class PrimeArtController {

	@Autowired
	PrimalityService primalityService;

	@Autowired
	ImageFormattingService imageFormattingService;
	
	@Value("${prime-art.output.folder}")
	private String outputFolder;

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/image/png")
	public Art aggregate(@RequestBody String pngImage) throws IOException {
		double[][] pixels = imageFormattingService.convertPngtoBitmap(pngImage);
		String[] asciiStrings = imageFormattingService.constructAsciiString(pixels);
		return run(new Art(asciiStrings));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/image")
	public Art run(@RequestBody Art input) throws IOException {
		System.out.println("Begin processing the image");
		if (primalityService.isPrime(input.toString())) {
			logger.info(input + " :PRIME");
			return input;
		} else {
			String nextPrimeNumber = primalityService.nextPrime(input.toString());
			logger.info(nextPrimeNumber + " :PRIME");
			return new Art(nextPrimeNumber, input.getRows()[0].length());
		}
	}

}