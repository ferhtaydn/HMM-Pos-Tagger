package tagger.domain;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 12/12/13
 */

public class WordTagBigram {

    private Word word;
    private Tag tag;

    public WordTagBigram(Word word, Tag tag) {
        this.word = word;
        this.tag = tag;
    }

    @Override
    public String toString() {

        return word + "_" + tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordTagBigram)) return false;

        WordTagBigram that = (WordTagBigram) o;

        return tag.equals(that.tag) && word.equals(that.word);

    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + tag.hashCode();
        return result;
    }
}
