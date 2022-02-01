package benutzerschnittstelle;

import fachkonzept.Person;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class Konsole {

    public static void main(String[] args) {
        System.out.println("BMI-Rechner\n-----------");

        Scanner input = new Scanner(System.in);

        int gewicht;

        while (true) {
            System.out.println("Gewicht: ");
            try {
                gewicht = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Bitte geben Sie eine positive Zahl ein!");
            }
        }

        double groesse;

        while (true) {
            System.out.println("Größe: ");
            try {
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                groesse = numberFormat.parse(input.nextLine()).doubleValue();
                break;
            } catch (ParseException e) {
                System.out.println("Bitte geben Sie eine Zahl ein!");
            }
        }

        input.close();

        Person person = null;

        try {
            person = new Person(gewicht, groesse);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        //System.out.println("\nBMI: " + person.berechneBMI() + " (" + person.ermittleGewichtsklasse() + ")");

        System.out.printf("\nBMI: %s (%s)", person.berechneBMI(), person.ermittleGewichtsklasse());
    }
}