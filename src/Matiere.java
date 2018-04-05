
public class Matiere {

    private double densite;
    private String nom;

    public Matiere(int nindentification) {
        switch (nindentification) {
            case 1:
                this.densite = 0.450;
                this.nom = "bois";
                break;
            case 2:
                this.densite = 8.2;
                this.nom = "acier";
                break;
            default:
                this.densite = 2.4;
                this.nom = "beton";
                break;
        }
    }

    public double getDensite() {
        return densite;
    }

}
