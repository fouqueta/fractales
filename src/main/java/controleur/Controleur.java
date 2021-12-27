package controleur;
import vue.*;
import modele.*;

public class Controleur {
	private Vue vue;
	private Fractales fractale;


	public Controleur(Fractales fractale) {
		this.fractale = fractale;
	}

	
	//GETTER
	public int getIte() { return fractale.getMAX_ITER(); }
	public String getMatriceSize() { return fractale.getMatrice(); }
	public Fractales getFractale() { return this.fractale; }
	
	//SETTER
	public void setVue(Vue vue) { this.vue = vue; }
	
	

}