import java.awt.*;
import java.util.ArrayList;
//convertir 20pixel = 1 m

public class Torseur implements Comparable<Torseur> {
    private double X; // composante de l'effort de traction/compression du torseur
    private double Y; // composante de l'effort tranchant du torseur
    private double M; // composante de la flexion du torseur
    private double x; // distance (partant de l'extremité d'un segment du pont) à laquelle on calcule le torseur
    private Vehicule V;  // vehicule traversant le pont

    /**
     * @param X
     * @param Y
     * @param M
     * @param x
     */
    public Torseur(double X, double Y, double M, double x) {
        this.X = X;
        this.Y = Y;
        this.M = M;
    }

    /**
     * @param V
     */
    public Torseur(Vehicule V) {
        this.V = V;
    }

    /*On calcul le torseur des action suivant x (effort de traction/compression) et y (effort tranchant) ainsi que le moment M suivant z (flexion)
     *Le problème étant un problème plan les autres composantes sont nulles.On calcule ce torseur sur un segment du pont (poutre) en une position de ce segment (représentée par x).
     * Les valeurs du torseur dépendent de la présence ou non de la voiture (point V) sur le segment du pont, ainsi que de la position
     * sur ce segment pour laquelle on calcule le torseur (variable x). On différencie alors plusieurs cas
     */

