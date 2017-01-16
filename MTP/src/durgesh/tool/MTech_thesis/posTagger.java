package durgesh.tool.MTech_thesis;

import java.util.HashMap;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class posTagger {
	public final static HashMap<String, Boolean> pos = new HashMap<String, Boolean>();

	public static void selectPOS() {
		/*
		 * JJ : adjective, RB : adverb, VB : verb
		 */
		pos.put("JJ", true);
		pos.put("JJR", true);
		pos.put("JJS", true);
		pos.put("RBR", true);
		pos.put("RBS", true);
		pos.put("VB", true);
		pos.put("VBD", true);
		pos.put("VBG", true);
		pos.put("VBN", true);
		pos.put("VBP", true);
		/*
		 * pos.put("RB", true); pos.put("VBZ", true);
		 */
	}

	public static Boolean isCandidatePOS(MaxentTagger tagger, String target) {
		String tagged = tagger.tagString(target);
		String posVal = tagged.substring(tagged.indexOf("_") + 1,
				tagged.length() - 1);
		return pos.containsKey(posVal);
	}
}
