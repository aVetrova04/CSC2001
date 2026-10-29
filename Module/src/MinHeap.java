
import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * A min-heap of ints stored in an array, as presented in lecture.
 *
 * The heap is a complete binary tree kept in heap[1] .. heap[size];
 * heap[0] is never used. For the node at index i:
 *     left child  = 2 * i
 *     right child = 2 * i + 1
 *     parent      = i / 2
 *
 * Heap-order property: every parent is <= its children, so the
 * minimum is always at heap[1]. Duplicate values are allowed.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 */
public class MinHeap {

    /** Number of values a new heap can hold before it has to grow. */
    public static final int DEFAULT_CAPACITY = 10;

    private int[] heap;   // the root is heap[1]
    private int size;     // values live in heap[1] ... heap[size]

    /**
     * Purpose: Create an empty heap with DEFAULT_CAPACITY spaces.
     * Input: None.
     * Output: A new empty MinHeap.
     * Example: new MinHeap().size() returns 0.
     */
    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Purpose: Create an empty heap with the requested capacity.
     * Input: capacity, the number of values the heap can initially hold.
     * Output: A new empty MinHeap.
     * Example: new MinHeap(5).isEmpty() returns true.
     * Throws IllegalArgumentException if capacity is less than 1.
     */
    public MinHeap(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(
                    "Capacity must be at least 1.");
        }

        heap = new int[capacity + 1];
        size = 0;
    }

    /**
     * Purpose: Build a min-heap from an existing array using bottom-up
     * heap construction.
     * Input: values, the integers to store.
     * Output: A new MinHeap containing all the given integers.
     * Example: new MinHeap(new int[]{3, 1, 2}).peek() returns 1.
     * Throws IllegalArgumentException if values is null.
     */
    public MinHeap(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException(
                    "Values array cannot be null.");
        }

        heap = new int[Math.max(values.length + 1, 2)];
        size = values.length;

        for (int i = 0; i < values.length; i++) {
            heap[i + 1] = values[i];
        }

        buildHeap();
    }

    /**
     * Purpose: Insert a value while maintaining heap order.
     * Input: value, the integer to insert.
     * Output: None; the heap contains the new value.
     * Example: Inserting 1 into a heap containing {3, 5}
     * makes peek() return 1.
     */
    public void insert(int value) {
        if (size == heap.length - 1) {
            grow();
        }

        size++;
        heap[size] = value;
        percolateUp(size);
    }

    /**
     * Purpose: Remove and return the smallest value.
     * Input: None.
     * Output: The minimum integer previously stored in the heap.
     * Example: Removing the minimum from {2, 5, 7} returns 2.
     * Throws NoSuchElementException if the heap is empty.
     */
    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        int minimum = heap[1];

        heap[1] = heap[size];
        size--;

        if (size > 0) {
            percolateDown(1);
        }

        return minimum;
    }

    /**
     * Purpose: Return the smallest value without removing it.
     * Input: None.
     * Output: The minimum integer in the heap.
     * Example: If the heap contains {2, 5, 7}, peek() returns 2.
     * Throws NoSuchElementException if the heap is empty.
     */
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        return heap[1];
    }

    /**
     * Purpose: Report the number of values in the heap.
     * Input: None.
     * Output: The number of stored integers.
     * Example: A heap containing three values has size() == 3.
     */
    public int size() {
        return size;
    }

    /**
     * Purpose: Determine whether the heap contains any values.
     * Input: None.
     * Output: true if empty; otherwise false.
     * Example: new MinHeap().isEmpty() returns true.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Purpose: Return a copy of the heap's values in array order.
     * Input: None.
     * Output: A new array containing heap[1] through heap[size].
     * Example: A heap with array order {3, 5, 8} returns {3, 5, 8}.
     */
    public int[] toArray() {
        return Arrays.copyOfRange(heap, 1, size + 1);
    }

    /**
     * Purpose: Move a value upward until heap order is restored.
     * Input: i, the index of the value to move.
     * Output: None; the heap is reordered in place.
     * Example: Inserting 1 into {3, 5} causes it to percolate
     * upward to the root.
     */
    private void percolateUp(int i) {
        while (i > 1 && heap[i] < heap[i / 2]) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    /**
     * Purpose: Move a value downward until heap order is restored.
     * Input: i, the index of the value to move.
     * Output: None; the heap is reordered in place.
     * Example: A value 9 at the root swaps with its smaller child 3.
     * When children tie, the left child is selected.
     */
    private void percolateDown(int i) {
        while (2 * i <= size) {
            int left = 2 * i;
            int right = left + 1;
            int smallerChild = left;

            if (right <= size && heap[right] < heap[left]) {
                smallerChild = right;
            }

            if (heap[i] <= heap[smallerChild]) {
                break;
            }

            swap(i, smallerChild);
            i = smallerChild;
        }
    }

    /**
     * Purpose: Exchange two values in the heap array.
     * Input: a and b, the indices of the values to exchange.
     * Output: None; the two values exchange positions.
     * Example: Swapping indices 1 and 2 in {0, 4, 7}
     * exchanges 4 and 7.
     */
    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    /**
     * Purpose: Double the array's capacity without changing indices.
     * Input: None.
     * Output: None; the heap array has additional capacity.
     * Example: An array with 11 slots grows to 22 slots.
     */
    private void grow() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    /**
     * Purpose: Restore heap order using bottom-up construction.
     * Input: None; operates on the existing heap.
     * Output: None; the heap satisfies the min-heap property.
     * Example: Building from {3, 1, 2} produces {1, 3, 2}.
     */
    private void buildHeap() {
        for (int i = size / 2; i >= 1; i--) {
            percolateDown(i);
        }
    }

    /**
     * Purpose: Return a new array containing values in ascending order.
     * Input: values, the integers to sort.
     * Output: A new sorted array; the input remains unchanged.
     * Example: heapSort(new int[]{3, 1, 2}) returns {1, 2, 3}.
     * Throws IllegalArgumentException if values is null.
     */
    public static int[] heapSort(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException(
                    "Values array cannot be null.");
        }

        MinHeap h = new MinHeap(values);
        int[] result = new int[values.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = h.removeMin();
        }

        return result;
    }
}
