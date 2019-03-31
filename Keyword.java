/**
 * @name Sterling Blevins
 *
 * @file Keyword.java
 * @description The keyword enumeration for the keywords and built in math functions
 *
 * @date 5/3/17
 */
public enum Keyword{
    QUOTE("quote"),
    IF("if"),
    DEFINE("define"),
    SET("set!"),
    DEFUN("defun"), // "lambda"
    EXP("exp"),
    COS("cos"),
    SIN("sin"),
    TAN("tan"),
    SQRT("sqrt");

    private String word;

    private Keyword(String word) {
        this.word = word;
    }

    public String toString() {
        return this.word;
    }

    /**
     * Evaluation for two component math functions
     * @param x
     * @param y
     * @return
     */
    public double eval(double x, double y) {
        double temp = 0;
        switch (this) {
            case EXP:
                temp = Math.pow(x, y);
                break;
        }
        return temp;
    }

    /**
     * Evaluation for one component math functions
     * @param a
     * @return
     */
    public double eval(double a) {
        double temp = 0;
        switch (this) {
            case COS:
                temp = Math.cos(a);
                break;
            case SIN:
                temp = Math.sin(a);
                break;
            case TAN:
                temp = Math.tan(a);
                break;
            case SQRT:
                temp = Math.sqrt(a);
                break;
        }
        return temp;
    }
}
