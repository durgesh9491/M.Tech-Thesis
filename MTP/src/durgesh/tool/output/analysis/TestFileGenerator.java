package durgesh.tool.output.analysis;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class TestFileGenerator {

	public static void main(String[] args) throws IOException {
		String inpPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/output/analysis/Test.txt";
		String outPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/output/analysis/genMisspelTest.txt";
		try {
			File file = new File(inpPath);
			FileReader fileReader = new FileReader(file);
			StringBuffer stringBuffer = new StringBuffer();
			int numCharsRead;
			char[] charArray = new char[1024];
			while ((numCharsRead = fileReader.read(charArray)) > 0) {
				stringBuffer.append(charArray, 0, numCharsRead);
			}
			fileReader.close();
			String orgArticle = stringBuffer.toString()
					.replaceAll("[^a-zA-Z0-9']", " ").toLowerCase();
			orgArticle = orgArticle.replaceAll("  ", " ");
			
			String[] orgTokens = orgArticle.split(" ");
			String ans = "";
			for (String s : orgTokens) {
				Random rand = new Random();
				int r = Math.abs(rand.nextInt());
				int ch = Math.abs(rand.nextInt()) % 4;
				if (ch == 0)
					s = s.replace(s.charAt(r % s.length()),
							(char) ((r + 'a') % 26 + 'a'));
				if (ch == 1) {
					s = s.replace(s.charAt(r % s.length()), (char) (32));
					s = s.replaceAll(" ", "");
				}
				if (ch == 2) {
					int x = r % s.length();
					s = s.substring(0, x) + (char) ((r + 'a') % 26 + 'a')
							+ s.substring(x, s.length());
				}
				if (ch == 3) {
					int x = r % s.length();
					int y = Math.abs(rand.nextInt()) % s.length();
					char[] ar = s.toCharArray();
					char tmp = ar[y];
					ar[y] = ar[x];
					ar[x] = tmp;
					s = String.valueOf(ar);
				}
				if (ans == "") {
					ans += s;
				} else
					ans += " " + s;
			}
			PrintWriter out = new PrintWriter(new FileWriter(outPath));
			out.print(ans);
			out.close();
			System.out.println(orgTokens.length);
			System.out.println("Ans = " + ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
