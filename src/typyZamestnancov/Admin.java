package typyZamestnancov;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Tried Admin môže mazať/pridávať zamestnancov.
 */
public class Admin extends Zamestnanec {
    private final int mzda;

    public Admin(String id, String meno) {
        super(id, meno, 4, Typy.ADMIN );
        this.mzda = super.getRandom().nextInt(700) + 1100;
    }

    public int getMzda() {
        return this.mzda;
    }

    @Override
    public String vypis() {
        return "Ja som Admin " + this.getMeno() + " " + this.mzda + " " + this.getId();
    }
}

