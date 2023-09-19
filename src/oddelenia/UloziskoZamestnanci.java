package oddelenia;

import typyZamestnancov.Zamestnanec;
import java.util.ArrayList;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Každé oddelenie si bude držať zoznam svojich zamestnancov.
 * Su tu metody na pridavanie/odoberanie a ziskanie koeficientu zamestnanca.
 */
public class UloziskoZamestnanci {
    private final ArrayList<Zamestnanec> zamestnanci;
    private final int kapacita;

    public UloziskoZamestnanci() {
        this.zamestnanci = new ArrayList <> ();
        this.kapacita = 15;
    }

    public void odoberZamestnanca(String id) {
        this.zamestnanci.removeIf(z -> z.getMeno().equals(id));
    }

    public int dajKapacitu() {
        return this.kapacita;
    }

    public void pridajZamestnanca(Zamestnanec zamestnanec) {
        if (this.zamestnanci.size() <= this.kapacita) {
            this.zamestnanci.add(zamestnanec);
        }
    }

    public Zamestnanec dajZamestnanca(String meno) {
        /**
         * Vráti zamestnanca na základe Mena.
         */
        Zamestnanec zamestnanec = null;
        for (Zamestnanec z : this.zamestnanci) {
            if (z.getMeno().equals(meno)) {
                zamestnanec = z;
            }
        }
        return zamestnanec;
    }

    public int dajKoeficientZamestnancov()  {
        /**
         * Vráti súčet koeficientov zamestnancov. Podla toho aky je
         * toľko sa bude zvyšovať rozpočet.
         */
        int koef  = 1;
        if (this.zamestnanci.isEmpty()) {
            return koef;
        } else {
            for (Zamestnanec z : this.zamestnanci) {
                koef += z.getKoeficientPrace();
            }
        }
        return koef;
    }

    public int dajVelkost() {
        return this.zamestnanci.size();
    }

}
