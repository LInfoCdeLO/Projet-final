import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 */
public class ConstructionPont {

    private LinkedList<Distance> listePoutre;
    private ArrayList<Distance> pont;
    private boolean creationListe;

    /**
     * @param listePoutre
     */
    public ConstructionPont(LinkedList<Distance> listePoutre) {
        this.creationListe = false;
        this.listePoutre = new LinkedList<Distance>();
        int taille = listePoutre.size();
        for (int i = 0; i < taille; i++) {
            Distance dSave = listePoutre.get(i);
            dSave.miseEnForme();
            this.listePoutre.add(dSave);
        }
        Collections.sort(this.listePoutre);
        selectionChemin();

    }

    /**
     *
     */
    public void selectionChemin() {
        Point A = new Point(250, 308);
        Point B = new Point(1050, 308);
        Point pSave = A;
        Distance dSave = null;
        Distance dSave2;
        int taille = this.listePoutre.size();
        this.pont = new ArrayList<Distance>();
        boolean fin = false;
        boolean trouve = false;
        int iter = 0;
        int tailleTemp = this.listePoutre.size();
        int n = 0;

        if (taille == 0) {
            fin = true;
        }

        for (int i = 0; i < taille; i++) {
            if (this.listePoutre.get(i).getP1().equals(A)) {
                n++;
            }
        }
        if (n == 0) {
            fin = true;
        }

        n = 0;

        while (fin == false) {

            for (int i = 0; i < taille; i++) {
                if (this.listePoutre.get(i).getP1().equals(pSave)) {
                    if (dSave == null) {
                        dSave = this.listePoutre.get(i);
                        dSave2 = this.listePoutre.get(i);
                    } else if (this.listePoutre.get(i).getAngle() < dSave.getAngle()) {
                        dSave = this.listePoutre.get(i);
                        dSave2 = this.listePoutre.get(i);
                    }
                    trouve = true;
                }
            }

            if (trouve == true) {
                this.pont.add(dSave);
                pSave = dSave.getP2();

            } else {
                if (this.pont.size() != 0) {
                    for (int i = 0; i < taille; i++) {
                        if ((this.listePoutre.get(i).getP1().equals(this.pont.get(this.pont.size() - 1).getP1())) && (this.listePoutre.get(i).getP2().equals(this.pont.get(this.pont.size() - 1).getP2()))) {
                            listePoutre.remove(i);
                            i--;
                            taille = taille - 1;
                        }
                    }
                    pSave = this.pont.get(this.pont.size() - 1).getP1();
                    this.pont.remove(this.pont.size() - 1);
                } else {
                    for (int i = 0; i < taille; i++) {
                        if (this.listePoutre.get(i).getP1().equals(A)) {
                            listePoutre.remove(i);
                            i--;
                            taille = taille - 1;
                        }
                    }
                    pSave = A;
                }
            }

            dSave = null;
            trouve = false;
            taille = this.listePoutre.size();

            if (taille == 0) {
                fin = true;
            }

            if (pSave.equals(B)) {
                this.creationListe = true;
                fin = true;
            }

            if (taille == tailleTemp) {
                iter++;
            } else {
                iter = 0;
                tailleTemp = this.listePoutre.size();
            }

            if (iter > 100) {
                fin = true;
            }

        }
    }

    /**
     * @return
     */
    public boolean getCreationListe() {
        return this.creationListe;
    }

    /**
     * @return
     */
    public ArrayList<Distance> getPont() {
        return this.pont;
    }

}
