package modele;

import java.awt.image.BufferedImage;

public class Fractales {
	protected double width;
	protected double height;
	protected double pas;
	protected int MAX_ITER;
	
	
	/*public Fractales(double width, double height, double pas, int MAX_ITER, int RADIUS) {
		this.width = width;
		this.height=height;
		this.pas = pas;
		this.MAX_ITER = MAX_ITER;
		this.RADIUS = RADIUS;
	}//BUILDER*/
	
	public static class FractaleBuilder{
		private double width;
		private double height;
		private double pas;
		private int MAX_ITER;
		
		private FractaleBuilder() {}
		
		public static FractaleBuilder newFractaleBuilder() {
			return new FractaleBuilder();
		}
		
		public FractaleBuilder width(double largeur) {
			this.width=largeur;
			return this;
		}
		
		public FractaleBuilder height(double hauteur) {
			this.height=hauteur;
			return this;
		}
		public FractaleBuilder pas(double pasDiscretisation) {
			this.pas=pasDiscretisation;
			return this;
		}
		
		public FractaleBuilder MAX_ITER(int maxIter) {
			this.MAX_ITER=maxIter;
			return this;
		}
		
		public Fractales build() {
			return new Fractales(this);
		}
		public Julia buildJulia(Complexe c) {
			return new Julia(this,c);
		}
		/*public Mandelbrot buildMandelbrot(Complexe c) {
			return new Mandelbrot(this,c);
		}*/
		
	}
	
	protected Fractales (FractaleBuilder f) {
		this.width=f.width;
		this.height=f.height;
		this.pas=f.pas;
		this.MAX_ITER=f.MAX_ITER;
	}
	
	//calcul d'indice de divergence max de chaque point
	/*public int divergenceIndex (Complexe z0) {
		int ite = 0; 
		Complexe zn = z0;
		double module = Math.sqrt((zn.getReel()*zn.getReel()) + (zn.getImaginaire()*zn.getImaginaire()));
		while (ite < MAX_ITER-1 && module <= RADIUS) { 
			zn = f(zn); //definir f
			ite++;
		}
		return ite;
	}*/
		
	public void couleur(BufferedImage img, int indDivergence) {
		for (int i=0; i<img.getWidth(); i++) {
			for (int j=0; j<img.getHeight(); j++) {
				if (indDivergence<MAX_ITER) {
					int r = 64; int g = 224; int b = 208; //turquoise
					int col = (r << 16) | (g << 8) | b;
					img.setRGB(i, j, col);
				}else if (indDivergence>MAX_ITER) {
					int r = 0; int g = 0; int b = 0; //noir
					int col = (r << 16) | (g << 8) | b;
					img.setRGB(i, j, col);
				}
			}
		}
	}

}
