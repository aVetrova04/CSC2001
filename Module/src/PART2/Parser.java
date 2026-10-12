package PART2;

public class Parser {

    /*
     * Purpose:
     * Determines whether a token is a supported mathematical operator.
     *
     * Input:
     * @param token: String, the token to examine
     *
     * Output:
     * true if the token is +, -, *, /, or ^; false otherwise.
     *
     * Sample Tests:
     * 1. The token "+" should return true.
     * 2. The token "^" should return true.
     * 3. The token "a" should return false.
     * 4. The token "(" should return false.
     */
    private static boolean isOperator(String token) {
        return token.equals("+")
                || token.equals("-")
                || token.equals("*")
                || token.equals("/")
                || token.equals("^");
    }

    /*
     * Purpose:
     * Determines whether a token represents a number.
     *
     * Input:
     * @param token: String, the token to examine
     *
     * Output:
     * true if the token can be parsed as a double;
     * false otherwise.
     *
     * Sample Tests:
     * 1. The token "5" should return true.
     * 2. The token "2.5" should return true.
     * 3. The token "-3" should return true.
     * 4. The token "abc" should return false.
     */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    /*
     * Purpose:
     * Returns the precedence level of an operator.
     *
     * Input:
     * @param operator: String, the operator to examine
     *
     * Output:
     * 3 for exponentiation, 2 for multiplication or division,
     * and 1 for addition or subtraction.
     *
     * Exceptions:
     * Throws IllegalArgumentException with message
     * "invalid token" for an unsupported operator.
     *
     * Sample Tests:
     * 1. The precedence of "^" should be 3.
     * 2. The precedence of "*" should be 2.
     * 3. The precedence of "/" should be 2.
     * 4. The precedence of "+" should be 1.
     * 5. The precedence of "-" should be 1.
     */
    private static int precedence(String operator) {
        switch (operator) {
            case "^":
                return 3;

            case "*":
            case "/":
                return 2;

            case "+":
            case "-":
                return 1;

            default:
                throw new IllegalArgumentException("invalid token");
        }
    }

    /*
     * Purpose:
     * Determines whether an operator is right-associative.
     *
     * Input:
     * @param operator: String, the operator to examine
     *
     * Output:
     * true if the operator is ^; false otherwise.
     *
     * Sample Tests:
     * 1. The operator "^" should return true.
     * 2. The operator "+" should return false.
     * 3. The operator "*" should return false.
     */
    private static boolean isRightAssociative(String operator) {
        return operator.equals("^");
    }

    /*
     * Purpose:
     * Applies an operator to the top two AST expressions.
     * The right operand is popped first, followed by the left.
     * The resulting BinopNode is pushed onto the expression stack.
     *
     * Input:
     * @param expressions: ArrayStack<AST>, the expression stack
     * @param operator: String, the operator to apply
     *
     * Output:
     * No return value. The expression stack is updated.
     *
     * Exceptions:
     * Throws IllegalArgumentException with message
     * "insufficient operands" if fewer than two expressions exist.
     *
     * Sample Tests:
     * 1. Applying "+" to a stack containing 3 and 4 should
     *    replace those two expressions with a node for 3 + 4.
     * 2. Applying "-" to a stack containing 10 and 3 should
     *    create a node that evaluates to 7, not -7.
     * 3. Applying "+" when the stack contains only one expression
     *    should throw "insufficient operands".
     */
    private static void applyOperator(
            ArrayStack<AST> expressions, String operator) {

        if (expressions.size() < 2) {
            throw new IllegalArgumentException("insufficient operands");
        }

        AST right = expressions.pop();
        AST left = expressions.pop();

        AST result = new BinopNode(operator, left, right);
        expressions.push(result);
    }

    /*
     * Purpose:
     * Parses a postfix (Reverse Polish Notation) expression
     * into an abstract syntax tree.
     *
     * Input:
     * @param input: String, the space-separated postfix expression
     *
     * Output:
     * An AST representing the parsed expression.
     *
     * Exceptions:
     * "empty input" if input is null, empty, or whitespace only.
     * "invalid token" if a token is neither a number nor an operator.
     * "insufficient operands" if an operator lacks two operands.
     * "too many operands" if more than one expression remains.
     *
     * Sample Tests:
     * 1. "2 3 +" should evaluate to 5.
     * 2. "2 3 4 * +" should evaluate to 14.
     * 3. "10 3 -" should evaluate to 7.
     * 4. "8 4 /" should evaluate to 2.
     * 5. "2 3 ^" should evaluate to 8.
     * 6. "2.5 1.5 +" should evaluate to 4.
     * 7. An empty string should throw "empty input".
     * 8. "2 a +" should throw "invalid token".
     * 9. "2 +" should throw "insufficient operands".
     * 10. "2 3 4 +" should throw "too many operands".
     */
    public static AST parsePostfix(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }

