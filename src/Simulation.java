import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class Simulation {

    private ArrayList<Distance> pont;  // liste des segments constituants le pont
    private double pivotA;             // composante selon y de la liaison pivot entre le pont est le sol. Cette liasion est située à l'extremité gauche du pont (la composante selon x de cette liaison pivot est nulle)
    private double ponctuelleB;        // composante selon y de la liason ponctuelle entre le pont est le sol. Cette liaison est située à l'extremité droite du pont.
    private Vehicule vehicule;         // véhicule traversant le pont
    private Point[] tabPointsJonction;  

    /**
     * @param listePoutres
     * @param A
     * @param B
     * @param vehicule
     */
    public Simulation(ArrayList<Distance> listePoutres, Point A, Point B, Vehicule vehicule) {
        this.pont = listePoutres;
        this.pivotA = 0;
        this.ponctuelleB = 0;
        int taille = this.pont.size();
        System.out.println("taille Liste: " + taille);
        // calcul des composantes de la lisaison pivot ainsi que de la liaison ponctuelle
        for (int i = 0; i < taille; i++) { 
            this.pivotA = this.pivotA + this.pont.get(i).getPoids();
            this.ponctuelleB = (this.ponctuelleB - (this.pont.get(i).getPoids() * (this.pont.get(i).getBarycentre().getX() - A.getX()) / (A.distance(B))));
        }
        this.vehicule = vehicule;
    }

    /**
     * @return
     */
     /* méthode renvoyant la composante du torseur de la liaison pivot */
    public double getPivotA() {
        return this.pivotA;
    }


}
