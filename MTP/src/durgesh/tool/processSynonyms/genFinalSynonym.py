from nltk.corpus import wordnet
import sys

finp = open('/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/Dictionary.txt')
fout = open("/home/durgesh9491/workspace/MTP/src/durgesh/tool/MTech_thesis/cleanSynonyms1.txt", 'w')

sys.stdout = fout

while True:
	line = finp.readline().strip();
	if ("" == line):
		break;
	synonyms = []
	for syn in wordnet.synsets(line):
		for l in syn.lemmas:
			synonyms.append(l.name)

	synonyms = list(set(synonyms))
	if(len(synonyms) > 0):
		synonyms.insert(0, line);
		print ",".join(synonyms)
fout.close();

