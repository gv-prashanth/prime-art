package com.vadrin.primeart.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RandomizingService {

	@Value("${prime-art.random.replacementchancepercentage:10}")
	private int replacementChancePercentage;

	public String[] randomizeALittle(String[] asciiStrings) {
		for (int i = 0; i < asciiStrings.length; i++) {
			char[] lineElements = asciiStrings[i].toCharArray();
			String updatedThisLine = "";
			for (int j = 0; j < lineElements.length; j++) {
				updatedThisLine = updatedThisLine + randomReplacement(String.valueOf(lineElements[j]));
			}
			asciiStrings[i] = updatedThisLine;
		}
		return asciiStrings;
	}

	private String randomReplacement(String singleElement) {
		Random random = new Random();
		if (random.nextInt(100) < replacementChancePercentage) {
			return String.valueOf(random.nextInt(10));
		} else {
			return singleElement;
		}
	}
}
