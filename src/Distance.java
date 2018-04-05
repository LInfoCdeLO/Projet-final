import java.awt.*;

/**
 *
 */
public class Distance implements Comparable<Distance> {
	private Point p1;
	private Point p2;
	private Matiere matierePoutre;
	private double poids;
	private double longueur;
	private double angle;
	private Point barycentre;

	/**
	 *
	 * @param p1
	 * @param p2
	 * @param numeroMateriaux
	 */
	public Distance(Point p1, Point p2, int numeroMateriaux){
		this.p1=p1;
		this.p2=p2;
		this.matierePoutre= new Matiere(numeroMateriaux);
		this.longueur=p1.distance(p2);
		this.barycentre=new Point((int)((p1.getX()+p2.getX())/2), (int)((p1.getY()+p2.getY())/2));
		this.poids=10*0.80*this.matierePoutre.getDensite();
	}

	/**
	 *
	 * @param p1
	 */
	public void setP1(Point p1){
		this.p1=p1;
	}

	/**
	 *
	 * @param p2
	 */
	public void setP2(Point p2){
		this.p2=p2;
	}

	/**
	 *
	 * @return
	 */
	public Point getP1(){
		return this.p1;
	}

	/**
	 *
	 * @return
	 */
	public Point getP2(){
		return this.p2;
	}

	/**
	 *
	 * @return
	 */
	public Point getBarycentre(){
		return this.barycentre;		
	}

	/**
	 *
	 * @return
	 */
	public  double getLongueur(){
		return this.longueur;
	}

	/**
	 *
	 * @return
	 */
	public double getPoids(){
		return this.poids;
	}

	/**
	 *
	 * @param a
	 */
	public void setMatierePoutre(int a){
		this.matierePoutre=new Matiere(a);
		this.poids=10*0.80*this.matierePoutre.getDensite();
	}

	/**
	 *
	 * @return
	 */
	public double getAngle(){
		return this.angle;
	}

	/**
	 *
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
	 *
	 * @param d
	 * @return
	 */
	public int compareTo(Distance d){
			if(d.getP1().getX()>this.getP1().getX()){
				return -1;
			}
			if(d.getP1().getX()==this.getP1().getX()){
				return 0;
			}
			if(d.getP1().getX()<this.getP1().getX()){
				return 1;
			}
		return 0;
		
	}

	/**
	 *
	 * @return
	 */
	public String toString() {
		return "point1=(" + getP1().getX() + "," + getP1().getY() + ") point2=(" + getP2().getX() + "," + getP2().getY() + ")";
	}

	/**
	 *
	 * @return
	 */
    public Matiere getMatiere() {
        return matierePoutre;
    }
}



