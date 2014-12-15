package no.rolflekang.fpgrowth;


import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class FPTreeTest {

    private FPTree tree;
    private List<Integer> t1, t2, t3, t4;

    @Before
    public void setUp() {
        tree = new FPTree();
        t1 = Arrays.asList(1, 2, 3);
        t2 = Arrays.asList(1, 2, 3, 4);
        t3 = Arrays.asList(1, 2, 4, 5);
        t4 = Arrays.asList(4, 5);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tree.getRoot().getItem());
        assertEquals(0, tree.getRoot().getCount());
    }

    @Test
    public void testAddTransaction() {
        tree.addTransaction(t1);
        assertEquals(1, tree.getRoot().getChild(1).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(3).getCount());

        tree.addTransaction(t2);
        assertEquals(2, tree.getRoot().getChild(1).getCount());
        assertEquals(2, tree.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(2, tree.getRoot().getChild(1).getChild(2).getChild(3).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(3).getChild(4).getCount());

        tree.addTransaction(t3);
        assertEquals(3, tree.getRoot().getChild(1).getCount());
        assertEquals(3, tree.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(2, tree.getRoot().getChild(1).getChild(2).getChild(3).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(3).getChild(4).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(4).getCount());
        assertEquals(1, tree.getRoot().getChild(1).getChild(2).getChild(4).getChild(5).getCount());


        assertEquals(tree.getRoot().getChild(1).getChild(2).getChild(4), tree.getRoot().getChild(1).getChild(2).getChild(4).getChild(5).getParent());
        assertEquals(tree.getRoot().getChild(1).getChild(2), tree.getRoot().getChild(1).getChild(2).getChild(4).getParent());
        assertEquals(tree.getRoot().getChild(1), tree.getRoot().getChild(1).getChild(2).getParent());
        assertEquals(tree.getRoot(), tree.getRoot().getChild(1).getParent());
    }

    @Test
    public void serialization(){
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        tree.addTransaction(t3);
        assertNotNull(FPTree.serialize(tree));
        FPTree deserializedTree = FPTree.deserialize(FPTree.serialize(tree));
        assertEquals(3, deserializedTree.getRoot().getChild(1).getCount());
        assertEquals(3, deserializedTree.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(2, deserializedTree.getRoot().getChild(1).getChild(2).getChild(3).getCount());
        assertEquals(1, deserializedTree.getRoot().getChild(1).getChild(2).getChild(3).getChild(4).getCount());
        assertEquals(1, deserializedTree.getRoot().getChild(1).getChild(2).getChild(4).getCount());
        assertEquals(1, deserializedTree.getRoot().getChild(1).getChild(2).getChild(4).getChild(5).getCount());


        assertEquals(deserializedTree.getRoot().getChild(1).getChild(2).getChild(4), deserializedTree.getRoot().getChild(1).getChild(2).getChild(4).getChild(5).getParent());
        assertEquals(deserializedTree.getRoot().getChild(1).getChild(2), deserializedTree.getRoot().getChild(1).getChild(2).getChild(4).getParent());
        assertEquals(deserializedTree.getRoot().getChild(1), deserializedTree.getRoot().getChild(1).getChild(2).getParent());
        assertEquals(deserializedTree.getRoot(), deserializedTree.getRoot().getChild(1).getParent());
    }

    @Test
    public void testGetSinglePrefixPath() {
        assertNull(tree.getSinglePrefixPath());
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        ArrayList<FPNode> path = tree.getSinglePrefixPath();
        assertNotNull(path);
        assertEquals(3, path.size());
        assertEquals(1, path.get(0).getItem());
        assertEquals(2, path.get(1).getItem());
        assertEquals(3, path.get(2).getItem());
        tree.addTransaction(t3);
        path = tree.getSinglePrefixPath();
        assertNotNull(path);
        assertEquals(2, path.size());
        assertEquals(1, path.get(0).getItem());
        assertEquals(2, path.get(1).getItem());
        tree.addTransaction(t4);
        assertNull(tree.getSinglePrefixPath());
    }

    @Test
    public void testNumberOfTransactions() {
        assertEquals(0, tree.getNumberOfTransactions());
        tree.addTransaction(t1);
        assertEquals(1, tree.getNumberOfTransactions());
        tree.addTransaction(t2);
        assertEquals(2, tree.getNumberOfTransactions());
        tree.addTransaction(t3);
        assertEquals(3, tree.getNumberOfTransactions());
        tree.addTransaction(t4);
        assertEquals(4, tree.getNumberOfTransactions());
    }
    @Test
    public void testHeaderTable(){
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        tree.addTransaction(t3);
        tree.addTransaction(t4);
        assertEquals(5, tree.getItems().keySet().size());
        assertEquals(1, tree.getItems().get(1).size());
        assertEquals(1, tree.getItems().get(2).size());
        assertEquals(1, tree.getItems().get(3).size());
        assertEquals(3, tree.getItems().get(4).size());
        assertEquals(2, tree.getItems().get(5).size());
    }

    @Test
    public void testGetSupportCountForItem() {
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        tree.addTransaction(t3);
        tree.addTransaction(t4);
        assertEquals(3, tree.getSupportForItem(1));
        assertEquals(3, tree.getSupportForItem(2));
        assertEquals(2, tree.getSupportForItem(3));
        assertEquals(3, tree.getSupportForItem(4));
        assertEquals(2, tree.getSupportForItem(5));

    }
    @Test
    public void testPrefixPaths() {
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        tree.addTransaction(t3);
        tree.addTransaction(t4);
        assertEquals(1, tree.getPrefixPaths(4).get(0).get(0).getItem());
        assertEquals(2, tree.getPrefixPaths(4).get(0).get(1).getItem());
        assertEquals(3, tree.getPrefixPaths(4).get(0).get(2).getItem());
        assertEquals(4, tree.getPrefixPaths(4).get(0).get(3).getItem());
    }

    @Test
    public void testGetConditionalTree() {
        tree.addTransaction(t1);
        tree.addTransaction(t2);
        tree.addTransaction(t3);
        tree.addTransaction(t4);

        FPTree c5 = FPTree.conditionalTree(tree.getPrefixPaths(5), 0);
        assertEquals(2, c5.getRoot().getChildren().size());
        assertEquals(1, c5.getRoot().getChild(1).getCount());
        assertEquals(1, c5.getRoot().getChild(1).getChildren().size());
        assertEquals(1, c5.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(1, c5.getRoot().getChild(1).getChild(2).getChild(4).getCount());
        assertEquals(0, c5.getRoot().getChild(1).getChild(2).getChild(4).getChildren().size());
        assertEquals(1, c5.getRoot().getChild(4).getCount());
        assertEquals(0, c5.getRoot().getChild(4).getChildren().size());

        FPTree c4 = FPTree.conditionalTree(tree.getPrefixPaths(4), 2);
        assertEquals(1, c4.getRoot().getChildren().size());
        assertEquals(2, c4.getRoot().getChild(1).getCount());
        assertEquals(2, c4.getRoot().getChild(1).getChild(2).getCount());
        assertEquals(0, c4.getRoot().getChild(1).getChild(2).getChildren().size());
    }
}
