package durgesh.tool.MTech_thesis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class ArticleProcessorWithOptions {
	private static MaxentTagger tagger;

	private static String add(String src, String tgt) {
		if (src == null)
			src = tgt;
		else
			src += " " + tgt;
		return src;
	}

	private static String spellProcessor(String article) {

		String correctArticle = null;
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String s : tokens) {
			Vector<String> res = SpellCorrectorWithOptions.correct(tokens, idx,
					s);
			idx += 1;
			System.out.print(s + " = ");
			Iterator<String> it = res.iterator();
			while (it.hasNext()) {
				System.out.print(it.next() + " ");
			}
			System.out.println();
			System.out.print("Enter the index of the word to select:: ");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int chosen = scanner.nextInt();
			if (chosen == 0) {
				correctArticle = add(correctArticle, s);
			} else {
				correctArticle = add(correctArticle, res.elementAt(chosen - 1));
			}

		}
		return correctArticle;
	}

	public static String cleanInputArticle(String inputArticle) {
		String cleanArticle = null;
		String[] tokens = inputArticle.split(" ");
		for (String s : tokens) {
			String res = s.toString().replaceAll("[^a-zA-Z0-9']", " ")
					.toLowerCase();
			cleanArticle = add(cleanArticle, res);
		}
		return cleanArticle;
	}

	@SuppressWarnings("resource")
	public static final String spinProcessor(String article) {

		String resArticle = null;
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String token : tokens) {
			if (posTagger.isCandidatePOS(tagger, token)) {
				Vector<String> res = SpinnerWithOptions.SpinWord(tokens, idx,
						token);
				idx += 1;
				System.out.print(token + " = ");
				Iterator<String> it = res.iterator();
				while (it.hasNext()) {
					System.out.print(it.next() + " ");
				}
				System.out.println();
				System.out.print("Enter the index of the word to select:: ");
				Scanner scanner = new Scanner(System.in);
				int chosen = scanner.nextInt();
				if (chosen == 0) {
					resArticle = add(resArticle, token);
				} else {
					resArticle = add(resArticle, res.elementAt(chosen - 1));
				}
			} else
				resArticle = add(resArticle, token);
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
		String taggerPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/models/english-left3words-distsim.tagger";
		tagger = new MaxentTagger(taggerPath);
		posTagger.selectPOS();
		ProcessDataSet.Unigram(uniGramPath);
		ProcessDataSet.Bigram(biGramPath);
		ProcessDataSet.Trigram(triGramPath);
		ProcessDataSet.Fourgram(fourGramPath);
		ProcessDataSet.Fivegram(fiveGramPath);
		ProcessDataSet.Synonyms(synPath);
		ProcessDataSet.NgramCoefficient();
	}

	public static void main(String[] args) throws IOException {
		init();
		System.out.println("Enter Number of Test Cases :: ");
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(
				System.in));
		int test;
		test = Integer.parseInt(bufReader.readLine());
		for (int i = 1; i <= test; i++) {
			String inputArticle = bufReader.readLine();
			System.out.println("-----------------Spelling Correction---------------");
			String correctArticle = spellProcessor(cleanInputArticle(inputArticle));
			System.out.println("\nCorrected Article is:\n" + correctArticle
					+ "\n");
			System.out.println("-----------------Word Spinning---------------");
			String finalArticle = spinProcessor(correctArticle);
			System.out.println("\nFinal Output is:\n" + finalArticle);
			System.out.println("-----------------End---------------\n\n");
		}
		System.out.println("\nProcess Done !!!");
	}

}
