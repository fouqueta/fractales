package controleur;
import vue.*;
import modele.*;

public class Controleur {
	private Vue vue;
	private Fractales fractale;


	public Controleur(Fractales fractale) {
		this.fractale = fractale;
	}

	
	public void setVue(Vue vue) {
		this.vue = vue;
	}
	
	public Fractales getFractale() { return this.fractale; }

}