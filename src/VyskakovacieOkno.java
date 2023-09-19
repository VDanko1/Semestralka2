import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * 1. 5. 2022 - 13:20
 *
 * @author Danko Vladimír
 *
 * Trieda slúži ako informačné okienko, tzn. ked klikneme
 * zamestnanca, vyskočí nám okienko kde je jeho meno, typ zamestnanca,
 * jeho ID a mzda.
 *
 */
public class VyskakovacieOkno extends JDialog {
    private JLabel informacie;
    private JPanel mainPanel;

    public VyskakovacieOkno(JFrame parent) {
        super(parent);
        this.setTitle("Informacie");
        this.setContentPane(this.mainPanel);
        this.setSize(300, 100);
        this.setVisible(true);
        this.setResizable(false);
        ImageIcon ikona = new ImageIcon ("src/Obrazky/informacia.png");
        this.setIconImage(ikona.getImage());

    }

    public void nastavText(String text) {
        this.informacie.setText(text);
    }
}
