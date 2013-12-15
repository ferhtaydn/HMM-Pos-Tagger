package tagger.algo;

import tagger.domain.Constants;
import tagger.domain.Sentence;
import tagger.domain.Tag;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 12/12/13
 */

public class Viterbi {

    private int taggedCorrectly = 0;
    private int totalTagNumber = 0;

    public void run() {

        prepareViterbi();
    }

    private void prepareViterbi() {

        GeniaData geniaTestObj = new GeniaData();

        geniaTestObj = geniaTestObj.createGeniaTestObject();

        HmmProb hmmProb = new HmmProb();

        hmmProb.prepareCountHolderMaps();

        List<Tag> tagList = convertKeySetToList(hmmProb);

        runViterbi(geniaTestObj, hmmProb, tagList);
    }

    private void runViterbi(GeniaData geniaTestObj, HmmProb hmmProb, List<Tag> tagList) {

        try {

            PrintWriter writer = new PrintWriter(Constants.VITERBI_PATH_OUTPUT_FILE, "UTF-8");

            System.out.println(Constants.VITERBI_PATH_OUTPUT_FILE + " is being generated..");

            for (Sentence sentence : geniaTestObj.getSentenceList()) {

                int sentenceLength = sentence.getWords().size();
                int tagListLength = tagList.size();

                Double[][] viterbiTable = new Double[tagListLength][sentenceLength];

                List<Tag> viterbiPath = new ArrayList<Tag>();

                // calculate first transition (first column in table)
                for (int t = 1; t < tagListLength; t++) {

                    Double tagTransitionProb = hmmProb.calculateTagTransitionProb(tagList.get(0), tagList.get(t));
                    Double wordTagLikelihoodProb = hmmProb.calculateWordTagLikelihoodProb(sentence.getWords().get(1), tagList.get(t));

                    viterbiTable[t][1] = tagTransitionProb * wordTagLikelihoodProb;

                }

                for (int w = 2; w < sentenceLength; w++) {

                    for (int t = 1; t < tagListLength; t++) {

                        Double[] probs = new Double[tagListLength];

                        for (int i = 1; i < tagListLength; i++) {

                            if (viterbiTable[i][w-1] != null) {

                                Double tagTransitionProb = hmmProb.calculateTagTransitionProb(tagList.get(i), tagList.get(t));

                                Double wordTagLikelihoodProb = hmmProb.calculateWordTagLikelihoodProb(sentence.getWords().get(w), tagList.get(t));

                                probs[i] = viterbiTable[i][w-1] * tagTransitionProb * wordTagLikelihoodProb;

                            } else {

                                probs[i] = 0.0;

                            }
                        }

                        int argmax = argmax(probs);

                        viterbiTable[t][w] = probs[argmax];
                    }
                }

                generateViterbiPath(tagList, sentenceLength, tagListLength, viterbiTable, viterbiPath, sentence, writer);

            }

            writer.close();

            System.out.println(Constants.VITERBI_PATH_OUTPUT_FILE + " is generated..");

            printAccuracy();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void printAccuracy() {

        System.out.println("Viterbi taggedCorrectly: " + taggedCorrectly);
        System.out.println("Viterbi totalTagNumber: " + totalTagNumber);

        Double accuracy = ((double)taggedCorrectly / totalTagNumber) * 100;

        System.out.println("Viterbi Accuracy: %" + String.format(Locale.US, "%.2f", accuracy));
    }

    private void generateViterbiPath(List<Tag> tagList, int sentenceLength, int tagListLength, Double[][] viterbiTable, List<Tag> viterbiPath, Sentence sentence, PrintWriter writer) {

        for (int w = 1; w < sentenceLength; w++) {

            Double[] probs = new Double[tagListLength];

            for (int t = 1; t < tagListLength; t++) {

                probs[t] = viterbiTable[t][w];
            }

            int index = argmax(probs);

            viterbiPath.add(tagList.get(index));

        }

        viterbiPath.add(0, new Tag(Constants.SENTENCE_START));

        writer.println("test sentence words: " + sentence.getWords());
        writer.println("test sentence tags : " + sentence.getTags());
        writer.println("vibertiPath        : " + viterbiPath);
        writer.println();

        calculateAccuracy(viterbiPath, sentence);

    }

    private void calculateAccuracy(List<Tag> viterbiPath, Sentence sentence) {

        for (int i = 0; i < viterbiPath.size(); i++) {

            if (viterbiPath.get(i).equals(sentence.getTags().get(i))) {

                taggedCorrectly++;
            }

            totalTagNumber++;
        }
    }

    private static Integer argmax(Double[] arr) {

        Double max = arr[1];

        Integer argmax = 1;

        for (int i=2; i<arr.length; i++) {

            if (arr[i] > max) {

                max = arr[i];
                argmax = i;

            }
        }

        return argmax;
    }

    private List<Tag> convertKeySetToList(HmmProb hmmProb) {

        Set set = hmmProb.getTagMap().keySet();

        List<Tag> tagList = new ArrayList<Tag>();

        tagList.addAll(set);

        tagList.remove(new Tag(Constants.SENTENCE_START));
        tagList.remove(new Tag(Constants.SENTENCE_END));

        tagList.add(0, new Tag(Constants.SENTENCE_START));
        tagList.add(tagList.size(), new Tag(Constants.SENTENCE_END));

        return tagList;
    }

}
