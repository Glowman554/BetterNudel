package benutzerschnittstelle;

import fachkonzept.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


@SuppressWarnings("serial")
public class GUI extends JFrame {
    private JPanel contentPane;
    private JLabel lblNewLabel;
    private JTextField txtGewicht;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JTextField txtGroesse;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
    private JTextField txtBmi;
    private JTextField txtGwichtsklasse;
    private JButton btnBmiBerechnen;
    private JButton btnLoeschen;

    /**
     * Launch the application.
     *
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUI() {
        setResizable(false);
        setTitle("BMI-Rechner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setBackground(Color.DARK_GRAY);

        setForeground(Color.LIGHT_GRAY);
        setFont(new Font("Arial Black", Font.PLAIN, 10));

        contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblNewLabel = new JLabel("Gewicht");
        lblNewLabel.setBorder(null);
        lblNewLabel.setForeground(Color.LIGHT_GRAY);
        lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 10));
        lblNewLabel.setBounds(10, 10, 100, 13);
        contentPane.add(lblNewLabel);

        txtGewicht = new JTextField();
        txtGewicht.setBorder(null);
        txtGewicht.setForeground(Color.LIGHT_GRAY);
        txtGewicht.setFont(new Font("Arial Black", Font.PLAIN, 10));
        txtGewicht.setBackground(Color.DARK_GRAY);
        txtGewicht.setToolTipText("Gewicht");
        txtGewicht.setBounds(120, 7, 96, 19);
        contentPane.add(txtGewicht);
        txtGewicht.setColumns(10);

        lblNewLabel_1 = new JLabel("kg");
        lblNewLabel_1.setBorder(null);
        lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
        lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 10));
        lblNewLabel_1.setBounds(226, 10, 45, 13);
        contentPane.add(lblNewLabel_1);

        lblNewLabel_2 = new JLabel("Groesse");
        lblNewLabel_2.setBorder(null);
        lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
        lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 10));
        lblNewLabel_2.setBounds(10, 39, 100, 13);
        contentPane.add(lblNewLabel_2);

        txtGroesse = new JTextField();
        txtGroesse.setBorder(null);
        txtGroesse.setForeground(Color.LIGHT_GRAY);
        txtGroesse.setFont(new Font("Arial Black", Font.PLAIN, 10));
        txtGroesse.setBackground(Color.DARK_GRAY);
        txtGroesse.setToolTipText("Groesse");
        txtGroesse.setBounds(120, 36, 96, 19);
        contentPane.add(txtGroesse);
        txtGroesse.setColumns(10);

        lblNewLabel_3 = new JLabel("m");
        lblNewLabel_3.setBorder(null);
        lblNewLabel_3.setForeground(Color.LIGHT_GRAY);
        lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 10));
        lblNewLabel_3.setBounds(226, 39, 45, 13);
        contentPane.add(lblNewLabel_3);

        lblNewLabel_4 = new JLabel("BMI");
        lblNewLabel_4.setBorder(null);
        lblNewLabel_4.setForeground(Color.LIGHT_GRAY);
        lblNewLabel_4.setFont(new Font("Arial Black", Font.PLAIN, 10));
        lblNewLabel_4.setBounds(10, 68, 100, 13);
        contentPane.add(lblNewLabel_4);

        txtBmi = new JTextField();
        txtBmi.setBorder(null);
        txtBmi.setForeground(Color.LIGHT_GRAY);
        txtBmi.setFont(new Font("Arial Black", Font.PLAIN, 10));
        txtBmi.setBackground(Color.DARK_GRAY);
        txtBmi.setToolTipText("BMI");
        txtBmi.setEnabled(false);
        txtBmi.setEditable(false);
        txtBmi.setBounds(120, 65, 151, 19);
        contentPane.add(txtBmi);
        txtBmi.setColumns(10);

        txtGwichtsklasse = new JTextField();
        txtGwichtsklasse.setBorder(null);
        txtGwichtsklasse.setForeground(Color.LIGHT_GRAY);
        txtGwichtsklasse.setFont(new Font("Arial Black", Font.PLAIN, 10));
        txtGwichtsklasse.setBackground(Color.DARK_GRAY);
        txtGwichtsklasse.setToolTipText("Gewihtsklasse");
        txtGwichtsklasse.setEnabled(false);
        txtGwichtsklasse.setEditable(false);
        txtGwichtsklasse.setBounds(120, 94, 151, 19);
        contentPane.add(txtGwichtsklasse);
        txtGwichtsklasse.setColumns(10);

        btnBmiBerechnen = new JButton("BMI berechnen");
        btnBmiBerechnen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                geklicktBMIBerechnen();
            }
        });
        btnBmiBerechnen.setBorder(null);
        btnBmiBerechnen.setForeground(Color.LIGHT_GRAY);
        btnBmiBerechnen.setFont(new Font("Arial Black", Font.PLAIN, 10));
        btnBmiBerechnen.setBackground(Color.DARK_GRAY);
        btnBmiBerechnen.setBounds(10, 130, 261, 21);
        contentPane.add(btnBmiBerechnen);

        btnLoeschen = new JButton("loeschen");
        btnLoeschen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                geklicktLoeschen();
            }
        });
        btnLoeschen.setBorder(null);
        btnLoeschen.setForeground(Color.LIGHT_GRAY);
        btnLoeschen.setFont(new Font("Arial Black", Font.PLAIN, 10));
        btnLoeschen.setBackground(Color.DARK_GRAY);
        btnLoeschen.setBounds(10, 161, 261, 21);
        contentPane.add(btnLoeschen);
    }

    private void geklicktBMIBerechnen() {

        int gewicht;
        try {
            gewicht = Integer.parseInt(txtGewicht.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, String.format("Bitte geben Sie eine Zahl ein!\n%s ist keine Zahl!", txtGewicht.getText()), "Gewicht", JOptionPane.ERROR_MESSAGE);

            txtGewicht.requestFocusInWindow();
            txtGewicht.selectAll();
            return;
        }

        double groesse;
        try {
            // get user setting for the number format to decide if we input using a dot or a comma
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            groesse = numberFormat.parse(txtGroesse.getText()).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, String.format("Bitte geben Sie eine Zahl ein!\n%s ist keine Zahl!", txtGroesse.getText()), "Groesse", JOptionPane.ERROR_MESSAGE);

            txtGroesse.requestFocusInWindow();
            txtGroesse.selectAll();
            return;
        }

        Person person;
        try {
            person = new Person(gewicht, groesse);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Program yeeted itself!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double my_bmi = person.berechneBMI();
        String my_gewichtsklasse = person.ermittleGewichtsklasse();

        txtBmi.setText(String.format("%.2f", my_bmi));
        txtGwichtsklasse.setText(my_gewichtsklasse);
    }

    private void geklicktLoeschen() {
        txtGewicht.setText("");
        txtGroesse.setText("");
        txtBmi.setText("");
        txtGwichtsklasse.setText("");
    }
}
