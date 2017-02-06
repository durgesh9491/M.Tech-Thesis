package durgesh.tool.MTech_thesis;

import java.util.TreeMap;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * @author A Part-Of-Speech Tagger (POS Tagger) is a piece of code that reads
 *         text in some language and assigns parts of speech to each word (and
 *         other token), such as noun, verb, adjective, etc.
 *
 */
public class posTagger {
	private static final TreeMap<String, Boolean> pos = new TreeMap<String, Boolean>();

	public static final void selectPOS() {
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
		pos.put("RB", true);
		pos.put("VBZ", true);
		pos.put("NN", true);

	}

	public static final Boolean isCandidatePOS(MaxentTagger tagger,
			String target) {
		String tagged = tagger.tagString(target);
		String posVal = tagged.substring(tagged.indexOf("_") + 1,
				tagged.length() - 1);
		return pos.containsKey(posVal);
	}
}
