package fachkonzept;

public class Produkt
{
	private double einkaufspreis;
	private int lagerbestand;
	private double verkaufspreis;

	public Produkt(double pEinkaufspreis, double pVerkaufspreis)
	{
		this.einkaufspreis = pEinkaufspreis;
		this.verkaufspreis = pVerkaufspreis;
		this.lagerbestand = 0;
	}

	public int frageAn(int pMenge)
	{
		return pMenge > lagerbestand ? lagerbestand : pMenge;
	}

	public double kaufeEin(int pMenge)
	{
		lagerbestand += pMenge;

		return pMenge * einkaufspreis;
	}

	public double verkaufe(int pMenge)
	{
		lagerbestand -= pMenge;

		return pMenge * verkaufspreis;
	}

	public int liesLagerbestand()
	{
		return lagerbestand;
	}
}
