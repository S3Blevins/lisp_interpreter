import java.io.FileNotFoundException;
import java.util.*;
import static java.lang.System.exit;

/**
 * @name Sterling Blevins
 *
 * @file Interface.java
 * @description The interface for the Interpreter
 *
 * @date 5/3/17
 */
public class Interface {
    /**
     * Main will take the input and repeat a prompt. The input string is processed so that the parenthesis do not account
     * for spaces. The input is separated by spaces, after the parenthesis are removed and tokenized and inserted into an
     * array. The array is then processed.
     *
     * The program will exit with the term "quit", and can be changed from static to dynamic, with the default being static.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();
        int mapFlag = 0;

        while(true) {
            ArrayList<List<Token>> list = new ArrayList<List<Token>>();
            Stack<Token> stack = new Stack<Token>();
            try {
                System.out.print("Prompt> ");

                String input = scan.nextLine();

                // Fixes the malformed spaces in the commands put into the interpreter
                // preceding parenthesis replacement
                input = input.replaceAll("(\\s+([(]))", "(");
                input = input.replaceAll("(\\s+([)]))", ")");
                //suceeding parenthesis replacement
                input = input.replaceAll("(([(])+\\s)" , "(");
                input = input.replaceAll("(([)])+\\s)", ")");


                // removes the parenthesis
                String[] splitString = input.split("[()]");
                // splits string into an array separated by the parenthesis
                List<String> split = new ArrayList<String>(Arrays.asList(splitString));
                // removes extra elements
                split.removeAll(Arrays.asList("", null));

                Token token;
                for (int i = 0; i < split.size(); i++) {
                    // splits each array element further by space
                    String[] content = split.get(i).split("\\s+");
                    List<Token> contentParsed = new ArrayList<Token>();
                    for (int j = 0; j < content.length; j++) {
                        // tokenizes each component of the term within parenthesis
                        token = Token.parseToken(content[j]);
                        contentParsed.add(token);
                    }
                    list.add(contentParsed);
                }

                try {
                    // if the user quits
                    if (list.get(0).get(0).toString().equals(Token.parseToken("quit").toString())) {
                        exit(0);
                    // if the user wants a static scoping system enabled (enabled by default)
                    } else if (list.get(0).get(0).toString().equals(Token.parseToken("static").toString())) {
                        mapFlag = 0;
                    // if the user wants a dynamic scoping system enabled (shares variable mapping between variables
                    // defined inside and outside of functions
                    } else if (list.get(0).get(0).toString().equals(Token.parseToken("dynamic").toString())) {
                        mapFlag = 2;
                    } else {
                    // if the user inputs a normal expression
                        interpreter.process(list, 0, mapFlag);
                    }
                } catch(IndexOutOfBoundsException e) {
                    System.out.print("");
                } catch(NullPointerException e) {
                    System.out.print("");
                }

            } catch(NoSuchElementException e) {
                System.out.println("EXITING");
                exit(0);
            }
        }
    }
}
