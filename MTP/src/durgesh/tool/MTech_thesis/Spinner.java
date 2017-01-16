package durgesh.tool.MTech_thesis;

import java.util.Vector;

public class Spinner extends NgramScore {

	public static final String SpinWord(String[] tokens, int idx, String target) {
		String result = target;
		Object obj = ProcessDataSet.synonyms.get(target);
		if (obj == null)
			return result;

		Vector<String> synList = ProcessDataSet.synonyms.get(target);

		double maxi = 0.0;
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
			if (val > maxi) {
				result = syn;
				maxi = val;
			}
		}
		return result;
	}
}
