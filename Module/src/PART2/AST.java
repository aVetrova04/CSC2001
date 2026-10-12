package PART2;

/*
 * Purpose:
 * Defines the common interface for abstract syntax tree nodes.
 *
 * Input:
 * No input is required to define this interface.
 *
 * Output:
 * Declares eval(), which returns the numerical result of an expression.
 *
 * Sample Tests:
 * 1. A NumNode containing 5 should return 5 when eval() is called.
 * 2. A BinopNode representing 3 + 4 should return 7 when eval() is called.
 */
public interface AST {

    /*
     * Purpose:
     * Evaluates the expression represented by this AST node.
     *
     * Input:
     * No parameters.
     *
     * Output:
     * A double representing the result of evaluating this expression.
     *
     * Sample Tests:
     * 1. Evaluating a number node containing 5 should return 5.0.
     * 2. Evaluating a tree representing 2 + 3 should return 5.0.
     */
    double eval();
}