package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class Fractales {

	protected final String type;
	protected final String name;
	protected final int width;
	protected final int height;
	protected final double borneInfX;
	protected final double borneSupX;
	protected final double borneInfY;
	protected final double borneSupY;
	protected final double pas;
	protected final int couleur;
	protected final int MAX_ITER;
	protected double zoom;
	protected int translateX;
	protected int translateY;
	protected FonctionFractale f;
	protected BufferedImage img;
	
	
	public Fractales() {
		this.type = "";
		this.name = "";
		this.width = 0;
		this.height = 0;
		this.borneInfX = 0;
		this.borneSupX = 0;
		this.borneInfY = 0;
		this.borneSupY = 0;
		this.pas = 0;
		this.couleur = 0;
		this.MAX_ITER = 0;
	}
	
	
	public static class FractaleBuilder{
		private String type;
		private double borneInfX;
		private double borneSupX;
		private double borneInfY;
		private double borneSupY;
		private double pas;
		private int MAX_ITER;
		private int couleur;
		private String name;
		private double zoom = 1.0;
		private int translateX = 0;
		private int translateY = 0;
		
		public static FractaleBuilder newFractaleBuilder() {
			return new FractaleBuilder();
		}

		public FractaleBuilder type(String type) {
			this.type=type;
			return this;
		}
		public FractaleBuilder borneInfX(double x1) {
			this.borneInfX = x1;
			return this;
		}
		public FractaleBuilder borneSupX(double x2) {
			this.borneSupX = x2;
			return this;
		}
		public FractaleBuilder borneInfY(double y1) {
			this.borneInfY = y1;
			return this;
		}
		public FractaleBuilder borneSupY(double y2) {
			this.borneSupY = y2;
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
		
		public FractaleBuilder couleur(int color) {
			this.couleur=color;
			return this;
		}
		
		public FractaleBuilder name(String nom) {
			this.name=nom;
			return this;
		}
		
		public FractaleBuilder zoom(double zoom) {
			this.zoom=zoom;
			return this;
		}
		
		public FractaleBuilder translateX(int translateX) {
			this.translateX=translateX;
			return this;
		}
		
		public FractaleBuilder translateY(int translateY) {
			this.translateY=translateY;
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
		this.type=f.type;
		this.name = f.name;
		this.borneInfX = f.borneInfX;
		this.borneSupX = f.borneSupX;
		this.borneInfY = f.borneInfY;
		this.borneSupY = f.borneSupY;		
		this.pas = f.pas;
		this.MAX_ITER = f.MAX_ITER;
		this.zoom = f.zoom;
		this.translateX = f.translateX;
		this.translateY = f.translateY;
		this.couleur=f.couleur;
		this.width = (int) ((borneSupX - borneInfX) * 1/pas +1);
		this.height = (int) ((borneSupY - borneInfY) * 1/pas +1);
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);		
	}
		
	
	//Sauvegarde l'image de la fractale dans un .png et sa description dans un .txt
	public void saveFractal() {
		File fImg = new File("src/main/sauvegardes/" + name + ".png");
		
		List<String> lignes = new ArrayList<String>();
		lignes.add("TYPE DE FRACTALE:" + typeFractale());
		lignes.add("ATTRIBUTS:VALEURS");
		lignes.add("borneInfX:" + borneInfX);
		lignes.add("borneSupX:" + borneSupX);
		lignes.add("borneInfY:" + borneInfY);
		lignes.add("borneSupY:" + borneSupY);
		lignes.add("pas:" + pas);
		lignes.add("MAX_ITER:" + MAX_ITER);
		lignes.add("couleur:" + couleur);
		lignes.add("name:" + name);
		lignes.add("width:" + width);
		lignes.add("height:" + height);
		lignes.add("zoom:" + zoom);
		lignes.add("translateX:" + translateX);
		lignes.add("translateY:" + translateY);
		if (this instanceof Julia) {
			lignes.add(2,"constante partie reelle:" + ((Julia) this).getReelC());
			lignes.add(3,"constante partie imaginaire:" + ((Julia) this).getImaginaireC());
		}
		Path fTxt = Paths.get("src/main/sauvegardes/" + name + ".txt");
		try {
			ImageIO.write(img, "PNG", fImg);
			Files.write(fTxt, lignes, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//retourne le String type correspondant au type de la fractale
	public String typeFractale() {
		if (this instanceof Julia) { return "Julia"; }
		else if (this instanceof Mandelbrot) { return "Mandelbrot"; }
		else if (this instanceof BurningShip) { return "BurningShip"; }
		else { return "Tricorn"; }
	}
	
	
	//Fonction principale qui calcule la fractale et met a jour l'image
	public BufferedImage generateFractal() {
		long start=System.currentTimeMillis();
		stream(0,width,0,height, couleur);
	    long end=System.currentTimeMillis();
	    System.out.println("Temps de calcul : "+(end-start));
	    return img;
	}
	
	
	//Utilisation de stream paralleles pour le calcul des fractales
	public void stream(int startX, int endX, int startY, int endY, int couleur) {
		double gapX = 1./zoom;
		double gapY = 1./zoom;
		IntStream.range(startX, endX)
        .parallel()
        .forEach(i -> IntStream.range(startY, endY)
                .parallel()
                .forEach(j -> iterStream(i,j, gapX, gapY, couleur)));
	}
	
	//Pour un point dans la plan, calcule son nombre nombre d'iterations et lui associe une couleur en fonction de ce nombre
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) { }
	
	//Deplacement dans l'image de la fractale en fonction de la direction dir
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
		case 'L' : //inverse
			translate_pixels_LtoR(width, (int) (width*0.1), height, 0, width*0.1, 0);
			endX = (int) (width*0.1);
			break;
		case 'U' : //on se deplace vers le haut, donc translation des points existants vers le bas car nouveaux points a calculer en haut
			translate_pixels_LtoR(width, 0, height, (int) (height*0.1), 0, height*0.1);
			endY = (int) (height*0.1);
			break;
		case 'D' : //inverse
			translate_pixels_RtoL(0, width, 0, (int) (height-height*0.1), 0, height*0.1);
			startY = (int) (height-height*0.1);
			endY = height;
			break;
		}
		stream(startX, endX, startY, endY, couleur);
		return img;
	}
	
	//Pour un point courant, on regarde la couleur a la position (posCourante + pas) et on met a jour
	public void translate_pixels_RtoL(int startX, int endX, int startY, int endY, double moveX, double moveY) {		
		IntStream.range(startX, endX)
        .forEach(i -> IntStream.range(startY, endY)
               .forEach(j -> switch_colors(i,j, moveX, moveY)));
	}
	
	//Pour un point courant, on regarde la couleur a la position (posCourante - pas) et on met a jour
	public void translate_pixels_LtoR(int startX, int endX, int startY, int endY, double moveX, double moveY) {		
		for (int i = startX-1; i >= endX; i--) {
			for (int j = startY-1; j >= endY; j--) {
				switch_colors(i,j, -moveX, -moveY);
			}
		}
	}
	
	//Le pixel courant prend la couleur du pixel a la position voulue
	public void switch_colors(int x, int y, double moveX, double moveY) {
		int rgb = img.getRGB((int) (x+moveX), (int) (y+moveY));
		img.setRGB(x, y, rgb);
	}
	
	//incremente le zoom
	public void zoom() {
		this.zoom += 1;
	}
	
	//decremente le zoom
	public void dezoom() {
		this.zoom -= 1;
		if (this.zoom < 1) { this.zoom = 1; }
	}
	
	//fonctions pour les attributs de position dans l'image
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
	
	
}
