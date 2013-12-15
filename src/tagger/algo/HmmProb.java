package tagger.algo;

import tagger.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 11/12/13
 */

public class HmmProb {

    private Map<TagsBigram, Integer> tagsBigramMap = new HashMap<TagsBigram, Integer>();

    private Map<WordTagBigram, Integer> wordTagBigramMap = new HashMap<WordTagBigram, Integer>();

    private Map<Tag, Integer> tagMap = new HashMap<Tag, Integer>();

    public Map<Tag, Integer> getTagMap() {

        return tagMap;
    }

    public Double calculateTagTransitionProb(Tag previousTag, Tag currentTag) {

        Integer bigramCount = tagsBigramMap.get(new TagsBigram(previousTag, currentTag));

        Integer previousCount = tagMap.get(previousTag);

        if (bigramCount == null) {

            //return  (1.0 / (previousCount + tagMap.size()));

            //return  (((double)previousCount/tagsBigramMap.size()) / (previousCount + tagMap.size()));
            return  ((1.0/tagsBigramMap.size()) / (previousCount + tagMap.size()));
        }

        //return (double) bigramCount / previousCount;

        return ((double) bigramCount + 1.0) / (previousCount + tagMap.size());

    }

    public Double calculateWordTagLikelihoodProb(Word word, Tag tag) {

        Integer bigramCount = wordTagBigramMap.get(new WordTagBigram(word, tag));

        Integer tagCount = tagMap.get(tag);

        if (bigramCount == null ) {

            //return (1.0 / (tagCount + tagMap.size()));
            //return  (((double)tagCount/wordTagBigramMap.size()) / (tagCount + tagMap.size()));
            return  ((1.0/wordTagBigramMap.size()) / (tagCount + tagMap.size()));
        }

        //return (double) bigramCount / tagCount;

        return ((double) bigramCount + 1.0) / (tagCount + tagMap.size());
    }

    public void prepareCountHolderMaps() {

        GeniaData geniaDataObj = new GeniaData();

        geniaDataObj = geniaDataObj.createGeniaTrainObject();

        tagMap = geniaDataObj.getTagMap();

        createTagsBigramMap(geniaDataObj);

        createWordTagBigramMap(geniaDataObj);

    }

    private void createTagsBigramMap(GeniaData geniaDataObj) {

        for (Sentence sentence : geniaDataObj.getSentenceList()) {

            for (int i = 0; i < sentence.getTags().size() - 1; i++) {

                Tag previousTag = sentence.getTags().get(i);
                Tag currentTag = sentence.getTags().get(i + 1);

                TagsBigram tagsBigram = new TagsBigram(previousTag, currentTag);

                if (tagsBigramMap.containsKey(tagsBigram)) {

                    tagsBigramMap.put(tagsBigram, tagsBigramMap.get(tagsBigram) + 1);

                } else {

                    tagsBigramMap.put(tagsBigram, 1);
                }
            }
        }

        System.out.println("C(ti-1, ti) holder map is created..");
    }

    private void createWordTagBigramMap(GeniaData geniaDataObj) {

        for (Sentence sentence : geniaDataObj.getSentenceList()) {

            for (int i = 0; i < sentence.getWords().size(); i++) {

                Word word = sentence.getWords().get(i);
                Tag tag = sentence.getTags().get(i);

                WordTagBigram wordTagBigram = new WordTagBigram(word, tag);

                if (wordTagBigramMap.containsKey(wordTagBigram)) {

                    wordTagBigramMap.put(wordTagBigram, wordTagBigramMap.get(wordTagBigram) + 1);

                } else {

                    wordTagBigramMap.put(wordTagBigram, 1);
                }

            }
        }

        System.out.println("C(ti, wi) holder map is created..");
    }

}
