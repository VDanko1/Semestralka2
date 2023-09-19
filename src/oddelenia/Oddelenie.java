package oddelenia;

import typyZamestnancov.Zamestnanec;

import java.util.Timer;
import java.util.Random;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Abstraktna trieda ako šablona pre ostatné oddelenia, všetky
 * spoločné veci čo oddelenie má sa budú nachádzať tu.
 * Obsahuje tiká každé dve sekundy - vola metodu pracuj ta zabezpečuje
 * že sa rozpočet bude zvyšovať od počtu zamestnancov.
 *
 */
public abstract class Oddelenie {
    private final UloziskoZamestnanci uloziskoZamestnanci;
    private final Random random;
    private final int koeficientPrace;
    private final Timer timer;
    private int rozpocet;


    public Oddelenie(int koeficientPrace) {
        this.timer = new Timer();
        this.koeficientPrace = koeficientPrace;
        this.uloziskoZamestnanci = new UloziskoZamestnanci();
        this.random = new Random();
    }

    public void pracuj() {
        this.rozpocet += (this.dajKoeficientZamestnancov() * this.koeficientPrace);
    }

    public Timer dajTimer() {
        return this.timer;
    }

    public UloziskoZamestnanci getUloziskoZamestnanci() {
        return this.uloziskoZamestnanci;
    }

    public double dajKoeficientZamestnancov() {
        return this.uloziskoZamestnanci.dajKoeficientZamestnancov();
    }

    public int dajKapacitu() {
        return this.uloziskoZamestnanci.dajKapacitu();
    }

    public void zvysRozpocet() {
        this.rozpocet += this.uloziskoZamestnanci.dajKoeficientZamestnancov();
    }

    public int dajRozpocet() {
        return this.rozpocet;
    }

    public void odoberZamestnanca(String id) {
        this.uloziskoZamestnanci.odoberZamestnanca(id);
    }


    public void pridajZamestnanca(Zamestnanec zamestnanec) {
        this.uloziskoZamestnanci.pridajZamestnanca(zamestnanec);
    }


    public int dajVelkost() {
        return this.uloziskoZamestnanci.dajVelkost();
    }

}
