package modele;

import java.awt.image.BufferedImage;

public class Fractales {
	protected int width;
	protected int height;
	protected double zoom;
	protected int translateX;
	protected int translateY;
	protected double pas;
	protected int MAX_ITER;
	
	
	
	/*public Fractales(int width, int height, double pas, int MAX_ITER) {
		this.width = width;
		this.height = height;
		this.pas = pas;
		this.MAX_ITER = MAX_ITER;
	}*///BUILDER
	public Fractales() {}
	
	public static class FractaleBuilder{
		private int width;
		private int height;		
		private double pas;
		private int MAX_ITER;
		
		private FractaleBuilder() {}
		
		public static FractaleBuilder newFractaleBuilder() {
			return new FractaleBuilder();
		}
		
		public FractaleBuilder width(int largeur) {
			this.width=largeur;
			return this;
		}
		
		public FractaleBuilder height(int hauteur) {
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
		
		/*public Fractales build() {
			return new Fractales(this);
		}*/
		public Julia buildJulia(Complexe c) {
			return new Julia(this,c);
		}
		
		public Mandelbrot buildMandelbrot() {
			return new Mandelbrot(this);
		}
		
		public BurningShip buildBurningShip() {
			return new BurningShip(this);
		}
		
	}
	
	protected Fractales (FractaleBuilder f) {
		this.width=f.width;
		this.height=f.height;
		this.zoom = 1.0;
		this.translateX = 0;
		this.translateY = 0;
		this.pas=f.pas;
		this.MAX_ITER=f.MAX_ITER;
	}
	
	public void zoom() {
		this.zoom += 1;
	}
	
	public void dezoom() {
		this.zoom -= 1;
		if (this.zoom < 1) { this.zoom = 1; }
	}
	
	public void translateX(boolean sign) {
		if (sign) {  //Deplacement vers la droite
			this.translateX += (int) (width*0.1);
		}
		else { //Deplacement vers la gauche
			this.translateX -= (int) (width*0.1);
		}
	}
	
	public void translateY(boolean sign) {
		if (sign) { //Deplacement vers le bas
			this.translateY += (int) (height*0.1);
		}
		else { //Deplacement vers le haut
			this.translateY -= (int) (height*0.1);
		}
	}
	
	public BufferedImage createImg() {
		System.out.println("Bonjour");
		return null;
	}
	
//	int divergenceIndex(Complexe z0) {
//	int ite = 0; Complex zn = z0;
//	// sortie de boucle si divergence
//	while (ite < MAX_ITER-1 && |zn| <= RADIUS)
//	zn = f(zn); ite++;
//	return ite;
//	}

}
