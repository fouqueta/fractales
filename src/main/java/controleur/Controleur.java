package controleur;
import vue.*;

import java.awt.image.BufferedImage;

import modele.*;
import modele.Fractales.FractaleBuilder;

public class Controleur {
	private Vue vue;
	private Fractales fractale;


	public Controleur() {
		this.fractale=new Fractales();
	}
	
	
	//Build une fractale Julia selon les parametres et retourne son image
	public BufferedImage generateJulia(double cstR, double cstI, double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		Complexe c = Complexe.newComplexe(cstR, cstI);
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildJulia(c);
		return fractale.generateFractal();
	}
	
	public BufferedImage generateJuliaBis(String type, double infX, double supX, double infY, double supY, double pas, int max_iter, 
			int couleur, String name, double zoom, int translateX, int translateY, double cstR, double cstI) {
		Complexe c = Complexe.newComplexe(cstR, cstI);
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name)
					.type(type)				
					.zoom(zoom)
					.translateX(translateX)
					.translateY(translateY)
					.buildJulia(c)
					;
		return fractale.generateFractal();
	}
	
	
	//Build une fractale Mandelbrot selon les parametres et retourne son image
	public BufferedImage generateMandelbrot(double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildMandelbrot();				;
		return fractale.generateFractal();
	}
	
	public BufferedImage generateMandelbrotBis(String type, double infX, double supX, double infY, double supY, double pas, int max_iter, 
			int couleur, String name, double zoom, int translateX, int translateY) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name)
				.type(type)				
				.zoom(zoom)
				.translateX(translateX)
				.translateY(translateY)
				.buildMandelbrot()
				;
		return fractale.generateFractal();
	}
	
	
	//Build une fractale Burning ship selon les parametres et retourne son image
	public BufferedImage generateBurningShip(double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildBurningShip();
		return fractale.generateFractal();
	}
	
	public BufferedImage generateBurningShipBis(String type, double infX, double supX, double infY, double supY, double pas, int max_iter, 
			int couleur, String name, double zoom, int translateX, int translateY) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name)
				.type(type)				
				.zoom(zoom)
				.translateX(translateX)
				.translateY(translateY)
				.buildBurningShip()
				;
		return fractale.generateFractal();
	}
	
	
	//Build une fractale Tricorn selon les parametres et retourne son image
	public BufferedImage generateTricorn(double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildTricorn();
		return fractale.generateFractal();
	}
	
	public BufferedImage generateTricornBis(String type, double infX, double supX, double infY, double supY, double pas, int max_iter, 
			int couleur, String name, double zoom, int translateX, int translateY) {
		fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name)
				.type(type)				
				.zoom(zoom)
				.translateX(translateX)
				.translateY(translateY)
				.buildTricorn()
				;
		return fractale.generateFractal();
	}
	
	//Fonction auxilliaire qui retourne un FractaleBuilder
	public FractaleBuilder builder(double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		return FractaleBuilder.newFractaleBuilder()
				.borneInfX(infX)
				.borneSupX(supX)
				.borneInfY(infY)
				.borneSupY(supY)
				.pas(pas)
				.MAX_ITER(max_iter)
				.couleur(couleur)
				.name(name);
	}
	
	//Incremente ou decremente le zoom
	public BufferedImage zoom(boolean zoom) {
		if (zoom) { //Si true, zoom
			fractale.zoom();
		}
		else { //Si false, dezoom
			fractale.dezoom();
		}
		return fractale.generateFractal();
	}
	
	//Deplacement dans l'image
	public BufferedImage translateX(boolean sign) {
		fractale.translateX(sign);
		char dir = sign ? 'R' : 'L';
		return fractale.translate(dir);
	}
	
	public BufferedImage translateY(boolean sign) {
		fractale.translateY(sign);
		char dir = sign ? 'D' : 'U';
		return fractale.translate(dir);
	}
	
	public void saveFractale() {
		fractale.saveFractal();
	}

	
	//SETTER
	public void setVue(Vue vue) { this.vue = vue; }

	
}