package gq.glowman554.bot.utils.math.interpreter;


import gq.glowman554.bot.utils.Pair;
import gq.glowman554.bot.utils.math.MathInterpreter;
import gq.glowman554.bot.utils.math.parser.ParserNode;

import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    private HashMap<String, MathFunction> math_function = new HashMap<>();

    private void fail(int num_args_expected) {
        throw new IllegalArgumentException("Expected " + num_args_expected + " arguments!");
    }

    private void load_math_functions() {
        math_function.put("sqrt", in -> {
            if (in.length != 1) {
                fail(1);
            }
            return Math.sqrt(in[0]);
        });

        math_function.put("pow", in -> {
            if (in.length != 2) {
                fail(2);
            }
            return Math.pow(in[0], in[1]);
        });

        math_function.put("random", in -> {
            if (in.length != 0) {
                fail(0);
            }
            return Math.random();
        });

        math_function.put("sin", in -> {
            if (in.length != 1) {
                fail(1);
            }
            return Math.sin(in[0]);
        });

        math_function.put("cos", in -> {
            if (in.length != 1) {
                fail(1);
            }
            return Math.cos(in[0]);
        });

        math_function.put("tan", in -> {
            if (in.length != 1) {
                fail(1);
            }
            return Math.tan(in[0]);
        });
    }

    public Interpreter() {
        load_math_functions();
    }

    public double interpret(ParserNode root, MathInterpreter.DebugPrint dbg) {
        dbg.log("Interpreting " + root.getType().toString());
        switch (root.getType()) {
            case number_node:
                return (double) root.getValue();
            case add_node:
                return interpret(root.getNode_a(), dbg) + interpret(root.getNode_b(), dbg);
            case substract_node:
                return interpret(root.getNode_a(), dbg) - interpret(root.getNode_b(), dbg);
            case multiply_node:
                return interpret(root.getNode_a(), dbg) * interpret(root.getNode_b(), dbg);
            case divide_node:
                return interpret(root.getNode_a(), dbg) / interpret(root.getNode_b(), dbg);
            case modulo_node:
                return interpret(root.getNode_a(), dbg) % interpret(root.getNode_b(), dbg);
            case pow_node:
                return Math.pow(interpret(root.getNode_a(), dbg), interpret(root.getNode_b(), dbg));
            case fcall_node: {
                Pair<ArrayList<ParserNode>, String> arg_name_pair = (Pair) root.getValue();
                MathFunction function = math_function.get(arg_name_pair.t2);

                if (function == null) {
                    throw new IllegalStateException("Could not find function " + arg_name_pair.t2);
                }

                double[] result = new double[arg_name_pair.t1.size()];

                for (int i = 0; i < arg_name_pair.t1.size(); i++) {
                    result[i] = interpret(arg_name_pair.t1.get(i), dbg);
                }

                return function.call(result);
            }
        }

        throw new IllegalStateException("Unreachable!");
    }

    public interface MathFunction {
        double call(double[] in);
    }
}
