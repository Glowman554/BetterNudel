package gq.glowman554.bot.utils.math.parser;


import gq.glowman554.bot.utils.math.lexer.LexerToken;

import java.util.HashMap;

public class Parser {
    private final LexerToken[] tokens;
    private int tokens_pos = -1;
    private LexerToken current_token = null;

    private HashMap<String, Double> defined_variables = new HashMap<String, Double>();

    private void load_defined_variables() {
        defined_variables.put("pi", 355d / 113d);
    }

    public Parser(LexerToken[] tokens) {
        this.tokens = tokens;
        next();

        load_defined_variables();
    }

    private void fail() {
        throw new IllegalStateException("Invalid syntax! " + current_token.toString());
    }

    private void next() {
        tokens_pos++;

        if (tokens.length <= tokens_pos) {
            current_token = null;
        } else {
            current_token = tokens[tokens_pos];
        }
    }

    private void reverse() {
        tokens_pos--;

        if (tokens.length <= tokens_pos) {
            current_token = null;
        } else {
            current_token = tokens[tokens_pos];
        }
    }

    public ParserNode parse() {
        return expr();
    }

    public ParserNode expr() {
        ParserNode result = term();

        while (current_token != null && (current_token.getType() == LexerToken.LexerTokenType.plus || current_token.getType() == LexerToken.LexerTokenType.minus)) {
            if (current_token.getType() == LexerToken.LexerTokenType.minus) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.substract_node, result, term());
            } else if (current_token.getType() == LexerToken.LexerTokenType.plus) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.add_node, result, term());
            } else {
                fail();
            }
        }

        return result;
    }

    public ParserNode term() {
        ParserNode result = power();

        while (current_token != null && (current_token.getType() == LexerToken.LexerTokenType.multiply || current_token.getType() == LexerToken.LexerTokenType.divide || current_token.getType() == LexerToken.LexerTokenType.modulo)) {
            if (current_token.getType() == LexerToken.LexerTokenType.multiply) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.multiply_node, result, power());
            } else if (current_token.getType() == LexerToken.LexerTokenType.divide) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.divide_node, result, power());
            } else if (current_token.getType() == LexerToken.LexerTokenType.modulo) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.modulo_node, result, power());
            } else {
                fail();
            }
        }

        return result;
    }

    public ParserNode power() {
        ParserNode result = factor();

        while (current_token != null && (current_token.getType() == LexerToken.LexerTokenType.pow)) {
            if (current_token.getType() == LexerToken.LexerTokenType.pow) {
                next();
                result = new ParserNode(ParserNode.ParserNodeType.pow_node, result, factor());
            } else {
                fail();
            }
        }

        return result;
    }

    public ParserNode factor() {
        LexerToken token = current_token;

        if (token.getType() == LexerToken.LexerTokenType.lparen) {
            next();
            ParserNode result = expr();

            if (current_token.getType() != LexerToken.LexerTokenType.rparen) {
                fail();
            }

            next();

            return result;
        } else if (token.getType() == LexerToken.LexerTokenType.number) {
            next();
            return new ParserNode(ParserNode.ParserNodeType.number_node, token.getValue());
        } else if (token.getType() == LexerToken.LexerTokenType.plus) {
            next();
            return new ParserNode(ParserNode.ParserNodeType.plus_node, factor());
        } else if (token.getType() == LexerToken.LexerTokenType.minus) {
            next();
            return new ParserNode(ParserNode.ParserNodeType.minus_node, factor());
        } else if (token.getType() == LexerToken.LexerTokenType.id) {
            next();
            if (current_token.getType() == LexerToken.LexerTokenType.lparen) {
                next();
                ParserNode result = expr();

                if (current_token.getType() != LexerToken.LexerTokenType.rparen) {
                    fail();
                }

                next();

                return new ParserNode(ParserNode.ParserNodeType.fcall_node, result, token.getValue());
            } else {
                reverse();
                Double from_lookup = defined_variables.get((String) token.getValue());

                if (from_lookup == null) {
                    throw new IllegalStateException("Could not complete lookup for " + token.getValue());
                }

                next();
                return new ParserNode(ParserNode.ParserNodeType.number_node, from_lookup);
            }
        } else {
            fail();
        }

        return null;
    }
}
