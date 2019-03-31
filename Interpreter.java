import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @name Sterling Blevins
 *
 * @file Interpreter.java
 * @description The interpreter class which contains all interpreter methods
 *
 * @date 5/3/17
 */
public class Interpreter {
    // hash map for variables defined outside of functions
    public HashMap<String, Double> map = new HashMap<>();
    // hash map for variables defined insied a function
    public HashMap<String, Double> functionMap = new HashMap<>();

    // list of function classes that are defined
    List<Function> functions = new ArrayList<Function>();
    // prints to a file
    PrintStream writer = new PrintStream(new BufferedOutputStream(new FileOutputStream("results.txt")), true);
    DecimalFormat decimalFormat=new DecimalFormat("#.#");


    public Interpreter() throws FileNotFoundException {
    }

    /**
     * Function class contains all components of a defined function
     */
    public class Function {
        private String name;
        private List<Token> variables;
        private ArrayList<List<Token>> equation;


        /**
         * Getter for the function name
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Setter for the function name
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Getter for the paramters of the function
         * @return
         */
        public List<Token> getVariables() {
            return variables;
        }

        /**
         * Setter for paramters of the function
         * @param variables
         */
        public void setVariables(List<Token> variables) {
            this.variables = variables;
        }

        /**
         * Getter for the equations defined within the function
         * @return
         */
        public ArrayList<List<Token>> getEquation() {
            return equation;
        }

        /**
         * Setter for the equations defined within the function
         * @param parts
         * @param index
         */
        public void setEquation(ArrayList<List<Token>> parts, int index) {
            ArrayList<List<Token>> equationList = new ArrayList<List<Token>>();
            for(int i = index; i < parts.size(); i++) {
                equationList.add(parts.get(i));
            }
            this.equation = equationList;
        }
    }

    /**
     * Sets the variable hash maps
     * @param var           variable to be set
     * @param value         value of the variable
     * @param mapFlag       flag to decide which map to place the variable in
     */
    public void setMap(String var, Double value, int mapFlag) {
        // 0 = define
        // 1 = set!
        // 2 = function
        if(mapFlag == 0 || mapFlag == 1) {
            this.map.put(var, value);
        } else if(mapFlag == 2) {
            this.functionMap.put(var, value);
        }
    }

    /**
     * Evaluates the numerical expression
     * @param line          expression array
     * @param index         index location of expression
     * @param mapFlag       flag to decide which map to extract the variable from
     */
    public void evaluate(ArrayList<List<Token>> line, int index, int mapFlag) {
        List<Token> tokens = new ArrayList<Token>();

        // if the evaluation has more than one term, it swaps out the variables and places them back into the array
        if(line.get(index).size() > 1) {
            double x;
            double y;
            Token a = line.get(index).get(1);
            Token b = line.get(index).get(2);

            // swaps out the variable for a value
            if(a.isNumber()) {
                x = a.getNumber();
            } else {
                x = variableExchange(a, mapFlag);
            }

            // swaps out the variable for a value
            if(b.isNumber()) {
                y = b.getNumber();
            } else {
                y = variableExchange(b, mapFlag);
            }

            tokens.add(Token.parseToken(String.valueOf(line.get(index).get(0).getOperator().eval(x, y))));
            line.set(index, tokens);
        // if the first element is an operator, the term is evaluated and placed back into the array
        } else if(line.get(index).get(0).isOperator()){
            //recursive evaluations for both a and b components
            evaluate(line, index + 1, mapFlag);
            evaluate(line, index + 2, mapFlag);

            Token a = line.get(index + 1).get(0);
            Token b = line.get(index + 2).get(0);

            double x;
            double y;

            if(a.isNumber()) {
                x = a.getNumber();
            } else {
                x = variableExchange(a, mapFlag);
            }

            if(b.isNumber()) {
                y = b.getNumber();
            } else {
                y = variableExchange(b, mapFlag);
            }

            double temp = line.get(index).get(0).getOperator().eval(x, y);
            tokens.add(Token.parseToken(String.valueOf(temp)));
            line.set(index, tokens);
        }
    }

    /**
     * Interprets the conditional if statement
     * @param line          Array of elements
     * @param index         index position of if statement
     * @param mapFlag       indicator of variable map to use
     */
    public void condition(ArrayList<List<Token>> line, int index, int mapFlag) {
        // evaluates the conditional statement
        evaluate(line, index + 1, mapFlag);

        // if true executes the first term
        if(line.get(index + 1).get(0).getNumber() == 1) {
            process(line, index + 2, mapFlag);
        // if false executes the second term
        } else {
            process(line, index + 3, mapFlag);
        }
    }

