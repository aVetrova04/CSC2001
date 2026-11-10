import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Project 3: Huffman Encoding / Decoding.
 *
 * Implement the methods below to compress and decompress text files using
 * Huffman coding. See the assignment write-up for the full specification.
 *
 *  - Treat characters as 8-bit values in the range [0, 255].
 *  - When writing output files, write the newline character explicitly as
 *    '\n' (do not rely on System.lineSeparator() / println()), so your
 *    output is identical across Windows/Mac/Linux. A BufferedWriter over
 *    a FileWriter, writing "\n" yourself, works well for this.
 *  - Encoded files store the code as literal '0'/'1' characters (one byte
 *    each), not as packed bits, so the "encoded" file will actually be
 *    larger than the original -- that's expected for this assignment.
 *  - createHuffTree() is built on top of addNode()/removeMin(), which
 *    operate generically over a java.util.Collection<HuffmanNode>.
 *  - HuffmanNode is a record, so its components are read with
 *    node.charAscii(), node.freq(), node.left(), and node.right() -- no
 *    "get" prefix.
 */
public class Huffman {

    /**
     * Number of possible 8-bit character values.
     */
    public static final int NUM_CHARS = 256;

    /**
     * Opens the text file named filename and counts the frequency of
     * occurrence of every character in it.
     * Returns an int array of size NUM_CHARS, indexed by ASCII value, with
     * the count of each character (0 for characters that do not occur).
     * <p>
     * Purpose: Counts occurrences of each 8-bit character in a file.
     * Parameter: filename, the input file name.
     * Returns: An integer array containing 256 frequency counts.
     * Example: A file containing "aab" produces freq[97] = 2 and freq[98] = 1.
     */
    public static int[] cntFreq(String filename) throws IOException {
        int[] freq = new int[NUM_CHARS];

        try (FileInputStream input = new FileInputStream(filename)) {
            int value;

            while ((value = input.read()) != -1) {
                freq[value]++;
            }
        }

        return freq;
    }

    /**
     * Adds node to collection, preserving whatever ordering your chosen
     * implementation of collection relies on to make the minimum element
     * easy to find.
     * <p>
     * Purpose: Adds a node while maintaining ascending node order.
     * Parameters: A collection and the node to insert.
     * Returns: Nothing.
     * Example: Adding a frequency-2 node before a frequency-5 node.
     */

    public static void addNode(Collection<HuffmanNode> collection, HuffmanNode node) {

        if (collection instanceof List) {
            List<HuffmanNode> nodes = (List<HuffmanNode>) collection;

            int position = 0;

            while (position < nodes.size()
                    && nodes.get(position).compareTo(node) <= 0) {
                position++;
            }

            nodes.add(position, node);
        } else {
            collection.add(node);
        }
    }

    /**
     * Removes and returns the smallest HuffmanNode in collection, using
     * HuffmanNode.comesBefore() (or compareTo(), which relies on it) to
     * determine "smallest". Returns null if collection is empty.
     * <p>
     * This must work for ANY Collection passed in -- not just the one you
     * use inside createHuffTree(). For example, if you back createHuffTree()
     * with a PriorityQueue, poll() it; for any other collection, find the
     * minimum node and remove it.
     * <p>
     * Purpose: Removes and returns the smallest node.
     * Parameter: A collection of HuffmanNode objects.
     * Returns: The smallest node, or null if empty.
     * Example: Removing from [frequency 2, frequency 5] returns frequency 2.
     */

    public static HuffmanNode removeMin(Collection<HuffmanNode> collection) {

        if (collection.isEmpty()) {
            return null;
        }

        if (collection instanceof PriorityQueue) {
            return ((PriorityQueue<HuffmanNode>) collection).poll();
        }

        Iterator<HuffmanNode> iterator = collection.iterator();

        HuffmanNode smallest = iterator.next();

        while (iterator.hasNext()) {
            HuffmanNode current = iterator.next();

            if (HuffmanNode.comesBefore(current, smallest)) {
                smallest = current;
            }
        }

        iterator = collection.iterator();

        while (iterator.hasNext()) {
            if (iterator.next() == smallest) {
                iterator.remove();
                break;
            }
        }

        return smallest;
    }

