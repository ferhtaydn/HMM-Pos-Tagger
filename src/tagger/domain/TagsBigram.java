package tagger.domain;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 11/12/13
 */

public class TagsBigram {

    private Tag previousTag;
    private Tag currentTag;

    public TagsBigram(Tag previousTag, Tag currentTag) {
        this.previousTag = previousTag;
        this.currentTag = currentTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagsBigram)) return false;

        TagsBigram that = (TagsBigram) o;

        return currentTag.equals(that.currentTag) && previousTag.equals(that.previousTag);

    }

    @Override
    public int hashCode() {
        int result = previousTag.hashCode();
        result = 31 * result + currentTag.hashCode();
        return result;
    }

    @Override
    public String toString() {

        return previousTag + "_" + currentTag;
    }
}
