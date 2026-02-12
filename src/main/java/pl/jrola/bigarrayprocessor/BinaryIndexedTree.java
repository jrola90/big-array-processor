package pl.jrola.bigarrayprocessor;

/**
 * A Binary Indexed Tree (also known as Fenwick Tree) used to manage dynamic frequency counts.
 * It allows for O(log n) updates and O(log n) prefix sum queries.
 */
class BinaryIndexedTree {
    private final int[] tree;
    private final int n;

    /**
     * @param n The maximum number of elements the tree will handle.
     */
    public BinaryIndexedTree(int n) {
        this.n = n;
        this.tree = new int[n + 1];
    }

    /**
     * Updates the frequency at a given index.
     * @param i The 1-based index to update.
     * @param delta The value to add (e.g., 1 for insertion, -1 for removal).
     */
    public void update(int i, int delta) {
        for (; i <= n; i += i & -i) {
            tree[i] += delta;
        }
    }

    /**
     * Computes the prefix sum up to index i.
     * @param i 1-based index.
     * @return The number of "active" elements up to this index.
     */
    public int getPrefixSum(int i) {
        int sum = 0;
        for (; i > 0; i -= i & -i) {
            sum += tree[i];
        }
        return sum;
    }

    /**
     * Efficiently finds the smallest index i such that getPrefixSum(i) >= k.
     * This uses binary lifting to keep the operation O(log n).
     * @param k The target rank (n-th active element).
     * @return The physical 1-based index in the values array.
     */
    public int findKthIdx(int k) {
        int idx = 0;
        int bitMask = Integer.highestOneBit(n);
        for (int i = bitMask; i > 0; i >>= 1) {
            int nextIdx = idx + i;
            if (nextIdx <= n && tree[nextIdx] < k) {
                idx = nextIdx;
                k -= tree[idx];
            }
        }
        return idx + 1;
    }
}