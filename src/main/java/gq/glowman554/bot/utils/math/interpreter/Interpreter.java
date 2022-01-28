package gq.glowman554.bot.utils.math.interpreter;


import gq.glowman554.bot.utils.math.parser.ParserNode;

import java.util.HashMap;

public class Interpreter {
    private HashMap<String, MathFunction> math_function = new HashMap<>();

    private void load_math_functions() {
        math_function.put("sqrt", Math::sqrt);
    }

    public Interpreter() {
        load_math_functions();
    }

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
            case pow_node:
                return Math.pow(interpret(root.getNode_a()), interpret(root.getNode_b()));
            case fcall_node: {
                MathFunction function = math_function.get((String) root.getValue());

                if (function == null) {
                    throw new IllegalStateException("Could not find function " + root.getValue());
                }

                return function.call(interpret(root.getNode_a()));
            }
        }

        throw new IllegalStateException("Unreachable!");
    }

    public interface MathFunction {
        double call(double in);
    }
}
