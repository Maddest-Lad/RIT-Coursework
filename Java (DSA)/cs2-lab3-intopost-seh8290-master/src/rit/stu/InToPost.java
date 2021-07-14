package rit.stu;

import rit.cs.Queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A program that converts an infix expression to postfix.<br>
 * <br>
 * Usage: java InToPost filename<br>
 * <br>
 * For example, prog1.txt, containing infix expressions (one per line):<br>
 * <br>
 * A + B<br>
 * A + B * C<br>
 * A + B * C + D<br>
 * ( A + B ) * C<br>
 * ( A + B ) / ( C - D )<br>
 * ( ( A + B ) * C - ( D - E ) )<br>
 * <br>
 * When run: java InToPost prog1.txt:<br>
 * <br>
 * InToPost: converting expressions from infix to postfix...<br>
 * [A, +, B]<br>
 * [A, +, B, *, C]<br>
 * [A, +, B, *, C, +, D]<br>
 * [(, A, +, B, ), *, C]<br>
 * [(, A, +, B, ), /, (, C, -, D, )]<br>
 * [(, (, A, +, B, ), *, C, -, (, D, -, E, ), )]<br>
 * InToPost: emitting postfix expressions...<br>
 * A B +<br>
 * A B C * +<br>
 * A B C * + D +<br>
 * A B + C *<br>
 * A B + C D - /<br>
 * A B + C * D E - -<br>
 * <br>
 *
 * @author RIT CS
 * @author YOUR Sam Harris
 */
public class InToPost {
    /**
     * The add operator
     */
    public final static String ADD = "+";
    /**
     * The subtract operator
     */
    public final static String SUBTRACT = "-";
    /**
     * The multiply operator
     */
    public final static String MULTIPLY = "*";
    /**
     * The divide operator
     */
    public final static String DIVIDE = "/";
    /**
     * The open parentheses operator
     */
    public final static String OPEN_PAREN = "(";
    /**
     * The close parentheses operator
     */
    public final static String CLOSE_PAREN = ")";
    /**
     * The list of converted postfix expressions
     */
    private final List<Queue<String>> expressions;
    /**
     * The name of the infix source file
     */
    private final String srcFile;
    // Set for ease of checking operands

    private final Map<String, String> operands = Map.of(ADD, "+", SUBTRACT, "-", MULTIPLY, "*", DIVIDE, "/");

    /**
     * A dictionary that maps operators (strings) to their precedence level
     * (integer) from highest (3) to lowest (1):
     * *, /: 3
     * +, -: 2
     * (: 1
     */
    private final Map<String, Integer> precedence;

    /**
     * Create a new IntoPost object.
     *
     * @param filename The name of the infix source file
     */
    public InToPost(String filename) {
        this.expressions = new LinkedList<>();
        this.srcFile = filename;

        /* populate the precedence map */
        this.precedence = new HashMap<>();
        this.precedence.put(MULTIPLY, 3);
        this.precedence.put(DIVIDE, 3);
        this.precedence.put(ADD, 2);
        this.precedence.put(SUBTRACT, 2);
        this.precedence.put(OPEN_PAREN, 1);
    }

    /**
     * The main program takes the source file of infix expressions, converts
     * them to postfix, and displays them.
     *
     * @param args command line arguments
     * @throws FileNotFoundException if the source file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1)
        {
            System.out.println("Usage: java InToPost.py source-file");
            return;
        }

        InToPost inToPost = new InToPost(args[0]);
        System.out.println("InToPost: converting expressions from infix to postfix...");
        inToPost.convert();
        System.out.println("InToPost: emitting postfix expressions...");
        inToPost.emit();
    }

    /**
     * A private utility method that can be used by the private convert
     * method when dealing with the precedence of math operators on
     * the stack.  Indicates whether the top token on stack has greater
     * than or equal precedence than the current token.  For example:
     * greaterEqualPrecedence("+", "*") -> false
     * greaterEqualPrecedence("*", "+") -> true
     * greaterEqualPrecedence("+", "-") -> true
     * greaterEqualPrecedence("(", "/") -> false
     *
     * @param token the current math token, e.g. "+"
     * @param top   the top token to compare to, e.g. "*"
     * @return true if top has greater than or equal precedence than
     * token, false otherwise
     */
    private boolean greaterEqualPrecedence(String top, String token) {
        return this.precedence.get(top) >= this.precedence.get(token);
    }


