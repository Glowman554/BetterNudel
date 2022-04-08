package gq.glowman554.bot.ttt;

import gq.glowman554.bot.utils.Pair;

public class TicTacToeAi {
	public TicTacToeField[][] field;

	public TicTacToeAi(TicTacToeField[][] field) {
		this.field = field;

		assert field.length == 3;
		assert field[0].length == 3;
	}

	public Pair<Boolean, TicTacToeField> is_game_over() {
		// check rows
		for (int i = 0; i < field.length; i++) {
			if (field[i][0] != TicTacToeField.FIELD_EMPTY && field[i][0] == field[i][1] && field[i][1] == field[i][2]) {
				return new Pair<>(true, field[i][0]);
			}
		}

		// check columns
		for (int i = 0; i < field[0].length; i++) {
			if (field[0][i] != TicTacToeField.FIELD_EMPTY && field[0][i] == field[1][i] && field[1][i] == field[2][i]) {
				return new Pair<>(true, field[0][i]);
			}
		}

		// check diagonals
		if (field[0][0] != TicTacToeField.FIELD_EMPTY && field[0][0] == field[1][1] && field[1][1] == field[2][2]) {
			return new Pair<>(true, field[0][0]);
		}

		if (field[0][2] != TicTacToeField.FIELD_EMPTY && field[0][2] == field[1][1] && field[1][1] == field[2][0]) {
			return new Pair<>(true, field[0][2]);
		}

		// check if there is any empty field
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == TicTacToeField.FIELD_EMPTY) {
					return new Pair<>(false, null);
				}
			}
		}

		return new Pair<>(true, null);
	}

	private int minmax(TicTacToeField player, int depth, boolean is_maximizing) {
		Pair<Boolean, TicTacToeField> game_over = is_game_over();
		if (game_over.t1) {
			if (game_over.t2 == TicTacToeField.FIELD_X) {
				return -10 + depth;
			} else if (game_over.t2 == TicTacToeField.FIELD_O) {
				return 10 - depth;
			} else {
				return 0;
			}
		}

		if (is_maximizing) {
			int best = -1000;
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == TicTacToeField.FIELD_EMPTY) {
						field[i][j] = player;
						best = Math.max(best, minmax(player == TicTacToeField.FIELD_X ? TicTacToeField.FIELD_O : TicTacToeField.FIELD_X, depth + 1, false));
						field[i][j] = TicTacToeField.FIELD_EMPTY;
					}
				}
			}
			return best;
		} else {
			int best = 1000;
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == TicTacToeField.FIELD_EMPTY) {
						field[i][j] = player;
						best = Math.min(best, minmax(player == TicTacToeField.FIELD_X ? TicTacToeField.FIELD_O : TicTacToeField.FIELD_X, depth + 1, true));
						field[i][j] = TicTacToeField.FIELD_EMPTY;
					}
				}
			}
			return best;
		}
	}

	public Pair<Integer, Integer> get_move() {
		int best_score = -1000;
		int best_x = -1;
		int best_y = -1;

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] == TicTacToeField.FIELD_EMPTY) {
					field[i][j] = TicTacToeField.FIELD_O;
					int score = minmax(TicTacToeField.FIELD_X, 0, false);
					field[i][j] = TicTacToeField.FIELD_EMPTY;

					if (score > best_score) {
						best_score = score;
						best_x = i;
						best_y = j;
					}
				}
			}
		}

		if (best_x != -1 && best_y != -1) {
			field[best_x][best_y] = TicTacToeField.FIELD_O;
		}

		return new Pair<>(best_x, best_y);
	}
}
