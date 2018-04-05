import java.awt.Color;


public class Matiere {
	
	private double densite;
	private String nom;
	private Color c;
	
	public Matiere(int nindentification) {
		switch(nindentification){
		case 1:this.densite=0.450;
		this.nom="bois";
		this.c=new Color(153,76,0);
		break;
		case 2: this.densite=8.2;
		this.nom="acier";
		this.c=new Color(213,213,213);
		break;
		default: this.densite=2.4;
		this.nom="beton";
		this.c=new Color(100,100,100);
		break;
		}
	}
	
	public double getDensite(){
		return densite;
	}
	
	public Color getColor(){
		return c;
	}
}
