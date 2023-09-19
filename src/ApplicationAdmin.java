import oddelenia.Administrativa;
import oddelenia.IT;
import oddelenia.Serverove;
import typyZamestnancov.Admin;
import typyZamestnancov.Zamestnanec;
import typyZamestnancov.Typy;
import typyZamestnancov.Manager;
import typyZamestnancov.Pracovnik;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Po prihláseni sa pomocou okienka Login sa dostaneme do tejto triedy
 * taktiež reprentovanej pomocou GUIka. V tejto triede podľa toho
 * akú hodnosť máme - Admin, Manager, Pracovník môžme upravovať zamestnancov.
 * Admin môže všetkych (odoberať, pridávať), Manager iba pracovníkov (-||-).
 * Navod na obsluhu - Naklikáme si hodnosť a kam chceme zamestnanca pridať a následne
 * napíšeme jeho meno a zaklikneme pridať.
 * Odobrať môžeme až po kliknutí na daneho pracovnika.
 * Po otvorení budú náhodní zamestnanci zo súboru pridelení do oddelení.
 * Trieda obsahuje aj timer, ktory každe dve sekundy vola metodu pracuj
 * pomocou ktorej sa pridáva do rozpočtu - čiastka ktorá pribúda
 * záleží od počtu a hodnosti zamestnancov.
 */
public class ApplicationAdmin extends JDialog {

    private JPanel mainPanel;
    private JList zoznamITzamestnancov;
    private JButton odoberTlacitko;
    private JButton pridajTlacitko;
    private JList zoznamAdministrativaZamestnanci;
    private JLabel rozpocetIT;
    private JLabel oznamRozpocetIT;
    private JLabel rozpocetAdministrativa;
    private JLabel rozpocetServerove;
    private JLabel oznamRozpocetServerove;
    private JLabel oznamRozpocetAdministrativa;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JComboBox comboBox2;
    private JList zoznamServeroveZamestnanci;
    private JLabel informacieOPridani;
    private final Serverove serverove;
    private final Administrativa administrativa;
    private final IT it;
    private final DefaultListModel listIT;
    private final DefaultListModel listAdministrativa;
    private final DefaultListModel listServerove;
    private final Timer timer;
    private final Uzivatel uzivatel;

