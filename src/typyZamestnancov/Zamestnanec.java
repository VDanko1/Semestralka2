package typyZamestnancov;

import java.util.Random;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Tato trieda slúži ako šablona pre konkretnych zamestnancov - pracovnik
 * admin, manager. Všetky spoločné veci budú tu.
 *
 */
public abstract class Zamestnanec {
    private final String id;
    private final String meno;
    private final int koeficientPrace;
    private final Typy typ;
    private final Random random;
    private final int mzda;


    public Zamestnanec(String id, String meno, int koeficientPrace, Typy typ) {
        this.id = id;
        this.meno = meno;
        this.koeficientPrace = koeficientPrace;
        this.typ = typ;
        this.random = new Random();
        this.mzda = this.random.nextInt(500) + 700;
    }

    public int getMzda () {
        return this.mzda;
    }

    public Random getRandom() {
        return this.random;
    }

    public String getId() {
        return this.id;
    }

    public String getMeno() {
        return this.meno;
    }

    public int getKoeficientPrace() {
        return this.koeficientPrace;
    }

    public abstract String vypis();

    public Typy getTyp() {
        return this.typ;
    }
}
