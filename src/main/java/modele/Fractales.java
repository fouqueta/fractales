package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class Fractales {

	protected int width;
	protected int height;
	protected double zoom;
	protected int translateX;
	protected int translateY;
	protected double pas;
	protected int MAX_ITER;
	protected FonctionFractale f;
	protected BufferedImage img;
	
	
	
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
		
		public Julia buildJulia(Complexe c) {
			return new Julia(this,c);
		}
		
		public Mandelbrot buildMandelbrot() {
			return new Mandelbrot(this);
		}
		
		public BurningShip buildBurningShip() {
			return new BurningShip(this);
		}
		
		public Tricorn buildTricorn() {
			return new Tricorn(this);
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
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
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
	
	
	/*public BufferedImage createImg() {		
		long start=System.currentTimeMillis();
		stream(0,width,0,height);
	    long end=System.currentTimeMillis();
	    System.out.println("Parallel stream took time : "+(end-start));
    
	    File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}*/
	
	public BufferedImage generateFractal() {
		long start=System.currentTimeMillis();
		stream(0,width,0,height);
	    long end=System.currentTimeMillis();
	    System.out.println("Parallel stream took time : "+(end-start));
	    return img;
	}
	
	public void saveFractal() {
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Utilisation de parallel stream pour le calcul des fractales
	public void stream(int startX, int endX, int startY, int endY) {
		double gapX = 1./zoom;
		double gapY = 1./zoom;
		IntStream.range(startX, endX)
        .parallel()
        .forEach(i -> IntStream.range(startY, endY)
                .parallel()
                .forEach(j -> iterStream(i,j, gapX, gapY)));
	}
	
	//pour un point dans la plan, calcule son nombre nombre d'iterations et lui associe une couleur en fonction
	public void iterStream(int x, int y, double gapX, double gapY) { }
	
	//Deplacement de la fractale en fonction de la direction dir
	public BufferedImage translate(char dir) {
		//bornes pour le calcul de la nouvelle bande de pixels, modifiees en fonction de la direction
		int startX = 0;
		int endX = width;
		int startY = 0;
		int endY = height;
		
		switch(dir) {
		case 'R' : //on se deplace vers la droite, donc translation des points existants vers la gauche car nouveaux points a calculer a droite
			translate_pixels_RtoL(0, (int) (width-width*0.1), 0, height, width*0.1, 0);
			startX = (int) (width-width*0.1);
			endX = width;
			break;
		case 'L' : //inversement
			translate_pixels_LtoR(width, (int) (width*0.1), height, 0, width*0.1, 0);
			endX = (int) (width*0.1);
			break;
		case 'U' : //on se deplace vers le haut, donc translation des points existants vers le bas car nouveaux points a calculer en haut
			translate_pixels_LtoR(width, 0, height, (int) (height*0.1), 0, height*0.1);
			endY = (int) (height*0.1);
			break;
		case 'D' : //inversement
			translate_pixels_RtoL(0, width, 0, (int) (height-height*0.1), 0, height*0.1);
			startY = (int) (height-height*0.1);
			endY = height;
			break;
		}
		stream(startX, endX, startY, endY);
		return img;
	}
	
	public void translate_pixels_RtoL(int startX, int endX, int startY, int endY, double moveX, double moveY) {		
		IntStream.range(startX, endX)
        .forEach(i -> IntStream.range(startY, endY)
               .forEach(j -> switch_colors(i,j, moveX, moveY)));
	}
	
	
	public void translate_pixels_LtoR(int startX, int endX, int startY, int endY, double moveX, double moveY) {		
		for (int i = startX-1; i >= endX; i--) {
			for (int j = startY-1; j >= endY; j--) {
				switch_colors(i,j, -moveX, -moveY);
			}
		}
	}
	
	public void switch_colors(int x, int y, double moveX, double moveY) {
		int rgb = img.getRGB((int) (x+moveX), (int) (y+moveY));
		img.setRGB(x, y, rgb);
	}
	
	
	//GETTER
	public String getMatrice() { return this.width+" x "+this.height; }



	
//	int divergenceIndex(Complexe z0) {
//	int ite = 0; Complex zn = z0;
//	// sortie de boucle si divergence
//	while (ite < MAX_ITER-1 && |zn| <= RADIUS)
//	zn = f(zn); ite++;
//	return ite;
//	}

}
