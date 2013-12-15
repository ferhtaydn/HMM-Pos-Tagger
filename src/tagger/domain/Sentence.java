package tagger.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 11/12/13
 */

public class Sentence implements Serializable {

    private List<Word> words = new ArrayList<Word>();
    private List<Tag> tags = new ArrayList<Tag>();

    public List<Word> getWords() {
        return words;
    }

    public List<Tag> getTags() {
        return tags;
    }

}
