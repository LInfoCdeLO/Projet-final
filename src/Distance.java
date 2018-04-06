import java.awt.*;

/**
 * Classe qui represente une poutre caracterisee par deux points et quelques parametres
 */
public class Distance implements Comparable<Distance> {
    private Point p1; //point 1 de la poutre
    private Point p2; //point 2 de la poutre
    private Matiere matierePoutre; //matiere de la poutre
    private double poids; //poids de la poutre
    private double longueur; //longueur de la poutre
    private double angle; //angle que fait la poutre avec l'horizontale
    private Point barycentre; //point representant le barycentre de la poutre

    /**
     * Constructeur
     *
     * @param p1              point 1 de la poutre
     * @param p2              point 2 de la poutre
     * @param numeroMateriaux numero qui permet de creer un materiaux associe
     */
    public Distance(Point p1, Point p2, int numeroMateriaux) {
        this.p1 = p1;
        this.p2 = p2;
        this.matierePoutre = new Matiere(numeroMateriaux);
        this.longueur = p1.distance(p2);
        this.barycentre = new Point((int) ((p1.getX() + p2.getX()) / 2), (int) ((p1.getY() + p2.getY()) / 2));
        this.poids = this.longueur * 5 * 16.0 / 20.0 * this.matierePoutre.getDensite();
    }

    /**
     * Change le point 1
     *
     * @param p1 un point
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * Change le point 2
     *
     * @param p2 un point
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    /**
     * @return le point 1
     */
    public Point getP1() {
        return this.p1;
    }

    /**
     * @return le point 2
     */
    public Point getP2() {
        return this.p2;
    }

    /**
     * @return le point du barycentre
     */
    public Point getBarycentre() {
        return this.barycentre;
    }

    /**
     * @return la longueur de la poutre
     */
    public double getLongueur() {
        return this.longueur;
    }

    /**
     * @return le poids de la poutre
     */
    public double getPoids() {
        return this.poids;
    }

    /**
     * Change le materiau et le poids de la poutre
     *
     * @param a un entier correspondant a un materiaux
     */
    public void setMatierePoutre(int a) {
        this.matierePoutre = new Matiere(a);
        this.poids = this.longueur * 5 * 16.0 / 20.0 * this.matierePoutre.getDensite();
    }

    /**
     * @return l angle de la poutre par rapport a horizontale
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * permet de faire en sorte que le point 1 soit le point le plus a gauche et calcule l angle, cela a une utilite pour optimiser le temps de calcul et trier les listes de poutres utilisees dans d autres classes
     */
    public void miseEnForme() {
        if (this.getP1().getX() > this.getP2().getX()) {
            Point pSave = this.p1;
            this.p1 = this.p2;
            this.p2 = pSave;
        }
        this.angle = Math.abs(Math.atan((p2.getY() - p1.getY()) / (p2.getX() - p1.getX())));
    }

    /**
     * methode compareTO qui compare les poutres par rapport a la position du point 1 de facon a parcourir les poutres de gauche a droite
     *
     * @param d
     * @return
     */
    public int compareTo(Distance d) {
        if (d.getP1().getX() > this.getP1().getX()) {
            return -1;
        }
        if (d.getP1().getX() == this.getP1().getX()) {
            return 0;
        }
        if (d.getP1().getX() < this.getP1().getX()) {
            return 1;
        }
        return 0;

    }

    /**
     * methode ToString
     *
     * @return
     */
    public String toString() {
        return "point1=(" + getP1().getX() + "," + getP1().getY() + ") point2=(" + getP2().getX() + "," + getP2().getY() + ")";
    }

    /**
     * @return l instance Matiere de la poutre
     */
    public Matiere getMatiere() {
        return matierePoutre;
    }
}



