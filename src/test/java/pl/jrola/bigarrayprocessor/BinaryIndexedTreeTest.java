package pl.jrola.bigarrayprocessor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryIndexedTreeTest {

    @Test
    void should_UpdateAndQuery() {
        BinaryIndexedTree binaryIndexedTree = new BinaryIndexedTree(5);
        binaryIndexedTree.update(1, 1);
        binaryIndexedTree.update(2, 1);
        binaryIndexedTree.update(3, 1);

        assertEquals(1, binaryIndexedTree.getPrefixSum(1));
        assertEquals(2, binaryIndexedTree.getPrefixSum(2));
        assertEquals(3, binaryIndexedTree.getPrefixSum(3));

        binaryIndexedTree.update(2, -1);
        assertEquals(1, binaryIndexedTree.getPrefixSum(1));
        assertEquals(1, binaryIndexedTree.getPrefixSum(2)); // Sum stays 1 because index 2 is now 0
        assertEquals(2, binaryIndexedTree.getPrefixSum(3));
    }

    @Test
    void should_FindKthIdx() {
        BinaryIndexedTree binaryIndexedTree = new BinaryIndexedTree(10);
        binaryIndexedTree.update(1, 1);
        binaryIndexedTree.update(3, 1);
        binaryIndexedTree.update(5, 1);

        assertEquals(1, binaryIndexedTree.findKthIdx(1));
        assertEquals(3, binaryIndexedTree.findKthIdx(2));
        assertEquals(5, binaryIndexedTree.findKthIdx(3));
    }
}