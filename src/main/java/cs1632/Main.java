package cs1632;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    private static Boolean endsSequence(Token tok) {
        if (tok == null) return true;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == ';') return true;
            if (((OpToken) tok).getOperator() == '.') return true;
        }
        return false;
    }
    private static ArrayList<Token> getNextSequence(Tokenizer toknizr) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        Token tok;
        do {
            tok = toknizr.nextToken();
            if (tok != null) tokens.add(tok);
        } while (!endsSequence(tok));
        if (tok == null) return null;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == '.') {
                return null;
            }
        }
        return tokens;
    }

    static int divider(int a, int b) {
        if (a * b < 0 && a % b != 0)
            return (a / b) - 1;
        return a / b;
    }

    public static int evaluatePrefix(ArrayList<Token> tokenSequence) {
        Stack<Integer> st = new Stack<>();

        // traverse from right to left
        for (int i = tokenSequence.size() - 1; i >= 0; i--) {
            String token = tokenSequence.get(i).getTokenString();

            // Skip the sequence ending semicolon
            if (token.equals(";")) {
                continue;
            }

            // if the token is an integer, push it
            if (Character.isDigit(token.charAt(0)) ||
                    (token.length() > 1 && token.charAt(0) == '-')) {

                //making string to integer
                int x = Integer.parseInt(token);
                st.push(x);

            } else {
                // operator needs two operands
                int val1 = st.pop();
                int val2 = st.pop();

                //executing the equation using two operands
                switch (token) {
                    case "+":
                        st.push(val1 + val2);
                        break;
                    case "-":
                        st.push(val1 - val2);
                        break;
                    case "*":
                        st.push(val1 * val2);
                        break;
                    case "/":
                        st.push(divider(val1, val2));
                        break;
                    case "^":
                        st.push((int) Math.pow(val1, val2));
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown operator: " + token);
                }
            }
        }

        if (st.size() != 1) {
            throw new IllegalArgumentException("Invalid prefix expression");
        }

        return st.pop();
    }

    public static void main(String[] args) {
        try {
            Tokenizer myScanner = new Tokenizer(System.in);
            ArrayList<Token> tokenSequence;
            ArrayList<Token> postfixSequence = null;
            do {
                System.out.print("Equation: ");
                tokenSequence = getNextSequence(myScanner);
                if (tokenSequence != null) {
                    // Replace the code starting here with code to evaluate the expression
                    System.out.println("Result: " + evaluatePrefix(tokenSequence));
                }
            } while (tokenSequence != null);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}