    /**
     * Builds a Huffman tree from an array of character frequencies (as
     * produced by cntFreq()) and returns the root HuffmanNode of the tree.
     * <p>
     * Use addNode()/removeMin() above to manage the collection of
     * not-yet-combined nodes as you build the tree -- pick whichever
     * concrete Collection type you want to back them with (e.g. a sorted
     * ArrayList, or a PriorityQueue). The rest of this method, and the
     * resulting tree, should not depend on which one you chose.
     * <p>
     * Special cases:
     * - If freq contains no non-zero counts (empty input file), return null
     * - If freq contains exactly one non-zero count (input file has only
     * one unique character), return a single leaf HuffmanNode for that
     * character (no combine() call needed).
     * <p>
     * Purpose: Builds a Huffman tree from character frequencies.
     * Parameter: An integer array containing character frequencies.
     * Returns: The root of the Huffman tree, or null if empty.
     * Example: createHuffTree(cntFreq("file1.txt")).
     */
    public static HuffmanNode createHuffTree(int[] freq) {

        Collection<HuffmanNode> nodes = new ArrayList<>();

        // Transform nonzero frequency entries into leaf nodes.
        for (int i = 0; i < NUM_CHARS; i++) {
            if (freq[i] > 0) {
                HuffmanNode node = new HuffmanNode(i, freq[i]);
                addNode(nodes, node);
            }
        }

        // Empty input file.
        if (nodes.isEmpty()) {
            return null;
        }

        // Combine the two smallest nodes until one remains.
        while (nodes.size() > 1) {
            HuffmanNode first = removeMin(nodes);
            HuffmanNode second = removeMin(nodes);

            HuffmanNode parent = HuffmanNode.combine(first, second);

            addNode(nodes, parent);
        }

        return removeMin(nodes);
    }

    /**
     * Traverses the Huffman tree rooted at root and builds the Huffman
     * code for every character that appears in it. Going left appends
     * '0' to the code, going right appends '1'.
     * <p>
     * Returns a String array of size NUM_CHARS, indexed by ASCII value.
     * Characters that do not appear in the tree have an empty string ""
     * at their index. The same is true of the lone character in a
     * single-node tree (the only-one-unique-character case) - it has no
     * left/right branch to build a code out of; huffmanEncode/huffmanDecode
     * handle that case entirely through the header.
     * <p>
     * Purpose: Generates Huffman codes for all characters in the tree.
     * Parameter: The root of a Huffman tree.
     * Returns: A 256-entry String array of character codes.
     * Example: createCode(root)[97] returns the code for 'a'.
     */

    public static String[] createCode(HuffmanNode root) {

        String[] codes = new String[NUM_CHARS];
        Arrays.fill(codes, "");

        if (root == null || root.isLeaf()) {
            return codes;
        }

        buildCodes(root, "", codes);

        return codes;
    }

    /**
     * Purpose: Recursively generates codes by traversing the tree.
     * Parameters: Current node, current code, and code table.
     * Returns: Nothing; updates the code table.
     * Example: A left branch followed by a right branch produces "01".
     */
    private static void buildCodes(HuffmanNode node,
                                   String code,
                                   String[] codes) {

        if (node.isLeaf()) {
            codes[node.charAscii()] = code;
            return;
        }

        if (node.left() != null) {
            buildCodes(node.left(), code + "0", codes);
        }

        if (node.right() != null) {
            buildCodes(node.right(), code + "1", codes);
        }
    }

    /**
     * Given a frequency array (as produced by cntFreq()), returns a
     * header string listing the ASCII values and their associated
     * frequencies, separated by single spaces, in ascending order of
     * ASCII value. Only characters with a non-zero count are included.
     * <p>
     * Example: for the text "aaabbbbcc" this returns "97 3 98 4 99 2".
     * <p>
     * Purpose: Creates a header containing ASCII values and frequencies.
     * Parameter: A 256-entry frequency array.
     * Returns: A space-separated header String.
     * Example: {97:3, 98:4} produces "97 3 98 4".
     */
    public static String createHeader(int[] freq) {

        StringBuilder header = new StringBuilder();

        for (int i = 0; i < NUM_CHARS; i++) {

            if (freq[i] > 0) {

                if (header.length() > 0) {
                    header.append(" ");
                }

                header.append(i);
                header.append(" ");
                header.append(freq[i]);
            }
        }

        return header.toString();
    }

    /**
     * Reads the input text file inFile and writes to outFile:
     * 1. A header line (see createHeader), followed by a newline
     * 2. The Huffman-encoded contents of inFile, using the codes from
     * createCode() -- UNLESS inFile has only one unique character,
     * see below.
     * If outFile already exists, its old contents are overwritten.
     * <p>
     * Special cases:
     * - Empty input file: outFile should end up empty too
     * - Input file with only one unique character: should contain only the
     * header line (e.g. "97 5") and nothing else - no second line of code bits.
     * <p>
     * Purpose: Encodes an input file using Huffman coding.
     * Parameters: Input filename and output filename.
     * Returns: Nothing; writes the encoded file.
     * Example: huffmanEncode("file1.txt", "file1_out.txt").
     */

