import java.util.*;
import java.awt.*;

public class ConstructionPont {

    private LinkedList<Distance> listePoutre;
    private ArrayList<Distance> pont;
    private boolean creationListe;

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

    public void selectionChemin() {
        Point A = new Point(250, 308);
        Point B = new Point(1050, 308);
        Point pSave = A;
        Distance dSave = new Distance(A, new Point(0, 0), 1);
        int taille = this.listePoutre.size();
        this.pont = new ArrayList<Distance>();

        while (pSave.equals(B) == false) {
            int n = 0;
            LinkedList<Distance> l = new LinkedList<Distance>();
            for (int i = 0; i < taille; i++) {
                if (this.listePoutre.get(i).getP1() == pSave) {
                    l.add(this.listePoutre.get(i));
                    n++;
                }
            }
            if (n == 0) {
                if (n == 0 && A.equals(new Point(250, 308))) {
                    System.out.println("Erreur le programme ne peut pas se lancer");
                    break;
                }
                System.out.println("Erreur");
                for (int i = 0; i < taille; i++) {
                    if (this.listePoutre.get(i).getP1().equals(dSave)) {
                        listePoutre.remove(i);
                        A = new Point(250, 308);
                        pont = new ArrayList<Distance>();
                    }
                }
            }
            dSave = l.get(0);
            for (int i = 0; i <= l.size(); i++) {

                dSave = l.get(0);

            }
            this.pont.add(dSave);
            pSave = dSave.getP2();
            if (pSave.equals(B) == true) {
                this.creationListe = true;
            }
        }

    }

    public boolean getCreationListe() {
        return this.creationListe;
    }

    public ArrayList<Distance> getPont() {
        return this.pont;
    }

}