    /**
     * Processes the mathematical operations that are built in
     * @param line          array of input tokens
     * @param index         index of array position
     * @param mapFlag       indicator of variable mapping to use
     */
    public void math(ArrayList<List<Token>> line, int index, int mapFlag) {
        Keyword key = line.get(index).get(0).getExpression();
        Token tokenX = line.get(index + 1).get(0);
        double x;

        // exchanges a variable to a number if one exists
        if(tokenX.isVariable()) {
            x = variableExchange(tokenX, mapFlag);
        } else {
            x = tokenX.getNumber();
        }

        if(key == Keyword.COS) {
            System.out.println(key.eval(x));
            this.writer.println(key.eval(x));
        } else if(key == Keyword.SIN) {
            System.out.println(key.eval(x));
            this.writer.println(key.eval(x));
        } else if(key == Keyword.TAN) {
            System.out.println(key.eval(x));
            this.writer.println(key.eval(x));
        } else if(key == Keyword.SQRT) {
            System.out.println(decimalFormat.format(key.eval(x)));
            this.writer.println(decimalFormat.format(key.eval(x)));
        } else if(key == Keyword.EXP) {
            Token tokenY = line.get(index + 1).get(1);
            double y;
            if(tokenY.isVariable()) {
                y = variableExchange(tokenY, mapFlag);
            } else {
                y = tokenY.getNumber();
            }
            System.out.println(decimalFormat.format(key.eval(x, y)));
            this.writer.println(decimalFormat.format(key.eval(x, y)));
        }
    }

    /**
     * Attempts to print out the quotation
     * @param line          array of lisp elements
     * @param index         index position
     */
    public void quotation(ArrayList<List<Token>> line, int index) {
        System.out.print("(" + line.get(index + 1).get(0));
        this.writer.print("(" + line.get(index + 1).get(0));

        for(int i = 1; i < line.get(index + 1).size(); i++) {
            if(line.get(index + 1).get(i).isNumber()) {
                System.out.print(" " + decimalFormat.format(line.get(index + 1).get(i).getNumber()));
                this.writer.print(" " + decimalFormat.format(line.get(index + 1).get(i).getNumber()));
            } else {
                System.out.print(" " + line.get(index + 1).get(i));
                this.writer.print(" " + line.get(index + 1).get(i));
            }
        }
        System.out.println(")");
        this.writer.println(")");

    }

    /**
     * Defines a variable and places it in a hash map
     * @param line          array of lisp elements
     * @param index         index of variable
     * @param type          hash map selection
     */
    public void variableDefinition(ArrayList<List<Token>> line, int index, int type) {
        // 0 = definition
        // 1 = set
        // 2 = function
        String key;
        double value;

        if(line.get(index).size() < 3) {
            key = line.get(index).get(1).toString();
            evaluate(line, index + 1, type);
            value = line.get(index + 1).get(0).getNumber();
        } else {
            key = line.get(index).get(1).toString();
            value = line.get(index).get(2).getNumber();
        }
        // sets map based on whether it is a definition outside or inside of a function
        setMap(key, value, type);
        // if a flag is for a definition, print out the key
        if(type == 0) {
            System.out.println(key);
            this.writer.println(key);
        // if a flag is for a set!, print out the variable
        } else if (type == 1){
            System.out.println(decimalFormat.format(value));
            this.writer.println(decimalFormat.format(value));
        }
    }

    /**
     * Sets the parameters of a function based on definition to variables based on when a function is called
     * @param line          array of lisp elements
     * @param parameters    parameters for a function definition
     * @param index         index location
     * @param type          sets the mapping type
     */
    public void parameterDefinition(ArrayList<List<Token>> line, List<Token> parameters, int index, int type) {
        // 0 = definition
        // 1 = set
        // 2 = function
        String key;
        double value;

        for(int i = 0; i < parameters.size(); i++) {
            key = parameters.get(i).toString();
            value = line.get(index).get(i).getNumber();
            setMap(key, value, type);
        }

    }

    /**
     * Variable exchange will swap out the character name for the value when executing and processing equations
     * @param var           variables names to swap out
     * @param mapFlag       which map to swap from
     * @return
     */
    public Double variableExchange(Token var, int mapFlag) {
        String key = var.toString();

        if(mapFlag == 2) {
            if (functionMap.containsKey(key)) {
                return (Double.parseDouble(functionMap.get(key).toString()));
            } else {
                System.out.println("ERROR: variable " + key + " does not exist.");
                this.writer.println("ERROR: variable " + key + " does not exist.");
                return null;
            }
        } else {
            if (map.containsKey(key)) {
                return (Double.parseDouble(map.get(key).toString()));
            } else {
                System.out.println("ERROR: variable " + key + " does not exist.");
                this.writer.println("ERROR: variable " + key + " does not exist.");
                return null;
            }
        }
    }

