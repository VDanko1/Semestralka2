package oddelenia;
import typyZamestnancov.Zamestnanec;

import java.util.Date;
import java.util.TimerTask;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Jedno z oddelení. Ma stredný koeficient zárobku.
 *
 */
public class Administrativa extends Oddelenie {
    private final TimerTask timerTask;

    public Administrativa() {
        super(3);
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                Administrativa.this.pracuj();
            }
        };
        super.dajTimer().schedule(this.timerTask, new Date(), 2000);
    }

    public Zamestnanec dajZamestnanca(String meno) {
        return this.getUloziskoZamestnanci().dajZamestnanca(meno);
    }

}
