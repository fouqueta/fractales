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
	
	
	public BufferedImage generateJulia(double reel, double imaginaire, double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		Complexe c = Complexe.newComplexe(reel, imaginaire);
		fractale = FractaleBuilder.newFractaleBuilder()
				.borneInfX(infX)
				.borneSupX(supX)
				.borneInfY(infY)
				.borneSupY(supY)
				.pas(pas)
				.MAX_ITER(max_iter)
				.couleur(couleur)
				.name(name)
				.buildJulia(c)
				;
		return fractale.generateFractal();
	}
	
	public BufferedImage generateJuliaBis(String type, double borneInfX, double borneSupX, double borneInfY, double borneSupY, double pas, int MAX_ITER, int couleur, String name, double zoom, int translateX, int translateY, double reel, double imaginaire) {
		Complexe c = Complexe.newComplexe(reel, imaginaire);
		fractale = FractaleBuilder.newFractaleBuilder()
				.type(type)
				.borneInfX(borneInfX)
				.borneSupX(borneSupX)
				.borneInfY(borneInfY)
				.borneSupY(borneSupY)
				.pas(pas)
				.MAX_ITER(MAX_ITER)
				.couleur(couleur)
				.name(name)
				.zoom(zoom)
				.translateX(translateX)
				.translateY(translateY)
				.buildJulia(c)
				;
		
		return fractale.generateFractal();
	}

//	public BufferedImage generateJulia(double reel, double imaginaire, double pas, int iterateur, int largeur, int hauteur, int color) {
//		Complexe c = Complexe.newComplexe(reel, imaginaire);
//		fractale = FractaleBuilder.newFractaleBuilder()
//				.width(largeur)
//				.height(hauteur)
//				.pas(pas)
//				.MAX_ITER(iterateur)
//				.couleur(color)
//				.buildJulia(c)
//				;
//		return fractale.generateFractal();
//	}
	

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

	
	
	public BufferedImage generateMandelbrot() {
		fractale = FractaleBuilder.newFractaleBuilder()
//				.width(3001)
//				.height(2001)
				.pas(0.0001)
				.MAX_ITER(100)
				.buildMandelbrot()
				;
		return fractale.generateFractal();
	}
	
	public BufferedImage generateBurningShip() {
		fractale = FractaleBuilder.newFractaleBuilder()
//				.width(3001)
//				.height(2001)
				.pas(0.0001)
				.MAX_ITER(100)
				.buildBurningShip()
				;
		return fractale.generateFractal();
	}
	
	public BufferedImage generateTricorne() {
		fractale = FractaleBuilder.newFractaleBuilder()
//				.width(3001)
//				.height(2001)
				.pas(0.0001)
				.MAX_ITER(100)
				.buildTricorn()
				;
		return fractale.generateFractal();
	}
	
	public BufferedImage zoom(boolean zoom) {
		if (zoom) { //Si true, zoom
			fractale.zoom();
		}
		else { //Si false, dezoom
			fractale.dezoom();
		}
		return fractale.generateFractal();
	}
	
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