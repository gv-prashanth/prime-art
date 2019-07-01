package com.vadrin.primeart.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

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

	@Value("${prime-art.maxdigits:1024}")
	private int maxDigits;

	private static final Logger logger = LoggerFactory.getLogger(PrimeArtController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/image/base64png")
	public Art aggregate(@RequestBody String base64Png) throws IOException {
		BufferedImage pngImage = imageFormattingService.getPngImageFromBase64(base64Png);
		BufferedImage jpegImage = imageFormattingService.convertPngToJpeg(pngImage);
		double finalWidth = Math.sqrt(maxDigits) * Math.sqrt((double) jpegImage.getWidth() / jpegImage.getHeight());
		double scale = finalWidth / jpegImage.getWidth();
		BufferedImage jpegResizedImage = imageFormattingService.resizeImage(jpegImage, scale);
		BufferedImage greyJpegImage = imageFormattingService.getGreyScaleJpegFromRGBJpeg(jpegResizedImage);
		String[] asciiStrings = imageFormattingService.constructAsciiStringFromGreyJpegImage(greyJpegImage);
		Arrays.stream(asciiStrings).forEach(line -> logger.info(line));
		asciiStrings = randomizingService.randomizeALittle(asciiStrings);
		return run(new Art(asciiStrings));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/image")
	public Art run(@RequestBody Art input) throws IOException {
		String nextPrimeNumber = primalityService.nextPrime(input.toString());
		logger.info(nextPrimeNumber + " :PRIME");
		return new Art(nextPrimeNumber, input.getRows()[0].length());
	}

}