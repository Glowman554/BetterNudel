package gq.glowman554.bot.utils.math.interpreter;


import gq.glowman554.bot.utils.math.parser.ParserNode;

public class Interpreter {
    public double interpret(ParserNode root) {
        switch (root.getType()) {
            case number_node:
                return (double) root.getValue();
            case add_node:
                return interpret(root.getNode_a()) + interpret(root.getNode_b());
            case substract_node:
                return interpret(root.getNode_a()) - interpret(root.getNode_b());
            case multiply_node:
                return interpret(root.getNode_a()) * interpret(root.getNode_b());
            case divide_node:
                return interpret(root.getNode_a()) / interpret(root.getNode_b());
            case modulo_node:
                return interpret(root.getNode_a()) % interpret(root.getNode_b());
        }

        throw new IllegalStateException("Unreachable!");
    }
}
