/**
 * @name Sterling Blevins
 *
 * @file Token.java
 * @description The Tokenizing class to tokenize each element of the input, broken up into
 * expressions (keywords), operators, numbers, and variables
 *
 * @date 5/3/17
 */
public class Token {
    private Keyword expression;
    private Operator operator;
    private Double number;
    private String variable;

    /**
     * Getter for the expression
     * @return
     */
    public Keyword getExpression() {
        return expression;
    }

    /**
     * Setter for the expression
     * @param expression
     */
    public void setExpression(Keyword expression) {
        this.expression = expression;
    }

    /**
     * Getter for operators
     * @return
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Setter for the operators
     * @param operator
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * Getter for the numbers
     * @return
     */
    public double getNumber() {
        return number;
    }

    /**
     * Setter for the numbers
     * @param number
     */
    public void setNumber(double number) {
        this.number = number;
    }

    /**
     * Getter for variables
     * @return
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Setter for variables
     * @param variable
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * Tokenizer constructor to store operators
     * @param operator
     */
    public Token(Operator operator) {
        this.expression = null;
        this.operator = operator;
        this.number = null;
        this.variable = null;
    }

    /**
     * Tokenizer constructor to store keywords
     * @param expression
     */
    public Token(Keyword expression) {
        this.expression = expression;
        this.operator = null;
        this.number = null;
        this.variable = null;
    }

    /**
     * Tokenizer constructor to store numbers
     * @param number
     */
    public Token(double number) {
        this.expression = null;
        this.operator = null;
        this.number = number;
        this.variable = null;
    }

    /**
     * Tokenizer constructor to store variables
     * @param variable
     */
    public Token(String variable) {
        this.expression = null;
        this.operator = null;
        this.number = null;
        this.variable = variable;
    }

    /**
     * Determines if an operator
     * @return
     */
    public boolean isOperator() {
        if(this.operator != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if a keyword
     * @return
     */
    public boolean isKeyword() {
        if(this.expression != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if a number
     * @return
     */
    public boolean isNumber() {
        if(this.number != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if a variable
     * @return
     */
    public boolean isVariable() {
        if(this.variable != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * To string conversion
     * @return
     */
    public String toString() {
        if(this.variable != null) {
            return this.getVariable();
        } else if(this.operator != null) {
            return this.getOperator().toString();
        } else if(this.number != null) {
            return String.valueOf(this.getNumber());
        } else if(this.getExpression() != null) {
            return this.getExpression().toString();
        } else
            return null;
    }

    /**
     * Actual tokenizer takes in a string, and converts to a token
     * @param str
     * @return
     */
    public static Token parseToken(String str) {
        if (str.trim().equals("("))
            return new Token(Operator.LPAREN);
        else if(str.trim().equals(")"))
            return new Token(Operator.RPAREN);
        else if(str.trim().equals("^"))
            return new Token(Operator.EXPONENT);
        else if(str.trim().equals("*"))
            return new Token(Operator.MULTIPLY);
        else if(str.trim().equals("/"))
            return new Token(Operator.DIVIDE);
        else if(str.trim().equals("%"))
            return new Token(Operator.MODULO);
        else if(str.trim().equals("+"))
            return new Token(Operator.ADD);
        else if(str.trim().equals("-"))
            return new Token(Operator.SUBTRACT);
        else if(str.trim().equals("<"))
            return new Token(Operator.LESSER);
        else if(str.trim().equals(">"))
            return new Token(Operator.GREATER);
        else if(str.trim().equals("<="))
            return new Token(Operator.LESSEREQ);
        else if(str.trim().equals(">="))
            return new Token(Operator.GREATEREQ);
        else if(str.trim().equals("=="))
            return new Token(Operator.EQUAL);
        else if(str.trim().equals("cos"))
            return new Token(Keyword.COS);
        else if(str.trim().equals("sin"))
            return new Token(Keyword.SIN);
        else if(str.trim().equals("tan"))
            return new Token(Keyword.TAN);
        else if(str.trim().equals("sqrt"))
            return new Token(Keyword.SQRT);
        else if(str.trim().equals("exp"))
            return new Token(Keyword.EXP);
        else if(str.trim().equals("quote"))
            return new Token(Keyword.QUOTE);
        else if(str.trim().equals("if"))
            return new Token(Keyword.IF);
        else if(str.trim().equals("define"))
            return new Token(Keyword.DEFINE);
        else if(str.trim().equals("set!"))
            return new Token(Keyword.SET);
        else if(str.trim().equals("defun"))
            return new Token(Keyword.DEFUN);
        else {
            try {
                Double value = Double.parseDouble(str);
                return new Token(value);
            } catch (NumberFormatException e) {
                return new Token(str);
            }
        }
    }
}
