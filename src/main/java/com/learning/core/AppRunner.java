package com.learning.core;

import static com.learning.core.QRWifi.displayQRImage;
import static com.learning.core.QRWifi.generateWifiQRCode;
import static com.learning.core.QRWifi.saveQRImage;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.zxing.WriterException;

public class AppRunner {

	private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String[] args) throws IOException, WriterException {
		// required not flagged arguments
		validateWifiArguments(args);
		// not required flagged arguments
		CommandLine cmd = parseCommandLineArguments(args);

		BufferedImage image = generateWifiQRCode(args[0], args[1]);
		if (cmd.hasOption("file")) {
			File file = new File(cmd.getOptionValue("file"));
			if (file.isDirectory()) {
				file = new File(file, "WiFi_QR.jpg");
			}
			saveQRImage(image, file);
			println("Image has been saved to: " + file.getAbsolutePath());
		} else {
			displayQRImage(image);
		}
	}

	private static void validateWifiArguments(String[] args) {
		if (args.length < 2) {
			println("SSID/Name and password arguments are mandatory");
			System.exit(1);
		}
	}

	private static CommandLine parseCommandLineArguments(String[] args) {
		Options options = new Options();

		Option input = new Option("f", "file", true, "output file or destination");
		input.setRequired(false);
		options.addOption(input);

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			println(e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("utility-name", options);
			System.exit(1);
		}
		return cmd;
	}

	private static void println(CharSequence input) {
		try {
			WRITER.append(input);
			WRITER.append(System.lineSeparator());
			WRITER.flush();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}
