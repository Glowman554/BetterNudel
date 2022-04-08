package gq.glowman554.bot.ttt;

import gq.glowman554.bot.log.Log;

public abstract class TicTacToeParser {
	public TicTacToeField[][] field = new TicTacToeField[3][3];

	public TicTacToeParser() {
		reset();
	}

	public abstract boolean parse(String s); // returns false if string is unparseable
	public abstract String toString();

	public void debug_print() {
		Log.log(toString());

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				Log.log(field[i][j].toString());
			}
		}
	}

	public void reset() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = TicTacToeField.FIELD_EMPTY;
			}
		}
	}

	public static TicTacToeParser[] parsers = new TicTacToeParser[] {};

	public static void add_parser(TicTacToeParser parser) {
		TicTacToeParser[] new_parsers = new TicTacToeParser[parsers.length + 1];
		for (int i = 0; i < parsers.length; i++) {
			new_parsers[i] = parsers[i];
		}
		new_parsers[new_parsers.length - 1] = parser;
		parsers = new_parsers;

		Log.log("Added parser: " + parser.getClass().getSimpleName());
	}

	public static TicTacToeParser try_parse(String s) {
		for (int i = 0; i < parsers.length; i++) {
			if (parsers[i].parse(s)) {
				return parsers[i];
			} else {
				parsers[i].reset();
			}
		}

		return null;
	}
}
