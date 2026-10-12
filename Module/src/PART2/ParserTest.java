package PART2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParserTest {

    /*
     * Purpose:
     * Tests postfix addition.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 5.0.
     *
     * Sample Tests:
     * 1. Parsing "2 3 +" should represent 2 + 3.
     * 2. Evaluating the expression should return 5.0.
     */
    @Test
    public void testPostfixAddition() {
        assertEquals(5.0, Parser.parsePostfix("2 3 +").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix subtraction and operand order.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 7.0.
     *
     * Sample Tests:
     * 1. Parsing "10 3 -" should represent 10 - 3.
     * 2. The result should be 7.0, not -7.0.
     */
    @Test
    public void testPostfixSubtraction() {
        assertEquals(7.0, Parser.parsePostfix("10 3 -").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix multiplication.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 12.0.
     *
     * Sample Tests:
     * 1. Parsing "3 4 *" should return 12.0.
     */
    @Test
    public void testPostfixMultiplication() {
        assertEquals(12.0, Parser.parsePostfix("3 4 *").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix division.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 4.0.
     *
     * Sample Tests:
     * 1. Parsing "8 2 /" should return 4.0.
     */
    @Test
    public void testPostfixDivision() {
        assertEquals(4.0, Parser.parsePostfix("8 2 /").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix exponentiation.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 8.0.
     *
     * Sample Tests:
     * 1. Parsing "2 3 ^" should return 8.0.
     */
    @Test
    public void testPostfixExponentiation() {
        assertEquals(8.0, Parser.parsePostfix("2 3 ^").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests a postfix expression with multiple operators.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 14.0.
     *
     * Sample Tests:
     * 1. "2 3 4 * +" should multiply 3 by 4 first.
     * 2. The result should then be added to 2.
     * 3. The final result should be 14.0.
     */
    @Test
    public void testPostfixCombinedOperations() {
        assertEquals(14.0, Parser.parsePostfix("2 3 4 * +").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix expressions with decimal operands.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 4.0.
     *
     * Sample Tests:
     * 1. "2.5 1.5 +" should accept decimal operands.
     * 2. The result should be 4.0.
     */
    @Test
    public void testPostfixDecimalOperands() {
        assertEquals(4.0, Parser.parsePostfix("2.5 1.5 +").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix expressions with negative numbers.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to -3.0.
     *
     * Sample Tests:
     * 1. "-5 2 +" should recognize -5 as a number.
     * 2. Adding 2 should produce -3.0.
     */
    @Test
    public void testPostfixNegativeNumber() {
        assertEquals(-3.0, Parser.parsePostfix("-5 2 +").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests postfix expressions containing a single number.
     *
     * Input/Output:
     * No parameters. Verifies that the number is returned unchanged.
     *
     * Sample Tests:
     * 1. Parsing "42" should return an AST.
     * 2. Evaluating that AST should return 42.0.
     */
    @Test
    public void testPostfixSingleNumber() {
        assertEquals(42.0, Parser.parsePostfix("42").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests null input for postfix parsing.
     *
     * Input/Output:
     * No parameters. Expects IllegalArgumentException with "empty input".
     *
     * Sample Tests:
     * 1. Passing null should throw IllegalArgumentException.
     * 2. The message should be "empty input".
     */
    @Test
    public void testPostfixNullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePostfix(null));

        assertEquals("empty input", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests empty and whitespace-only postfix input.
     *
     * Input/Output:
     * No parameters. Both inputs should be rejected as empty.
     *
     * Sample Tests:
     * 1. An empty string should produce "empty input".
     * 2. A string containing only spaces should produce "empty input".
     */
    @Test
    public void testPostfixEmptyInput() {
        assertEquals("empty input",
                assertThrows(IllegalArgumentException.class,
                        () -> Parser.parsePostfix("")).getMessage());

        assertEquals("empty input",
                assertThrows(IllegalArgumentException.class,
                        () -> Parser.parsePostfix("   ")).getMessage());
    }

    /*
     * Purpose:
     * Tests invalid tokens in postfix expressions.
     *
     * Input/Output:
     * No parameters. Expects IllegalArgumentException with "invalid token".
     *
     * Sample Tests:
     * 1. "2 a +" contains an invalid token, a.
     * 2. The parser should reject it.
     */
    @Test
    public void testPostfixInvalidToken() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 a +"));

        assertEquals("invalid token", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests insufficient operands in postfix notation.
     *
     * Input/Output:
     * No parameters. Expects "insufficient operands".
     *
     * Sample Tests:
     * 1. "2 +" has only one operand for the plus operator.
     * 2. The parser should throw IllegalArgumentException.
     */
    @Test
    public void testPostfixInsufficientOperands() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 +"));

        assertEquals("insufficient operands", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests an operator with no operands in postfix notation.
     *
     * Input/Output:
     * No parameters. Expects "insufficient operands".
     *
     * Sample Tests:
     * 1. "+" has no operands.
     * 2. The parser should reject the expression.
     */
    @Test
    public void testPostfixNoOperands() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePostfix("+"));

        assertEquals("insufficient operands", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests too many operands in postfix notation.
     *
     * Input/Output:
     * No parameters. Expects "too many operands".
     *
     * Sample Tests:
     * 1. "2 3 4 +" leaves an extra expression on the stack.
     * 2. The parser should reject the expression.
     */
    @Test
    public void testPostfixTooManyOperands() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parsePostfix("2 3 4 +"));

        assertEquals("too many operands", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests infix addition.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 5.0.
     *
     * Sample Tests:
     * 1. "2 + 3" should evaluate to 5.0.
     */
    @Test
    public void testInfixAddition() {
        assertEquals(5.0, Parser.parseInfix("2 + 3").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests infix subtraction.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 7.0.
     *
     * Sample Tests:
     * 1. "10 - 3" should evaluate to 7.0.
     */
    @Test
    public void testInfixSubtraction() {
        assertEquals(7.0, Parser.parseInfix("10 - 3").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests infix multiplication.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 12.0.
     *
     * Sample Tests:
     * 1. "3 * 4" should evaluate to 12.0.
     */
    @Test
    public void testInfixMultiplication() {
        assertEquals(12.0, Parser.parseInfix("3 * 4").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests infix division.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 4.0.
     *
     * Sample Tests:
     * 1. "8 / 2" should evaluate to 4.0.
     */
    @Test
    public void testInfixDivision() {
        assertEquals(4.0, Parser.parseInfix("8 / 2").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests infix exponentiation.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 8.0.
     *
     * Sample Tests:
     * 1. "2 ^ 3" should evaluate to 8.0.
     */
    @Test
    public void testInfixExponentiation() {
        assertEquals(8.0, Parser.parseInfix("2 ^ 3").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests multiplication precedence over addition.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 14.0.
     *
     * Sample Tests:
     * 1. "2 + 3 * 4" should multiply 3 by 4 first.
     * 2. The final result should be 14.0.
     */
    @Test
    public void testInfixPrecedence() {
        assertEquals(14.0, Parser.parseInfix("2 + 3 * 4").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests parentheses overriding operator precedence.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 14.0.
     *
     * Sample Tests:
     * 1. "2 * ( 3 + 4 )" should add 3 and 4 first.
     * 2. Multiplying that result by 2 should produce 14.0.
     */
    @Test
    public void testInfixParentheses() {
        assertEquals(14.0,
                Parser.parseInfix("2 * ( 3 + 4 )").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests nested parentheses.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 22.0.
     *
     * Sample Tests:
     * 1. "2 * ( 3 + ( 4 * 2 ) )" evaluates the inner multiplication first.
     * 2. The complete expression should evaluate to 22.0.
     */
    @Test
    public void testInfixNestedParentheses() {
        assertEquals(22.0,
                Parser.parseInfix("2 * ( 3 + ( 4 * 2 ) )").eval(),
                0.000001);
    }

    /*
     * Purpose:
     * Tests exponentiation precedence over multiplication.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 128.0.
     *
     * Sample Tests:
     * 1. "2 * 4 ^ 3" should evaluate 4 ^ 3 first.
     * 2. The final result should be 2 * 64 = 128.
     */
    @Test
    public void testInfixExponentiationPrecedence() {
        assertEquals(128.0,
                Parser.parseInfix("2 * 4 ^ 3").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests right-associativity of exponentiation.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 512.0.
     *
     * Sample Tests:
     * 1. "2 ^ 3 ^ 2" should be interpreted as 2 ^ (3 ^ 2).
     * 2. The final result should be 512.0.
     */
    @Test
    public void testInfixRightAssociativity() {
        assertEquals(512.0,
                Parser.parseInfix("2 ^ 3 ^ 2").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests left-associativity of division.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 1.0.
     *
     * Sample Tests:
     * 1. "8 / 4 / 2" should be interpreted as (8 / 4) / 2.
     * 2. The final result should be 1.0.
     */
    @Test
    public void testInfixLeftAssociativity() {
        assertEquals(1.0,
                Parser.parseInfix("8 / 4 / 2").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests decimal operands in infix notation.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to 4.0.
     *
     * Sample Tests:
     * 1. "2.5 + 1.5" should accept decimal numbers.
     * 2. The result should be 4.0.
     */
    @Test
    public void testInfixDecimalOperands() {
        assertEquals(4.0,
                Parser.parseInfix("2.5 + 1.5").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests negative operands in infix notation.
     *
     * Input/Output:
     * No parameters. Verifies that the expression evaluates to -3.0.
     *
     * Sample Tests:
     * 1. "-5 + 2" should recognize -5 as a number.
     * 2. The result should be -3.0.
     */
    @Test
    public void testInfixNegativeNumber() {
        assertEquals(-3.0,
                Parser.parseInfix("-5 + 2").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests a single numeric operand in infix notation.
     *
     * Input/Output:
     * No parameters. Verifies that the number is returned unchanged.
     *
     * Sample Tests:
     * 1. Parsing "42" should return an AST.
     * 2. Evaluating the AST should return 42.0.
     */
    @Test
    public void testInfixSingleNumber() {
        assertEquals(42.0, Parser.parseInfix("42").eval(), 0.000001);
    }

    /*
     * Purpose:
     * Tests null input for infix parsing.
     *
     * Input/Output:
     * No parameters. Expects IllegalArgumentException with "empty input".
     *
     * Sample Tests:
     * 1. Passing null should throw IllegalArgumentException.
     * 2. The exception message should be "empty input".
     */
    @Test
    public void testInfixNullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix(null));

        assertEquals("empty input", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests empty and whitespace-only infix input.
     *
     * Input/Output:
     * No parameters. Both inputs should produce "empty input".
     *
     * Sample Tests:
     * 1. An empty string should be rejected.
     * 2. A string containing only spaces should be rejected.
     */
    @Test
    public void testInfixEmptyInput() {
        assertEquals("empty input",
                assertThrows(IllegalArgumentException.class,
                        () -> Parser.parseInfix("")).getMessage());

        assertEquals("empty input",
                assertThrows(IllegalArgumentException.class,
                        () -> Parser.parseInfix("   ")).getMessage());
    }

    /*
     * Purpose:
     * Tests invalid tokens in infix notation.
     *
     * Input/Output:
     * No parameters. Expects IllegalArgumentException with "invalid token".
     *
     * Sample Tests:
     * 1. "2 a +" contains the invalid token "a".
     * 2. The parser should reject it.
     */
    @Test
    public void testInfixInvalidToken() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix("2 a +"));

        assertEquals("invalid token", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests insufficient operands in infix notation.
     *
     * Input/Output:
     * No parameters. Expects "insufficient operands".
     *
     * Sample Tests:
     * 1. "2 +" contains only one operand for addition.
     * 2. The parser should reject the expression.
     */
    @Test
    public void testInfixInsufficientOperands() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix("2 +"));

        assertEquals("insufficient operands", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests too many operands in infix notation.
     *
     * Input/Output:
     * No parameters. Expects "too many operands".
     *
     * Sample Tests:
     * 1. "2 3 4 +" contains too many operands.
     * 2. The parser should reject the expression.
     */
    @Test
    public void testInfixTooManyOperands() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix("2 3 4 +"));

        assertEquals("too many operands", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests an unmatched opening parenthesis.
     *
     * Input/Output:
     * No parameters. Expects "mismatched open paren".
     *
     * Sample Tests:
     * 1. "( 2 + 3" has an opening parenthesis that is never closed.
     * 2. The parser should report "mismatched open paren".
     */
    @Test
    public void testMismatchedOpenParen() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix("( 2 + 3"));

        assertEquals("mismatched open paren", exception.getMessage());
    }

    /*
     * Purpose:
     * Tests an unmatched closing parenthesis.
     *
     * Input/Output:
     * No parameters. Expects "mismatched close paren".
     *
     * Sample Tests:
     * 1. "2 + 3 )" has a closing parenthesis without an opening one.
     * 2. The parser should report "mismatched close paren".
     */
    @Test
    public void testMismatchedCloseParen() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Parser.parseInfix("2 + 3 )"));

        assertEquals("mismatched close paren", exception.getMessage());
    }
}