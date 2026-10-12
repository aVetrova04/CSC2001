package PART2;

public class NumNode implements AST {

    private final double value;

    /*
     * Purpose:
     * Constructs a number node containing the specified value.
     *
     * Input:
     * @param value: double, the number to store in this node
     *
     * Output:
     * A NumNode containing the given number.
     *
     * Sample Tests:
     * 1. Creating a node with 5 should store the value 5.0.
     * 2. Creating a node with -2.5 should store the value -2.5.
     * 3. Creating a node with 0 should store the value 0.0.
     */
    public NumNode(double value) {
        this.value = value;
    }

    /*
     * Purpose:
     * Evaluates this number node.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The double value stored in this node.
     *
     * Sample Tests:
     * 1. A node containing 5 should return 5.0.
     * 2. A node containing -2.5 should return -2.5.
     * 3. A node containing 0 should return 0.0.
     */
    @Override
    public double eval() {
        return value;
    }
}