        String[] tokens = input.trim().split("\\s+");
        ArrayStack<AST> expressions = new ArrayStack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                double value = Double.parseDouble(token);
                expressions.push(new NumNode(value));

            } else if (isOperator(token)) {
                applyOperator(expressions, token);

            } else {
                throw new IllegalArgumentException("invalid token");
            }
        }

        if (expressions.isEmpty()) {
            throw new IllegalArgumentException("insufficient operands");
        }

        if (expressions.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }

        return expressions.pop();
    }

    /*
     * Purpose:
     * Parses an infix expression into an abstract syntax tree
     * using the shunting-yard algorithm.
     *
     * Input:
     * @param input: String, the space-separated infix expression
     *
     * Output:
     * An AST representing the parsed expression.
     *
     * Exceptions:
     * "empty input" if input is null, empty, or whitespace only.
     * "invalid token" if a token is not recognized.
     * "insufficient operands" if an operator lacks operands.
     * "too many operands" if extra expressions remain.
     * "mismatched open paren" if an opening parenthesis is unmatched.
     * "mismatched close paren" if a closing parenthesis is unmatched.
     *
     * Sample Tests:
     * 1. "2 + 3" should evaluate to 5.
     * 2. "2 + 3 * 4" should evaluate to 14.
     * 3. "2 * ( 3 + 4 )" should evaluate to 14.
     * 4. "2 * 4 ^ 3" should evaluate to 128.
     * 5. "2 ^ 3 ^ 2" should evaluate to 512.
     * 6. "8 / 4 / 2" should evaluate to 1.
     * 7. "2.5 + 1.5" should evaluate to 4.
     * 8. An empty string should throw "empty input".
     * 9. "2 a +" should throw "invalid token".
     * 10. "2 +" should throw "insufficient operands".
     * 11. "( 2 + 3" should throw "mismatched open paren".
     * 12. "2 + 3 )" should throw "mismatched close paren".
     */
    public static AST parseInfix(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }

        String[] tokens = input.trim().split("\\s+");

        ArrayStack<AST> expressions = new ArrayStack<>();
        ArrayStack<String> operators = new ArrayStack<>();

        int openParenCount = 0;

        for (String token : tokens) {

            if (isNumber(token)) {
                expressions.push(
                        new NumNode(Double.parseDouble(token)));

            } else if (token.equals("(")) {
                operators.push(token);
                openParenCount++;

            } else if (token.equals(")")) {

                if (openParenCount == 0) {
                    throw new IllegalArgumentException(
                            "mismatched close paren");
                }

                while (!operators.isEmpty()
                        && !operators.peek().equals("(")) {

                    String operator = operators.pop();
                    applyOperator(expressions, operator);
                }

                if (operators.isEmpty()) {
                    throw new IllegalArgumentException(
                            "mismatched close paren");
                }

                operators.pop();
                openParenCount--;

            } else if (isOperator(token)) {

                while (!operators.isEmpty()
                        && !operators.peek().equals("(")) {

                    String topOperator = operators.peek();

                    boolean higherPrecedence =
                            precedence(topOperator) > precedence(token);

                    boolean equalPrecedenceAndLeftAssociative =
                            precedence(topOperator) == precedence(token)
                                    && !isRightAssociative(token);

                    if (higherPrecedence
                            || equalPrecedenceAndLeftAssociative) {

                        operators.pop();
                        applyOperator(expressions, topOperator);

                    } else {
                        break;
                    }
                }

                operators.push(token);

            } else {
                throw new IllegalArgumentException("invalid token");
            }
        }

        if (openParenCount > 0) {
            throw new IllegalArgumentException("mismatched open paren");
        }

        while (!operators.isEmpty()) {
            String operator = operators.pop();
            applyOperator(expressions, operator);
        }

        if (expressions.isEmpty()) {
            throw new IllegalArgumentException("insufficient operands");
        }

        if (expressions.size() > 1) {
            throw new IllegalArgumentException("too many operands");
        }

        return expressions.pop();
    }
}