    /**
     * @param poutre
     * @param X
     * @param Y
     * @param M
     * @param x
     * @param V
     */
    public Torseur(Distance poutre, double X, double Y, double M, double x, Vehicule V) {  // les variables X, Y et Z représentent les composantes du torseur de l'action s'exercant sur l'extremitée gauche du segment
        this.V = V;
        this.x = x;

        if (V.appartientPoutre(poutre)) { // V (la voiture) appartient au segment et on sait que V est sur la droite
            if (poutre.getP1().distance(V.getX(), V.getY()) < poutre.getLongueur() / 2) { // V est avant le barycentre
                if (x < poutre.getP1().distance(V.getX(), V.getY())) {//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
                    this.X = -X;
                    this.Y = -Y;
                    this.M = -M + x * Y;
                }
                if (x >= poutre.getP1().distance(V.getX(), V.getY()) && (x <= poutre.getLongueur() / 2)) {// le point sur lequel on calcul le torseur est après la voiture et avant le barycentre
                    this.X = -X - (V.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y - (V.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY()));
                }
                if (x > poutre.getLongueur() / 2) {//le point sur lequel on calcul le torseur est après la voiture et après le barycentre
                    this.X = -X - (V.getPoids() * (Math.sin(poutre.getAngle()))) - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y + (V.getPoids() * (Math.cos(poutre.getAngle()))) + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY())) - poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur() / 2);
                }

            }
            if (poutre.getP1().distance(V.getX(), V.getY()) >= poutre.getLongueur() / 2) {//V est après le barycentre
                if (x < poutre.getLongueur() / 2) {//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
                    this.X = -X;
                    this.Y = -Y;
                    this.M = -M;
                }

                if (x < poutre.getP1().distance(V.getX(), V.getY()) && x >= poutre.getLongueur() / 2) {// le point sur lequel on calcul le torseur est avant voiture et après barycentre
                    this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / 2);
                }
                if (x >= poutre.getP1().distance(V.getX(), V.getY())) {//le point sur lequel on calcul le torseur est après voiture
                    this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle()))) - (V.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle()))) + (V.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / 2) - (V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY())));
                }
            }
        } else {

            if (x < poutre.getLongueur() / 2) {//le point sur lequel on calcul le torseur est avant barycentre de la poutre
                this.X = -X;
                this.Y = -Y;
                this.M = -M + x * Y;
            }
            if (x > poutre.getLongueur() / 2) {//le point sur lequel on calcul le torseur est apres barycentre de la poutre
                this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / 2);
            }

        }

    }

    /**
     * @param poutre
     * @param X
     * @param Y
     * @param M
     * @param V
     */
    public Torseur(Distance poutre, double X, double Y, double M, Vehicule V) {
        this.V = V;

        if (V.appartientPoutre(poutre)) { // V appartient au segment et on sait que V est sur la droite
            if (V.getX() < poutre.getBarycentre().getX()) { //V est avant le barycentre
                this.X = -X - (V.getPoids() * (Math.sin(poutre.getAngle()))) - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                this.Y = -Y + (V.getPoids() * (Math.cos(poutre.getAngle()))) + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                this.M = -M + poutre.getLongueur() * Y - V.getPoids() * Math.cos(poutre.getAngle() * (poutre.getLongueur() - poutre.getP1().distance(V.getX(), V.getY())) - poutre.getPoids() * Math.cos(poutre.getAngle() * (poutre.getLongueur() - poutre.getLongueur()) / 2));
            }

            if (V.getX() >= poutre.getBarycentre().getX()) {//V est après le barycentre
                this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle()))) - (V.getPoids() * (Math.sin(poutre.getAngle())));
                this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle()))) + (V.getPoids() * (Math.cos(poutre.getAngle())));
                this.M = -M + poutre.getP2().getX() * Y - poutre.getPoids() * Math.cos(poutre.getAngle()) * (poutre.getLongueur() - poutre.getLongueur() / 2) - V.getPoids() * Math.cos(poutre.getAngle()) * (poutre.getLongueur() - poutre.getP1().distance(V.getX(), V.getY()));
            }

        } else { // V N'appartient pas au segment
            this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
            this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
            this.M = -M + poutre.getLongueur() * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (poutre.getLongueur() - poutre.getLongueur()) / 2);
        }


    }


    /* méthode renvoyant la composante X de traction du torseur */

    /**
     * @return
     */
    public double getX() {
        return this.X;
    }

    /* méthode renvoyant la composante Y de cisaillement du torseur */

    /**
     * @return
     */
    public double getY() {
        return this.Y;
    }

    /* méthode renvoyant la composante M de flexion du torseur */

    /**
     * @return
     */
    public double getM() {
        return this.M;
    }

    /**
     * @param torseur
     * @return
     */
    public int compareTo(Torseur torseur) {
        return 0;
    }


    /* méthode renvoyant une liste comportant les torseurs de chaque extremité gauche des segments du pont.
    Cette liste est ordonnée (Le ième torseur est le torseur de l'extremité gauche du ième segment en comptant de gauche à droite) */

    /**
     * @param pont
     * @return
     */
    public ArrayList<Torseur> getTorseursExtremites(ArrayList<Distance> pont) {
        Simulation simulation = new Simulation(pont, new Point(250, 432), new Point(1050, 432), V);
        ArrayList<Torseur> Resultat = new ArrayList<>();
        Torseur TorseurExtremiteAvant = new Torseur(pont.get(0), simulation.getPivotA() * Math.sin(pont.get(0).getAngle()), simulation.getPivotA() * Math.cos(pont.get(0).getAngle()), 0, this.V);
        Resultat.add(TorseurExtremiteAvant);  //on ajoute le Torseur de la 1er extremitée (point de départ du pont) dans la liste Resultat,

        for (int i = 0; i < pont.size() - 1; i++) { // on parcourt les segments du pont
            double phi = pont.get(i + 1).getAngle() - pont.get(i).getAngle(); // phi représente l'angle entre deux segments consécutifs
            Torseur Torseurextremite = new Torseur(pont.get(i), TorseurExtremiteAvant.X * Math.cos(phi) + TorseurExtremiteAvant.Y * Math.sin(phi), -TorseurExtremiteAvant.X * Math.sin(phi) + TorseurExtremiteAvant.Y * Math.cos(phi), TorseurExtremiteAvant.M, this.V);
            TorseurExtremiteAvant = Torseurextremite;
            Resultat.add(Torseurextremite);
        }
        return Resultat;
    }

    /* méthode renvoyant les coordonnées du point du pont sur lequel il y a une rupture si le pont se casse.
    Si le pont ne se casse pas, cette méthode renvoie un point de coordonnée (-1,-1) */

    /**
     * @param pont
     * @return
     */
    public Point Pointrupture(ArrayList<Distance> pont) {
        ArrayList<Torseur> TorseurExtremite = getTorseursExtremites(pont);

        double Xmax = 0;
        double posX = 0;
        double SegmentX = 0;
        double posY = 0;
        double SegmentY = 0;
        double posZ = 0;
        double SegmentZ = 0;
        double Ymax = 0;
        double Zmax = 0;
        double Mmax;
        double posM;
        double SegmentM;
        double EcartX;
        double EcartY;
        double EcartZ;
        double coordonneex;
        double coordonneey;
        double sec = 5 * 16.0 / 20.0;

        for (int i = 0; i < pont.size(); i++) {
            for (double j = 0; j <= pont.get(i).getLongueur(); j++) {
                Torseur a = new Torseur(pont.get(i), TorseurExtremite.get(i).getX(), TorseurExtremite.get(i).getY(), TorseurExtremite.get(i).getM(), j, this.V);
                if (Math.abs(a.getX()) > Xmax) {
                    Xmax = Math.abs(a.getX());
                    posX = j;
                    SegmentX = i;
                }

                if (Math.abs(a.getY()) > Ymax) {
                    Ymax = Math.abs(a.getY());
                    posY = j;
                    SegmentY = i;
                }

                if (Math.abs(a.getY()) > Ymax) {
                    Mmax = Math.abs(a.getY());
                    posM = j;
                    SegmentM = i;
                }
            }
        }
        if (pont.get(0).getMatiere().getDensite() == 0.450) {
            EcartX = (Xmax / sec) - 40 * 100;
            EcartY = (Ymax / sec) - 8 * 100;
            EcartZ = (Zmax / sec) - 30.6 * 100;
        } else if (pont.get(0).getMatiere().getDensite() == 8.2) {
            EcartX = (Xmax / sec) - 360 * 1000000;
            EcartY = (Ymax / sec) - 0.4 * 1000000;
            EcartZ = (Zmax / sec) - 1.56 * 100000000;
        } else {
            EcartX = (Xmax / sec) - 2.6 * 1000000;
            EcartY = (Ymax / sec) - 1000000;
            EcartZ = (Zmax / sec) - 30 * 1000000;
        }

        if ((EcartX <= 0) && (EcartY <= 0) && (EcartZ <= 0)) {

            return new Point(-1, -1);
        } else if ((EcartX >= 0) && (EcartX >= EcartY) && (EcartX >= EcartZ)) {

            coordonneex = posX * Math.sin(pont.get((int) (SegmentX)).getAngle()) + pont.get((int) (SegmentX)).getP1().getX();
            coordonneey = posX * Math.cos(pont.get((int) (SegmentX)).getAngle()) + pont.get((int) (SegmentX)).getP1().getY();

            return new Point((int) (coordonneex), (int) (coordonneey));
        } else if ((EcartY >= 0) && (EcartY >= EcartX) && (EcartY >= EcartZ)) {

            coordonneex = posY * Math.sin(pont.get((int) (SegmentY)).getAngle()) + pont.get((int) (SegmentY)).getP1().getX();
            coordonneey = posY * Math.cos(pont.get((int) (SegmentY)).getAngle()) + pont.get((int) (SegmentY)).getP1().getY();

            return new Point((int) (coordonneex), (int) (coordonneey));
        } else {

            coordonneex = posZ * Math.sin(pont.get((int) (SegmentZ)).getAngle()) + pont.get((int) (SegmentZ)).getP1().getX();
            coordonneey = posZ * Math.cos(pont.get((int) (SegmentZ)).getAngle()) + pont.get((int) (SegmentZ)).getP1().getY();

            return new Point((int) (coordonneex), (int) (coordonneey));
        }
    }

}
