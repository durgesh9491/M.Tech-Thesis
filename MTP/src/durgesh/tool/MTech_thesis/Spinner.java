package durgesh.tool.MTech_thesis;

import java.util.Vector;

public class Spinner extends NgramScore {

	public static final String SpinWord(String[] tokens, int idx, String target) {
		String result = target;
		Object obj = ProcessDataSet.synonyms.get(target);
		if (obj == null)
			return result;
		
		Vector<String> synList = ProcessDataSet.synonyms.get(target);

		double maxi = 0.0, p = 0.5;
		final double c_1 = Math.pow(p, 5);
		final double c_2 = Math.pow(p, 4);
		final double c_3 = Math.pow(p, 3);
		final double c_4 = Math.pow(p, 2);
		final double c_5 = Math.pow(p, 1);
		for (String syn : synList) {
			double val = c_1 * unigramScore(tokens, idx, syn) + c_2
					* bigramScore(tokens, idx, syn) + c_3
					* trigramScore(tokens, idx, syn) + c_4
					* fourgramScore(tokens, idx, syn) + c_5
					* fivegramScore(tokens, idx, syn);
			if (val > maxi) {
				result = syn;
				maxi = val;
			}
		}
		return result;
	}
}
