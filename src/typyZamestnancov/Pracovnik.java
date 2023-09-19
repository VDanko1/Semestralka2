package typyZamestnancov;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda pracovník so žiadnymi pravomocami
 */
public class Pracovnik extends Zamestnanec {
    private final int mzda;

    public Pracovnik(String id, String meno) {
        super(id, meno, 3, Typy.PRACOVNIK);
        this.mzda = super.getRandom().nextInt(300) + 700;
    }

    public int getMzda() {
        return this.mzda;
    }

    @Override
    public String vypis() {
        return "Ja som roboš " + this.getMeno() + " " + this.mzda + " " + this.getId();
    }
}