    /**
     * A private helper conversion function that takes a list of tokens
     * (strings) in infix form and converts them to a list of tokens
     * (strings) in postfix form.
     * <p>
     * For example:
     * tokens = ["A", "+", "B"]
     * postfix = ["A", "B", "+"]
     * <p>
     * Note, this method must use either StackList<E> or StackNode<E>
     * to represent the stack structure in the conversion algorithm.
     *
     * @param tokens the list of tokens (strings) in infix form
     * @return a new queue of tokens (strings) in postfix form
     */
    private Queue<String> convert(List<String> tokens) {

        String regex = "^[a-zA-Z0-9]*$";
        Pattern operators = Pattern.compile(regex);

        //Create an empty stack, opstack, for keeping operators.
        StackNode opStack = new StackNode();

        //Create an empty queue, postfix, for the output.
        QueueNode postfix = new QueueNode();

        // Scan the token list from left to right
        for (String t : tokens)
        {
            //If the token is an operand, append it to the end of postfix
            Matcher alphaNum = operators.matcher(t);
            if (alphaNum.matches())
            {
                postfix.enqueue(t);
            }
            //If the token is a left parenthesis, push it on to opstack.
            else if (t.equals(OPEN_PAREN))
            {
                // Breaks Everything
                //opStack.push(t);
            }
            //If the token is a right parenthesis,
            else if (t.equals(CLOSE_PAREN))
            {
                var current = "";
                // pop opstack until the corresponding left parenthesis is removed.
                // Append each popped operator to the end of postfix in the order they are removed from the stack.
                while (!opStack.empty())
                {
                    current = (String) opStack.pop();
                    if (current.equals(OPEN_PAREN))
                    {
                        break;
                    } else
                    {
                        postfix.enqueue(current);
                    }
                }
            }

            // If the token is an operator, *, /, +, or -, push it on opstack.
            else if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/"))
            {
                if (opStack.empty())
                {
                    opStack.push(t);
                } else
                {
                    //first remove any operators already on opstack that have higher or equal precedence and append them to postfix
                    var current = opStack.top();
                    while (!opStack.empty() && greaterEqualPrecedence((String) current, t))
                    {
                        current = opStack.pop();
                        postfix.enqueue(current);
                    }
                    opStack.push(t);
                }
            }
        }


        for (int i = 0; i < opStack.size; i++)

        {
            var current = opStack.pop();

            postfix.enqueue(current);
        }

        return postfix;
    }

    /**
     * The public conversion function that converts all infix expressions
     * in the source file (one per line), into postfix expressions.
     *
     * @throws FileNotFoundException if the file is not found
     */
    public void convert() throws FileNotFoundException {
        Scanner in = new Scanner(new File(this.srcFile));
        while (in.hasNext())
        {
            // convert the line into a list of strings, e.g.
            //      line = "A B +"
            //      tokens = ["A", "B", "+"]
            List<String> tokens = Arrays.asList(in.nextLine().split(" "));
            // if the line isn't empty, convert it to postfix and add it to
            // the list of expressions
            if (tokens.size() > 0)
            {
                System.out.println(tokens);
                this.expressions.add(convert(tokens));
            }
        }
        in.close();  // <3 Jim
    }

    /**
     * Display the converted postfix expressions.
     */
    public void emit() {
        for (Queue<String> expression : this.expressions)
        {
            while (!expression.empty())
            {
                System.out.print(expression.dequeue() + " ");
            }
            System.out.println();
        }
    }
}
