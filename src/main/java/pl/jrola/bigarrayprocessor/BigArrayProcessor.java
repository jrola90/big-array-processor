package pl.jrola.bigarrayprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BigArrayProcessor {

    /**
     * Processes input according to the rules:
     * 1. If val < 0: Add to list.
     * 2. If val > 0: Remove the n-th active element (where n = val).
     * 3. If val == 0: Ignore.
     * * @param input Array of integers (~1,000,000 elements).
     *
     * @return A list of integers that survived the processing.
     */
    public List<Integer> process(int[] input) {
        if (input == null || input.length == 0) {
            return Collections.emptyList();
        }

        int maxPossibleElements = input.length;
        // Primitive array to avoid early boxing and overhead
        int[] values = new int[maxPossibleElements];
        int totalAddedCount = 0;
        int currentlyActiveCount = 0;

        BinaryIndexedTree binaryIndexedTree = new BinaryIndexedTree(maxPossibleElements);

        for (int val : input) {
            if (val < 0) {
                // Store the value and mark it as 'active' (1) in the Binary Indexed Tree
                values[totalAddedCount] = val;
                totalAddedCount++;
                binaryIndexedTree.update(totalAddedCount, 1);
                currentlyActiveCount++;
            } else if (val > 0 && val <= currentlyActiveCount) {
                // Identify the physical index of the val-th active element
                int physicalIdx = binaryIndexedTree.findKthIdx(val);
                // Mark as 'removed' (-1)
                binaryIndexedTree.update(physicalIdx, -1);
                currentlyActiveCount--;
            }
        }

        return collectResults(values, binaryIndexedTree, totalAddedCount, currentlyActiveCount);
    }

    /**
     * Converts the filtered primitive values into the final List.
     * Boxing happens only here, and only for elements that weren't removed.
     */
    private List<Integer> collectResults(int[] values, BinaryIndexedTree binaryIndexedTree, int totalAdded, int activeCount) {
        List<Integer> result = new ArrayList<>(activeCount);
        int previousPrefixSum = 0;

        for (int i = 1; i <= totalAdded; i++) {
            int currentPrefixSum = binaryIndexedTree.getPrefixSum(i);
            // If the prefix sum increased, it means the element at this index is still active
            if (currentPrefixSum > previousPrefixSum) {
                result.add(values[i - 1]); // Autoboxing happens here
            }
            previousPrefixSum = currentPrefixSum;
        }
        return result;
    }
}