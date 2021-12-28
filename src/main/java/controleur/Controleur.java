package controleur;
import vue.*;
import modele.*;
import modele.Fractales.FractaleBuilder;

public class Controleur {
	private Vue vue;
	private Fractales fractale;


	public Controleur() {
		//this.fractale = fractale;
		this.fractale=new Fractales();
	}
	
	public void create_Julia(double reel, double imaginaire, double pas, int iterateur, int largeur, int hauteur) {
		Complexe c = Complexe.newComplexe(reel, imaginaire);
		fractale = FractaleBuilder.newFractaleBuilder()
				.width(largeur)
				.height(hauteur)
				.pas(pas)
				.MAX_ITER(iterateur)
				.buildJulia(c)
				;
		//System.out.println(fractale.getMatrice());
		//System.out.println(c.getReel());
	}
	
	
	//GETTER
	public Fractales getFractale() { return this.fractale; }
	
	//SETTER
	public void setVue(Vue vue) { this.vue = vue; }
	
	public void setFractale(Fractales fractale) { 
		this.fractale=fractale; 
	}
	
}