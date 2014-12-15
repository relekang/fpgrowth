package no.rolflekang.fpgrowth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ItemsetComparatorTest {
    Map<Integer, Integer> counts;

    @Before
    public void setUp() {
        counts = new HashMap<Integer, Integer>();
        counts.put(1, 1);
        counts.put(2, 2);
        counts.put(3, 3);
        counts.put(4, 4);
    }

    @Test
    public void testCompare() {
        Comparator<Integer> comparator = new ItemsetComparator(counts);
        Assert.assertTrue(comparator.compare(1, 2) > 0);
        Assert.assertTrue(comparator.compare(3, 2) < 0);
        Assert.assertTrue(comparator.compare(4, 4) == 0);
    }

    @Test
    public void testSort() {
        int[] transaction = new int[]{3, 4, 1, 2};
        transaction = FPGrowth.sortTransaction(transaction, counts);
        Assert.assertEquals(4, transaction[0]);
        Assert.assertEquals(3, transaction[1]);
        Assert.assertEquals(2, transaction[2]);
        Assert.assertEquals(1, transaction[3]);
    }
}
