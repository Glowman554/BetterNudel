package fachkonzept;

public class Haendler
{
	private double eigenkapital;

	public Haendler()
	{
		this.eigenkapital = 100;
	}

	public boolean istPleite()
	{
		return eigenkapital < 0;
	}

	public double verbucheErgebnis(double pUmsatz, double pKosten)
	{
		double gewinn = pUmsatz - pKosten;
		eigenkapital += gewinn;

		return gewinn;
	}

	public double liesEigenkapital()
	{
		return eigenkapital;
	}
}
