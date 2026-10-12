package PART2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ASTTest {

    /*
     * Purpose:
     * Tests evaluation of a number node.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if eval returns the stored number.
     *
     * Sample Tests:
     * 1. A node containing 5 should evaluate to 5.0.
     * 2. A node containing -2.5 should evaluate to -2.5.
     * 3. A node containing 0 should evaluate to 0.0.
     */
    @Test
    public void testNumNode() {
        AST positive = new NumNode(5.0);
        AST negative = new NumNode(-2.5);
        AST zero = new NumNode(0.0);

        assertEquals(5.0, positive.eval(), 0.000001);
        assertEquals(-2.5, negative.eval(), 0.000001);
        assertEquals(0.0, zero.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests addition.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 3 + 4 evaluates to 7.
     *
     * Sample Tests:
     * 1. A node representing 3 + 4 should return 7.0.
     */
    @Test
    public void testAddition() {
        AST expression = new BinopNode(
                "+", new NumNode(3), new NumNode(4));

        assertEquals(7.0, expression.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests subtraction.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 10 - 3 evaluates to 7.
     *
     * Sample Tests:
     * 1. A node representing 10 - 3 should return 7.0.
     * 2. The operands must remain in the correct order.
     */
    @Test
    public void testSubtraction() {
        AST expression = new BinopNode(
                "-", new NumNode(10), new NumNode(3));

        assertEquals(7.0, expression.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests multiplication.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 3 * 4 evaluates to 12.
     *
     * Sample Tests:
     * 1. A node representing 3 * 4 should return 12.0.
     */
    @Test
    public void testMultiplication() {
        AST expression = new BinopNode(
                "*", new NumNode(3), new NumNode(4));

        assertEquals(12.0, expression.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests division.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 8 / 2 evaluates to 4.
     *
     * Sample Tests:
     * 1. A node representing 8 / 2 should return 4.0.
     * 2. Dividing 7 by 2 should return 3.5.
     */
    @Test
    public void testDivision() {
        AST expression = new BinopNode(
                "/", new NumNode(8), new NumNode(2));

        AST fractional = new BinopNode(
                "/", new NumNode(7), new NumNode(2));

        assertEquals(4.0, expression.eval(), 0.000001);
        assertEquals(3.5, fractional.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests exponentiation.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 2 ^ 3 evaluates to 8.
     *
     * Sample Tests:
     * 1. A node representing 2 ^ 3 should return 8.0.
     * 2. A node representing 2 ^ 0 should return 1.0.
     */
    @Test
    public void testExponentiation() {
        AST expression = new BinopNode(
                "^", new NumNode(2), new NumNode(3));

        AST zeroExponent = new BinopNode(
                "^", new NumNode(2), new NumNode(0));

        assertEquals(8.0, expression.eval(), 0.000001);
        assertEquals(1.0, zeroExponent.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests recursive evaluation of a nested expression tree.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if 2 + (3 * 4) evaluates to 14.
     *
     * Sample Tests:
     * 1. The multiplication child should evaluate to 12.
     * 2. The addition parent should evaluate to 14.
     */
    @Test
    public void testNestedExpression() {
        AST multiplication = new BinopNode(
                "*", new NumNode(3), new NumNode(4));

        AST addition = new BinopNode(
                "+", new NumNode(2), multiplication);

        assertEquals(14.0, addition.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests evaluation through an AST interface reference.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if both node implementations can be
     * evaluated through the AST interface.
     *
     * Sample Tests:
     * 1. An AST reference to NumNode(9) should return 9.0.
     * 2. An AST reference to a tree representing 5 + 2 should return 7.0.
     */
    @Test
    public void testASTInterfaceReferences() {
        AST number = new NumNode(9);

        AST expression = new BinopNode(
                "+", new NumNode(5), new NumNode(2));

        assertEquals(9.0, number.eval(), 0.000001);
        assertEquals(7.0, expression.eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests an unsupported operator.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * The test passes if IllegalArgumentException is thrown
     * with message "invalid token".
     *
     * Sample Tests:
     * 1. Evaluating a node with operator "%" should throw an exception.
     * 2. The exception message should be "invalid token".
     */
    @Test
    public void testInvalidOperator() {
        AST expression = new BinopNode(
                "%", new NumNode(5), new NumNode(2));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> expression.eval());

        assertEquals("invalid token", exception.getMessage());
    }
}