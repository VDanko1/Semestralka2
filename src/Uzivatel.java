import typyZamestnancov.Typy;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda slúži ako objektová reprezentácia uživatelov
 * zapísaných v súbore (prihlasovacie udaje...).
 */
public class Uzivatel {
    private String meno;
    private String heslo;
    private Typy typ;

    public Uzivatel(String meno, String heslo) {
        this.meno = meno;
        this.heslo = heslo;
    }

    public Uzivatel() {

    }

    public String getMeno() {
        return this.meno;
    }

    public String getHeslo() {
        return this.heslo;
    }

    public Typy getTyp() {
        return this.typ;
    }

    public void setTyp(Typy typ) {
        this.typ = typ;
    }
}
