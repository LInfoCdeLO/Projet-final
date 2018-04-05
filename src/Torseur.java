import java.awt.*;
import java.util.*;
//convertir 20pixel = 1 m

public class Torseur implements Comparable <Torseur> {
	private double X; // composante de l'effort de traction/compression du torseur 
	private double Y; // composante de l'effort tranchant du torseur
	private double M; // composante de la flexion du torseur
	private double x; // distance (partant de l'extremité d'un segment du pont) à laquelle on calcule le torseur
	private Point V;  // point sur lequel la force de la voiture sur le pont s'exerce
	
	
	public Torseur( double X, double Y, double M, double x){
		this.X=X;
		this.Y=Y;
		this.M=M;
	}
	 
	/*On calcul le torseur des action suivant x (effort de traction/compression) et y (effort tranchant) ainsi que le moment M suivant z (flexion)
	 *Le problème étant un problème plan les autres composantes sont nulles.On calcule ce torseur sur un segment du pont (poutre) en une position de ce segment (représentée par x). 
	 * Les valeurs du torseur dépendent de la présence ou non de la voiture (point V) sur le segment du pont, ainsi que de la position 
	 * sur ce segment pour laquelle on calcule le torseur (variable x). On différencie alors plusieurs cas  
	 */
	 public Torseur(Distance poutre, double X, double Y, double M, double x, Point V){  // les variables X, Y et Z représentent les composantes du torseur de l'action s'exercant sur l'extremitée gauche du segment
		this.V=V;
		this.x=x;
		
		if( V.appartientPoutre(poutre)){ // V (la voiture) appartient au segment et on sait que V est sur la droite
			if( poutre.getP1().getDistance(V) < poutre.setLongueur()/2 ){ // V est avant le barycentre
				if( x < poutre.getP1.getDistance(V)){//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
					this.X= -X;
					this.Y= -Y;
					this.M= -M + x*Y;
				}
				if( x >= poutre.getP1.getDistance(V) && (x <= poutre.setLongueur()/2)){// le point sur lequel on calcul le torseur est après la voiture et avant le barycentre
					this.X= -X -(V.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y - (V.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + x*Y - V.getPoids()*(Math.cos(poutre.getAngle()))* (x - poutre.getP1().getDistance(V));
				}
				if( x> poutre.setLongueur()/2){//le point sur lequel on calcul le torseur est après la voiture et après le barycentre
					this.X= -X - (V.getPoids()*(Math.sin(poutre.getAngle()))) - (poutre.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y + (V.getPoids()*(Math.cos(poutre.getAngle()))) + (poutre.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + x*Y - V.getPoids()*(Math.cos(poutre.getAngle()))* (x - poutre.getP1().getDistance(V)) - poutre.getPoids()*(Math.cos(poutre.getAngle()))* (x- poutre.setLongueur()/2);
				}
				
			}
			if ( poutre.getP1().getDistance(V) >= poutre.setLongueur()/2 ){//V est après le barycentre
				if(x < poutre.setLongueur()/2 ){//le point sur lequel on calcul le torseur est avant la voiture et avant le barycentre
					this.X= -X;
					this.Y= -Y;
					this.M= -M;
				}
				
				if(x < poutre.getP1().getDistance(V) && x >= poutre.setLongueur()/2){// le point sur lequel on calcul le torseur est avant voiture et après barycentre
					this.X= -X -(poutre.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y - (poutre.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + x*Y - (poutre.getPoids()*(Math.cos(poutre.getAngle()))* (x-poutre.setLongueur())/2);
				}
				if(x >= poutre.getP1().getDistance(V)){//le point sur lequel on calcul le torseur est après voiture
					this.X= -X -(poutre.getPoids()*(Math.sin(poutre.getAngle()))) - (V.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y + (poutre.getPoids()*(Math.cos(poutre.getAngle()))) + (V.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + x*Y - (poutre.getPoids()*(Math.cos(poutre.getAngle()))* (x-poutre.setLongueur())/2) - (V.getPoids()*(Math.cos(poutre.getAngle()))*(x - V.getDistance(P1) ));
				}
			}
		}else{ 
			
			if (x < poutre.setLongueur()/2){//le point sur lequel on calcul le torseur est avant barycentre de la poutre
				this.X= -X;
				this.Y= -Y;
				this.M= -M + x*Y;
			}	
			if(P.getX()>poutre.setLongueur()/2){//le point sur lequel on calcul le torseur est apres barycentre de la poutre
				this.X= -X -(poutre.getPoids()*(Math.sin(poutre.getAngle())));
				this.Y= -Y + (poutre.getPoids()*(Math.cos(poutre.getAngle())));
				this.M= -M + x*Y - (poutre.getPoids()*(Math.cos(poutre.getAngle()))* (x-poutre.setLongueur())/2);
			}
		
		}
		
	}
	
	public Torseur (Distance poutre, double X, double Y, double M, Point V){
		this.V = V;
		
		if( V.appartientPoutre(poutre))){ // V appartient au segment et on sait que V est sur la droite
			if( V.getX()<poutre.getBarycentre().getX()){ //V est avant le barycentre
					this.X= -X- (V.getPoids()*(Math.sin(poutre.getAngle()))) - (poutre.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y + (V.getPoids()*(Math.cos(poutre.getAngle()))) + (poutre.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + poutre.setLongueur()*Y - V.getPoids()*Math.cos(poutre.getAngle()* (poutre.setLongueur() - V.getDistance(P1)) - poutre.getPoids()*Math.cos(poutre.getAngle()* (poutre.setLongueur() - poutre.setLongueur())/2));
				}
				
			if ( V.getX() >= poutre.getBarycentre().getX() ){//V est après le barycentre
					this.X= -X -(poutre.getPoids()*(Math.sin(poutre.getAngle()))) - (V.getPoids()*(Math.sin(poutre.getAngle())));
					this.Y= -Y + (poutre.getPoids()*(Math.cos(poutre.getAngle()))) + (V.getPoids()*(Math.cos(poutre.getAngle())));
					this.M= -M + poutre.getP2.getX()*Y - poutre.getPoids()*Math.cos(poutre.getAngle())* (poutre.setLongueur()-poutre.setLongueur()/2) - V.getPoids()*Math.cos(poutre.getAngle())*(poutre.setLongueur() - V.getDistance(P1));
				}
				
		}else{// V N'appartient pas au segment
			this.X= -X -(poutre.getPoids()*(Math.sin(poutre.getAngle())));
			this.Y= -Y + (poutre.getPoids()*(Math.cos(poutre.getAngle())));
			this.M= -M + poutre.setLongueur()*Y - (poutre.getPoids()*(Math.cos(poutre.getAngle()))* (poutre.setLongueur() -poutre.setLongueur())/2);
			}	
	
				
		}
		

	
	/* méthode renvoyant la composante X de traction du torseur */
	public double getX(){
		return this.X;
	}
	
	/* méthode renvoyant la composante Y de cisaillement du torseur */
	public double getY(){
		return this.Y;
	}
	
	/* méthode renvoyant la composante M de flexion du torseur */
	public double getM(){
		return this.M;
	}
	
	public int compareTo(Torseur torseur){
		return 0;		
	}

}


/* méthode renvoyant une liste comportant les torseurs de chaque extremité gauche des segments du pont.
 Cette liste est ordonnée (Le ième torseur est le torseur de l'extremité gauche du ième segment en comptant de gauche à droite) */
 public Arraylist<Torseur> getTorseursExtremites( Arrayliste <Distance> Pont){
	 Arraylist<Torseur> Resultat = new Arraylist<Torseur> ;
	 Torseur TorseurExtremiteAvant= new Torseur(simulation.pivotA*Math.sin(pont.get(0).getAngle(),simulation.pivotA*Math.cos(pont.get(0).getAngle(),0); 
	 Resultat.add(TorseurExtremiteAvant);  //on ajoute le Torseur de la 1er extremitée (point de départ du pont) dans la liste Resultat,
	 
	 for (int i :Pont){ // on parcourt les segments du pont
		 double phi= pont.get(i+1).getAngle()-pont.get(i).getAngle(); // phi représente l'angle entre deux segments consécutifs
		 Torseur Torseurextremite= (  pont.get(i), TorseurExtremiteAvant.X*Math.cos(phi)+TorseurExtremiteAvant.Y*Math.sin(phi),-TorseurExtremiteAvant.X*Math.sin(phi)+TorseurExtremiteAvant.Y*Math.cos(phi), TorseurExtremiteAvant.M, Point V);
		 Torseur TorseurExtremiteAvant= Torseur Torseurextremite;
		 Resultat.add(Torseuretremite);
	   return Resultat;
	}  
/* méthode renvoyant les coordonnées du point du pont sur lequel il y a une rupture si le pont se casse.
 Si le pont ne se casse pas, cette méthode renvoie un point de coordonnée (-1,-1) */ 
public 	 Point Pointrupture( ArrayList <Distance> pont){
		ArrayList < Torseur> TorseurExtremite =  pont.getTorseursExtremites;
		Point V = new Point ( Vehicule.getX(), Vehicule.getY() );
		
		Double Xmax=0;
		double posX= 0;
		double SegmentX= 0;
		double posY= 0;
		double SegmentY= 0;
		double posZ= 0;
		double SegmentZ= 0;
		Double Ymax=0;
		Double Zmax=0;
		for ( int i : pont){
			for ( double j=0; j<= pont.get(i).setLongueur(); j++){
				Torseur = new Torseur ( pont.get(i), torseurExtremite.get(i).getX, torseurExtremite.get(i).getY, torseurExtremite.get(i).getM, j, point V);
				If ( Math.abs(Torseur.getX()) > Xmax){
					Xmax= Math.abs(Torseur.getX());
					posX= j;
					SegmentX= i;
					}
				If ( Math.abs(Torseur.getY()) > Ymax){
					Ymax=Math.abs(Torseur.getY());
					posY=j;
					SegmentY=i;
					}
				If ( Math.abs(Torseur.getY()) > Ymax){
					Mmax=Math.abs(Torseur.getY();
					posM=j;
					SegmentM=i;
					}
		}
		}
		if (Pont.matiere==1){
			double EcartX=(Xmax/10)-40*1000000;
			double EcartY=(Ymax/10)-8*100000;
			double EcartZ=(Zmax/10)-30,6*1000000;
		
		if (Pont.matiere==2){
			double EcartX=(Xmax/10)-360*1000000;
			double EcartY=(Ymax/10)-0,4*1000000;
			double EcartZ=(Zmax/10)-1,56*100000000;
		}
		
		if (Pont.matiere==3){
			double EcartX=(Xmax/10)-2,6*1000000;
			double EcartY=(Ymax/10)-1000000;
			double EcartZ=(Zmax/10)-30*1000000;
		}
			
		if ((EcartX<=0) && (EcartY<=0) && (EcartZ<=0)){
			
			return new Point (-1,-1);
		}
		
		
		
		if ((EcartX>=0) && (EcartX>=EcartY) && (EcartX>=EcartZ)){
			
			double coordonneex= posX*Math.sin(pont.get(SegmentX).getAngle())+pont.get(SegmentX).get(P1).get(x);
			double coordonneey= posX*Math.cos(pont.get(SegmentX).getAngle())+pont.get(SegmentX).get(P1).get(y);
				
			return new Point (coordonneex,coordonneey);
		}
		
		if ((EcartY>=0) && (EcartY>=EcartX) && (EcartY>=EcartZ)){
			
			double coordonneex= posY*Math.sin(pont.get(SegmentY).getAngle())+pont.get(SegmentY).get(P1).get(x);
			double coordonneey= posY*Math.cos(pont.get(SegmentY).getAngle())+pont.get(SegmentY).get(P1).get(y);
			
			return new Point (coordonneex,coordonneey);
		}
			
		if ((EcartZ>=0) && (EcartZ>=EcartY) && (EcartZ>=EcartX)){
			
			double coordonneex= posZ*Math.sin(pont.get(SegmentZ).getAngle())+pont.get(SegmentZ).get(P1).get(x);
			double coordonneey= posZ*Math.cos(pont.get(SegmentZ).getAngle())+pont.get(SegmentZ).get(P1).get(y);
			
			return new Point (coordonneex,coordonneey);
		}	
			
			
		
		}			
			
		
		
	
	 
	 
	 
	
