package com.learning.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

	public static void main(String[] args) {
		validateCommandLineArguments(args);
		CommandLine cmd = parseCommandLineArguments(args);
		try {
			BufferedImage image = QRWifi.generateWifiQRCode(args[0], args[1]);
			String optionValue = cmd.getOptionValue("file", "display");

			switch (optionValue) {
			case "display":
				displayImage(image);
				break;
			default:
				saveImage(image, new File(optionValue));
				println("Image has been saved to: " + optionValue);
			}
		} catch (WriterException | IOException e) {
			println(e.getMessage());
		}
	}

	private static void validateCommandLineArguments(String[] args) {
		if (args.length < 2) {
			println("SSID/Name and password arguments are mandatory");
			System.exit(1);
		}
	}

	private static CommandLine parseCommandLineArguments(String[] args) {
		Options options = new Options();

		Option input = new Option("f", "file", true, "output file path");
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

	@SuppressWarnings("serial")
	private static void displayImage(BufferedImage image) {
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

	private static void saveImage(BufferedImage image, File outputFile) throws IOException {
		// TODO: validate outputFile suffix and if it is a folder, use default name
		ImageIO.write(image, "jpg", outputFile);
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
