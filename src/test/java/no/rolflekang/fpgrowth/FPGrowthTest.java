package no.rolflekang.fpgrowth;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FPGrowthTest {

    ArrayList<int[]> transactions;
    @Before
    public void setUp() {
        transactions = new ArrayList<int[]>();
        transactions.add(new int[]{1, 2, 3});
        transactions.add(new int[]{1, 2, 3, 4});
        transactions.add(new int[]{2, 3, 5});
        transactions.add(new int[]{1, 3, 4, 5});
    }

    @Test
    public void testGetOneItemsetCounts() {
        Map<Integer, Integer> counts = FPGrowth.getOneItemsetCounts(transactions);
        assertEquals(3, (int) counts.get(1));
        assertEquals(3, (int) counts.get(2));
        assertEquals(4, (int) counts.get(3));
        assertEquals(2, (int) counts.get(4));
        assertEquals(2, (int) counts.get(5));
    }
    @Test
    public void testAddToOneItemsetCounts() {
        Map<Integer, Integer> counts = FPGrowth.getOneItemsetCounts(transactions);
        Map<Integer, Integer> counts2 = FPGrowth.getOneItemsetCounts(transactions);
        counts2 = FPGrowth.addToOneItemsetCounts(counts2, transactions);
        assertEquals(counts.get(1) * 2, (int) counts2.get(1));
        assertEquals(counts.get(2) * 2, (int) counts2.get(2));
        assertEquals(counts.get(3) * 2, (int) counts2.get(3));
        assertEquals(counts.get(4) * 2, (int) counts2.get(4));
        assertEquals(counts.get(5) * 2, (int) counts2.get(5));
    }

    @Test
    public void testBuildFPTree() {
        FPTree tree = FPGrowth.buildFPTree(transactions, FPGrowth.getOneItemsetCounts(transactions), false, 0.4);
        assertEquals(1, tree.getRoot().getChildren().size());
        assertEquals(4, tree.getRoot().getChild(3).getCount());
        assertEquals(3, tree.getRoot().getChild(3).getChild(1).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(2).getCount());
        assertEquals(2, tree.getRoot().getChild(3).getChild(1).getChild(2).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(1).getChild(2).getChild(4).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(1).getChild(4).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(1).getChild(4).getChild(5).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(2).getChild(5).getCount());
        tree = FPGrowth.buildFPTree(transactions, FPGrowth.getOneItemsetCounts(transactions), true, 0.7);
        assertEquals(1, tree.getRoot().getChildren().size());
        assertEquals(4, tree.getRoot().getChild(3).getCount());
        assertEquals(3, tree.getRoot().getChild(3).getChild(1).getCount());
        assertEquals(1, tree.getRoot().getChild(3).getChild(2).getCount());
        assertEquals(2, tree.getRoot().getChild(3).getChild(1).getChild(2).getCount());
    }

    @Test
    public void testFindFrequentItemsetWithSuffix() {
        FPTree tree = new FPTree();
        tree.addTransaction(Arrays.asList(1, 2, 3));
        tree.addTransaction(Arrays.asList(1, 2, 3, 4));
        tree.addTransaction(Arrays.asList(1, 2, 4, 5));
        tree.addTransaction(Arrays.asList(4, 5));
        assertEquals(0, FPGrowth.findFrequentItemsetWithSuffix(tree, new ArrayList<Integer>(), 4).size());
        assertEquals(4, FPGrowth.findFrequentItemsetWithSuffix(tree, new ArrayList<Integer>(), 3).size());
        assertEquals(13, FPGrowth.findFrequentItemsetWithSuffix(tree, new ArrayList<Integer>(), 2).size());
        assertEquals(23, FPGrowth.findFrequentItemsetWithSuffix(tree, new ArrayList<Integer>(), 1).size());
        assertEquals(23, FPGrowth.findFrequentItemsetWithSuffix(tree, new ArrayList<Integer>(), 0).size());
    }
}
