package com.learning.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRWifi {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String[] args) {
		validateInputArgs(args);		
		try {
			BufferedImage qrWifi = generateWifiQRCode(args[0], args[1]);
			SwingUtilities.invokeLater(() -> displayInWindow(qrWifi));
		} catch (WriterException wex) {
			LOGGER.severe(() -> wex.getMessage());
		}
	}

	private static void validateInputArgs(String[] args) {
		if (args.length != 2) {
			LOGGER.severe(() -> "Expected arguments are 2, found " + args.length);
			System.exit(500);
		}
	}

	private static final BufferedImage generateWifiQRCode(String wifiName, String wifiPassword) throws WriterException {
		BitMatrix matrix = new QRCodeWriter()
			.encode(constructWifiCommand(wifiName, wifiPassword), BarcodeFormat.QR_CODE, 400, 400);

		return MatrixToImageWriter.toBufferedImage(matrix);
	}

	// WIFI:S:<SSID>;T:<WPA|WEP|>;P:<password>;;
	private static String constructWifiCommand(String wifiName, String wifiPassword) {
		// we assume that password is not a mandatory field
		if (wifiName == null || wifiName.isBlank()) {
			LOGGER.severe("The wifi ssid/name is mandatory and it can not be null nor blank");
			System.exit(500);
		}
		
		StringBuilder wifiDesc = new StringBuilder("WIFI:S:");
		wifiDesc.append(wifiName);
		wifiDesc.append(";T:WPA;P:");
		wifiDesc.append(wifiPassword);
		wifiDesc.append(";;");

		return wifiDesc.toString();
	}
	
	@SuppressWarnings("serial")
	private static void displayInWindow(BufferedImage image) {
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
}
