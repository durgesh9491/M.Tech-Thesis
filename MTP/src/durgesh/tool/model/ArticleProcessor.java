package durgesh.tool.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import durgesh.tool.output.analysis.ResultAnalysis;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * @author Article Processor is the main module having results from all the
 *         other modules Implementing: Data-set processor, Article cleaning
 *         using regex, Spell processor, and Word spinner.
 *
 */
public class ArticleProcessor {
	private static MaxentTagger tagger;

	private static final String add(String src, String tgt) {
		if (src.length() == 0)
			src = tgt;
		else
			src += " " + tgt;
		return src;
	}

	/**
	 * @spellProcessor: Processes each token(word) of the clean article and
	 *                  performs spell correction based on the priorities of the
	 *                  words. i.e., res vector<String>.
	 */
	@SuppressWarnings("resource")
	private static final String spellProcessor(String article) {
		String correctArticle = "";
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String s : tokens) {
			Vector<String> res = SpellCorrector.correct(
					correctArticle.split(" "), idx, s);
			idx += 1;
			if (res.size() == 0) {
				correctArticle = add(correctArticle, s);
				continue;
			}
			System.out.print(s + " = ");
			Iterator<String> it = res.iterator();
			while (it.hasNext()) {
				System.out.print(it.next() + " ");
			}
			System.out.print("\nEnter index:: ");
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

	public static final void testSpellProcessor(String article, String orgString) {
		String[] tokens = article.split(" ");
		String[] orgTokens = orgString.split(" ");
		int idx = 0;
		for (String s : tokens) {
			Vector<String> res = new Vector<String>(SpellCorrector.testCorrect(
					orgTokens, idx, s));
			boolean flag = false;
			for (int i = 0; i < res.size(); i++) {
				if (res.elementAt(i).equals(orgTokens[idx])) {
					flag = true;
					ResultAnalysis.myHit++;
					break;
				}
			}
			if (!flag)
				ResultAnalysis.myMiss++;
			idx += 1;
		}
	}

	/**
	 * @param inputArticle
	 * @return article with no special characters except (a-zA-Z0-9')
	 */
	private static final String cleanInputArticle(String inputArticle) {
		String cleanArticle = "";
		String[] tokens = inputArticle.split(" ");
		for (String s : tokens) {
			String res = s.toString().replaceAll("[^a-zA-Z0-9']", "")
					.toLowerCase();
			cleanArticle = add(cleanArticle, res);
		}
		return cleanArticle;
	}

	/**
	 * @param target
	 * @spinProcessor: Processes each token(word) of the corretedArticle and
	 *                 performs word spinning based on the priorities of the
	 *                 words(synonyms). i.e., res vector<String>.
	 * @return final article after performing spinning
	 */
	@SuppressWarnings("resource")
	private static final String spinProcessor(String article) {
		String resArticle = "";
		String[] tokens = article.split(" ");
		int idx = 0;
		for (String token : tokens) {
			if (posTagger.isCandidatePOS(tagger, token)) {
				Vector<String> res = Spinner.SpinWord(resArticle.split(" "),
						idx, token);
				idx += 1;
				if (res.size() == 0) {
					resArticle = add(resArticle, token);
					continue;
				}
				System.out.print(token + " = ");
				Iterator<String> it = res.iterator();
				while (it.hasNext()) {
					System.out.print(it.next() + " ");
				}
				System.out.print("\nEnter index:: ");
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

	/**
	 * This function performs pre-processing and prepares dictionary and data
	 * set for lookups
	 */
	public static final void init() {
		String synPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/cleanSynonyms.txt";
		String dicPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/Dictionary.txt";
		String uniGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/n1_.txt";
		String biGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/n2_.txt";
		String triGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/n3_.txt";
		String fourGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/n4_.txt";
		String fiveGramPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/model/n5_.txt";
		String taggerPath = "/home/durgesh9491/workspace/MTP/src/durgesh/tool/POSmodels/english-left3words-distsim.tagger";
		tagger = new MaxentTagger(taggerPath);
		posTagger.selectPOS();
		ProcessDataSet.Unigram(uniGramPath);
		ProcessDataSet.Bigram(biGramPath);
		ProcessDataSet.Trigram(triGramPath);
		ProcessDataSet.Fourgram(fourGramPath);
		ProcessDataSet.Fivegram(fiveGramPath);
		ProcessDataSet.Synonyms(synPath);
		ProcessDataSet.DicWords(dicPath);
		ProcessDataSet.NgramCoefficient();
	}

	public static void main(String[] args) throws IOException {
		init();
		System.out
				.println("Note: Enter 0 to select current word, otherwise enter sequence number of a word from listed words");
		System.out.println("Enter Number of Test Cases :: ");
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(
				System.in));
		int test = 0;
		test = Integer.parseInt(bufReader.readLine());
		for (int i = 1; i <= test; i++) {
			String inputArticle = bufReader.readLine();
			System.out
					.println("-----------------Spelling Correction---------------");
			System.out.println(cleanInputArticle(inputArticle));
			String correctArticle = spellProcessor(cleanInputArticle(inputArticle));
			System.out.println("\nCorrected Article is:\n" + correctArticle
					+ "\n");
			System.out.println("-----------------Word Spinning---------------");
			String finalArticle = spinProcessor(correctArticle);
			System.out.println("\nFinal Output is:\n" + finalArticle);
			System.out.println("-----------------End---------------\n\n");
		}
		bufReader.close();
		System.out.println("\nProcess Done !!!");
	}
}
