import java.util.NoSuchElementException;

public class IntArray {

    // Fields
    int[] array;
    int count;

    // Constructor
    public IntArray(int[] array, int count) {
        this.array = array;
        this.count = count;
    }

    /*
     * Purpose:
     * Returns true if both IntArrays have the same count and the same
     * elements in their arrays.
     *
     * Example:
     * If this IntArray is [2, 3, 4, 5] with count 4 and
     * the other IntArray is [2, 3, 4, 5] with count 4,
     * equals() returns true.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IntArray)) {
            return false;
        }

        IntArray other = (IntArray) obj;

        // Check numeric fields
        if (this.count != other.count) {
            return false;
        }

        // Check array lengths
        if (this.array.length != other.array.length) {
            return false;
        }

        // Check array elements
        for (int i = 0; i < this.array.length; i++) {
            if (this.array[i] != other.array[i]) {
                return false;
            }
        }

        return true;
    }

    /*
     * Purpose:
     * Returns true if this IntArray and the other IntArray contain
     * the same sequence of integers.
     *
     * Example:
     * If this IntArray is [2, 3, 4] and the other IntArray is
     * [2, 3, 4, 0, 0], equalElts() returns true because both
     * have the same sequence of live elements.
     */
    public boolean equalElts(IntArray other) {

        // They must have the same number of live elements.
        if (this.count != other.count) {
            return false;
        }

        // Compare only the live elements.
        for (int i = 0; i < this.count; i++) {
            if (this.array[i] != other.array[i]) {
                return false;
            }
        }

        return true;
    }

    /*
     * Purpose:
     * Creates and returns a new IntArray representing an empty list
     * of integers.
     *
     * Example:
     * If empty() is called, it returns an IntArray with no live elements.
     */
    public IntArray empty() {
        return new IntArray(new int[10], 0);
    }

    /*
     * Purpose:
     * Returns the number of live elements in the IntArray.
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and has
     * additional unused capacity, length() returns 3.
     */
    public int length() {
        return count;
    }

    /*
     * Purpose:
     * Accepts an index and returns the element at that index
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and get(2) is called
     * the method returns 30
     */
    public int get(int index) {
        if (index < 0 || index >= count) {
            throw new NoSuchElementException();
        }

        return array[index];
    }

    /*
     * Purpose:
     * accepts an index and a new value and mutates the underlying array
     * to contain the new value at the given index
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and set(2, 70) is called
     * the IntArray will contain [10, 20, 70]
     */
    public void set(int index, int val) {
        if (index < 0 || index >= count) {
            throw new NoSuchElementException();
        }

        array[index] = val;
    }

    /*
     * Purpose:
     * accepts an index and a new value and mutates the underlying array to
     * contain the new value at the given index while shifting remaining elements
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and insert(2, 70) is called
     * the IntArray will contain [10, 20, 70, 30]
     */
    public void insert(int index, int val) {
        if (index < 0 || index > count) {
            throw new NoSuchElementException();
        }

        if (count == array.length) {
            int[] newArray = new int[array.length+1];

            for (int i = 0; i < count; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
        }

        for (int i = count; i > index; i--) {
            array[i] = array[i - 1];
        }

        array[index] = val;
        count++;
    }

    /*
     * Purpose:
     * Accepts an index and removes the element at that index while shifting
     * later elements to the left.
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and remove(1) is called,
     * the IntArray will contain [10, 30].
     */
    public void remove(int index) {
        if (index < 0 || index >= count) {
            throw new NoSuchElementException();
        }

        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }

        count--;
    }

    /*
     * Purpose:
     * Adds an element to the end of the list.
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and addToEnd(40) is called,
     * the IntArray will contain [10, 20, 30, 40].
     */
    public void addToEnd(int val) {
        insert(count, val);
    }

    /*
     * Purpose:
     * Adds an element to the start of the list.
     *
     * Example:
     * If the IntArray contains [10, 20, 30] and addToStart(40) is called,
     * the IntArray will contain [40, 10, 20, 30].
     */
    public void addToStart(int val) {
        insert(0, val);
    }
}
