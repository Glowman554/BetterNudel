package fachkonzept;

import java.util.concurrent.ThreadLocalRandom;

public class Kunde
{
	// int[] kunde_anfragen = new int[] {
	// 	47,
	// 	53,
	// 	25,
	// 	27
	// };

	// int curr;

	private int min_anfrage;
	private int max_anfrage;

	public Kunde(int min_anfrage, int max_anfrage)
	{
		this.min_anfrage = min_anfrage;
		this.max_anfrage = max_anfrage;
	}

	public int frageAn()
	{
		return ThreadLocalRandom.current().nextInt(min_anfrage, max_anfrage);
		// return kunde_anfragen[curr++];
	}
}
