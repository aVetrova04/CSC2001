import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Starter test suite for Project 3 (Huffman Encoding/Decoding).
 * Write more tests!
 *  ok! yay!!
 * These tests assume the test files are in the same directory as HuffmanTest.java
 */
public class HuffmanTest {

    @Test
    public void testCntFreq() throws IOException {
        int[] freqList = Huffman.cntFreq("src/file2.txt");
        int[] ansList = {2, 4, 8, 16, 0, 2, 0};

        for (int i = 0; i < ansList.length; i++) {
            assertEquals(ansList[i], freqList[97 + i]);
        }
    }

    @Test
    public void testComesBefore() {
        HuffmanNode d = new HuffmanNode('d', 5);
        HuffmanNode k = new HuffmanNode('k', 5);

        assertTrue(HuffmanNode.comesBefore(d, k));
        assertFalse(HuffmanNode.comesBefore(k, d));
        assertTrue(HuffmanNode.comesBefore(
                new HuffmanNode('a', 2),
                new HuffmanNode('z', 5)));
    }

    @Test
    public void testCombine() {
        HuffmanNode a = new HuffmanNode(97, 2);
        HuffmanNode b = new HuffmanNode(98, 3);

        HuffmanNode parent = HuffmanNode.combine(a, b);

        assertEquals(5, parent.freq());
        assertEquals(97, parent.charAscii());
        assertEquals(a, parent.left());
        assertEquals(b, parent.right());
    }

    @Test
    public void testAddNode() {
        Collection<HuffmanNode> nodes = new ArrayList<HuffmanNode>();

        HuffmanNode a = new HuffmanNode(97, 5);
        HuffmanNode b = new HuffmanNode(98, 2);
        HuffmanNode c = new HuffmanNode(99, 3);

        Huffman.addNode(nodes, a);
        Huffman.addNode(nodes, b);
        Huffman.addNode(nodes, c);

        List<HuffmanNode> list = (List<HuffmanNode>) nodes;

        assertEquals(b, list.get(0));
        assertEquals(c, list.get(1));
        assertEquals(a, list.get(2));
    }

    @Test
    public void testRemoveMin() {
        Collection<HuffmanNode> nodes = new ArrayList<HuffmanNode>();

        HuffmanNode a = new HuffmanNode(97, 5);
        HuffmanNode b = new HuffmanNode(98, 2);

        nodes.add(a);
        nodes.add(b);

        assertEquals(b, Huffman.removeMin(nodes));
        assertEquals(1, nodes.size());
        assertEquals(a, Huffman.removeMin(nodes));
        assertNull(Huffman.removeMin(nodes));
    }

    @Test
    public void testRemoveMinPriorityQueue() {
        Collection<HuffmanNode> nodes = new PriorityQueue<HuffmanNode>();

        nodes.add(new HuffmanNode(97, 5));
        nodes.add(new HuffmanNode(98, 2));

        assertEquals(2, Huffman.removeMin(nodes).freq());
    }

    @Test
    public void testCreateHuffTreeEmpty() {
        int[] freq = new int[256];

        assertNull(Huffman.createHuffTree(freq));
    }

    @Test
    public void testCreateHuffTreeSingleCharacter() {
        int[] freq = new int[256];
        freq[97] = 5;

        HuffmanNode root = Huffman.createHuffTree(freq);

        assertNotNull(root);
        assertTrue(root.isLeaf());
        assertEquals(97, root.charAscii());
        assertEquals(5, root.freq());
    }

    @Test
    public void testCreateCode() {
        HuffmanNode a = new HuffmanNode(97, 2);
        HuffmanNode b = new HuffmanNode(98, 3);

        HuffmanNode root = HuffmanNode.combine(a, b);
        String[] codes = Huffman.createCode(root);

        assertEquals("0", codes[97]);
        assertEquals("1", codes[98]);
        assertEquals("", codes[99]);
    }

    @Test
    public void testCreateCodeSingleCharacter() {
        HuffmanNode root = new HuffmanNode(97, 5);
        String[] codes = Huffman.createCode(root);

        assertEquals("", codes[97]);
    }

    @Test
    public void testCreateHeader() throws IOException {
        int[] freqList = Huffman.cntFreq("src/file2.txt");

        assertEquals(
                "97 2 98 4 99 8 100 16 102 2",
                Huffman.createHeader(freqList));
    }

