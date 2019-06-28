package com.vadrin.primeart.services;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageFormattingService {

	public BufferedImage getGreyScaleJpegFromRGBJpeg(BufferedImage colorJpegImage) {
		// get image width and height
		int width = colorJpegImage.getWidth();
		int height = colorJpegImage.getHeight();

		// convert to grayscale
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = colorJpegImage.getRGB(x, y);

				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;

				// calculate average
				int avg = (r + g + b) / 3;

				// replace RGB value with avg
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;

				colorJpegImage.setRGB(x, y, p);
			}
		}
		return colorJpegImage;
	}

	public BufferedImage getPngImageFromBase64(String pngImage) throws IOException {
		String base64Image = pngImage.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		return ImageIO.read(new ByteArrayInputStream(imageBytes));
	}

	public BufferedImage convertPngToJpeg(BufferedImage image) throws IOException {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return result;
	}

	public BufferedImage resizeImage(BufferedImage originalImage, double scale) {
		BufferedImage resizedImage = new BufferedImage((int) (originalImage.getWidth() * scale),
				(int) (originalImage.getHeight() * scale), originalImage.getType());
		Graphics graphics = resizedImage.createGraphics();
		graphics.drawImage(originalImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), null);
		graphics.dispose();
		return resizedImage;
	}
	
	public String[] constructAsciiStringFromGreyJpegImage(BufferedImage greyJpegImage) {
		StringBuffer sb = new StringBuffer();
		// get image width and height
		int width = greyJpegImage.getWidth();
		int height = greyJpegImage.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = greyJpegImage.getRGB(x, y);
				
				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;
				
				int avg = (r + g + b) / 3;
				//0 is black & 255 is white
				if (avg == 0)
					sb.append("8");
				 else if (avg < 256 / 3)
				 sb.append("3");
				 else if (avg < 2 * (256 / 3))
				 sb.append("7");
				else
					sb.append("1");
			}
			sb.append("\n");
		}
		return sb.toString().split("\n");
	}
	
}
