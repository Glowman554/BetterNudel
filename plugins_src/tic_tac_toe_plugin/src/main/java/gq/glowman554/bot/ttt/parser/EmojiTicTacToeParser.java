package gq.glowman554.bot.ttt.parser;

import gq.glowman554.bot.ttt.TicTacToeField;
import gq.glowman554.bot.ttt.TicTacToeParser;

public class EmojiTicTacToeParser extends TicTacToeParser{

	@Override
	public boolean parse(String s) {
		String[] split = s.split("\n");
		if (split.length != 3) {
			return false;
		}

		for (int i = 0; i < split.length; i++) {
			String[] line = split[i].replace(" ", "").split("");
			if (line.length != 3) {
				return false;
			}

			for (int j = 0; j < line.length; j++) {
				switch (line[j]) {
				case "❔":
				case "❓":
					field[i][j] = TicTacToeField.FIELD_EMPTY;
					break;
				case "⭕":
					field[i][j] = TicTacToeField.FIELD_O;
					break;
				case "❌":
					field[i][j] = TicTacToeField.FIELD_X;
					break;
				default:
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				switch (field[i][j]) {
				case FIELD_EMPTY:
					sb.append("❔");
					break;
				case FIELD_O:
					sb.append("⭕");
					break;
				case FIELD_X:
					sb.append("❌");
					break;
				}
				sb.append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}
	
}
