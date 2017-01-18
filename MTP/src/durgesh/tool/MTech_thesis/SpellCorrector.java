package durgesh.tool.MTech_thesis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

/**
 * @author Module Implementing spell corrector based on weighted N-gram score of
 *         the words.
 */

class SpellCorrector extends NgramScore {
	private static TreeMap<Double, String> treeMap = new TreeMap<Double, String>(
			Collections.reverseOrder());
	private static HashMap<String, Boolean> makeUnique = new HashMap<String, Boolean>();
	private static Vector<String> correctedWords = new Vector<String>();
	private static HashMap<Integer, String> candidates_1 = new HashMap<Integer, String>();
	private static HashMap<Integer, String> candidates_2 = new HashMap<Integer, String>();
	private static ArrayList<String> wordEdits = new ArrayList<String>();

	private static final ArrayList<String> edits(String word) {
		wordEdits.clear();
		for (int i = 0; i < word.length(); ++i)
			wordEdits.add(word.substring(0, i) + word.substring(i + 1));
		for (int i = 0; i < word.length() - 1; ++i)
			wordEdits.add(word.substring(0, i) + word.substring(i + 1, i + 2)
					+ word.substring(i, i + 1) + word.substring(i + 2));
		for (int i = 0; i < word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c)
				wordEdits.add(word.substring(0, i) + String.valueOf(c)
						+ word.substring(i + 1));
		for (int i = 0; i <= word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c)
				wordEdits.add(word.substring(0, i) + String.valueOf(c)
						+ word.substring(i));
		return wordEdits;
	}

	private static final HashMap<Integer, String> findCandidates(
			ArrayList<String> list) {
		candidates_1.clear();
		for (String s : list) {
			if (ProcessDataSet.dicWords.containsKey(s)) {
				if (ProcessDataSet.uniGram.containsKey(s)) {
					candidates_1.put(ProcessDataSet.uniGram.get(s), s);
				}
			}
		}
		return candidates_1;
	}

	public static final HashMap<Integer, String> findNextCandidates(
			ArrayList<String> list) {
		candidates_2.clear();
		for (String s : list) {
			for (String w : edits(s)) {
				if (ProcessDataSet.dicWords.containsKey(w)) {
					if (ProcessDataSet.uniGram.containsKey(w)) {
						candidates_2.put(ProcessDataSet.uniGram.get(w), w);
					}
				}
			}
		}
		return candidates_2;
	}

	public static final Vector<String> correct(String[] tokens, int idx,
			String target) {

		/**
		 * @param tokens
		 *            contains the past history of the ongoing sentence
		 * @param idx
		 *            current index of the word
		 * @param target
		 *            word under consideration
		 * @return list of candidate words eligible for spelling correction
		 */

		correctedWords.clear();
		treeMap.clear();
		makeUnique.clear();
		for (String s : tokens) {
			System.out.print(s + " ");
		}
		System.out.println();
		ArrayList<String> list = new ArrayList<String>(edits(target));
		HashMap<Integer, String> candidates = findCandidates(list);

		if (ProcessDataSet.dicWords.containsKey(target)) {
			makeUnique.put(target, true);
		}
		/*
		 * for candidate words at edit distance 1
		 */
		for (Map.Entry<Integer, String> itr : candidates.entrySet()) {
			double val = ProcessDataSet.ngramCoefficient[1]
					* unigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[2]
					* bigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[3]
					* trigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[4]
					* fourgramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[5]
					* fivegramScore(tokens, idx, itr.getValue());
			treeMap.put(val, itr.getValue());
		}
		int i = 1;
		for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
			Object obj = makeUnique.get(entry.getValue());
			if (obj != null)
				continue;
			correctedWords.add(entry.getValue());
			makeUnique.put(entry.getValue(), true);
			if (++i > 5)
				break;
		}
		/*
		 * for candidate words at edit distance 2
		 */
		treeMap.clear();
		candidates = SpellCorrector.findNextCandidates(list);
		for (Map.Entry<Integer, String> itr : candidates.entrySet()) {
			double val = ProcessDataSet.ngramCoefficient[1]
					* unigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[2]
					* bigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[3]
					* trigramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[4]
					* fourgramScore(tokens, idx, itr.getValue())
					+ ProcessDataSet.ngramCoefficient[5]
					* fivegramScore(tokens, idx, itr.getValue());
			treeMap.put(val, itr.getValue());
		}

		for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
			Object obj = makeUnique.get(entry.getValue());
			if (obj != null)
				continue;
			correctedWords.add(entry.getValue());
			makeUnique.put(entry.getValue(), true);
			if (++i > 10)
				break;
		}
		return correctedWords;
	}
}