package dendron.treenodes.operations;

import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class UnaryOperation implements ExpressionNode {

    // Unary Operators
    public static final String NEG = "_";
    public static final String SQRT = "%";

    // Collection of Operators
    public static final Map<String, String> OPERATORS = Map.of("_", NEG, "%", SQRT);
    public static final Map<String, String> SOROS = Map.of(NEG, "NEG", SQRT, "SQRT");

    // Member Data
    private String operator;
    private ExpressionNode expr;

    /**
     * Create a new UnaryOperation node
     * Precondition OPERATORS.contains(operator), expr != null
     *
     * @param operator the string rep. of the operation
     * @param expr     the operand
     */
    public UnaryOperation(String operator, ExpressionNode expr) {
        this.operator = operator;
        this.expr = expr;
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     *
     * @param symTab symbol table, needed to evaluate the child tree
     * @return the result of the computation
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return switch (operator)
            {
                case NEG -> -expr.evaluate(symTab);
                case SQRT -> (int)Math.sqrt(expr.evaluate(symTab));
                default -> 0;
            };
    }

    /**
     * Print, on standard output, the infixDisplay of the child nodes preceded by the operator and without an
     * intervening blank.
     */
    @Override
    public void infixDisplay() {
        System.out.print(operator);
        expr.infixDisplay();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, computes the
     * result of this operation.
     *
     * @param out the output stream for the compiled code — usually System.out;
     */
    @Override
    public void compile(PrintWriter out) {
        expr.compile(out);
        out.write("\n" +SOROS.get(operator));
    }
}
