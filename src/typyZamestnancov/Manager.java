package typyZamestnancov;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda Manager dedí zamestnanca, v hierarchí zamestnancov ma stredné
 * právomoci.
 */
public class Manager extends Zamestnanec {
    private final int mzda;

    public Manager(String id, String meno) {
        super(id, meno, 3, Typy.MANAGER);
        this.mzda = super.getRandom().nextInt(700) + 500;
    }

    public int getMzda() {
        return this.mzda;
    }

    @Override
    public String vypis() {
        return "Ja som manažér " + this.getMeno() + " " + this.mzda + " " + this.getId();
    }
}
