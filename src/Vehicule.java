import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class Vehicule {

    private int type;
    private double poids;
    private double largeur;
    private double hauteur;
    private double vitesse;
    private double kp;//constante de raideur du ressort lié aux poutres

    private double[][] R1;
    private double[][] R2;

    private double[][] V1;
    private double[][] V2;

    private double[][] F1;
    private double[][] F2;

    private double[][] diff;

    private Distance poutre1;
    private Distance poutre2;
    private ArrayList<Distance> poutres;

    /**
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     */
    public Vehicule(int a, double b, double c, double d, double e) {
        type = a;

        if (a == 1) {
            kp = 10000;
        } else if (a == 2) {
            kp = 100000;
        } else {
            kp = 1000000;
        }


        poids = b;
        largeur = c;
        hauteur = d;
        vitesse = e;
        poutre1 = new Distance(new Point(0, 0), new Point(0, 0), 1);
        poutre2 = new Distance(new Point(0, 0), new Point(0, 0), 1);

        R1 = new double[2][2];
        R2 = new double[2][2];

        R1[0][0] = 170.0;
        R1[0][1] = 740.0 - 290.0;
        R2[0][0] = 170.0 - largeur;
        R2[0][1] = 740.0 - 290.0;

        V1 = new double[2][2];
        V2 = new double[2][2];

        V1[0][0] = 0;
        V1[0][1] = 0;
        V2[0][0] = 0;
        V2[0][1] = 0;

        F1 = new double[2][2];
        F2 = new double[2][2];

        F1[0][0] = 0;
        F1[0][1] = 0;
        F2[0][0] = 0;
        F2[0][1] = 0;

        diff = new double[2][2];

        diff[0][0] = 0;
        diff[0][1] = 0;

    }

    /**
     * @return
     */
    public boolean moteur() {

        boolean basculer = false;

        double echelle = 20; //convertion pixels/metre
        double g1 = 9.81; //acceleration de la pesanteur
        double g2 = g1 * echelle; //acceleration de la pesanteur convertie en pixel
        double vit = (vitesse / 3.6) * echelle; //vitesse en pixels/seconde
        double lp = 8; //epaisseur d'une poutre/2
        double rayon = 10; //rayon des roues du vehicule
        double gamma = 3000; //coefficient d'amortissement visqueux

        double dt = 0.01; //pas de temps d'intégration

        double x1;
        double x2;
        double y1;
        double y2;

        double R1x;
        double R1y;
        double R2x;
        double R2y;

        double alpha;
        double a;
        double b;
        double a1;
        double bh;
        double bb;
        double dist;
        double beta;


        V1[1][0] = V1[0][0] + (2 / poids) * F1[0][0] * dt;
        V1[1][1] = V1[0][1] + (2 / poids) * F1[0][1] * dt;
        V2[1][0] = V2[0][0] + (2 / poids) * F2[0][0] * dt;
        V2[1][1] = V2[0][1] + (2 / poids) * F2[0][1] * dt;


        R1[1][0] = R1[0][0] + V1[1][0] * dt;
        R1[1][1] = R1[0][1] + V1[1][1] * dt;
        R2[1][0] = R2[0][0] + V2[1][0] * dt;
        R2[1][1] = R2[0][1] + V2[1][1] * dt;

        R1x = R1[1][0];
        R1y = R1[1][1];
        R2x = R2[1][0];
        R2y = R2[1][1];


        //calcul des forces
        F1[1][1] = -(poids / 2) * g2;
        F2[1][1] = -(poids / 2) * g2;

        for (Distance d : poutres) {

            x1 = d.getP1().getX();
            x2 = d.getP2().getX();
            y1 = 740 - d.getP1().getY();
            y2 = 740 - d.getP2().getY();

            if (y1 == y2) {
                if ((R1x >= x1) && (R1x <= x2) && (R1y - rayon < y1 + lp)) {
                    poutre1 = new Distance(new Point((int) (x1), (int) (740 - y1)), new Point((int) (x2), (int) (740 - y2)), 1);
                    diff[1][0] = R1y - rayon - (y1 + lp);
                    F1[1][1] = F1[1][1] - kp * diff[1][0] - gamma * (diff[1][0] - diff[0][0]) / dt;
                    V1[1][0] = vit;

                } else {
                    poutre1 = new Distance(new Point(0, 0), new Point(0, 0), 1);
                }

                if ((R2x >= x1) && (R2x <= x2) && (R2y - rayon < y1 + lp)) {
                    poutre2 = new Distance(new Point((int) (x1), (int) (740 - y1)), new Point((int) (x2), (int) (740 - y2)), 1);
                    diff[1][1] = R2y - rayon - (y1 + lp);
                    F2[1][1] = F2[1][1] - kp * diff[1][1] - gamma * (diff[1][1] - diff[0][1]) / dt;
                    V2[1][0] = vit;

                } else {
                    poutre2 = new Distance(new Point(0, 0), new Point(0, 0), 1);
                }
            } else {
                alpha = Math.atan((y2 - y1) / (x2 - x1));

                a = (y2 - y1) / (x2 - x1);
                b = y1 - a * x1 + lp / Math.cos(alpha);

                a1 = -1 / a;

                bh = Math.max(y1 - a1 * x1, y2 - a1 * x2);
                bb = Math.min(y1 - a1 * x1, y2 - a1 * x2);

                if ((R1y - rayon >= a1 * R1x + bb) && (R1y - rayon <= a1 * R1x + bh) && (R1y - rayon < a * R1x + b)) {

                    poutre1 = new Distance(new Point((int) (x1), (int) (740 - y1)), new Point((int) (x2), (int) (740 - y2)), 1);
                    diff[1][0] = Math.cos(alpha) * ((a * R1x + b) - (R1y - rayon));

                    F1[1][0] = F1[1][0] - (kp * diff[1][0] + gamma * (diff[1][0] - diff[0][0]) / dt) * Math.sin(alpha);
                    F1[1][1] = F1[1][1] + (kp * diff[1][0] + gamma * (diff[1][0] - diff[0][0]) / dt) * Math.cos(alpha);

                    V1[1][0] = vit * Math.cos(alpha) * (1 - 2 * alpha / Math.PI);
                    V1[1][1] = vit * Math.sin(alpha) * (1 - 2 * alpha / Math.PI);
                } else {
                    poutre1 = new Distance(new Point(0, 0), new Point(0, 0), 1);
                }

                if ((R2y - rayon >= a1 * R2x + bb) && (R2y - rayon <= a1 * R2x + bh) && (R2y - rayon < a * R2x + b)) {

                    poutre2 = new Distance(new Point((int) (x1), (int) (740 - y1)), new Point((int) (x2), (int) (740 - y2)), 1);
                    diff[1][1] = Math.cos(alpha) * ((a * R2x + b) - (R2y - rayon));

                    F2[1][0] = F2[1][0] - (kp * diff[1][1] + gamma * (diff[1][1] - diff[0][1]) / dt) * Math.sin(alpha);
                    F2[1][1] = F2[1][1] + (kp * diff[1][1] + gamma * (diff[1][1] - diff[0][1]) / dt) * Math.cos(alpha);

                    V2[1][0] = vit * Math.cos(alpha) * (1 - 2 * alpha / Math.PI);
                    V2[1][1] = vit * Math.sin(alpha) * (1 - 2 * alpha / Math.PI);
                } else {
                    poutre2 = new Distance(new Point(0, 0), new Point(0, 0), 1);
                }
            }
        }

        dist = Math.sqrt(Math.pow(R1x - R2x, 2) + Math.pow(R1y - R2y, 2));
        beta = Math.atan((R1y - R2y) / (R1x - R2x));


        R1[1][0] = R1x - Math.cos(beta) * (dist - largeur) / 2;
        R1[1][1] = R1y - Math.sin(beta) * (dist - largeur) / 2;
        R2[1][0] = R2x + Math.cos(beta) * (dist - largeur) / 2;
        R2[1][1] = R2y + Math.sin(beta) * (dist - largeur) / 2;

        //actualisation des variables
        R1[0][0] = R1[1][0];
        R1[0][1] = R1[1][1];
        R2[0][0] = R2[1][0];
        R2[0][1] = R2[1][1];

        R1[1][0] = 0;
        R1[1][1] = 0;
        R2[1][0] = 0;
        R2[1][1] = 0;


        V1[0][0] = V1[1][0];
        V1[0][1] = V1[1][1];
        V2[0][0] = V2[1][0];
        V2[0][1] = V2[1][1];

        V1[1][0] = 0;
        V1[1][1] = 0;
        V2[1][0] = 0;
        V2[1][1] = 0;


        F1[0][0] = F1[1][0];
        F1[0][1] = F1[1][1];
        F2[0][0] = F2[1][0];
        F2[0][1] = F2[1][1];

        F1[1][0] = 0;
        F1[1][1] = 0;
        F2[1][0] = 0;
        F2[1][1] = 0;


        diff[0][0] = diff[1][0];
        diff[0][1] = diff[1][1];

        diff[1][0] = 0;
        diff[1][1] = 0;

        if (R1[0][0] <= R2[0][0]) {
            basculer = true;
        }

        return basculer;

    }

    /**
     * @param d
     * @return
     */
    public boolean appartientPoutre(Distance d) {
        if ((poutre1.getP1().getX() == d.getP1().getX()) && (poutre1.getP1().getY() == d.getP1().getY()) && (poutre1.getP2().getX() == d.getP2().getX()) && (poutre1.getP2().getY() == d.getP2().getY())) {
            return true;
        } else if ((poutre2.getP1().getX() == d.getP1().getX()) && (poutre2.getP1().getY() == d.getP1().getY()) && (poutre2.getP2().getX() == d.getP2().getX()) && (poutre2.getP2().getY() == d.getP2().getY())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param a
     */
    public void setPoutres(ArrayList<Distance> a) {
        this.poutres = a;
        poutres.add(new Distance(new Point(0, 308), new Point(250, 308), 1));
        poutres.add(new Distance(new Point(1050, 308), new Point(1500, 308), 1));
    }

    /**
     * @param a
     */
    public void setType(int a) {
        this.type = a;
    }

    /**
     * @return
     */
    public int getType() {
        return type;
    }

    /**
     * @param a
     */
    public void setPoids(double a) {
        this.poids = a;
    }

    /**
     * @return
     */
    public double getPoids() {
        return poids;
    }

    /**
     * @param a
     */
    public void setLargeur(double a) {
        this.largeur = a;
    }

    /**
     * @return
     */
    public double getLargeur() {
        return largeur;
    }

    /**
     * @param a
     */
    public void setHauteur(double a) {
        this.hauteur = a;
    }

    /**
     * @return
     */
    public double getHauteur() {
        return hauteur;
    }

    /**
     * @param a
     */
    public void setVitesse(double a) {
        this.vitesse = a;
    }

    /**
     * @return
     */
    public double getVitesse() {
        return vitesse;
    }

    /**
     * @return
     */
    public double getX1() {
        return R1[0][0];
    }

    /**
     * @return
     */
    public double getX2() {
        return R2[0][0];
    }

    /**
     * @return
     */
    public double getY1() {
        return 740 - R1[0][1];
    }

    /**
     * @return
     */
    public double getY2() {
        return 740 - R2[0][1];
    }

    /**
     * @return
     */
    public double getX() {
        return (R1[0][0] + R2[0][0]) / 2;
    }

    /**
     * @return
     */
    public double getY() {
        return (740 - R1[0][1] + 740 - R2[0][1]) / 2;
    }

    /**
     * @return
     */
    public ArrayList<Distance> getPoutres() {
        ArrayList<Distance> p = new ArrayList<Distance>();
        for (int i = 0; i < poutres.size() - 2; i++) {
            p.add(poutres.get(i));
        }
        return p;
    }
}
