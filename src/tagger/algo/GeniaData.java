package tagger.algo;

import tagger.domain.Constants;
import tagger.domain.Sentence;
import tagger.domain.Tag;
import tagger.domain.Word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 11/12/13
 */

public class GeniaData {

    private List<Sentence> sentenceList = new ArrayList<Sentence>();

    private Map<Tag, Integer> tagMap = new HashMap<Tag, Integer>();

    private Map<Word, Map<Tag, Integer>> wordTagCountMap = new HashMap<Word, Map<Tag, Integer>>();

    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    public Map<Tag, Integer> getTagMap() {
        return tagMap;
    }

    public void readGeniaFile(String fileName) {

        BufferedReader br = null;

        try {

            String fileLine;

            br = new BufferedReader(new FileReader(fileName));

            while ((fileLine = br.readLine()) != null) {

                Sentence sentence  = new Sentence();
                sentence.getWords().add(new Word(Constants.SENTENCE_START));
                sentence.getTags().add(new Tag(Constants.SENTENCE_START));

                do {

                    //take words as one part, they may include more than one word
                    String word = fileLine.substring(0, fileLine.lastIndexOf(Constants.WORD_TAG_SEPERATOR));
                    sentence.getWords().add(new Word(word.toLowerCase()));

                    String tag = fileLine.substring(fileLine.lastIndexOf(Constants.WORD_TAG_SEPERATOR) + 1);
                    //tags may be like tag1|tag2, take first one as analysis design
                    if (tag.contains(Constants.MULTI_TAG_SEPERATOR)) {

                        String tagFirst = tag.substring(0, tag.lastIndexOf(Constants.MULTI_TAG_SEPERATOR));
                        sentence.getTags().add(new Tag(tagFirst));

                    } else {

                        sentence.getTags().add(new Tag(tag));
                    }

                    //sentence.getTags().add(new Tag(tag));

                } while (!(fileLine = br.readLine()).equals(Constants.SENTENCE_SEPERATOR));

                sentence.getWords().add(new Word(Constants.SENTENCE_END));
                sentence.getTags().add(new Tag(Constants.SENTENCE_END));
                sentenceList.add(sentence);
            }

            System.out.println(fileName + " is read and sentences are created..");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void createTagMap(String fileName) {

        for (Sentence sentence : sentenceList) {

            for (Tag tag : sentence.getTags()) {

                if (tagMap.containsKey(tag)) {

                    tagMap.put(tag, tagMap.get(tag) + 1);

                } else {

                    tagMap.put(tag, 1);
                }

            }
        }

        System.out.println("Tag Map of " + fileName + " is created..");

        System.out.println("Size of tag map is: " + tagMap.size());

        System.out.println("tag set of " + fileName + ": " + tagMap.keySet());
    }

    //this method is used for baseline tagger
    public void createWordTagCountMap() {

        for (Sentence sentence : sentenceList) {

            for (int i = 0; i < sentence.getWords().size(); i++) {

                Word word = sentence.getWords().get(i);

                Tag tag = sentence.getTags().get(i);

                if (wordTagCountMap.containsKey(word)) {

                    Map<Tag, Integer> map = wordTagCountMap.get(word);

                    if (map.containsKey(tag)) {

                        map.put(tag, map.get(tag) + 1);

                    } else {

                        map.put(tag, 1);
                    }

                    wordTagCountMap.put(word, map);

                } else {

                    Map<Tag, Integer> map = new HashMap<Tag, Integer>();

                    map.put(tag, 1);

                    wordTagCountMap.put(word, map);
                }
            }
        }

    }

    public Tag retrieveMostFreqTagForWord(Word word) {

        if (wordTagCountMap.containsKey(word)) {

            Map<Tag, Integer> map = wordTagCountMap.get(word);

            ValueComparator valueComparator = new ValueComparator(map);

            TreeMap<Tag, Integer> treeMap = new TreeMap<Tag, Integer>(valueComparator);

            treeMap.putAll(map);

            return treeMap.firstKey();

        } else {

            return new Tag("NN");
        }


    }

    public GeniaData createGeniaTrainObject() {

        GeniaData trainData = new GeniaData();

        trainData.readGeniaFile(Constants.GENIA_TRAIN_FILE);

        trainData.createTagMap(Constants.GENIA_TRAIN_FILE);

        trainData.createWordTagCountMap();

        return trainData;
    }

    public GeniaData createGeniaTestObject() {

        GeniaData testData = new GeniaData();

        testData.readGeniaFile(Constants.GENIA_TEST_FILE);

        testData.createTagMap(Constants.GENIA_TEST_FILE);

        return testData;
    }

}
