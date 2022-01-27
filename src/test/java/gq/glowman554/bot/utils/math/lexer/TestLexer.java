package gq.glowman554.bot.utils.math.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLexer {
    @Test
    public void test1() {
        Lexer lexer = new Lexer("10");

        LexerToken[] tokens = lexer.tokenize();

        assertEquals(new LexerToken(LexerToken.LexerTokenType.number, (double) 10), tokens[0]);
    }

    @Test
    public void test2() {
        Lexer lexer = new Lexer("10.5");

        LexerToken[] tokens = lexer.tokenize();

        assertEquals(new LexerToken(LexerToken.LexerTokenType.number, (double) 10.5), tokens[0]);
    }

    @Test
    public void test3() {
        Lexer lexer = new Lexer("0x10");

        LexerToken[] tokens = lexer.tokenize();

        assertEquals(new LexerToken(LexerToken.LexerTokenType.number, (double) 16), tokens[0]);
    }

    @Test
    public void test4() {
        Lexer lexer = new Lexer("10 + 20.5 * pi");

        LexerToken[] tokens = lexer.tokenize();
        LexerToken[] expected = new LexerToken[]{
                new LexerToken(LexerToken.LexerTokenType.number, 10d),
                new LexerToken(LexerToken.LexerTokenType.plus),
                new LexerToken(LexerToken.LexerTokenType.number, 20.5d),
                new LexerToken(LexerToken.LexerTokenType.multiply),
                new LexerToken(LexerToken.LexerTokenType.id, "pi"),
        };

        for (int i = 0; i < tokens.length; i++) {
            assertEquals(expected[i], tokens[i]);
        }
    }
}
