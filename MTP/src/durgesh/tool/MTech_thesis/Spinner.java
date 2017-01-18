package durgesh.tool.MTech_thesis;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 * @author Module Implementing word spinner based on weighted N-gram score of
 *         the words.
 *
 */
public class Spinner extends NgramScore {
	private static TreeMap<Double, String> treeMap = new TreeMap<Double, String>(
			Collections.reverseOrder());
	private static Vector<String> result = new Vector<String>();

	/**
	 * @param tokens
	 *            contains the past history of the ongoing sentence
	 * @param idx
	 *            current index of the word
	 * @param target
	 *            word under consideration
	 * @return list of candidate words eligible for replacement
	 */
	public static final Vector<String> SpinWord(String[] tokens, int idx,
			String target) {
		result.clear();
		for (String s : tokens) {
			System.out.print(s + " ");
		}
		System.out.println();
		Object obj = ProcessDataSet.synonyms.get(target);
		if (obj == null) {
			result.addElement(target);
			return result;
		}
		treeMap.clear();
		Vector<String> synList = ProcessDataSet.synonyms.get(target);
		for (String syn : synList) {
			double val = ProcessDataSet.ngramCoefficient[1]
					* unigramScore(tokens, idx, syn)
					+ ProcessDataSet.ngramCoefficient[2]
					* bigramScore(tokens, idx, syn)
					+ ProcessDataSet.ngramCoefficient[3]
					* trigramScore(tokens, idx, syn)
					+ ProcessDataSet.ngramCoefficient[4]
					* fourgramScore(tokens, idx, syn)
					+ ProcessDataSet.ngramCoefficient[5]
					* fivegramScore(tokens, idx, syn);
			treeMap.put(val, syn);
		}
		int i = 1;
		for (Map.Entry<Double, String> entry : treeMap.entrySet()) {
			result.add(entry.getValue());
			if (++i > 10)
				break;
		}
		return result;
	}
}
