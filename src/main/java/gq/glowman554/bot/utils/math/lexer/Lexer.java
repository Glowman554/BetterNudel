package gq.glowman554.bot.utils.math.lexer;



import gq.glowman554.bot.utils.ArrayUtils;
import gq.glowman554.bot.utils.math.BinParser;
import gq.glowman554.bot.utils.math.HexParser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String text;
    private int text_pos = -1;
    private char current_char = 0;

    public Lexer(String text) {
        this.text = text;
        next();
    }

    private void next() {
        text_pos++;

        if (text.length() <= text_pos) {
            current_char = 0;
        } else {
            current_char = text.charAt(text_pos);
        }
    }

    private void reverse() {
        text_pos--;

        if (text.length() <= text_pos) {
            current_char = 0;
        } else {
            current_char = text.charAt(text_pos);
        }
    }

    public LexerToken[] tokenize() {
        List<LexerToken> tokens = new ArrayList<LexerToken>();

        while (current_char != 0) {
            switch (current_char) {
                case ' ':
                case '\n':
                case '\t': {
                    next();
                }
                break;

                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '.': {
                    tokens.add(number());
                }
                break;

                case '+': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.plus));
                    next();
                }
                break;

                case '-': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.minus));
                    next();
                }
                break;

                case '*': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.multiply));
                    next();
                }
                break;

                case '/': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.divide));
                    next();
                }
                break;

                case '%': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.modulo));
                    next();
                }
                break;

                case '^': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.pow));
                    next();
                }
                break;

                case '(': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.lparen));
                    next();
                }
                break;

                case ')': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.rparen));
                    next();
                }
                break;

                case ',': {
                    tokens.add(new LexerToken(LexerToken.LexerTokenType.comma));
                    next();
                }
                break;

                default: {
                    if ("abcdefghijklmnopqrstuvwxyz".contains(String.valueOf(current_char).toLowerCase())) {
                        tokens.add(id());
                    } else {
                        throw new IllegalStateException("Unknown char: " + current_char);
                    }
                }
            }
        }

        return tokens.toArray(new LexerToken[0]);
    }

    private LexerToken id() {
        String identifier = "";

        while (current_char != 0 && Character.isLetter(current_char)) {
            identifier += current_char;
            next();
        }

        return new LexerToken(LexerToken.LexerTokenType.id, identifier);
    }

    private LexerToken number() {
        int decimal_point_count = 0;
        String number_string = String.valueOf(current_char);

        boolean hex = false;
        boolean bin = false;

        if (current_char == '0') {
            next();
            number_string += current_char;

            switch (current_char) {
                case 'x': {
                    hex = true;
                }
                break;

                case 'b': {
                    bin = true;
                }
                break;

                default: {
                    reverse();
                }
                break;
            }

            next();
        } else {
            next();
        }

        while (current_char != 0 && "0123456789.".indexOf(current_char) != -1) {
            if (current_char == '.') {
                decimal_point_count++;
                if (decimal_point_count > 1) {
                    break;
                }
            }

            number_string += current_char;
            next();
        }

        if (!bin && !hex) {
            return new LexerToken(LexerToken.LexerTokenType.number, Double.parseDouble(number_string));
        } else {
            if (bin) {
                return new LexerToken(LexerToken.LexerTokenType.number, (double) BinParser.from_bin(number_string));
            } else {
                return new LexerToken(LexerToken.LexerTokenType.number, (double) HexParser.from_hex(number_string));
            }
        }
    }
}