    /**
     * Defines the function and places it into an array of classes
     * @param line          array of elements
     * @param index         index position
     * @param functionList  array of function classes
     */
    public void defineFunction(ArrayList<List<Token>> line, int index, List<Function> functionList) {
        Function function = new Function();

        // checks to see if it is a normal function definition with parameters
        boolean standard = false;
        for(int i = 0; i < line.get(1).size(); i++) {
            if(line.get(1).get(i).isVariable()) {
                standard = true;
            } else {
                standard = false;
                break;
            }
        }

        // parameters
        if(standard) {
            function.setVariables(line.get(index + 1));
            function.setEquation(line, index + 2);
            function.setName(line.get(index).get(1).toString());

            functionList.add(function);
        // no parameters
        } else {
            function.setName(line.get(index).get(1).toString());
            function.setEquation(line, index + 1);
            functionList.add(function);
        }

    }

    /**
     * Processes and executes a function
     * @param functionPosition      index for which class out of an array
     * @param index                 index for line array
     * @param line                  array of lisp elements
     */
    public void executeFunction(int functionPosition, int index, ArrayList<List<Token>> line) {
        if(functions.get(functionPosition).getVariables() == null) {
            process(functions.get(functionPosition).getEquation(), index, 2);
        } else {
            List<Token> variables = functions.get(functionPosition).getVariables();
            parameterDefinition(line, variables, index + 1, 2);
            process(functions.get(functionPosition).getEquation(), index, 2);
        }
    }

    /**
     * Processes the entire interpreter structure for if a number, operator, or a keyword
     * @param line          array of elements
     * @param index         index of array position, changes are line is parsed, initial index is 0
     * @param mapFlag       map flag to choose which variable collection to operate on
     */
    public void process(ArrayList<List<Token>> line, int index, int mapFlag) {
        ArrayList<List<Token>> listStack = new ArrayList<List<Token>>();

        // token to check
        Token token = line.get(index).get(0);

        // if token is a number, just print out the value
        if (token.isNumber()) {
            System.out.println(decimalFormat.format(token.getNumber()));
            this.writer.println(decimalFormat.format(token.getNumber()));
        // if token is a an operator, evaluate the equation
        } else if (token.isOperator()) {
            evaluate(line, index, mapFlag);
            System.out.println(decimalFormat.format(line.get(index).get(0).getNumber()));
            this.writer.println(decimalFormat.format(line.get(index).get(0).getNumber()));
        // if token is a keyword, evaluate depending on the keyword
        } else if (token.isKeyword()) {
            Keyword key = token.getExpression();
            if (key == Keyword.IF) {
                condition(line, index, mapFlag);
            } else if (key == Keyword.COS) {
                math(line, index, mapFlag);
            } else if (key == Keyword.SIN) {
                math(line, index, mapFlag);
            } else if (key == Keyword.TAN) {
                math(line, index, mapFlag);
            } else if (key == Keyword.SQRT) {
                math(line, index, mapFlag);
            } else if (key == Keyword.EXP) {
                math(line, index, mapFlag);
            } else if (key == Keyword.QUOTE) {
                quotation(line, index);
            } else if (key == Keyword.DEFINE) {
                variableDefinition(line, index, 0);
            } else if (key == Keyword.SET) {
                variableDefinition(line, index, 1);
            } else if (key == Keyword.DEFUN) {
                defineFunction(line, index, functions);
            }
        // if a variable or a function, process, and evaluate
        } else if (token.isVariable()) {
            int loopFlag = 0;
            List<Token> tokens = new ArrayList<Token>();

            // checks if variable is a function, and if it is, execute the function
            for(int i = 0; i < functions.size(); i++) {
                if(line.get(index).get(0).toString().equals(functions.get(i).getName())) {
                    loopFlag = 1;
                    executeFunction(i, index, line);
                }
            }
            // if not a function, swap out the variable for a number, and output value
            if(loopFlag == 0) {
                Double var = variableExchange(line.get(index).get(0), mapFlag);
                tokens.add(Token.parseToken(String.valueOf(var)));
                line.set(index, tokens);
                System.out.println(decimalFormat.format(line.get(index).get(0).getNumber()));
                this.writer.println(decimalFormat.format(line.get(index).get(0).getNumber()));
            }
        }

    }
}
