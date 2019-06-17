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

	public double[][] convertPngtoBitmap(String pngImage) throws IOException {
		String base64Image = pngImage.split(",")[1];
		byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
		BufferedImage jpeImage = convertPngToJpg(ImageIO.read(new ByteArrayInputStream(imageBytes)));
		BufferedImage jpegResizedImage = resizeImage(jpeImage, 0.075);
		double[][] pixels = new double[jpegResizedImage.getHeight()][jpegResizedImage.getWidth()];
		for( int i = 0; i < jpegResizedImage.getWidth(); i++ ) {
		    for( int j = 0; j < jpegResizedImage.getHeight(); j++ ) {
		    	if(jpegResizedImage.getRGB( i, j )==-1) {
		    		 pixels[j][i] = 0;
		    	}else {
		    		 pixels[j][i] = 1;
		    	}
		    }
		}
		return pixels;
	}

	private BufferedImage convertPngToJpg(BufferedImage image) throws IOException {
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return result;
	}

	private BufferedImage resizeImage(BufferedImage originalImage, double scale) {
		BufferedImage resizedImage = new BufferedImage((int) (originalImage.getWidth() * scale),
				(int) (originalImage.getHeight() * scale), originalImage.getType());
		Graphics graphics = resizedImage.createGraphics();
		graphics.drawImage(originalImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), null);
		graphics.dispose();
		return resizedImage;
	}
	
	//TODO: There is bug in this method, even incase of black and white, nothing is going beyond 256/3
	public String[] constructAsciiString(double[][] image) {
		StringBuffer sb = new StringBuffer();

		for (int row = 0; row < image.length; row++) {
//			sb.append("|");
			for (int col = 0; col < image[row].length; col++) {
				int pixelVal = (int) image[row][col];
				if (pixelVal == 0)
					sb.append("1");
//				else if (pixelVal < 256 / 3)
//					sb.append(".");
//				else if (pixelVal < 2 * (256 / 3))
//					sb.append("x");
				else
					sb.append("8");
			}
//			sb.append("|");
			sb.append("\n");
		}
		System.out.println(sb);
		return sb.toString().split("\n");
	}

}
