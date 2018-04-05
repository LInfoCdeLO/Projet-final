import java.util.*;
import java.awt.*;


public class Simulation {
	
	private ArrayList <Distance> pont;
	private double pivotA;
	private double ponctuelleB;
	private Véhicule vehicule;
	private Point [] tabPointsJonction;
	
	public Simulation(ArrayList <Distance> listePoutres, Point A, Point B, Véhicule vehicule){
		this.pont=listePoutres;
		this.pivotA=0;
		this.ponctuelleB=0;
		int taille=this.pont.size();
		System.out.println("taille Liste: "+taille);
		for(int i =0; i<taille; i++){
			this.pivotA=this.pivotA+this.pont.get(i).getPoids();
			this.ponctuelleB=(this.ponctuelleB-(this.pont.get(i).getPoids()*(this.pont.get(i).getBarycentre().getX()-A.getX())/(A.distance(B))));
			this.tabPointsJonction[i]=this.pont.get(i).getP1();
		}
		this.vehicule=vehicule;
	}
	
	
	
	
	

}
