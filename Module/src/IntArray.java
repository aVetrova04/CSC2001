public class IntArray {

    // Fields
    int[] array;
    int count;


    // Constructor
    public IntArray(int[] array, int count) {
        this.array = array;
        this.count = count;
    }


    // Returns true if both IntArrays have the same count and the same elements in their arrays.
    // {2, 3, 4, 5} and {2, 3, 4, 5} --> returns True
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

    // Returns true if this IntArray and the other IntArray contain the same sequence of integers.
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

    // Create an empty IntArray
    // After call result should be {}
}
