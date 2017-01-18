package durgesh.tool.MTech_thesis;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class Spinner extends NgramScore {
	private static TreeMap<Double, String> treeMap = new TreeMap<Double, String>(
			Collections.reverseOrder());
	private static Vector<String> result = new Vector<String>();

	public static final Vector<String> SpinWord(String[] tokens, int idx,
			String target) {
		result.clear();
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
