package durgesh.too.output.analysis;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import durgesh.tool.MTech_thesis.ArticleProcessor;

public class ResultAnalysis {
	public static int myHit = 0, myMiss = 0;

	public static void main(String[] args) {
		ArticleProcessor.init();
		String inpPath1 = "/home/durgesh9491/workspace/MTP/src/durgesh/too/output/analysis/genMisspelTest.txt";
		String inpPath2 = "/home/durgesh9491/workspace/MTP/src/durgesh/too/output/analysis/Test.txt";
		try {
			FileReader fileReader = new FileReader(new File(inpPath1));
			StringBuffer stringBuffer = new StringBuffer();
			int numCharsRead;
			char[] charArray = new char[1024];
			while ((numCharsRead = fileReader.read(charArray)) > 0) {
				stringBuffer.append(charArray, 0, numCharsRead);
			}
			String target = stringBuffer.toString();
			fileReader = new FileReader(new File(inpPath2));
			stringBuffer = new StringBuffer();
			charArray = new char[1024];
			while ((numCharsRead = fileReader.read(charArray)) > 0) {
				stringBuffer.append(charArray, 0, numCharsRead);
			}
			fileReader.close();
			String correctString = stringBuffer.toString();
			ArticleProcessor.spellProcessor2(target, correctString);
			int totalTokens = target.split(" ").length;
			System.out.println("Total Real-word Errors: = " + totalTokens);
			System.out.println("True Correct = " + myHit);
			System.out.println("False Correct = " + myMiss);
			System.out.println("Success Rate = " + (double)myHit/(double)totalTokens);
			System.out.println("Failure Rate = " + (double)myMiss/(double)totalTokens);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
