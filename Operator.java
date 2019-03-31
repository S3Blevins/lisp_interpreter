/**
 * @name Sterling Blevins
 *
 * @file Operator.java
 * @description The operator enumeration contains all the operational equations
 *
 * @date 5/3/17
 */
public enum Operator {
    LPAREN("("),
    RPAREN(")"),
    EXPONENT("^"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    ADD("+"),
    SUBTRACT("-"),
    GREATER(">"),
    LESSER("<"),
    LESSEREQ("<="),
    GREATEREQ(">="),
    EQUAL("==");

    private String operator;

    private Operator(String operator) {
        this.operator = operator;
    }

    /**
     * evaluate based on what the operator is
     * @param a
     * @param b
     * @return
     */
    public double eval(double a, double b) {
        double temp = 0;
        switch(this) {
            case LPAREN:    temp = Double.NaN;
                break;
            case RPAREN:    temp = Double.NaN;
                break;
            case EXPONENT:  temp = Math.pow(a, b);
                break;
            case MULTIPLY:  temp = a * b;
                break;
            case DIVIDE:    temp = a / b;
                break;
            case MODULO:    temp = a % b;
                break;
            case ADD:       temp = a + b;
                break;
            case SUBTRACT:  temp = a - b;
                break;
            // conditionals return 1 for true, 0 for false
            case GREATER:
                if(a > b)
                    temp = 1;
                else
                    temp = 0;
                break;
            case GREATEREQ:
                if(a >= b)
                    temp = 1;
                else
                    temp = 0;
                break;
            case LESSER:
                if(a < b)
                    temp = 1;
                else
                    temp = 0;
                break;
            case LESSEREQ:
                if(a <= b)
                    temp = 1;
                else
                    temp = 0;
                break;
            case EQUAL:
                if(a <= b)
                    temp = 1;
                else
                    temp = 0;
                break;
        }
        return temp;
    }

    public String toString() {
        return this.operator;
    }
}