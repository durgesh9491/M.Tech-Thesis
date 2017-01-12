package durgesh.tool.MTech_thesis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ArticleProcessor {

	private static String spellProcessor(String article) {
		String correctArticle = null;
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String s : tokens) {
			String res = SpellCorrector.correct(tokens, idx, s);
			if (correctArticle == null)
				correctArticle = res;
			else
				correctArticle += " " + res;
			idx += 1;
		}
		return correctArticle;
	}

	@SuppressWarnings("resource")
	public static String cleanInputArticle(String inpPath) {
		FileReader input = null;
		try {
			input = new FileReader(inpPath);
		} catch (FileNotFoundException e1) {
			System.out.println("File not found or Bad path");
			e1.printStackTrace();
		}
		String line = null, cleanArticle = null;
		try {
			BufferedReader bufRead = new BufferedReader(input);
			while ((line = bufRead.readLine()) != null) {
				String[] tokens = line.split(" ");
				for (String s : tokens) {
					String res = s.toString().replaceAll("[^a-zA-Z0-9]", "")
							.toLowerCase();
					if (cleanArticle == null)
						cleanArticle = res;
					else
						cleanArticle += " " + res;
				}
			}
		} catch (IOException e) {
			System.out.println("line not found");
			e.printStackTrace();
		}
		return cleanArticle;

	}

	public static final String spinProcessor(String article) {
		String taggerPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/models/english-left3words-distsim.tagger";
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		posTagger.selectPOS();

		String resArticle = "";
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String token : tokens) {
			if (posTagger.isCandidatePOS(tagger, token)) {
				String res = Spinner.SpinWord(tokens, idx, token);
				if (resArticle.isEmpty())
					resArticle = res;
				else
					resArticle += " " + res;
				idx += 1;
			} else {
				if (resArticle.isEmpty())
					resArticle = token;
				else
					resArticle += " " + token;
			}
		}
		return resArticle;
	}

	public static final void init() {
		String synPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/cleanSynonyms.txt";
		String uniGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/n1_.txt";
		String biGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/n2_.txt";
		String triGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/n3_.txt";
		String fourGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/n4_.txt";
		String fiveGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/n5_.txt";

		ProcessDataSet.Unigram(uniGramPath);
		ProcessDataSet.Bigram(biGramPath);
		ProcessDataSet.Trigram(triGramPath);
		ProcessDataSet.Fourgram(fourGramPath);
		ProcessDataSet.Fivegram(fiveGramPath);
		ProcessDataSet.Synonyms(synPath);
	}

	public static void main(String[] args) throws IOException {
		init();
		String inpPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/article.txt";
		String correctArticle = spellProcessor(cleanInputArticle(inpPath));
		System.out.println(correctArticle);

		String finalArticle = spinProcessor(correctArticle);
		System.out.println(finalArticle);
		System.out.println("Process Done !!!");
	}
}
