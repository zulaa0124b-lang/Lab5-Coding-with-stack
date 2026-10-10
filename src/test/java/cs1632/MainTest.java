package cs1632;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

public class MainTest {
    private ArrayList<Token> tokens(String text) throws IOException {
        Tokenizer t = new Tokenizer(new ByteArrayInputStream(text.getBytes()));
        ArrayList<Token> list = new ArrayList<>();
        Token tok;
        while ((tok = t.nextToken()) != null) {
            list.add(tok);
            String s = tok.getTokenString();
            if (s.equals(";") || s.equals(".")) break;
        }
        return list;
    }

    private int eval(String text) throws IOException {
        return Main.evaluatePrefix(tokens(text));
    }
    //testing individual operations working or not
    @Test
    public void testEval() throws IOException {
        assertEquals(8, eval("+ 5 3"));
        assertEquals(2, eval("- 5 3"));
        assertEquals(15, eval("* 5 3"));
        assertEquals(1, eval("/ 5 3"));
    }
    //tesing multiple operations
    @Test
    public void testEval2() throws IOException {
        assertEquals(10, eval("+ * 2 3 4 ;"));
        assertEquals(20, eval("+ * 3 4 * 2 4 ;"));
    }
    @Test
    public void divisionByZeroThrows() throws IOException {
        assertThrows(ArithmeticException.class, () -> eval("/ 5 0;"));
    }
    @Test
    void tooFewOperandsThrows() {
        assertThrows(EmptyStackException.class, () -> eval("+ 1;"));
    }
    @Test
    void emptyTokenListThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> Main.evaluatePrefix(new ArrayList<>()));
    }
    @Test
    void operatorOnlyThrows() {
        assertThrows(EmptyStackException.class, () -> eval("+;"));
    }
    @Test
    void emptySequenceThrows() {
        // only the terminator, stack ends with size 0
        assertThrows(IllegalArgumentException.class, () -> eval(";"));
    }

    }