    public ApplicationAdmin(JFrame parent) {
        super(parent);
        this.setTitle("Informacny system");
        this.setContentPane(this.mainPanel);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setResizable(false);
        this.listIT = new DefaultListModel();
        this.listAdministrativa = new DefaultListModel();
        this.listServerove = new DefaultListModel();

        ImageIcon ikona = new ImageIcon ("src/Obrazky/admin.png");
        this.setIconImage(ikona.getImage());

        this.comboBox1.addItem("Pracovnik");
        this.comboBox1.addItem("Admin");
        this.comboBox1.addItem("Manager");

        this.comboBox2.addItem("IT");
        this.comboBox2.addItem("Serverove");
        this.comboBox2.addItem("Administrativa");

        this.serverove = new Serverove();
        this.administrativa = new Administrativa();
        this.it = new IT();

        this.uzivatel = new Uzivatel();

        this.rozpocetIT.setText(Double.toString(this.it.dajRozpocet()));
        this.rozpocetServerove.setText(Double.toString(this.serverove.dajRozpocet()));
        this.rozpocetAdministrativa.setText(Double.toString(this.administrativa.dajRozpocet()));

        this.timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationAdmin.this.rozpocetIT.setText(Integer.toString(ApplicationAdmin.this.it.dajRozpocet()));
                ApplicationAdmin.this.rozpocetServerove.setText(Integer.toString(ApplicationAdmin.this.serverove.dajRozpocet()));
                ApplicationAdmin.this.rozpocetAdministrativa.setText(Integer.toString(ApplicationAdmin.this.administrativa.dajRozpocet()));
                ApplicationAdmin.this.serverove.zvysRozpocet();
                ApplicationAdmin.this.administrativa.zvysRozpocet();
                ApplicationAdmin.this.it.zvysRozpocet();
            }
        });
        this.timer.start();

        /**
         * Odobere nám zakliknutého zamestnanca. Porovnáva našu hodnosť
         * s jeho hodnosťou - Admin môže všetkých, manager len pracovníkov.
         * V prípade neoprávneného odobratia vypíše výpis.
         */
        this.odoberTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Typy hodnostUzivatela = ApplicationAdmin.this.uzivatel.getTyp();
                String clovekZIT = (String) ApplicationAdmin.this.zoznamITzamestnancov.getSelectedValue();
                String clovekZAdminu = (String) ApplicationAdmin.this.zoznamAdministrativaZamestnanci.getSelectedValue();
                String clovekZServeroveho = (String) ApplicationAdmin.this.zoznamServeroveZamestnanci.getSelectedValue();
                String hodnostClovekaZAdminu;
                String hodnostClovekaZServeroveho;
                String hodnostClovekaZIT;

                switch (hodnostUzivatela) {
                    case ADMIN:
                        if (clovekZIT != null) {
                            ApplicationAdmin.this.it.odoberZamestnanca(clovekZIT);
                            ApplicationAdmin.this.listIT.removeElement(clovekZIT);
                            ApplicationAdmin.this.zoznamITzamestnancov.setModel(ApplicationAdmin.this.listIT);

                        } else if (clovekZAdminu != null) {
                            ApplicationAdmin.this.administrativa.odoberZamestnanca(clovekZAdminu);
                            ApplicationAdmin.this.listAdministrativa.removeElement(clovekZAdminu);
                            ApplicationAdmin.this.zoznamAdministrativaZamestnanci.setModel(ApplicationAdmin.this.listAdministrativa);

                        } else if (clovekZServeroveho != null) {
                            ApplicationAdmin.this.serverove.odoberZamestnanca(clovekZServeroveho);
                            ApplicationAdmin.this.listServerove.removeElement(clovekZServeroveho);
                            ApplicationAdmin.this.zoznamServeroveZamestnanci.setModel(ApplicationAdmin.this.listServerove);
                        }
                        break;
                    case MANAGER:
                        if (clovekZIT != null) {
                            hodnostClovekaZIT = clovekZIT.split(", " )[1];
                            if (hodnostClovekaZIT.equals("PRACOVNIK")) {
                                ApplicationAdmin.this.it.odoberZamestnanca(clovekZIT);
                                ApplicationAdmin.this.listIT.removeElement(clovekZIT);
                                ApplicationAdmin.this.zoznamITzamestnancov.setModel(ApplicationAdmin.this.listIT);
                                break;
                            }
                        } else if (clovekZAdminu != null) {
                            hodnostClovekaZAdminu = clovekZAdminu.split(", ")[1];
                            if (hodnostClovekaZAdminu.equals("PRACOVNIK")) {
                                ApplicationAdmin.this.administrativa.odoberZamestnanca(clovekZAdminu);
                                ApplicationAdmin.this.listAdministrativa.removeElement(clovekZAdminu);
                                ApplicationAdmin.this.zoznamAdministrativaZamestnanci.setModel(ApplicationAdmin.this.listAdministrativa);
                                break;
                            }
                        } else if (clovekZServeroveho != null) {
                            hodnostClovekaZServeroveho = clovekZServeroveho.split(", ")[1];
                            if (hodnostClovekaZServeroveho.equals("PRACOVNIK")) {
                                ApplicationAdmin.this.serverove.odoberZamestnanca(clovekZServeroveho);
                                ApplicationAdmin.this.listServerove.removeElement(clovekZServeroveho);
                                ApplicationAdmin.this.zoznamServeroveZamestnanci.setModel(ApplicationAdmin.this.listServerove);
                                break;
                            }
                        }
                        break;
                    default:
                        ApplicationAdmin.this.informacieOPridani.setText("Nemáte oprávenenie odoberať zamestnancov!");
                        ApplicationAdmin.this.informacieOPridani.setForeground(Color.red);
                        break;
                }
            }
        });
        /**
        * Po napisani mena a vyklikaní - kam a aku hodnosť bude mať
        * nam prida zamestnanca. Ošetrený vstup - meno musí mať minimálne
        * jeden znak a taktiež pridávať môže len Admin.
        */
        this.pridajTlacitko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Typy hodnostUzivatela = ApplicationAdmin.this.uzivatel.getTyp();
                String menoZamestnanca = ApplicationAdmin.this.textField1.getText();
                String akeOddelenie = (String) ApplicationAdmin.this.comboBox2.getSelectedItem();
                String hodnostZamestnanca = (String) ApplicationAdmin.this.comboBox1.getSelectedItem();
                Zamestnanec novy;
                switch (hodnostZamestnanca) {
                    case "Pracovnik": //porovnavame typy zamestnanca
                        if (akeOddelenie.equals("IT")) {
                            novy = new Pracovnik(Integer.toString(ApplicationAdmin.this.it.dajVelkost() + 1), menoZamestnanca);
                            break;
                        } else {
                            novy = new Pracovnik(Integer.toString(ApplicationAdmin.this.serverove.dajVelkost() + 1), menoZamestnanca);
                            break;
                        }
                    case "Manager":
                        if (akeOddelenie.equals("Serverove")) {
                            novy = new Manager(Integer.toString(ApplicationAdmin.this.serverove.dajVelkost() + 1), menoZamestnanca);
                            break;
                        } else {
                            novy = new Manager(Integer.toString(ApplicationAdmin.this.it.dajVelkost() + 1) , menoZamestnanca);
                            break;
                        }
                    default:
                        novy = new Admin(Integer.toString(ApplicationAdmin.this.administrativa.dajVelkost() + 1), menoZamestnanca);
                }
                if (hodnostUzivatela.equals(Typy.ADMIN)) {
                    if (!menoZamestnanca.isBlank()) {
                        ApplicationAdmin.this.informacieOPridani.setText("Úspešne ste pridali zamestnanca " + menoZamestnanca + ", " +
                                hodnostZamestnanca);
                        ApplicationAdmin.this.informacieOPridani.setForeground(Color.green);
                        switch (akeOddelenie) {
                            case "IT":
                                if (ApplicationAdmin.this.listIT.size() < ApplicationAdmin.this.it.dajKapacitu()) {
                                    ApplicationAdmin.this.it.pridajZamestnanca(novy);
                                    ApplicationAdmin.this.listIT.addElement(novy.getMeno() + ", " + novy.getTyp());
                                    ApplicationAdmin.this.zoznamITzamestnancov.setModel(ApplicationAdmin.this.listIT);
                                }
                                break;
                            case "Serverove":
                                if (ApplicationAdmin.this.listServerove.size() < ApplicationAdmin.this.serverove.dajKapacitu()) {
                                    ApplicationAdmin.this.serverove.pridajZamestnanca(novy);
                                    ApplicationAdmin.this.listServerove.addElement(novy.getMeno() + ", " + novy.getTyp());
                                    ApplicationAdmin.this.zoznamServeroveZamestnanci.setModel(ApplicationAdmin.this.listServerove);
                                }
                                break;
                            case "Administrativa":
                                if (ApplicationAdmin.this.listAdministrativa.size() < ApplicationAdmin.this.administrativa.dajKapacitu()) {
                                    ApplicationAdmin.this.administrativa.pridajZamestnanca(novy);
                                    ApplicationAdmin.this.listAdministrativa.addElement(novy.getMeno() + ", " + novy.getTyp());
                                    ApplicationAdmin.this.zoznamAdministrativaZamestnanci.setModel(ApplicationAdmin.this.listAdministrativa);
                                    break;
                                }
                        }
                    } else {
                        ApplicationAdmin.this.informacieOPridani.setText("Meno musí obsahovať minimálne jeden znak!");
                        ApplicationAdmin.this.informacieOPridani.setForeground(Color.red);
                    }
                } else {
                    ApplicationAdmin.this.informacieOPridani.setText("Nemáte oprávnenie pridávať zamestnancov!");
                    ApplicationAdmin.this.informacieOPridani.setForeground(Color.red);
                }
            }
        });
        /**
         * MouseListener nám zabezpečuje že ked klikenem na zamestnanca
         * zobrazí sa nám pomocné okienko ktoré obsahuje základné informácie o zamestnancovi.
         * Mzdu bežne nevidíme- až po rozkliknutí.
         */
        this.zoznamITzamestnancov.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JList tlistIT = (JList)e.getSource();
                Object objekt = tlistIT.getSelectedValue();
                String vypisZamestnanca = objekt.toString();
                String[] riadokRozdeleny = vypisZamestnanca.split(",");
                Zamestnanec zamestnanec = ApplicationAdmin.this.it.dajZamestnanca(riadokRozdeleny[0]);
                VyskakovacieOkno okno = new VyskakovacieOkno(null);
                okno.nastavText(objekt + ", ID: " + zamestnanec.getId() + ", Mzda: " + zamestnanec.getMzda());
            }
        });
        this.zoznamAdministrativaZamestnanci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JList tlistIT = (JList)e.getSource();
                Object objekt = tlistIT.getSelectedValue();
                String vypisZamestnanca = objekt.toString();
                String[] riadokRozdeleny = vypisZamestnanca.split(",");
                Zamestnanec zamestnanec = ApplicationAdmin.this.administrativa.dajZamestnanca(riadokRozdeleny[0]);
                VyskakovacieOkno okno = new VyskakovacieOkno(null);
                okno.nastavText(objekt + ", ID: " + zamestnanec.getId() + ", Mzda: " + zamestnanec.getMzda());
            }
        });
        this.zoznamServeroveZamestnanci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JList tlistIT = (JList)e.getSource();
                Object objekt = tlistIT.getSelectedValue();
                String vypisZamestnanca = objekt.toString();
                String[] riadokRozdeleny = vypisZamestnanca.split(",");
                Zamestnanec zamestnanec = ApplicationAdmin.this.serverove.dajZamestnanca(riadokRozdeleny[0]);
                VyskakovacieOkno okno = new VyskakovacieOkno(null);
                okno.nastavText(objekt + ", ID: " + zamestnanec.getId() + ", Mzda: " + zamestnanec.getMzda());
            }
        });
    }

    public static void main(String[] args) {
        ApplicationAdmin admin = new ApplicationAdmin(null);
    }

    public void setUzivatela(Typy typ) {
        this.uzivatel.setTyp(typ);
    }
    /**
     * Náhodné mená z internetu zapísané v súbore, po načitaní sa náhodne pridajú do
     * jednotlivých oddelení s nahodnymi hodnostami.
     */
    public void nahodnyZoSuboru() throws FileNotFoundException {
        Random random = new Random();
        File menaPracacovnikov = new File("MenaPracovnici.txt");
        Scanner scan = new Scanner(menaPracacovnikov);
        do {
            int randomCislo = random.nextInt(3);
            boolean randomTyp = random.nextBoolean();
            String menoNaZapis = scan.nextLine();
            Zamestnanec novy;
            if (randomTyp) {
                novy = new Pracovnik(Integer.toString(random.nextInt(10)), menoNaZapis);
            } else {
                novy = new Manager(Integer.toString(random.nextInt(10)), menoNaZapis);
            }
            switch (randomCislo) {
                case 0:
                    this.it.pridajZamestnanca(novy);
                    this.listIT.addElement(novy.getMeno() + ", " + novy.getTyp());
                    this.zoznamITzamestnancov.setModel(this.listIT);
                    break;
                case 1:
                    this.serverove.pridajZamestnanca(novy);
                    this.listServerove.addElement(novy.getMeno() + ", " + novy.getTyp());
                    this.zoznamServeroveZamestnanci.setModel(this.listServerove);
                    break;
                case 2:
                    this.administrativa.pridajZamestnanca(novy);
                    this.listAdministrativa.addElement(novy.getMeno() + ", " + novy.getTyp());
                    this.zoznamAdministrativaZamestnanci.setModel(this.listAdministrativa);
                    break;
            }
        } while (scan.hasNextLine());
    }
}
