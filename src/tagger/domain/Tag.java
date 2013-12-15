package tagger.domain;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 11/12/13
 */

public class Tag implements Comparator{

    private String tag;

    public String getTag() {
        return tag;
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    public int compare(Object o, Object o2) {

        Tag tag1 = (Tag)o;
        Tag tag2 = (Tag)o2;

        return tag1.getTag().compareTo(tag2.getTag());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;

        Tag tag1 = (Tag) o;

        return tag.equals(tag1.tag);

    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }
}
