package durgesh.tool.MTech_thesis;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;

import edu.stanford.nlp.util.Pair;

public class Spinner extends NgramScore {
	private static PriorityQueue<Pair<Double, String>> queue = new PriorityQueue<Pair<Double, String>>(
			Collections.reverseOrder());
	private static Vector<String> result = new Vector<String>();

	public static final Vector<String> SpinWord(String[] tokens, int idx,
			String target) {
		queue.clear();
		result.clear();
		Object obj = ProcessDataSet.synonyms.get(target);
		if (obj == null) {
			result.addElement(target);
			return result;
		}
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
			queue.add(new Pair<Double, String>(val, syn));
		}
		int i = 1;
		for (Pair<Double, String> entry : queue) {
			result.add(entry.second());
			if (++i > 5)
				break;
		}
		return result;
	}
}
