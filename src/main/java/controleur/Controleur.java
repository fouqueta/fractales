package controleur;
import vue.*;

import java.awt.image.BufferedImage;

import modele.*;
import modele.Fractales.FractaleBuilder;

public class Controleur {
	private Vue vue;
	private Fractales fractale;


	public Controleur() {
		//this.fractale = fractale;
		this.fractale=new Fractales();
	}
	
	public BufferedImage create_Julia(double reel, double imaginaire, double pas, int iterateur, int largeur, int hauteur) {
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
		return ((Julia) fractale).createImg();
	}
	

	/*public BufferedImage generateJulia() {
		Complexe c1 = Complexe.newComplexe(-0.7269, 0.1889);
		fractale = FractaleBuilder.newFractaleBuilder()
				.width(3001)
				.height(2001)
				.pas(0.0001)
				.MAX_ITER(1000)
				.buildJulia(c1)
				;
		return ((Julia) fractale).createImg();
	}*/
	
	public BufferedImage zoom(boolean zoom) {
		if (zoom) { //Si true, zoom
			fractale.zoom();
		}
		else { //Si false, dezoom
			fractale.dezoom();
		}
		return ((Julia) fractale).createImg();
	}
	
	public BufferedImage translateX(boolean sign) {
		fractale.translateX(sign);
		char dir = sign ? 'R' : 'L';
		return ((Julia) fractale).translate(dir);
	}
	
	public BufferedImage translateY(boolean sign) {
		fractale.translateY(sign);
		char dir = sign ? 'D' : 'U';
		return ((Julia) fractale).translate(dir);
	}
	
	//SETTER
	public void setVue(Vue vue) { this.vue = vue; }
	
}