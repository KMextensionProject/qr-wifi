package com.learning.core;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRWifi {

//	public static void main(String[] args) {
//		validateInputArgs(args);
//		try {
//			BufferedImage qrWifi = generateWifiQRCode(args[0], args[1]);
//			SwingUtilities.invokeLater(() -> displayInWindow(qrWifi));
//		} catch (WriterException wex) {
//			println(wex.getMessage());
//		}
//	}
//
//	private static void validateInputArgs(String[] args) {
//		if (args.length < 2) {
//			println("At least 2 arguments are expected, but found " + args.length);
//			System.exit(500);
//		}
//		// we assume that password is not a mandatory field
//		else if (args[0] == null || args[0].isBlank()) {
//			println("The wifi ssid/name is mandatory and it can not be null nor blank");
//			System.exit(500);
//		}
//	}

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

//	private static void initCli() {
//		Options options = new Options();
//
//        Option input = new Option("f", "file", true, "output file path");
//        input.setRequired(false);
//        options.addOption(input);
//
//        CommandLineParser parser = new DefaultParser();
//        CommandLine cmd = null;//not a good practice, it serves it purpose 
//
//        try {
//            cmd = parser.parse(options, args);
//        } catch (ParseException e) {
//            println(e.getMessage());
//            HelpFormatter formatter = new HelpFormatter();
//            formatter.printHelp("utility-name", options);
//
//            System.exit(1);
//        }
////
////        String inputFilePath = cmd.getOptionValue("input");
////        String outputFilePath = cmd.getOptionValue("output");
//	}
}
