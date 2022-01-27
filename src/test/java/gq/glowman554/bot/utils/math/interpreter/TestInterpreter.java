package gq.glowman554.bot.utils.math.interpreter;

import gq.glowman554.bot.utils.math.parser.ParserNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInterpreter {
    @Test
    public void test1() {
        ParserNode root_node = new ParserNode(ParserNode.ParserNodeType.add_node, new ParserNode(ParserNode.ParserNodeType.number_node, 10d), new ParserNode(ParserNode.ParserNodeType.multiply_node, new ParserNode(ParserNode.ParserNodeType.number_node, 20.5d), new ParserNode(ParserNode.ParserNodeType.number_node, 100d)));

        Interpreter interpreter = new Interpreter();

        assertEquals(2060d, interpreter.interpret(root_node));
    }
}
