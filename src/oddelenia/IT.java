package oddelenia;

import typyZamestnancov.Zamestnanec;

import java.util.Date;
import java.util.TimerTask;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda IT pracuje zo všetkých najrýchlejšie. Má najvyšši koeficient zárobku
 *
 */
public class IT extends Oddelenie {
    private final TimerTask timerTask;

    public IT() {
        super(4);
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                IT.this.pracuj();
            }
        };
        super.dajTimer().schedule(this.timerTask, new Date(), 2000);
    }

    public Zamestnanec dajZamestnanca(String meno) {
        return this.getUloziskoZamestnanci().dajZamestnanca(meno);
    }

}
