package durgesh.tool.MTech_thesis;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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

	public static final HashMap<Integer, String> findCandidates(String word,
			ArrayList<String> list) {
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		for (String s : list) {
			if (ProcessDataSet.uniGram.containsKey(s)) {
				candidates.put(ProcessDataSet.uniGram.get(s), s);
			}
		}
		return candidates;
	}

	public static final HashMap<Integer, String> findNextCandidates(
			ArrayList<String> list) {
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		for (String s : list) {
			for (String w : edits(s)) {
				if (ProcessDataSet.uniGram.containsKey(w)) {
					candidates.put(ProcessDataSet.uniGram.get(w), w);
				}
			}
		}
		return candidates;
	}

	public static final String correct(String[] tokens, int idx, String target) {
		String correctedWord = target;
		if (ProcessDataSet.uniGram.containsKey(correctedWord) == true) {
			return correctedWord;
		}
		ArrayList<String> list = edits(target);
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		candidates = findCandidates(correctedWord, list);

		if (candidates.isEmpty()) {
			candidates = SpellCorrector.findNextCandidates(list);
		}
		double maxi = 0.0;

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
			if (val > maxi) {
				correctedWord = itr.getValue().toString();
				maxi = val;
			}
		}
		return correctedWord;
	}
}