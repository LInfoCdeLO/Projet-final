import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui gere les forces s appliquant sur les poutres et la resistance des materiaux
 * <p>
 * Il est interessant de savoir qu il y a une conversion entre les pixels de l ecran et les metres utilisees dans les calculs, cette conversion est de 20 pixels pour 1 metre.
 * C est pour ca qu il y a beaucoup de divisions et multiplications par 20 car c est la conversion.
 */
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
     * Change la valeur du Vehicule
     *
     * @param V
     */
    public Torseur(Vehicule V) {
        this.V = V;
    }

    /**
     * On calcul le torseur des action suivant x (effort de traction/compression) et y (effort tranchant) ainsi que le moment M suivant z (flexion)
     * Le problème étant un problème plan les autres composantes sont nulles.On calcule ce torseur sur un segment du pont (poutre) en une position de ce segment (représentée par x).
     * Les valeurs du torseur dépendent de la présence ou non de la voiture (point V) sur le segment du pont, ainsi que de la position
     * sur ce segment pour laquelle on calcule le torseur (variable x). On différencie alors plusieurs cas
     *
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
                if (x < poutre.getP1().distance(V.getX(), V.getY()) / 20) {//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
                    this.X = -X;
                    this.Y = -Y;
                    this.M = -M + x * Y;
                }
                if (x >= poutre.getP1().distance(V.getX(), V.getY()) / 20 && (x <= poutre.getLongueur() / (2 * 20))) {// le point sur lequel on calcul le torseur est après la voiture et avant le barycentre
                    this.X = -X - (V.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y - (V.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY()) / 20);
                }
                if (x > poutre.getLongueur() / (2 * 20)) {//le point sur lequel on calcul le torseur est après la voiture et après le barycentre
                    this.X = -X - (V.getPoids() * (Math.sin(poutre.getAngle()))) - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y + (V.getPoids() * (Math.cos(poutre.getAngle()))) + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY()) / 20) - poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur() / (2 * 20));
                }

            }
            if (poutre.getP1().distance(V.getX(), V.getY()) >= poutre.getLongueur() / 2) {//V est après le barycentre
                if (x < poutre.getLongueur() / (2 * 20)) {//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
                    this.X = -X;
                    this.Y = -Y;
                    this.M = -M;
                }

                if (x < poutre.getP1().distance(V.getX(), V.getY()) / 20 && x >= poutre.getLongueur() / (2 * 20)) {// le point sur lequel on calcul le torseur est avant voiture et après barycentre
                    this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / (2 * 20));
                }
                if (x >= poutre.getP1().distance(V.getX(), V.getY()) / 20) {//le point sur lequel on calcul le torseur est après voiture
                    this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle()))) - (V.getPoids() * (Math.sin(poutre.getAngle())));
                    this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle()))) + (V.getPoids() * (Math.cos(poutre.getAngle())));
                    this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / (2 * 20)) - (V.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getP1().distance(V.getX(), V.getY()) / 20));
                }
            }
        } else {

            if (x < poutre.getLongueur() / (2 * 20)) {//le point sur lequel on calcul le torseur est avant barycentre de la poutre
                this.X = -X;
                this.Y = -Y;
                this.M = -M + x * Y;
            }
            if (x > poutre.getLongueur() / (2 * 20)) {//le point sur lequel on calcul le torseur est apres barycentre de la poutre
                this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
                this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
                this.M = -M + x * Y - (poutre.getPoids() * (Math.cos(poutre.getAngle())) * (x - poutre.getLongueur()) / (2 * 20));
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
                this.M = -M + poutre.getLongueur() / 20 * Y - V.getPoids() * Math.cos(poutre.getAngle()) * (poutre.getLongueur() / 20 - poutre.getP1().distance(V.getX(), V.getY()) / 20) - poutre.getPoids() * Math.cos(poutre.getAngle()) * poutre.getLongueur() / (2 * 20);
            }

            if (V.getX() >= poutre.getBarycentre().getX()) {//V est après le barycentre
                this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle()))) - (V.getPoids() * (Math.sin(poutre.getAngle())));
                this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle()))) + (V.getPoids() * (Math.cos(poutre.getAngle())));
                this.M = -M + poutre.getP2().getX() / 20 * Y - poutre.getPoids() * Math.cos(poutre.getAngle()) * poutre.getLongueur() / (2 * 20) - V.getPoids() * Math.cos(poutre.getAngle()) * (poutre.getLongueur() / 20 - poutre.getP1().distance(V.getX(), V.getY()) / 20);
            }

        } else { // V N'appartient pas au segment
            this.X = -X - (poutre.getPoids() * (Math.sin(poutre.getAngle())));
            this.Y = -Y + (poutre.getPoids() * (Math.cos(poutre.getAngle())));
            this.M = -M + poutre.getLongueur() / 20 * Y - (poutre.getPoids() * Math.cos(poutre.getAngle()) * poutre.getLongueur() / (2 * 20));
        }


    }

    /**
     * @return la composante X de traction du torseur
     */
    public double getX() {
        return this.X;
    }

    /**
     * @return la composante Y de cisaillement du torseur
     */
    public double getY() {
        return this.Y;
    }

    /**
     * @return la composante M de flexion du torseur
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

    /**
     * méthode renvoyant une liste comportant les torseurs s'exprimants sur chaque extremité gauche des segments du pont.
     * Cette liste est ordonnée (Le ième torseur est le torseur de l'extremité gauche du ième segment en comptant de gauche à droite)
     *
     * @param pont
     * @return
     */
    public ArrayList<Torseur> getTorseursExtremites(ArrayList<Distance> pont) {
        Simulation simulation = new Simulation(pont, new Point(250, 432), new Point(1050, 432), V);
        ArrayList<Torseur> Resultat = new ArrayList<>();
        Torseur TorseurExtremiteAvant = new Torseur(pont.get(0), simulation.getPivotA() * Math.sin(pont.get(0).getAngle()), simulation.getPivotA() * Math.cos(pont.get(0).getAngle()), 0, this.V);
        Resultat.add(TorseurExtremiteAvant);  //on ajoute le Torseur de la 1er extremitée (point de départ du pont) dans la liste Resultat. Les composantes de ce Torseur sont correctement projetées.

        for (int i = 0; i < pont.size() - 1; i++) { // on parcourt les segments du pont
            double phi = pont.get(i + 1).getAngle() - pont.get(i).getAngle(); // phi représente l'angle entre deux segments consécutifs
            // on crée une instance Torseurextremite qui calcul les actions mécaniques de la poutre numéro i à son extremité droite. 
            //On crée cette instance à l'aide les composantes du Torseur des actions s'exercants sur l'extremité gauche de la poutre numero i.
            //Ces composantes sont correctements projetées à l'aide de l'angle phi.
            Torseur Torseurextremite = new Torseur(pont.get(i), TorseurExtremiteAvant.X * Math.cos(phi) + TorseurExtremiteAvant.Y * Math.sin(phi), -TorseurExtremiteAvant.X * Math.sin(phi) + TorseurExtremiteAvant.Y * Math.cos(phi), TorseurExtremiteAvant.M, this.V);

            TorseurExtremiteAvant = Torseurextremite; // On sauvergarde le Torseur afin d'avoir les composantes du Torseur des actions s'exercants sur l'extremité gauche de la poutre numero i+1 (pour le prochain passage dans la boucle) 
            Resultat.add(Torseurextremite);
        }
        return Resultat;
    }


    /**
     * méthode renvoyant les coordonnées du point du pont sur lequel il y a une rupture si le pont se casse.
     * Si le pont ne se casse pas, cette méthode renvoie un point de coordonnée (-1,-1)
     *
     * @param pont
     * @return
     */
    public Point Pointrupture(ArrayList<Distance> pont) {
        ArrayList<Torseur> TorseurExtremite = getTorseursExtremites(pont);

        double Xmax = 0; // Xmax est la composante de traction/compression maximum de l'ensemble du pont
        double posX = 0;  // posX représente la position sur le segment (distance par rapport à l'extremité gauche du segment) pour laquelle Xmax est atteint
        double SegmentX = 0; // SegmentX est le numéro du segment sur lequel Xmax est atteint
        double posY = 0;     //posY représente la position sur le segment  pour laquelle Ymax est atteint
        double SegmentY = 0;// SegmentY est le numéro du segment sur lequel Ymax est atteint
        double posZ = 0;    //posZ représente la position sur le segment pour laquelle Mmax est atteint
        double SegmentZ = 0;// SegmentZ est le numéro du segment sur lequel Mmax est atteint
        double Ymax = 0;    //Ymax est la composante de l'effort tranchant maximum de l'ensemble du pont
        double Mmax = 0;    //Mmax est la composante de la flexion maximum de l'ensemble du pont
        double posM;
        double SegmentM;// Segment X est le numéro du segment sur lequel Xmax est atteint
        double EcartX;  // Ecart entre la solicitation de traction/compression et la valeur limite de solicitation (valeur au dessus de laquelle le pont se casse) 
        double EcartY;  // Ecart entre la solicitation de l'effort tranchant et la valeur limite de solicitation 
        double EcartZ;  // Ecart entre la solicitation de flexion et la valeur limite de solicitation 
        double coordonneex; // coordonnée selon x du point de rupture du pont
        double coordonneey; // coordonnée selon y du point de rupture du pont
        double sec = 5 * 16.0 / 20.0; // section des segments constituants le pont

        // on parcourt les segments du pont ainsi que les positions de chaque segment afin de determiner les valeur de Xmax Ymax et Mmax
        for (int i = 0; i < pont.size(); i++) {
            for (double j = 0; j <= pont.get(i).getLongueur(); j++) {
                Torseur a = new Torseur(pont.get(i), TorseurExtremite.get(i).getX(), TorseurExtremite.get(i).getY(), TorseurExtremite.get(i).getM(), j / 20.0, this.V);
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

                if (Math.abs(a.getM()) > Mmax) {
                    Mmax = Math.abs(a.getM());
                    posM = j;
                    SegmentM = i;
                }
            }
        }
        // Les valeur de sollicitation maximum sont différentes selon le materiau utilisé. On différencie donc les cas.
        if (pont.get(0).getMatiere().getDensite() == 0.450) {
            EcartX = (Xmax / sec) - 40 * 100;
            EcartY = (Ymax / sec) - 8 * 100;
            EcartZ = (Mmax / sec) - 12 * 100;
        } else if (pont.get(0).getMatiere().getDensite() == 8.2) {
            EcartX = (Xmax / sec) - 360 * 1000;
            EcartY = (Ymax / sec) - 0.4 * 100000;
            EcartZ = (Mmax / sec) - 1.56 * 100000;
        } else {
            EcartX = (Xmax / sec) - 2.6 * 1000;
            EcartY = (Ymax / sec) - 1000;
            EcartZ = (Mmax / sec) - 5.2 * 1000;
        }

        //System.out.println("xmax "+Xmax/sec+" ymax "+Ymax/sec+" zmax "+Mmax/sec);

        // On retourne le point pour lequel l'ecart entre la solicitation et la valeur limite de solicitation est le plus grand
        if ((EcartX <= 0) && (EcartY <= 0) && (EcartZ <= 0)) {

            return new Point(-1, -1);
        } else if ((EcartX >= 0) && (EcartX >= EcartY) && (EcartX >= EcartZ)) {

            coordonneex = posX * Math.sin(pont.get((int) (SegmentX)).getAngle()) + pont.get((int) (SegmentX)).getP1().getX() / 20.0;
            coordonneey = posX * Math.cos(pont.get((int) (SegmentX)).getAngle()) + (740 - pont.get((int) (SegmentX)).getP1().getY()) / 20.0;

            Point rupture = new Point((int) (coordonneex * 20), (int) (740 - coordonneey * 20));
            return rupture;

        } else if ((EcartY >= 0) && (EcartY >= EcartX) && (EcartY >= EcartZ)) {

            coordonneex = posY * Math.sin(pont.get((int) (SegmentY)).getAngle()) + pont.get((int) (SegmentY)).getP1().getX() / 20.0;
            coordonneey = posY * Math.cos(pont.get((int) (SegmentY)).getAngle()) + (740 - pont.get((int) (SegmentY)).getP1().getY()) / 20.0;

            Point rupture = new Point((int) (coordonneex * 20), (int) (740 - coordonneey * 20));
            return rupture;

        } else {

            coordonneex = posZ * Math.sin(pont.get((int) (SegmentZ)).getAngle()) + pont.get((int) (SegmentZ)).getP1().getX() / 20.0;
            coordonneey = posZ * Math.cos(pont.get((int) (SegmentZ)).getAngle()) + (740 - pont.get((int) (SegmentZ)).getP1().getY()) / 20.0;

            Point rupture = new Point((int) (coordonneex * 20), (int) (740 - coordonneey * 20));
            return rupture;

        }
    }

}
