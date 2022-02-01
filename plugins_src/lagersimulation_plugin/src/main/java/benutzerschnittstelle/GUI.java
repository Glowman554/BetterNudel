package benutzerschnittstelle;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import fachkonzept.Haendler;
import fachkonzept.Kunde;
import fachkonzept.Produkt;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class GUI extends JFrame
{

	private JPanel contentPane;
	private JLabel lblHeading;
	private JLabel lblAngefragteMenge;
	private JLabel lblVerkaufteMenge;
	private JLabel lblKosten;
	private JLabel lblUmsatz;
	private JLabel lblGewinn;
	private JLabel lblEigenkapital;
	private JLabel lblLagerbestand;
	private JTextField txtAngefragteMenge;
	private JTextField txtVerkaufteMenge;
	private JTextField txtKosten;
	private JTextField txtUmsatz;
	private JTextField txtGewinn;
	private JTextField txtEigenkapital;
	private JTextField txtLagerbestand;
	private JLabel lblCountToBuy;
	private JTextField txtCountToBuy;
	private JButton btnNewButton;

	private Haendler haendler = new Haendler();
	private Kunde kunde = new Kunde(0, 99);
	private Produkt produkt = new Produkt(1.50, 2.00);

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblHeading = new JLabel("Lagersimulation");
		lblHeading.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHeading.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeading.setBounds(10, 10, 271, 25);
		contentPane.add(lblHeading);
		
		lblAngefragteMenge = new JLabel("angefragte Menge");
		lblAngefragteMenge.setBounds(20, 80, 150, 13);
		contentPane.add(lblAngefragteMenge);
		
		lblVerkaufteMenge = new JLabel("verkaufte menge");
		lblVerkaufteMenge.setBounds(20, 103, 150, 13);
		contentPane.add(lblVerkaufteMenge);
		
		lblKosten = new JLabel("Kosten");
		lblKosten.setBounds(20, 126, 150, 13);
		contentPane.add(lblKosten);
		
		lblUmsatz = new JLabel("Umsatz");
		lblUmsatz.setBounds(20, 149, 150, 13);
		contentPane.add(lblUmsatz);
		
		lblGewinn = new JLabel("Gewinn");
		lblGewinn.setBounds(20, 172, 150, 13);
		contentPane.add(lblGewinn);
		
		lblEigenkapital = new JLabel("Eigenkapital");
		lblEigenkapital.setBounds(20, 195, 150, 13);
		contentPane.add(lblEigenkapital);
		
		lblLagerbestand = new JLabel("Lagerbestand");
		lblLagerbestand.setBounds(20, 216, 150, 13);
		contentPane.add(lblLagerbestand);
		
		txtAngefragteMenge = new JTextField();
		txtAngefragteMenge.setEditable(false);
		txtAngefragteMenge.setBounds(180, 77, 96, 19);
		contentPane.add(txtAngefragteMenge);
		txtAngefragteMenge.setColumns(10);
		
		txtVerkaufteMenge = new JTextField();
		txtVerkaufteMenge.setEditable(false);
		txtVerkaufteMenge.setBounds(180, 100, 96, 19);
		contentPane.add(txtVerkaufteMenge);
		txtVerkaufteMenge.setColumns(10);
		
		txtKosten = new JTextField();
		txtKosten.setEditable(false);
		txtKosten.setBounds(180, 123, 96, 19);
		contentPane.add(txtKosten);
		txtKosten.setColumns(10);
		
		txtUmsatz = new JTextField();
		txtUmsatz.setEditable(false);
		txtUmsatz.setBounds(180, 146, 96, 19);
		contentPane.add(txtUmsatz);
		txtUmsatz.setColumns(10);
		
		txtGewinn = new JTextField();
		txtGewinn.setEditable(false);
		txtGewinn.setBounds(180, 169, 96, 19);
		contentPane.add(txtGewinn);
		txtGewinn.setColumns(10);
		
		txtEigenkapital = new JTextField();
		txtEigenkapital.setEditable(false);
		txtEigenkapital.setBounds(180, 192, 96, 19);
		contentPane.add(txtEigenkapital);
		txtEigenkapital.setColumns(10);
		
		txtLagerbestand = new JTextField();
		txtLagerbestand.setEditable(false);
		txtLagerbestand.setBounds(180, 213, 96, 19);
		contentPane.add(txtLagerbestand);
		txtLagerbestand.setColumns(10);
		
		lblCountToBuy = new JLabel("Count to buy");
		lblCountToBuy.setBounds(20, 280, 150, 13);
		contentPane.add(lblCountToBuy);
		
		txtCountToBuy = new JTextField();
		txtCountToBuy.setBounds(180, 277, 96, 19);
		contentPane.add(txtCountToBuy);
		txtCountToBuy.setColumns(10);
		
		btnNewButton = new JButton("BUY!");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int eingekaufteMenge = Integer.parseInt(txtCountToBuy.getText());

				double kosten_for_haenlder = produkt.kaufeEin(eingekaufteMenge);

				int kunde_wants = kunde.frageAn();
				int kunde_wants_and_gets = produkt.frageAn(kunde_wants);

				double kosten_for_kunde = produkt.verkaufe(kunde_wants_and_gets);
				double gewinn = haendler.verbucheErgebnis(kosten_for_kunde, kosten_for_haenlder);

				txtAngefragteMenge.setText(String.valueOf(eingekaufteMenge));
				txtVerkaufteMenge.setText(String.valueOf(kunde_wants_and_gets));
				txtKosten.setText(String.valueOf(kosten_for_haenlder));
				txtUmsatz.setText(String.valueOf(kosten_for_kunde));
				txtGewinn.setText(String.valueOf(gewinn));
				txtEigenkapital.setText(String.valueOf(haendler.liesEigenkapital()));
				txtLagerbestand.setText(String.valueOf(produkt.liesLagerbestand()));

				if (haendler.istPleite())
				{
					JOptionPane.showMessageDialog(null, "Sie haben kein Eigenkapital mehr.", "Ende Ihres Unternehmens!", JOptionPane.ERROR_MESSAGE);

					txtAngefragteMenge.setText("");
					txtVerkaufteMenge.setText("");
					txtKosten.setText("");
					txtUmsatz.setText("");
					txtGewinn.setText("");
					txtEigenkapital.setText("");
					txtLagerbestand.setText("");
					txtCountToBuy.setText("");

					haendler = new Haendler();
					kunde = new Kunde(0, 99);
					produkt = new Produkt(1.50, 2.00);
				}
			}
		});
		btnNewButton.setBounds(10, 315, 266, 21);
		contentPane.add(btnNewButton);
	}
}
