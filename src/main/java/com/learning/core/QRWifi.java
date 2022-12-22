package com.learning.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRWifi {

	public static final BufferedImage generateWifiQRCode(String wifiName, String wifiPassword) throws WriterException, IOException {
		BitMatrix matrix = new QRCodeWriter()
			.encode(constructWifiCommand(wifiName, wifiPassword), BarcodeFormat.QR_CODE, 400, 400);

		return MatrixToImageWriter.toBufferedImage(matrix);
	}

	private static String constructWifiCommand(String wifiName, String wifiPassword) {
		// WIFI:S:<SSID>;T:<WPA|WEP|>;P:<password>;;
		StringBuilder wifiDesc = new StringBuilder("WIFI:S:");
		wifiDesc.append(wifiName);
		wifiDesc.append(";T:WPA;P:");
		wifiDesc.append(wifiPassword);
		wifiDesc.append(";;");
		return wifiDesc.toString();
	}

	@SuppressWarnings("serial")
	public static void displayQRImage(BufferedImage image) {
		JFrame frame = new JFrame("WiFi connection");
		frame.setSize(image.getWidth() + 20, image.getHeight() + 60);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(image, 10, 10, null);
			}
		});
		frame.setVisible(true);
	}

	public static void saveQRImage(BufferedImage image, File outputFile) throws IOException {
		String fileName = outputFile.getName();
		ImageIO.write(image, fileName.substring(fileName.lastIndexOf('.') + 1), outputFile);
	}
}