    @Test
    public void testParseHeader() {
        int[] freq = Huffman.parseHeader("97 3 98 4 99 2");

        assertEquals(3, freq[97]);
        assertEquals(4, freq[98]);
        assertEquals(2, freq[99]);
        assertEquals(0, freq[100]);
    }

    @Test
    public void testParseEmptyHeader() {
        int[] freq = Huffman.parseHeader("");

        for (int count : freq) {
            assertEquals(0, count);
        }
    }

    @Test
    public void test01Textfile() throws IOException {
        Huffman.huffmanEncode("src/file1.txt", "src/file1_out.txt");

        String actual = Files.readString(
                new File("src/file1_out.txt").toPath());

        String expected = Files.readString(
                new File("src/file1_soln.txt").toPath());

        actual = actual.replace("\r\n", "\n").replace("\r", "\n");
        expected = expected.replace("\r\n", "\n").replace("\r", "\n");

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyEncodeDecode() throws IOException {
        Files.write(new File("src/empty.txt").toPath(), new byte[0]);

        Huffman.huffmanEncode("src/empty.txt", "src/empty_encoded.txt");

        assertEquals(0, new File("src/empty_encoded.txt").length());

        Huffman.huffmanDecode("src/empty_encoded.txt", "src/empty_decoded.txt");

        assertEquals(0, new File("src/empty_decoded.txt").length());
    }

    @Test
    public void testSingleCharacterEncodeDecode() throws IOException {
        Files.writeString(new File("src/single.txt").toPath(), "aaaaa");

        Huffman.huffmanEncode("src/single.txt", "src/single_encoded.txt");

        String result = Files.readString(
                new File("src/single_encoded.txt").toPath());

        assertEquals("97 5\n", result);

        Huffman.huffmanDecode("src/single_encoded.txt", "src/single_decoded.txt");

        assertEquals("aaaaa", Files.readString(
                new File("src/single_decoded.txt").toPath()));
    }

    @Test
    public void testEncodeDecode() throws IOException {
        String original = "aaabbbbcc";

        Files.writeString(new File("src/normal.txt").toPath(), original);

        Huffman.huffmanEncode("src/normal.txt", "src/normal_encoded.txt");

        Huffman.huffmanDecode("src/normal_encoded.txt", "src/normal_decoded.txt");

        assertEquals(original, Files.readString(
                new File("src/normal_decoded.txt").toPath()));
    }

    @Test
    public void testMissingFile() {
        assertThrows(IOException.class, () ->
                Huffman.cntFreq("src/this_file_does_not_exist.txt"));
    }

    private static boolean compareFiles(String file1, String file2)
            throws IOException {

        return java.util.Arrays.equals(
                Files.readAllBytes(new File(file1).toPath()),
                Files.readAllBytes(new File(file2).toPath())
        );
    }


    @Test
    public void testThirdFileEncodeDecode() throws IOException {
        String original = Files.readString(
                new File("src/test3.txt").toPath());

        Huffman.huffmanEncode("src/test3.txt", "src/test3_encoded.txt");

        String encoded = Files.readString(
                new File("src/test3_encoded.txt").toPath());

        assertTrue(encoded.startsWith(
                "97 4 98 3 99 2 100 2 101 1\n"));

        Huffman.huffmanDecode(
                "src/test3_encoded.txt",
                "src/test3_decoded.txt");

        String decoded = Files.readString(
                new File("src/test3_decoded.txt").toPath());

        assertEquals(original, decoded);
    }

    @Test
    public void testFourthFileEncodeDecode() throws IOException {
        String original = Files.readString(
                new File("src/test4.txt").toPath());

        Huffman.huffmanEncode("src/test4.txt", "src/test4_encoded.txt");

        String encoded = Files.readString(
                new File("src/test4_encoded.txt").toPath());

        assertTrue(encoded.startsWith(
                "32 1 33 1 72 1 87 1 100 1 101 1 108 3 111 2 114 1\n"));

        Huffman.huffmanDecode(
                "src/test4_encoded.txt",
                "src/test4_decoded.txt");

        String decoded = Files.readString(
                new File("src/test4_decoded.txt").toPath());

        assertEquals(original, decoded);
    }
}
