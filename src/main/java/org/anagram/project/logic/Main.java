package org.anagram.project.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] arg) {

		Main obj = new Main();
		obj.getFile("./wordlist.txt");
	}

	private void getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
//		System.out.println(file);
		try {
			Stream<String> stream2 = Files.lines(file.toPath(), StandardCharsets.ISO_8859_1);
			Map<Object, List<String>> anagrams = stream2.collect(Collectors.groupingBy(w -> sortChars(w)));
//			System.out.println(anagrams);

			// Write to File
			FileOutputStream fop = null;
			File out;
			try {

				file = new File("anagram.txt");
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
				String[] word = new String[1];
				anagrams.forEach((k, v) -> {
//					System.out.println("key: " + k + " value:" + v);
					word[0] += k.toString() + " " + v + "\n";
				});
				
				byte[] contentInBytes = word[0].getBytes();
				fop.write(contentInBytes);
				fop.flush();
				fop.close();
				
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String sortChars(String input) {
		char[] characters = input.toCharArray();
		Arrays.sort(characters);
		return new String(characters);
	}
}
