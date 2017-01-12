package durgesh.tool.MTech_thesis;

public abstract class NgramScore {

	public static String capitalize(boolean flag, String s) {
		if (s.length() == 0)
			return s;
		if (flag)
			return s.substring(0, 1).toUpperCase()
					+ s.substring(1).toLowerCase();
		return s;
	}

	public static final double unigramScore(String[] tokens, int idx,
			String target) {
		Object obj = ProcessDataSet.uniGram.get(target);
		if (obj == null)
			return 0;
		double num = ProcessDataSet.uniGram.get(target);
		double den = ProcessDataSet.getTotalwords();
		double res = num / den;
		return res;
	}

	public static final double bigramScore(String[] tokens, int idx,
			String target) {
		if (idx < 1)
			return 0;
		Object Obj1 = ProcessDataSet.uniGram.get(tokens[idx - 1]);
		Object Obj2 = ProcessDataSet.biGram.get(tokens[idx - 1] + " " + target);
		if (Obj1 == null || Obj2 == null)
			return 0.0;
		double den = ProcessDataSet.uniGram.get(tokens[idx - 1]);
		double num = ProcessDataSet.biGram.get(tokens[idx - 1] + " " + target);
		double res = num / den;
		/*
		 * System.out.println(tokens[idx - 1] + " " + target + " " + num + " " +
		 * den);
		 */
		return res;
	}

	public static final double trigramScore(String[] tokens, int idx,
			String target) {
		if (idx < 2)
			return 0;
		Object Obj2 = ProcessDataSet.biGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		Object Obj1 = ProcessDataSet.triGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		if (Obj1 == null || Obj2 == null)
			return 0.0;
		double den = ProcessDataSet.biGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		double num = ProcessDataSet.triGram.get(tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		double res = num / den;
		/*
		 * System.out.println(tokens[idx - 2] + " " + tokens[idx - 1] + " " +
		 * target + " " + num + " " + den);
		 */
		return res;
	}

	public static final double fourgramScore(String[] tokens, int idx,
			String target) {
		if (idx < 3)
			return 0;
		Object Obj2 = ProcessDataSet.triGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1]);
		Object Obj1 = ProcessDataSet.fourGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1] + " " + target);
		if (Obj1 == null || Obj2 == null)
			return 0.0;
		double den = ProcessDataSet.triGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1]);
		double num = ProcessDataSet.fourGram.get(tokens[idx - 3] + " "
				+ tokens[idx - 2] + " " + tokens[idx - 1] + " " + target);
		double res = num / den;
		/*
		 * System.out.println(tokens[idx - 3] + " " + tokens[idx - 2] + " " +
		 * tokens[idx - 1] + " " + target + num + " " + den);
		 */
		return res;
	}

	public static final double fivegramScore(String[] tokens, int idx,
			String target) {
		if (idx < 4)
			return 0;
		Object Obj2 = ProcessDataSet.fourGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		Object Obj1 = ProcessDataSet.fiveGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		if (Obj1 == null || Obj2 == null)
			return 0.0;
		double den = ProcessDataSet.fourGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1]);
		double num = ProcessDataSet.fiveGram.get(tokens[idx - 4] + " "
				+ tokens[idx - 3] + " " + tokens[idx - 2] + " "
				+ tokens[idx - 1] + " " + target);
		double res = num / den;
		/*
		 * System.out.println(tokens[idx - 4] + " " + tokens[idx - 3] + " " +
		 * tokens[idx - 2] + " " + tokens[idx - 1] + " " + target + num + " " +
		 * den);
		 */
		return res;
	}
}
