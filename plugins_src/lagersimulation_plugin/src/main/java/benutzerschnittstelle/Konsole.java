package benutzerschnittstelle;

import java.util.Scanner;

import fachkonzept.Haendler;
import fachkonzept.Kunde;
import fachkonzept.Produkt;

public class Konsole
{
	public static void main(String[] args)
	{
		/*
		Lagersimulation
		---------------
		eingekaufte Menge: 
		10
		angefragte Menge: 47
		verkaufte Menge: 10
		Kosten: 15.0
		Umsatz: 20.0
		Gewinn: 5.0
		Eigenkapital: 5.0
		Lagerbestand: 0
		eingekaufte Menge: 
		60
		angefragte Menge: 53
		verkaufte Menge: 53
		Kosten: 90.0
		Umsatz: 106.0
		Gewinn: 16.0
		Eigenkapital: 21.0
		Lagerbestand: 7
		eingekaufte Menge: 
		10
		angefragte Menge: 25
		verkaufte Menge: 17
		Kosten: 15.0
		Umsatz: 34.0
		Gewinn: 19.0
		Eigenkapital: 40.0
		Lagerbestand: 0
		eingekaufte Menge: 
		100
		angefragte Menge: 27
		verkaufte Menge: 27
		Kosten: 150.0
		Umsatz: 54.0
		Gewinn: -96.0
		Eigenkapital: -56.0
		Lagerbestand: 73
		Sie haben kein Eigenkapital 
		mehr.
		Ende Ihres Unternehmens!
		*/
		System.out.println("Lagersimulation");
		System.out.println("---------------");

		Haendler haendler = new Haendler();
		Kunde kunde = new Kunde(0, 99);
		Produkt produkt = new Produkt(1.50, 2.00);
		Scanner input = new Scanner(System.in);

		do
		{
			System.out.println("eingekaufte Menge: ");
			int eingekaufteMenge = Integer.parseInt(input.nextLine());

			double kosten_for_haenlder = produkt.kaufeEin(eingekaufteMenge);

			int kunde_wants = kunde.frageAn();
			int kunde_wants_and_gets = produkt.frageAn(kunde_wants);

			double kosten_for_kunde = produkt.verkaufe(kunde_wants_and_gets);
			double gewinn = haendler.verbucheErgebnis(kosten_for_kunde, kosten_for_haenlder);


			System.out.printf("angefragte Menge: %d\n", eingekaufteMenge);
			System.out.printf("verkaufte Menge: %d\n", kunde_wants_and_gets);
			System.out.printf("Kosten: %.2f\n", kosten_for_haenlder);
			System.out.printf("Umsatz: %.2f\n", kosten_for_kunde);
			System.out.printf("Gewinn: %.2f\n", gewinn);
			System.out.printf("Eigenkapital: %.2f\n", haendler.liesEigenkapital());
			System.out.printf("Lagerbestand: %d\n\n", produkt.liesLagerbestand());
		}
		while (!haendler.istPleite());

		System.out.println("Sie haben kein Eigenkapital mehr.");
		System.out.println("Ende Ihres Unternehmens!");

		input.close();
	}
}