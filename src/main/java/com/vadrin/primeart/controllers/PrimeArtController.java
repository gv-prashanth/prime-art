package com.vadrin.primeart.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vadrin.primeart.models.Art;
import com.vadrin.primeart.services.ImageFormattingService;
import com.vadrin.primeart.services.RandomizingService;
import com.vadrin.primeart.services.prime.PrimalityService;

@RestController
public class PrimeArtController {

	@Autowired
	PrimalityService primalityService;

	@Autowired
	ImageFormattingService imageFormattingService;
	
	@Autowired
	RandomizingService randomizingService;

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/image/base64png")
	public Art aggregate(@RequestBody String base64Png) throws IOException {
		BufferedImage pngImage = imageFormattingService.getPngImageFromBase64(base64Png);
		BufferedImage jpegImage = imageFormattingService.convertPngToJpeg(pngImage);
		BufferedImage jpegResizedImage = imageFormattingService.resizeImage(jpegImage, 0.075);
		BufferedImage greyJpegImage = imageFormattingService.getGreyScaleJpegFromRGBJpeg(jpegResizedImage);
		String[] asciiStrings = imageFormattingService.constructAsciiStringFromGreyJpegImage(greyJpegImage);
		Arrays.stream(asciiStrings).forEach(line -> logger.info(line));
		String[] randomizedAsciiStrings = randomizingService.randomizeALittle(asciiStrings);
		return run(new Art(randomizedAsciiStrings));
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/image")
	public Art run(@RequestBody Art input) throws IOException {
		String nextPrimeNumber = primalityService.nextPrime(input.toString());
		logger.info(nextPrimeNumber + " :PRIME");
		return new Art(nextPrimeNumber, input.getRows()[0].length());
	}

}