package dendron.treenodes.actionNodes;

import dendron.treenodes.interfaces.ActionNode;
import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Map;

public class Print implements ActionNode {

    // Member Variables
    private final ExpressionNode printee;

    /**
     * Set up a Print node.
     *
     * @param printee the expression to be evaluated and printed
     */
    public Print(ExpressionNode printee) {
        this.printee = printee;
    }

    /**
     * Evaluate the expression and display the result on the console. Precede it with three equal signs so it stands
     * out a little.
     *
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        System.out.println("=== " + printee.evaluate(symTab));
    }

    /**
     * Show this statement on standard output as the word "Print" followed by the infix form of the expression.
     */
    @Override
    public void infixDisplay() {
        System.out.print("Print ");
        printee.infixDisplay();
        System.out.println();
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, perform a print action.
     *
     * @param out the output stream for the compiled code — usually System.out
     */
    @Override
    public void compile(PrintWriter out) {
        // First We Push Printee To The Stack, So That Our Print Instruction Then Consumes Said Value For Printing
        printee.compile(out);
        out.write("\n");
        // PRINT - Pop one value off the stack, and display it on its own line on the console, preceded by "*** ".
        out.write("PRINT");
        out.write("\n");
    }
}
