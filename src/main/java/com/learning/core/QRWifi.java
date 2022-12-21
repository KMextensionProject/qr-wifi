package com.learning.core;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
}
