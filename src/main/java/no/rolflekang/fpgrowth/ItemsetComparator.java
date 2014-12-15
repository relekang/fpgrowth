package no.rolflekang.fpgrowth;

import java.util.Comparator;
import java.util.Map;

public class ItemsetComparator implements Comparator<Integer> {
    private final Map<Integer, Integer> counts;

    public ItemsetComparator(Map<Integer, Integer> counts) {
        this.counts = counts;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return counts.get(o2) - counts.get(o1);
    }
}
