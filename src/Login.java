import typyZamestnancov.Typy;
import typyZamestnancov.Zamestnanec;


import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda Login slúži ako GUI pre prihlásenie sa do informačného
 * systému tohto projektu. Celé GUI je vytvorené pomocou Formu ktorý ponúka
 * InteliJ. Pri spustení aplikácie nám vybehne malé okiénko kde zadáme meno, heslo
 * a vyberieme si hodnosť a dáme registrovať. Následne to zapíše do súboru a môžme sa prihlásiť.
 * V prípade že sme registrovaný napišeme iba meno a heslo - hodnosť nevyberáme je v súbore.
 */
public class Login extends JDialog {

    private JLabel icon;
    private JTextField idField;
    private JPasswordField passwordField1;
    private JLabel passLabel;
    private JLabel idLabel;
    private JLabel registerlabel;
    private JButton registerButton;
    private JButton loginButton;
    private JPanel loginPanel;
    private JLabel messageLabel;
    private JComboBox comboBox1;
    private final Storage storage;
    private Zamestnanec emp;
    private JLabel back;

    public Login(JFrame parent) {
        /**
         * Konstruktor tohto login okna - nastavujeme velkosť, ikonku v rohu, nadpis atď..
         */
        super (parent);
        this.setTitle("Prihlasenie do systemu");
        this.setContentPane(this.loginPanel);
        this.setSize(550, 500);
        this.setVisible(true);
        this.setResizable(false);
        this.storage = new Storage();
        ImageIcon ikona = new ImageIcon ("src/Obrazky/admin.png");
        this.setIconImage(ikona.getImage());

        this.comboBox1.addItem(Typy.ADMIN);
        this.comboBox1.addItem(Typy.MANAGER);
        this.comboBox1.addItem(Typy.PRACOVNIK);

        /**
         * Tlačítko prihlásenia, porovnava meno, heslo zadané v login okienku
         * s údajmi zo súboru. Sú ošetrené aj niektoré vstupy a výpisy chýb.
         */
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (Login.this.idField.getText().isBlank() || Login.this.passwordField1.equals("")) {
                    Login.this.messageLabel.setText ("Zly login!");
                    Login.this.messageLabel.setForeground (Color.red);
                } else {
                    try {
                        boolean uzivatelExistuje = Login.this.storage.najdiUzivatelaVSubore(new Uzivatel(Login.this.idField.getText(),
                                String.valueOf(Login.this.passwordField1.getPassword())));
                        if (uzivatelExistuje) {
                            String hodnost = Login.this.storage.dajHodnostUzivatela();
                            Login.this.dispose();
                            ApplicationAdmin admin = new ApplicationAdmin(null);
                            admin.nahodnyZoSuboru();
                            switch (hodnost) {
                                case "ADMIN":
                                    admin.setUzivatela(Typy.ADMIN);
                                    break;
                                case "MANAGER":
                                    admin.setUzivatela(Typy.MANAGER);
                                    break;
                                default:
                                    admin.setUzivatela(Typy.PRACOVNIK);
                            }
                        } else {
                            Login.this.messageLabel.setText("Uzivatel neexistuje");
                            Login.this.messageLabel.setForeground(Color.red);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        /**
         * Registračné tlačítko, načíta udaje z login okienka a zapíše ich do súboru.
         * Sú tu ošetrené aj vstupy napr. heslo dlhšie ako 6 znakov alebo ak
         * uživatel s takym menom už existuje - vypíše informačný výpis.
         */
        this.registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                String id = Login.this.idField.getText().trim();
                String pass = new String(Login.this.passwordField1.getPassword()).trim();
                Typy typ = (Typy) Login.this.comboBox1.getSelectedItem();

                if (pass.length() < 6) {
                    Login.this.messageLabel.setText("Heslo musí mať viac ako 6 znakov!");
                    Login.this.messageLabel.setForeground(Color.RED);
                } else {
                    try {
                        Uzivatel uzivatel = new Uzivatel(id, pass);
                        uzivatel.setTyp(typ);
                        if (!Login.this.storage.zapisUzivatelaDoSuboru(uzivatel)) {
                            Login.this.messageLabel.setForeground(Color.RED);
                            Login.this.messageLabel.setText("Uzivatel s tymto menom existuje!");
                        } else {
                            Login.this.messageLabel.setForeground(Color.green);
                            Login.this.messageLabel.setText("Registracia uspesna!");
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        Login log = new Login(null);
    }

}
