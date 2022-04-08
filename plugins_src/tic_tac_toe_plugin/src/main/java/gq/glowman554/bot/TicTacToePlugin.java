package gq.glowman554.bot;

import gq.glowman554.bot.event.EventManager;
import gq.glowman554.bot.event.EventTarget;
import gq.glowman554.bot.event.impl.MessageEvent;
import gq.glowman554.bot.log.Log;
import gq.glowman554.bot.plugin.Plugin;
import gq.glowman554.bot.ttt.TicTacToeAi;
import gq.glowman554.bot.ttt.TicTacToeField;
import gq.glowman554.bot.ttt.TicTacToeParser;
import gq.glowman554.bot.ttt.parser.EmojiTicTacToeParser;
import gq.glowman554.bot.utils.Pair;

public class TicTacToePlugin implements Plugin {
    @Override
    public void on_load() throws Exception {
        Log.log("Hello World!");

		TicTacToeParser.add_parser(new EmojiTicTacToeParser());

		EventManager.register(this);
    }

	@EventTarget
	public void on_message(MessageEvent event) {
		if (event.commandEvent.get_chat_name().contains("bot")) {
			TicTacToeParser parser = TicTacToeParser.try_parse(event.commandEvent.get_message());

			if (parser != null) {
				parser.debug_print();

				TicTacToeAi ai = new TicTacToeAi(parser.field);

				Pair<Integer, Integer> move = ai.get_move();
				Log.log(move.toString());

				if (move.t1 == -1 || move.t2 == -1) {
					event.commandEvent.message_quote("GG");
				} else {
					parser.field[move.t1][move.t2] = TicTacToeField.FIELD_O;
					parser.debug_print();

					event.commandEvent.message_quote(parser.toString());

					if (ai.is_game_over().t1) {
						if (ai.is_game_over().t2 == TicTacToeField.FIELD_X) {
							event.commandEvent.message_quote("You won! How???");
						} else {
							event.commandEvent.message_quote("I won!");
						}
					}
				}
			}
		}
	}
}
