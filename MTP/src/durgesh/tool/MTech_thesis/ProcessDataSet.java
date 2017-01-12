package durgesh.tool.MTech_thesis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.Scanner;

public class ProcessDataSet {

	public final static HashMap<String, Integer> uniGram = new HashMap<String, Integer>();
	public final static HashMap<String, Integer> biGram = new HashMap<String, Integer>();
	public final static HashMap<String, Integer> triGram = new HashMap<String, Integer>();
	public final static HashMap<String, Integer> fourGram = new HashMap<String, Integer>();
	public final static HashMap<String, Integer> fiveGram = new HashMap<String, Integer>();
	public final static HashMap<String, Vector<String>> synonyms = new HashMap<String, Vector<String>>();
	public static int totalwords;

	public static void Unigram(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfSpace = line.indexOf(" ");
			int item = Integer.parseInt(line.substring(0, indexOfSpace));
			String gram = line.substring(indexOfSpace).trim();
			uniGram.put(gram, item);
		}
		setTotalWords();
		scanner.close();

	}

	public static void Bigram(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfSpace = line.indexOf(" ");
			int item = Integer.parseInt(line.substring(0, indexOfSpace));
			String gram = line.substring(indexOfSpace).trim();
			biGram.put(gram, item);
		}
		scanner.close();
	}

	public static void Trigram(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfSpace = line.indexOf(" ");
			int item = Integer.parseInt(line.substring(0, indexOfSpace));
			String gram = line.substring(indexOfSpace).trim();
			triGram.put(gram, item);
		}
		scanner.close();
	}

	public static void Fourgram(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfSpace = line.indexOf(" ");
			int item = Integer.parseInt(line.substring(0, indexOfSpace));
			String gram = line.substring(indexOfSpace).trim();
			fourGram.put(gram, item);
		}
		scanner.close();
	}

	public static void Fivegram(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int indexOfSpace = line.indexOf(" ");
			int item = Integer.parseInt(line.substring(0, indexOfSpace));
			String gram = line.substring(indexOfSpace).trim();
			fiveGram.put(gram, item);
		}
		scanner.close();
	}

	public static void Synonyms(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or bad path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] tokens = line.split(",");
			Vector<String> synList = new Vector<String>();
			for (int i = 1; i < tokens.length; i++) {
				synList.add(tokens[i]);
			}
			synonyms.put(tokens[0], synList);
		}
		scanner.close();
	}

	public static void setTotalWords() {
		int size = 0;
		for (Entry<String, Integer> has : uniGram.entrySet()) {
			size += has.getValue();
		}
		totalwords = size;
	}

	public static int getTotalwords() {
		return totalwords;
	}
}
