import java.awt.*;

/**
 *
 */
public class Matiere {

    private double densite;
    private String nom;
    private Color c;

    /**
     * @param nindentification
     */
    public Matiere(int nindentification) {
        switch (nindentification) {
            case 1:
                this.densite = 0.450;
                this.nom = "bois";
                this.c = new Color(171, 100, 46);
                break;
            case 2:
                this.densite = 8.2;
                this.nom = "acier";
                this.c = new Color(192, 192, 192);
                break;
            default:
                this.densite = 2.4;
                this.nom = "beton";
                this.c = new Color(100, 100, 100);
                break;
        }
    }

    /**
     * @return
     */
    public double getDensite() {
        return densite;
    }

    /**
     * @return
     */
    public Color getColor() {
        return c;
    }
}
