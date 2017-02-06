package durgesh.tool.MTech_thesis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * @author Module Implementing spell corrector based on weighted N-gram score of
 *         the words.
 */

class SpellCorrector extends NgramScore {
	private static TreeMap<Double, String> treeMap = new TreeMap<Double, String>(
			Collections.reverseOrder());
	private static TreeMap<String, Boolean> makeUnique = new TreeMap<String, Boolean>();
	private static Vector<String> correctedWords = new Vector<String>();
	private static TreeMap<Integer, String> candidates_1 = new TreeMap<Integer, String>();
	private static TreeMap<Integer, String> candidates_2 = new TreeMap<Integer, String>();
	private static ArrayList<String> wordEdits = new ArrayList<String>();
	private static HashMap<String, Boolean> hashMap = new HashMap<String, Boolean>();

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

	private static final TreeMap<Integer, String> findCandidates(
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

	public static final TreeMap<Integer, String> findNextCandidates(
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

	private static final Vector<String> findCandidates1(ArrayList<String> list) {
		int cnt = 0;
		Vector<String> res = new Vector<String>();
		for (String s : list) {
			if (ProcessDataSet.dicWords.containsKey(s)) {
				if (ProcessDataSet.uniGram.containsKey(s)) {
					if (hashMap.containsKey(s) == false) {
						hashMap.put(s, true);
						++cnt;
						res.add(s);
						if (cnt >= 5)
							break;
					}
				}
			}
		}
		return res;
	}

	public static final Vector<String> findNextCandidates1(
			ArrayList<String> list) {
		int cnt = 0;
		Vector<String> res = new Vector<String>();
		for (String s : list) {
			if (cnt >= 5)
				break;
			for (String w : edits(s)) {
				if (ProcessDataSet.dicWords.containsKey(w)) {
					if (ProcessDataSet.uniGram.containsKey(w)) {
						if (hashMap.containsKey(w) == false) {
							hashMap.put(w, true);
							++cnt;
							res.add(w);
							if (cnt >= 5)
								break;
						}
					}
				}
			}
		}
		return res;
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
		ArrayList<String> list = new ArrayList<String>(edits(target));
		TreeMap<Integer, String> candidates = findCandidates(list);

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
			if (++i > 2)
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
			if (++i > 3)
				break;
		}
		return correctedWords;
	}

	public static final Vector<String> correct1(String[] tokens, int idx,
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
		hashMap.clear();
		ArrayList<String> list = new ArrayList<String>(edits(target));
		Vector<String> candidates = new Vector<String>(findCandidates1(list));
		candidates.addAll(findNextCandidates1(list));
		return candidates;
	}
}