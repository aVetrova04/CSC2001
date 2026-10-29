
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void newHeapIsEmpty() {
        MinHeap h = new MinHeap();
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void peekOnEmptyHeapThrows() {
        assertThrows(NoSuchElementException.class,
                () -> new MinHeap().peek());
    }

    @Test
    void insertOneValue() {
        MinHeap h = new MinHeap(1);
        h.insert(42);
        assertFalse(h.isEmpty());
        assertEquals(1, h.size());
        assertEquals(42, h.peek());
        assertArrayEquals(new int[]{42}, h.toArray());
    }

    // constructor

    @Test
    void constructorRejectsZeroCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new MinHeap(0));
    }

    @Test
    void constructorRejectsNegativeCapacity() {
        assertThrows(IllegalArgumentException.class,
                () -> new MinHeap(-5));
    }

    @Test
    void constructorFromArrayRejectsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new MinHeap((int[]) null));
    }

    @Test
    void constructorFromEmptyArray() {
        MinHeap h = new MinHeap(new int[0]);

        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void constructorFromSingleValue() {
        MinHeap h = new MinHeap(new int[]{17});

        assertEquals(1, h.size());
        assertEquals(17, h.peek());
        assertArrayEquals(new int[]{17}, h.toArray());
    }

    @Test
    void constructorBuildsBottomUp() {
        int[] values = {9, 4, 7, 1, 0, 3, 2};
        int[] original = values.clone();

        MinHeap h = new MinHeap(values);

        assertArrayEquals(original, values);

        assertEquals(values.length, h.size());
        assertEquals(0, h.peek());

        int[] heapArray = h.toArray();

        for (int i = 0; i < heapArray.length; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < heapArray.length) {
                assertTrue(heapArray[i] <= heapArray[left]);
            }

            if (right < heapArray.length) {
                assertTrue(heapArray[i] <= heapArray[right]);
            }
        }

        int[] sorted = new int[values.length];

        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = h.removeMin();
        }

        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 7, 9}, sorted);
        assertTrue(h.isEmpty());
    }

    // functionality

    @Test
    void peekDoesNotRemoveValue() {
        MinHeap h = new MinHeap();
        h.insert(8);
        h.insert(3);
        h.insert(5);

        int[] before = h.toArray();

        assertEquals(3, h.peek());
        assertEquals(3, h.peek());
        assertEquals(3, h.size());
        assertArrayEquals(before, h.toArray());
    }

    @Test
    void toArrayReturnsCopy() {
        MinHeap h = new MinHeap();
        h.insert(3);
        h.insert(5);
        h.insert(8);

        int[] copy = h.toArray();
        copy[0] = 100;
        copy[1] = -50;

        assertArrayEquals(new int[]{3, 5, 8}, h.toArray());
        assertEquals(3, h.peek());
    }

    @Test
    void insertingNewMinimumPercolatesToRoot() {
        MinHeap h = new MinHeap();
        h.insert(10);
        h.insert(20);
        h.insert(30);
        h.insert(40);
        h.insert(50);

        h.insert(1);

        assertEquals(1, h.peek());
        assertArrayEquals(
                new int[]{1, 20, 10, 40, 50, 30},
                h.toArray());
    }

    @Test
    void equalValuesDoNotPercolateAboveParent() {
        MinHeap h = new MinHeap();
        h.insert(2);
        h.insert(2);
        h.insert(2);

        assertArrayEquals(new int[]{2, 2, 2}, h.toArray());
    }

    @Test
    void insertGrowsHeapWithCapacityOne() {
        MinHeap h = new MinHeap(1);

        h.insert(5);
        h.insert(3);
        h.insert(7);
        h.insert(1);
        h.insert(4);

        assertEquals(5, h.size());
        assertEquals(1, h.peek());
        assertArrayEquals(new int[]{1, 3, 7, 5, 4}, h.toArray());
    }

    @Test
    void insertMoreThanDefaultCapacity() {
        MinHeap h = new MinHeap();

        for (int i = 20; i >= 1; i--) {
            h.insert(i);
        }

        assertEquals(20, h.size());
        assertEquals(1, h.peek());

        for (int i = 1; i <= 20; i++) {
            assertEquals(i, h.removeMin());
        }

        assertTrue(h.isEmpty());
    }

    @Test
    void removeMinFromEmptyHeapThrows() {
        assertThrows(NoSuchElementException.class,
                () -> new MinHeap().removeMin());
    }

    @Test
    void removeMinFromSingleValueHeap() {
        MinHeap h = new MinHeap();
        h.insert(42);

        assertEquals(42, h.removeMin());
        assertTrue(h.isEmpty());
        assertEquals(0, h.size());
        assertArrayEquals(new int[0], h.toArray());
    }

    @Test
    void removeMinSwapsWithSmallerChild() {
        MinHeap h = new MinHeap(
                new int[]{1, 4, 2, 5, 6, 3, 7});

        assertEquals(1, h.removeMin());

        assertArrayEquals(
                new int[]{2, 4, 3, 5, 6, 7},
                h.toArray());
    }

    @Test
    void removeMinChoosesLeftChildWhenChildrenTie() {
        MinHeap h = new MinHeap(
                new int[]{1, 2, 2, 4, 5, 3, 3});

        assertEquals(1, h.removeMin());

        // The replacement value 3 swaps with the left child 2.
        assertArrayEquals(
                new int[]{2, 3, 2, 4, 5, 3},
                h.toArray());
    }

    @Test
    void removeEveryValueThenOperationsThrow() {
        MinHeap h = new MinHeap();
        h.insert(5);
        h.insert(1);
        h.insert(3);

        assertEquals(1, h.removeMin());
        assertEquals(3, h.removeMin());
        assertEquals(5, h.removeMin());

        assertTrue(h.isEmpty());
        assertThrows(NoSuchElementException.class, h::removeMin);
        assertThrows(NoSuchElementException.class, h::peek);
    }

    @Test
    void duplicatesAndNegativeValues() {
        MinHeap h = new MinHeap();
        h.insert(0);
        h.insert(-5);
        h.insert(3);
        h.insert(-5);
        h.insert(2);
        h.insert(-1);

        assertEquals(-5, h.removeMin());
        assertEquals(-5, h.removeMin());
        assertEquals(-1, h.removeMin());
        assertEquals(0, h.removeMin());
        assertEquals(2, h.removeMin());
        assertEquals(3, h.removeMin());

        assertTrue(h.isEmpty());
    }

    @Test
    void manyInsertsThenRemoveInSortedOrder() {
        MinHeap h = new MinHeap(2);
        int[] values = {12, -4, 8, 0, 12, 3, -10, 5, 1, -4};

        for (int value : values) {
            h.insert(value);
        }

        int[] actual = new int[values.length];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = h.removeMin();
        }

        assertArrayEquals(
                new int[]{-10, -4, -4, 0, 1, 3, 5, 8, 12, 12},
                actual);
    }

    // heap from array

    @Test
    void arrayBuiltHeapSupportsInsertAndGrow() {
        MinHeap h = new MinHeap(new int[]{8, 4, 6});

        h.insert(2);
        h.insert(10);
        h.insert(1);
        h.insert(7);
        h.insert(3);

        assertEquals(8, h.size());
        assertEquals(1, h.peek());

        assertEquals(1, h.removeMin());
        assertEquals(2, h.removeMin());
        assertEquals(3, h.removeMin());
        assertEquals(4, h.removeMin());
        assertEquals(6, h.removeMin());
        assertEquals(7, h.removeMin());
        assertEquals(8, h.removeMin());
        assertEquals(10, h.removeMin());

        assertTrue(h.isEmpty());
    }

    // sort

    @Test
    void heapSortEmptyArray() {
        assertArrayEquals(new int[0], MinHeap.heapSort(new int[0]));
    }

    @Test
    void heapSortSingleValue() {
        assertArrayEquals(
                new int[]{7},
                MinHeap.heapSort(new int[]{7}));
    }

    @Test
    void heapSortDuplicatesAndNegativeValues() {
        int[] values = {5, -2, 3, -2, 0, 5, -10};
        int[] original = values.clone();

        assertArrayEquals(
                new int[]{-10, -2, -2, 0, 3, 5, 5},
                MinHeap.heapSort(values));

        assertArrayEquals(original, values);
    }

    @Test
    void heapSortDoesNotChangeInput() {
        int[] values = {9, 4, 7, 1, 0, 3, 2};
        int[] original = values.clone();

        int[] sorted = MinHeap.heapSort(values);

        assertArrayEquals(
                new int[]{0, 1, 2, 3, 4, 7, 9},
                sorted);
        assertArrayEquals(original, values);
        assertNotSame(values, sorted);
    }

    @Test
    void heapSortRejectsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> MinHeap.heapSort(null));
    }
}
