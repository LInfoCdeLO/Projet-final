import java.awt.*;

/**
 * classe permettant de definir la matiere qui compose les poutres ainsi que les caracteristiques associees tel que la densite ou la couleur.
 */
public class Matiere {

    private double densite; //densite de la poutre
    private String nom; //nom du materiaux
    private Color c; //couleur du materiaux

    /**
     * initialise la densite, le nom et la couleur en fonction du parametre
     *
     * @param nindentification 1 pour le bois, 2 pour l acier, 3 pour le beton
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
     * @return la valeur de la densite
     */
    public double getDensite() {
        return densite;
    }

    /**
     * @return la couleur du materiaux
     */
    public Color getColor() {
        return c;
    }
}
