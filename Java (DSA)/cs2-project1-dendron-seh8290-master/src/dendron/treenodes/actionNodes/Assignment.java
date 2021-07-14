package dendron.treenodes.actionNodes;

import dendron.treenodes.interfaces.ActionNode;
import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Map;

public class Assignment implements ActionNode {

    public String ident;
    private final ExpressionNode rhs;

    /**
     * Set up an Assignment node. Note that the identifier is not turned into a Variable node.
     * The reason is that the variable's value is not needed; instead it is a destination for a computation.
     * This use is not compatible with Variable's mission.
     *
     * @param ident the name of the variable that is getting a new value
     * @param rhs the expression on the "right-hand side" (RHS) of the assignment statement
     */
    public Assignment(String ident, ExpressionNode rhs) {
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluate the RHS expression and assign the result value to the variable.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        symTab.put(ident, rhs.evaluate(symTab)); // This Should Overwrite Which is Expected Behaviour
    }

    /**
     * Show this assignment on standard output as a variable followed by an assignment arrow
     * ie (":=") followed by the infix form of the RHS expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print(ident + " = ");
        rhs.infixDisplay();
        System.out.println();
    }

    /** Emit onto a stream the text of the Soros assembly language instructions that, when executed, perform an assignment.
     * @param out the output stream for the compiled code — usually System.out
     */
    @Override
    public void compile(PrintWriter out) {
        rhs.compile(out);
        out.write("\n" + "STORE " + ident + "\n");
    }
}
