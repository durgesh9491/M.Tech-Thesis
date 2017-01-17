package durgesh.tool.MTech_thesis;

public abstract class NgramScore {

	public static final double unigramScore(String[] tokens, int idx,
			String target) {
		Object obj = ProcessDataSet.uniGram.get(target);
		double res = 0.0;
		if (obj != null) {
			double num = (Integer) obj;
			double den = ProcessDataSet.getTotalwords();
			res = num / den;
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
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = num / den;
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
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = num / den;
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
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = num / den;
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
			double den = (Integer) Obj1;
			double num = (Integer) Obj2;
			res = num / den;
		}
		return res;
	}
}
