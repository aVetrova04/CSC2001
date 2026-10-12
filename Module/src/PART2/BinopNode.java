package PART2;

public class BinopNode implements AST {

    private final String operator;
    private final AST left;
    private final AST right;

    /*
     * Purpose:
     * Constructs a binary operation node with an operator,
     * a left child, and a right child.
     *
     * Input:
     * @param operator: String, the mathematical operator
     * @param left: AST, the left operand expression
     * @param right: AST, the right operand expression
     *
     * Output:
     * A BinopNode containing the operator and both children.
     *
     * Sample Tests:
     * 1. A node created with "+", 3, and 4 represents 3 + 4.
     * 2. A node created with "-", 10, and 3 represents 10 - 3.
     * 3. A node created with "*", 2, and 5 represents 2 * 5.
     */
    public BinopNode(String operator, AST left, AST right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /*
     * Purpose:
     * Evaluates both child expressions and performs the
     * mathematical operation stored in this node.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * A double representing the result of the operation.
     *
     * Exceptions:
     * Throws IllegalArgumentException with message
     * "invalid token" if the operator is unsupported.
     *
     * Sample Tests:
     * 1. A node representing 3 + 4 should return 7.0.
     * 2. A node representing 10 - 3 should return 7.0.
     * 3. A node representing 3 * 4 should return 12.0.
     * 4. A node representing 8 / 2 should return 4.0.
     * 5. A node representing 2 ^ 3 should return 8.0.
     * 6. A node representing 2 + (3 * 4) should return 14.0.
     * 7. A node with an unsupported operator such as "%"
     *    should throw "invalid token".
     */
    @Override
    public double eval() {
        double leftValue = left.eval();
        double rightValue = right.eval();

        switch (operator) {
            case "+":
                return leftValue + rightValue;

            case "-":
                return leftValue - rightValue;

            case "*":
                return leftValue * rightValue;

            case "/":
                return leftValue / rightValue;

            case "^":
                return Math.pow(leftValue, rightValue);

            default:
                throw new IllegalArgumentException("invalid token");
        }
    }
}