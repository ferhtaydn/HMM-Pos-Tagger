package tagger.algo;

import tagger.domain.Constants;
import tagger.domain.Sentence;
import tagger.domain.Tag;
import tagger.domain.Word;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 12/12/13
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public class Baseline {

    private int taggedCorrectly = 0;
    private int totalTagNumber = 0;

    public void run() {

        prepareBaseline();
    }

    private void prepareBaseline() {

        GeniaData geniaTestObj = new GeniaData();

        geniaTestObj = geniaTestObj.createGeniaTestObject();

        GeniaData geniaTrainObj = new GeniaData();

        geniaTrainObj = geniaTrainObj.createGeniaTrainObject();

        runBaseline(geniaTestObj, geniaTrainObj);
    }

    private void runBaseline(GeniaData geniaTestObj, GeniaData geniaTrainObj) {

        try {

            PrintWriter writer = new PrintWriter(Constants.BASELINE_OUTPUT_FILE, "UTF-8");

            System.out.println(Constants.BASELINE_OUTPUT_FILE + " is being generated..");

            for (Sentence testSentence : geniaTestObj.getSentenceList()) {

                List<Tag> baselinePath = new ArrayList<Tag>();

                for (Word word : testSentence.getWords()) {

                    Tag tag = geniaTrainObj.retrieveMostFreqTagForWord(word);

                    baselinePath.add(tag);
                }

                calculateAccuracy(baselinePath, testSentence);

                writer.println("test sentence words: " + testSentence.getWords());
                writer.println("test sentence tags : " + testSentence.getTags());
                writer.println("baselinePath       : " + baselinePath);
                writer.println();
            }

            writer.close();

            System.out.println(Constants.BASELINE_OUTPUT_FILE + " is generated..");

            printAccuracy();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void calculateAccuracy(List<Tag> baselinePath, Sentence sentence) {

        for (int i = 0; i < baselinePath.size(); i++) {

            if (baselinePath.get(i).equals(sentence.getTags().get(i))) {

                taggedCorrectly++;
            }

            totalTagNumber++;
        }
    }

    private void printAccuracy() {

        System.out.println("Baseline taggedCorrectly: " + taggedCorrectly);
        System.out.println("Baseline totalTagNumber: " + totalTagNumber);

        Double accuracy = ((double)taggedCorrectly / totalTagNumber) * 100;

        System.out.println("Baseline Accuracy: %" + String.format(Locale.US, "%.2f", accuracy));
    }

}
