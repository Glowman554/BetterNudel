package benutzerschnittstelle;

import fachkonzept.Haendler;
import fachkonzept.Kunde;
import fachkonzept.Produkt;
import gq.glowman554.bot.Main;
import gq.glowman554.bot.command.Command;
import gq.glowman554.bot.command.CommandConfig;
import gq.glowman554.bot.command.CommandEvent;

import java.util.HashMap;

public class BetterNudelCommand implements Command
{

	HashMap<String, GameState> state = new HashMap<>();
	
	@Override
	public void execute(CommandEvent event) throws Exception
	{
		if (event.get_arguments().length != 1)
		{
			event.message_send("Missing count to buy");
		}
		else
		{
			if (state.get(event.get_sender_id()) == null)
			{
				state.put(event.get_sender_id(), new GameState());
			}
			
			GameState curr = state.get(event.get_sender_id());
			
			int eingekaufteMenge = Integer.parseInt(event.get_arguments()[0]);

			double kosten_for_haenlder = curr.produkt.kaufeEin(eingekaufteMenge);

			int kunde_wants = curr.kunde.frageAn();
			int kunde_wants_and_gets = curr.produkt.frageAn(kunde_wants);

			double kosten_for_kunde = curr.produkt.verkaufe(kunde_wants_and_gets);
			double gewinn = curr.haendler.verbucheErgebnis(kosten_for_kunde, kosten_for_haenlder);


			String out = String.format("angefragte Menge: %d\n", eingekaufteMenge);
			out += String.format("verkaufte Menge: %d\n", kunde_wants_and_gets);
			out += String.format("Kosten: %.2f\n", kosten_for_haenlder);
			out += String.format("Umsatz: %.2f\n", kosten_for_kunde);
			out += String.format("Gewinn: %.2f\n", gewinn);
			out += String.format("Eigenkapital: %.2f\n", curr.haendler.liesEigenkapital());
			out += String.format("Lagerbestand: %d\n\n", curr.produkt.liesLagerbestand());
			
			if (curr.haendler.istPleite())
			{
				out += "Sie haben kein Eigenkapital mehr.\nEnde Ihres Unternehmens!";
				state.remove(event.get_sender_id());
			}
			
			event.message_send(out);
		}
		
	}

	@Override
	public CommandConfig get_config()
	{
		return new CommandConfig("Simple game we made in computer class", String.format("Use '%slagersimulation [count]' to play a simple game we made in computer class!", Main.commandManager.prefix), null);
	}

	@Override
	public void on_register()
	{
	}

	public static class GameState
	{
		public Haendler haendler;
		public Kunde kunde;
		public Produkt produkt;

		public GameState()
		{
			haendler = new Haendler();
			kunde = new Kunde(0, 99);
			produkt = new Produkt(1.50, 2.00);
		}
	}
}
