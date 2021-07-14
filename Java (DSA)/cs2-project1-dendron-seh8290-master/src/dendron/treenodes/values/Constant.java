package dendron.treenodes.values;

import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Map;

public class Constant implements ExpressionNode {

    // Member Data
    private int value;

    /**
     * Store the integer value in this new Constant.
     *
     * @param value the integer it will hold
     */
    public Constant(int value) {
        this.value = value;
    }

    /**
     * Evaluate the constant
     *
     * @param symTab unused
     * @return this Constant's value
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return value;
    }

    /**
     * Print this Constant's value on standard output.
     */
    @Override
    public void infixDisplay() {
        System.out.print(value);
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, saves the
     * constant on the stack.
     *
     * @param out the output stream for the compiled code — usually System.out
     */
    @Override
    public void compile(PrintWriter out) {
        // PUSH constant - Push the integer constant onto the stack.
        out.write("PUSH " + value);
    }
}
