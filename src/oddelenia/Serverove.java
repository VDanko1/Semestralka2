package oddelenia;

import typyZamestnancov.Zamestnanec;

import java.util.Date;
import java.util.TimerTask;
/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda serverove, zaraba najpomalsie z oddelení.
 */
public class Serverove extends Oddelenie {
    private final TimerTask timerTask;

    public Serverove() {
        super(2);
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                Serverove.this.pracuj();
            }
        };
        super.dajTimer().schedule(this.timerTask, new Date(), 2000);
    }

    public Zamestnanec dajZamestnanca(String meno) {
        return this.getUloziskoZamestnanci().dajZamestnanca(meno);
    }

}
