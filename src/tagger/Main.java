package tagger;

import tagger.algo.Baseline;
import tagger.algo.Viterbi;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 13/12/13
 * Time: 02:21
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) {

        Viterbi viterbi = new Viterbi();

        viterbi.run();

        Baseline baseline = new Baseline();

        baseline.run();
    }
}