    public static void huffmanEncode(String inFile, String outFile)
            throws IOException {

        int[] freq = cntFreq(inFile);

        // Empty file produces an empty output file.
        boolean empty = true;

        for (int count : freq) {
            if (count > 0) {
                empty = false;
                break;
            }
        }

        if (empty) {
            try (FileOutputStream output = new FileOutputStream(outFile)) {
                // Leave output empty.
            }
            return;
        }

        String header = createHeader(freq);
        HuffmanNode root = createHuffTree(freq);

        try (FileOutputStream output = new FileOutputStream(outFile)) {

            output.write(header.getBytes("US-ASCII"));
            output.write('\n');

            // Single-character input: header is sufficient.
            if (root.isLeaf()) {
                return;
            }

            String[] codes = createCode(root);

            byte[] input = Files.readAllBytes(new File(inFile).toPath());

            for (byte b : input) {
                int ascii = b & 0xFF;

                output.write(codes[ascii].getBytes("US-ASCII"));
            }
        }
    }

    /**
     * Parses a header string (in the format produced by createHeader) and
     * returns the corresponding frequency array of size NUM_CHARS, indexed
     * by ASCII value, i.e. the same format returned by cntFreq().
     * <p>
     * Example: for the header "97 3 98 4 99 2", freq[97, 98, 99] == {3, 4, 2}.
     * <p>
     * Purpose: Reconstructs a frequency array from an encoded header.
     * Parameter: A space-separated header String.
     * Returns: A 256-entry frequency array.
     * Example: "97 3 98 4" produces freq[97] = 3 and freq[98] = 4.
     */

    public static int[] parseHeader(String headerString) {

        int[] freq = new int[NUM_CHARS];

        if (headerString == null || headerString.trim().isEmpty()) {
            return freq;
        }

        String[] values = headerString.trim().split("\\s+");

        for (int i = 0; i < values.length; i += 2) {

            int ascii = Integer.parseInt(values[i]);
            int count = Integer.parseInt(values[i + 1]);

            freq[ascii] = count;
        }

        return freq;
    }

    /**
     * Reads an encoded file (in the format written by huffmanEncode) and
     * writes the decoded text to decodeFile.
     *
     * Recreate the frequency array from the header with parseHeader(),
     * rebuild the Huffman tree with createHuffTree(), then walk the tree
     * for each bit of the second line: '0' -> left child, '1' -> right
     * child. When a leaf is reached, emit its character and restart at
     * the root.
     *
     * Special case: if the recreated tree is a single leaf (the
     * only-one-unique-character case), there is no second line to read
     * The header's count alone tells you how many times that character occurred,
     * so write it out that many times directly.
     *
     * Purpose: Decodes a Huffman-encoded file.
     * Parameters: Encoded input filename and decoded output filename.
     * Returns: Nothing; writes the original text.
     * Example: huffmanDecode("file1_out.txt", "file1_decode.txt").
     */

    public static void huffmanDecode(String encodedFile, String decodeFile)
            throws IOException {

        byte[] encoded = Files.readAllBytes(new File(encodedFile).toPath());

        try (FileOutputStream output = new FileOutputStream(decodeFile)) {

            // Empty encoded file.
            if (encoded.length == 0) {
                return;
            }

            // Find the end of the header line.
            int newline = -1;

            for (int i = 0; i < encoded.length; i++) {
                if (encoded[i] == '\n') {
                    newline = i;
                    break;
                }
            }

            if (newline == -1) {
                throw new IOException("Invalid encoded file: missing header newline");
            }

            String header = new String(
                    encoded, 0, newline, "US-ASCII");

            int[] freq = parseHeader(header);

            HuffmanNode root = createHuffTree(freq);

            if (root == null) {
                return;
            }

            // Single-character special case.
            if (root.isLeaf()) {

                for (int i = 0; i < root.freq(); i++) {
                    output.write(root.charAscii());
                }

                return;
            }

            HuffmanNode current = root;

            // Decode the bits following the header.
            for (int i = newline + 1; i < encoded.length; i++) {

                if (encoded[i] == '0') {
                    current = current.left();
                } else if (encoded[i] == '1') {
                    current = current.right();
                } else {
                    throw new IOException("Invalid encoded bit");
                }

                if (current == null) {
                    throw new IOException("Invalid Huffman code");
                }

                if (current.isLeaf()) {

                    output.write(current.charAscii());

                    current = root;
                }
            }
        }
    }
}
