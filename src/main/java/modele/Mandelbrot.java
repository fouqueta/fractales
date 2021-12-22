package modele;

import modele.Fractales.FractaleBuilder;

public class Mandelbrot extends Fractales{
	private Complexe c;
	private FonctionFractale f;

	public Mandelbrot(FractaleBuilder fb, Complexe c) {
		super(fb);
		this.c = c;
	}
	

}
