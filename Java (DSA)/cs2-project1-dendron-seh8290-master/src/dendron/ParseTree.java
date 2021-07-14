package dendron;

import dendron.treenodes.actionNodes.Assignment;
import dendron.treenodes.actionNodes.Print;
import dendron.treenodes.interfaces.ActionNode;
import dendron.treenodes.interfaces.ExpressionNode;
import dendron.treenodes.operations.BinaryOperation;
import dendron.treenodes.operations.UnaryOperation;
import dendron.treenodes.values.Constant;
import dendron.treenodes.values.Variable;

import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTree {

    // Constants
    public static final String ASSIGN = ":=";
    public static final String PRINT = "#";

    // Member Variables
    List<ActionNode> ActionNodes;

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     *
     * @param tokens the token list (Strings). This list may be destroyed
     *               by this constructor.
     */
    public ParseTree(List<String> tokens) {

        // Split Tokens Into Sub-Lists of Instructions
        List<LinkedList<String>> instructions = new LinkedList<>();

        for(String token : tokens) {

            // When we reach a new Assignment or Print, Create Another Sub-List
            if(token.equals(ASSIGN) || token.equals(PRINT)) {
                instructions.add(new LinkedList<>());
            }

            // Always Add Tokens To The Latest (ie Last) Sub-List
            instructions.get(instructions.size()-1).add(token);
        }

        //instructions.forEach(System.out::println); // Fancy Printing is Fun,

        // Recursively Call Parse To Build the Action and Expression Nodes
        List<ActionNode> parseTrees = new LinkedList<>();

        // It's Worth Noting, That For All Further Uses Of Tokens, Both Here and In Parse,
        // That I Use The Linkedlist.Pop() Method to Keep Track of Where I am In The Processing Of Each Command
        for(LinkedList<String> list : instructions) {

            String operator = list.pop();

            if(operator.equals(ASSIGN)) {
                parseTrees.add(new Assignment(list.pop(), parse(list)));
            }

            // Technically Shouldn't Need To be An Else If, But Safe > Sorry
            else if (operator.equals(PRINT)) {
                parseTrees.add(new Print(parse(list)));
            }

            // Error Handling
            else {
                Errors.report(Errors.Type.ILLEGAL_VALUE, list.pop());
                break;
            }
        }

        this.ActionNodes = parseTrees;
    }

    // Regex
    private boolean isInteger(String token) {
        // I know y'all said no Try/Catch, but that was regarding error handling and not sketchy type checking, sue me
        try {Integer.parseInt(token); return true;}
        catch( Exception e ) { return false;}
    }

    // Regex
    private boolean isVariable(String token) {
        Pattern p = Pattern.compile("^[a-zA-Z].*");
        Matcher m = p.matcher(token);

        return m.matches();
    }

    private ExpressionNode parse(LinkedList<String> tokens) {
        String current = tokens.peek();

        if(BinaryOperation.OPERATORS.containsKey(current)) {
            return new BinaryOperation(tokens.pop(), parse(tokens), parse(tokens));
        }

        else if (UnaryOperation.OPERATORS.containsKey(current)) {
            return new UnaryOperation(tokens.pop(), parse(tokens));
        }

        else if (isVariable(current)) {
            return new Variable(tokens.pop());
        }

        else if (isInteger(current)) {
            return new Constant(Integer.parseInt(tokens.pop()));
        }

        // Error Handling
        Errors.report(Errors.Type.PREMATURE_END, current);
        return null;
    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     *
     * @see ActionNode#infixDisplay()
     */
    public void displayProgram() {
        System.out.println("The Program, with expressions in infix notation");
        this.ActionNodes.forEach(ActionNode::infixDisplay);
    }

    /**
     * Run the program represented by the tree directly
     *
     * @see ActionNode#execute(Map)
     */
    public void interpret() {
        System.out.println("\n" + "Interpreting the parse tree...");

        Map<String, Integer> symTab = new HashMap<>();

        for(ActionNode a : this.ActionNodes) {
            a.execute(symTab);
        }

        // Following CS Dept Print Specs
        System.out.println("Interpretation Complete" + "\n");
        System.out.println("Symbol Table Contents" + "\n" + "=====================");

        // Print Out SymTab Values
        symTab.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println();
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     *
     * @param out where to print the Soros instruction list
     */
    public void compileTo(PrintWriter out) {

        for(ActionNode a : this.ActionNodes) {
            a.compile(out);
        }
    }
}
