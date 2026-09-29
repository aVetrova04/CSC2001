public class Main {

    public static void main(String[] args) {

        // 1. Three examples of integer arrays with different lengths

        int[] array1 = {1, 2, 3};
        int[] array2 = {10, 20, 30, 40, 50};
        int[] array3 = {7};


        // 2. Test cases for allLessThan100

        System.out.println(allLessThan100(new int[]{1, 2, 3}));       // true
        System.out.println(allLessThan100(new int[]{99, 50, 20}));    // true
        System.out.println(allLessThan100(new int[]{100, 2, 3}));    // false
        System.out.println(allLessThan100(new int[]{101, 2, 3}));    // false
        System.out.println(allLessThan100(new int[]{}));              // true
    }


    // Purpose:
    // Returns true if all elements in the array are less than 100.

    // Header:
    static boolean allLessThan100(int[] arr) {

        // Accumulator
        boolean result = true;

        // Loop through every element
        for (int i = 0; i < arr.length; i++) {

            // Update the accumulator
            if (arr[i] >= 100) {
                result = false;
            }
        }

        return result;
    }
}
