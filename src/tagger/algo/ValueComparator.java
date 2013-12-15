package tagger.algo;

import tagger.domain.Tag;

import java.util.Comparator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ferhataydin
 * Date: 13/12/13
 * Time: 01:26
 * To change this template use File | Settings | File Templates.
 */
class ValueComparator implements Comparator<Tag> {

    Map<Tag, Integer> base;

    public ValueComparator(Map<Tag, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    @Override
    public int compare(Tag a, Tag b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }


}