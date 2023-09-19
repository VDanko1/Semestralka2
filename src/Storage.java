
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda slúži na spracovanie údajov z loginu - zápis a najdenie
 * zamestnanca zo súboru.
 */
public class Storage {
    private final File subor;
    private String typUzivatela;

    public Storage() {
        this.subor = new File("MenaHesla.txt");
        this.typUzivatela = "";
    }

    /**
     * Zobere údaje z Login aplikácie a zapíše ich do súboru
     * aj s hodnosťou uživatela. Overuje si pomocou metody najdiMeno či už neni
     * uživatel zaregistrovaný.
     */
    public boolean zapisUzivatelaDoSuboru(Uzivatel uzivatel) throws FileNotFoundException {
        try {
            FileWriter zapisovac = new FileWriter(this.subor, true);
            if (this.najdiMeno(uzivatel.getMeno())) {
                return false;
            } else {
                zapisovac.write(String.format("%n"));
                zapisovac.write(uzivatel.getMeno() + "," + uzivatel.getHeslo() + "," + uzivatel.getTyp());
                zapisovac.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * Rozdelí mi najdeny riadok na tri polia podľa "," a porovnávam či udaje z loginu su spravne
     * podla toho vrati true/false a otvorí sa aplikacia so zamestnancami.
     */
    public boolean najdiUzivatelaVSubore(Uzivatel uzivatel) throws FileNotFoundException {
        Scanner scan = new Scanner(this.subor);
        while (scan.hasNextLine()) {
            String najdenyUzivatel = scan.nextLine();
            String[] riadokRozdeleny = najdenyUzivatel.split(",");
            String meno = riadokRozdeleny[0];
            String heslo = riadokRozdeleny[1];
            this.typUzivatela = riadokRozdeleny[2];
            if ((uzivatel.getMeno().equals(meno) && uzivatel.getHeslo().equals(heslo))) {
                return true;
            }
        }
        return false;
    }

    public boolean najdiMeno(String meno) throws FileNotFoundException {
        /**
         * Overovanie toho či je zamestnanec už registrovaný.
         */
        Scanner scan = new Scanner(this.subor);
        while (scan.hasNextLine()) {
            String najdenyUzivatel = scan.nextLine();
            String najdeneMeno = najdenyUzivatel.split(",")[0];
            if (najdeneMeno.equals(meno)) {
                return true;
            }
        }
        return false;
    }

    public String dajHodnostUzivatela() {
        return this.typUzivatela;
    }

}
