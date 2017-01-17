package durgesh.tool.MTech_thesis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

import edu.stanford.nlp.util.Pair;

class SpellCorrector extends NgramScore {

	private static final ArrayList<String> edits(String word) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < word.length(); ++i)
			result.add(word.substring(0, i) + word.substring(i + 1));
		for (int i = 0; i < word.length() - 1; ++i)
			result.add(word.substring(0, i) + word.substring(i + 1, i + 2)
					+ word.substring(i, i + 1) + word.substring(i + 2));
		for (int i = 0; i < word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c)
				result.add(word.substring(0, i) + String.valueOf(c)
						+ word.substring(i + 1));
		for (int i = 0; i <= word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c)
				result.add(word.substring(0, i) + String.valueOf(c)
						+ word.substring(i));
		return result;
	}

	private static final HashMap<Integer, String> findCandidates(String word,
			ArrayList<String> list) {
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		for (String s : list) {
			if (ProcessDataSet.dicWords.containsKey(s)) {
				if (ProcessDataSet.uniGram.containsKey(s)) {
					candidates.put(ProcessDataSet.uniGram.get(s), s);
				}
			}
		}
		return candidates;
	}

	public static final HashMap<Integer, String> findNextCandidates(
			ArrayList<String> list) {
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		for (String s : list) {
			for (String w : edits(s)) {
				if (ProcessDataSet.dicWords.containsKey(s)) {
					if (ProcessDataSet.uniGram.containsKey(w)) {
						candidates.put(ProcessDataSet.uniGram.get(w), w);
					}
				}
			}
		}
		return candidates;
	}

	public static final Vector<String> correct(String[] tokens, int idx,
			String target) {
		Vector<String> correctedWord = new Vector<String>();

		ArrayList<String> list = edits(target);
		HashMap<Integer, String> candidates = findCandidates(target, list);
		HashMap<String, Boolean> check = new HashMap<String, Boolean>();
		if (ProcessDataSet.dicWords.containsKey(target)) {
			check.put(target, true);
		}
		/*
		 * for candidate words at edit distance 1
		 */
		PriorityQueue<Pair<Double, String>> queue = new PriorityQueue<Pair<Double, String>>(
				Collections.reverseOrder());
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
			queue.add(new Pair<Double, String>(val, itr.getValue()));
		}
		int i = 1;
		for (Pair<Double, String> entry : queue) {
			Object obj = check.get(entry.second());
			if (obj != null)
				continue;
			correctedWord.add(entry.second());
			check.put(entry.second(), true);
			if (++i > 5)
				break;
		}
		/*
		 * for candidate words at edit distance 2
		 */
		queue.clear();
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
			queue.add(new Pair<Double, String>(val, itr.getValue()));
		}
		for (Pair<Double, String> entry : queue) {
			Object obj = check.get(entry.second());
			if (obj != null)
				continue;
			correctedWord.add(entry.second());
			check.put(entry.second(), true);
			if (++i > 10)
				break;
		}
		return correctedWord;
	}
}