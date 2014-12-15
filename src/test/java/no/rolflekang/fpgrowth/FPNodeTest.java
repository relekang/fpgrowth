package no.rolflekang.fpgrowth;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class FPNodeTest {

    FPTree tree;

    @Before
    public void setUp() {
        tree = new FPTree();
    }

    @Test
    public void testConstructor(){
        FPNode node = new FPNode();
        assertEquals(0, node.getCount());
        assertEquals(0, node.getItem());
        assertEquals(0, node.getChildren().size());

        node = new FPNode(1, 2);
        assertEquals(1, node.getItem());
        assertEquals(2, node.getCount());
        assertEquals(0, node.getChildren().size());
    }

    @Test
    public void testIncrementCount() {
        FPNode node = new FPNode(1, 2);
        assertEquals(2, node.getCount());
        node.incrementCount();
        assertEquals(3, node.getCount());
    }

    @Test
    public void testAddChild() {
        FPNode root = new FPNode();
        FPNode node = new FPNode(1, 2);
        root.addChild(node);
        assertEquals(node, root.getChild(1));
        assertEquals(node, root.getChildren().toArray()[0]);
        assertEquals(root, node.getParent());
        root.addChild(node);
        assertEquals(1, root.getChildren().size());

    }

    @Test
    public void setNeighbor() {
        FPNode node1 = new FPNode(1, 2);
        FPNode node2 = new FPNode(2, 2);
        FPNode node3 = new FPNode(3, 2);
        node1.setNeighbor(node2);
        node2.setNeighbor(node3);
        assertEquals(2, node1.getNeighbor().getItem());
        assertEquals(3, node1.getNeighbor().getNeighbor().getItem());
    }

}
