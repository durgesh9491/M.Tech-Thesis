package durgesh.tool.MTech_thesis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Dataset preprocessing module, later all the data structures will be
 *         used for faster dictionary based lookups. Also implements the method
 *         calculating weight coefficient(lambda) of N-gram for a word 'x'.
 */

public class ProcessDataSet {
	public final static TreeMap<String, Integer> uniGram = new TreeMap<String, Integer>();
	public final static TreeMap<String, Integer> biGram = new TreeMap<String, Integer>();
	public final static TreeMap<String, Integer> triGram = new TreeMap<String, Integer>();
	public final static TreeMap<String, Integer> fourGram = new TreeMap<String, Integer>();
	public final static TreeMap<String, Integer> fiveGram = new TreeMap<String, Integer>();
	public final static TreeMap<String, Boolean> dicWords = new TreeMap<String, Boolean>();
	public final static TreeMap<String, Vector<String>> synonyms = new TreeMap<String, Vector<String>>();
	private static int totalwords;
	private final static int NgramsLimit = 5;
	private static final int scale = 1000000;
	public static final int smoothingConst = 100000;
	public final static double[] ngramCoefficient = new double[NgramsLimit + 1];

	public static void NgramCoefficient() {
		double select = 0, mx = 0;
		for (double i = 0; i < 1; i += 0.000001) {
			double res = 0;
			for (int j = 1; j <= NgramsLimit; j++) {
				res += Math.pow(i, j);
			}
			if (res <= 1 && mx < res) {
				select = i;
				mx = res;
			}
		}
		/*
		 * scaling is done for better comparison of results(to avoid precision
		 * errors)
		 */
		for (int i = 1; i <= NgramsLimit; i++) {
			ngramCoefficient[i] = scale * Math.pow(select, NgramsLimit - i + 1);
		}
	}

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

	public static void DicWords(String file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file), "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("File not found or Bad Path");
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			dicWords.put(line, true);
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
