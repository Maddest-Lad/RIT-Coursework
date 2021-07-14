package dendron.treenodes.operations;

import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;


public class BinaryOperation implements ExpressionNode {

    // The Four Binary Operators
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";

    // Collection of Operators
    public static final Map<String, String> OPERATORS = Map.of("+", ADD, "-", SUB, "*", MUL, "/", DIV);
    public static final Map<String, String> SOROS = Map.of(ADD, "ADD", SUB, "SUB", MUL, "MUL", DIV, "DIV");

    // Member Data
    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    /**
     * Create a new BinaryOperation node.
     * Precondition OPERATORS.contains(operator), leftChild != null, rightChild != null
     *
     * @param operator   the string rep. of the operation
     * @param leftChild  the left operand
     * @param rightChild the right operand
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Compute the result of evaluating both operands and applying the operator to them.
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return switch (operator)
            {
                case ADD -> leftChild.evaluate(symTab) + rightChild.evaluate(symTab);
                case SUB -> leftChild.evaluate(symTab) - rightChild.evaluate(symTab);
                case MUL -> leftChild.evaluate(symTab) * rightChild.evaluate(symTab);
                case DIV -> leftChild.evaluate(symTab) / rightChild.evaluate(symTab);
                default -> 0;
            };
    }

    /**
     * Print, on standard output, the infixDisplay of the two child nodes separated by the operator and
     * surrounded by parentheses. Blanks are inserted throughout.
     */
    @Override
    public void infixDisplay() {
        leftChild.infixDisplay();
        System.out.print(" " + operator + " ");
        rightChild.infixDisplay();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, computes the
     * result of this operation.
     *
     * @param out the output stream for the compiled code &mdash;
     */
    @Override //TODO Fix This Boy
    public void compile(PrintWriter out) {
        leftChild.compile(out);
        out.write("\n");
        rightChild.compile(out);
        out.write("\n");
        out.write(SOROS.get(operator));
    }
}