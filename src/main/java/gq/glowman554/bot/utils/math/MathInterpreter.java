package gq.glowman554.bot.utils.math;


import gq.glowman554.bot.utils.math.interpreter.Interpreter;
import gq.glowman554.bot.utils.math.lexer.Lexer;
import gq.glowman554.bot.utils.math.lexer.LexerToken;
import gq.glowman554.bot.utils.math.parser.Parser;
import gq.glowman554.bot.utils.math.parser.ParserNode;

public class MathInterpreter {
    public static double eval(String expr, DebugPrint ref) {
        Lexer lexer = new Lexer(expr);
        LexerToken[] tokens = lexer.tokenize();

        for (LexerToken token : tokens) {
            ref.log(token.toString());
        }

        Parser parser = new Parser(tokens);
        ParserNode node = parser.parse();

        ref.log(node.toString());

        Interpreter interpreter = new Interpreter();
        return interpreter.interpret(node, ref);
    }

    public static double eval(String expr) {
        return eval(expr, debug_msg -> {
        });
    }

    public interface DebugPrint {
        void log(String debug_msg);
    }
}