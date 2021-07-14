package dendron.treenodes.values;

import dendron.treenodes.interfaces.ExpressionNode;

import java.io.PrintWriter;
import java.util.Map;

public class Variable implements ExpressionNode {

    // Member Variables
    private String name;

    /**
     * Set the name of this new Variable. Note that it is not wrong for more than one Variable node to refer to the
     * same variable. Its actual value is stored in a symbol table.
     *
     * @param name the name of this variable
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Evaluate a variable by fetching its value
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this variable's current value in the symbol table
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return symTab.get(name);
    }

    /**
     * Print on standard output the Variable's name.
     */
    @Override
    public void infixDisplay() {
        System.out.print(name);
    }

    /**
     * Emit onto a stream the text of the Soros assembly language instructions that, when executed, pushes the
     * value of the variable on the stack.
     *
     * @param out the output stream for the compiled code — usually System.out;
     */
    @Override
    public void compile(PrintWriter out) {
        out.write("LOAD " + name);
    }
}
