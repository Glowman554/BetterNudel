package gq.glowman554.bot.utils.math.parser;

import gq.glowman554.bot.utils.math.lexer.LexerToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestParser {
    @Test
    public void test1() {
        LexerToken[] test_tokens = new LexerToken[]{
                new LexerToken(LexerToken.LexerTokenType.number, 10d),
                new LexerToken(LexerToken.LexerTokenType.plus),
                new LexerToken(LexerToken.LexerTokenType.number, 20.5d),
                new LexerToken(LexerToken.LexerTokenType.multiply),
                new LexerToken(LexerToken.LexerTokenType.number, 100)
        };

        Parser parser = new Parser(test_tokens);

        ParserNode node = parser.parse();

        assertEquals(ParserNode.ParserNodeType.add_node, node.getType());
        assertEquals(ParserNode.ParserNodeType.number_node, node.getNode_a().getType());
        assertEquals(ParserNode.ParserNodeType.multiply_node, node.getNode_b().getType());
        assertEquals(ParserNode.ParserNodeType.number_node, node.getNode_b().getNode_a().getType());
        assertEquals(ParserNode.ParserNodeType.number_node, node.getNode_b().getNode_b().getType());
    }
}
