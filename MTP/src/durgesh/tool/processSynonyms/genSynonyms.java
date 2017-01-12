package durgesh.tool.processSynonyms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class genSynonyms {
	public static void main(String[] str) throws FileNotFoundException {
		PrintWriter outputfile = new PrintWriter("/home/durgesh9491/workspace/MTP/src/durgesh/tool/processSynonyms/candidateWordSynonyms.txt");

		String inpPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/processSynonyms/rawSynonyms.txt";
		FileReader input = null;
		try {
			input = new FileReader(inpPath);
		} catch (FileNotFoundException e1) {
			System.out.println("File not found or Bad path");
			e1.printStackTrace();
		}
		String line = null;
		try {
			BufferedReader bufRead = new BufferedReader(input);
			while ((line = bufRead.readLine()) != null) {
				String[] tokens = line.split(",");
				for (String s : tokens) {
					String res = s.toString().replaceAll("[^a-zA-Z0-9]", "");
					outputfile.println(res);
				}
			}
		} catch (IOException e) {
			System.out.println("line not found");
			e.printStackTrace();
		}

		outputfile.close();
	}
}
