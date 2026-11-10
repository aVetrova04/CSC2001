
/**
 * A single node in a Huffman tree.
 *
 * Each node represents a character or an internal subtree.
 *
 * Example:
 * HuffmanNode node = new HuffmanNode(97, 5);
 * This creates a leaf representing 'a' with frequency 5.
 */
public record HuffmanNode(int charAscii, int freq, HuffmanNode left, HuffmanNode right)
        implements Comparable<HuffmanNode> {

    /** Convenience constructor for a leaf node (no children). */
    public HuffmanNode(int charAscii, int freq) {
        this(charAscii, freq, null, null);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Purpose: Determines whether node a comes before node b.
     * Parameters: Two HuffmanNode objects.
     * Returns: True if a has a smaller frequency, or the same frequency
     *          and a smaller ASCII value.
     * Example: comesBefore(new HuffmanNode(100, 5),
     *                     new HuffmanNode(107, 5)) returns true.
     */
    public static boolean comesBefore(HuffmanNode a, HuffmanNode b) {
        if (a.freq() < b.freq()) {
            return true;
        }

        if (a.freq() == b.freq() && a.charAscii() < b.charAscii()) {
            return true;
        }

        return false;
    }

    /**
     * Purpose: Combines two Huffman subtrees into one internal node.
     * Parameters: Two HuffmanNode objects.
     * Returns: A new parent node containing both children.
     * Example: combine(new HuffmanNode(97, 2),
     *                  new HuffmanNode(98, 3)).
     */
    public static HuffmanNode combine(HuffmanNode a, HuffmanNode b) {
        HuffmanNode leftChild;
        HuffmanNode rightChild;

        if (comesBefore(a, b)) {
            leftChild = a;
            rightChild = b;
        } else {
            leftChild = b;
            rightChild = a;
        }

        int totalFreq = a.freq() + b.freq();
        int smallestAscii = Math.min(a.charAscii(), b.charAscii());

        return new HuffmanNode(
                smallestAscii,
                totalFreq,
                leftChild,
                rightChild
        );
    }

    /**
     * Natural ordering consistent with comesBefore().
     */
    @Override
    public int compareTo(HuffmanNode other) {
        if (comesBefore(this, other)) return -1;
        if (comesBefore(other, this)) return 1;
        return 0;
    }
}
