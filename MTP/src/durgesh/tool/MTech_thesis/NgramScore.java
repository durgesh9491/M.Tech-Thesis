package durgesh.tool.MTech_thesis;

/**
 * @author This module finds out the score of a word based on the words which
 *         follows it. Uses a probabilistic model to find out N-gram score i.e.,
 *         P(x = N | N-1, N-2, N-3, ...) = count(N, N-1, N-2, N-3...)/count(N-1,
 *         N-2, N-3...). This module has implementation up to 5-grams, so N = 5.
 *
 */
public abstract class NgramScore {

	public static final double unigramScore(String[] tokens, int idx,
			String target) {
		Object obj = ProcessDataSet.uniGram.get(target);
		double res = 0.0;
		if (obj != null) {
			double k = ProcessDataSet.smoothingConst;
			double V = ProcessDataSet.uniGram.size();
			double num = (Integer) obj;
			double den = ProcessDataSet.getTotalwords();
			res = (num + k)/ (den + k*V);
		}
		return res;
	}

	public static final double bigramScore(String[] tokens, int idx,
			String target) {
		if (idx < 1)
			return 0;
		Object Obj1 = ProcessDataSet.uniGram.get(tokens[idx - 1]);
		Object Obj2 = ProcessDataSet.biGram.get(tokens[idx - 1] + " " + target);
		double res = 0.0;
		if (Obj1 != null && Obj2 != null) {
			double k = ProcessDataSet.smoothingConst;
			double V = ProcessDataSet.uniGram.size();
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = (num + k)/ (den + k*V);
		}
		return res;
	}

	public static final double trigramScore(String[] tokens, int idx,
			String target) {
		if (idx < 2)
			return 0;
		Object Obj1 = ProcessDataSet.biGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		Object Obj2 = ProcessDataSet.triGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		double res = 0.0;
		if (Obj1 != null && Obj2 != null) {
			double k = ProcessDataSet.smoothingConst;
			double V = ProcessDataSet.biGram.size();
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = (num + k)/ (den + k*V);
		}
		return res;
	}

	public static final double fourgramScore(String[] tokens, int idx,
			String target) {
		if (idx < 3)
			return 0;
		Object Obj1 = ProcessDataSet.triGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1]);
		Object Obj2 = ProcessDataSet.fourGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1] + " " + target);
		double res = 0.0;
		if (Obj1 != null && Obj2 != null) {
			double k = ProcessDataSet.smoothingConst;
			double V = ProcessDataSet.triGram.size();
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = (num + k)/ (den + k*V);
		}
		return res;
	}

	public static final double fivegramScore(String[] tokens, int idx,
			String target) {
		if (idx < 4)
			return 0;
		Object Obj1 = ProcessDataSet.fourGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		Object Obj2 = ProcessDataSet.fiveGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		double res = 0.0;
		if (Obj1 != null && Obj2 != null) {
			double k = ProcessDataSet.smoothingConst;
			double V = ProcessDataSet.fourGram.size();
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = (num + k)/ (den + k*V);
		}
		return res;
	}
}
