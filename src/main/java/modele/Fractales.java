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

	protected String type;
	protected int width;
	protected int height;
	protected double borneInfX;
	protected double borneSupX;
	protected double borneInfY;
	protected double borneSupY;
	protected double zoom;
	protected int translateX;
	protected int translateY;
	protected double pas;
	protected int couleur;
	protected String name;
	protected int MAX_ITER;
	protected FonctionFractale f;
	protected BufferedImage img;
	
	
	
	public Fractales() {}
	
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
		
		private FractaleBuilder() {}
		
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
		this.borneInfX = f.borneInfX;
		this.borneSupX = f.borneSupX;
		this.borneInfY = f.borneInfY;
		this.borneSupY = f.borneSupY;		
		this.pas = f.pas;
		this.width = (int) ((borneSupX - borneInfX) * 1/pas +1);
		this.height = (int) ((borneSupY - borneInfY) * 1/pas +1);
		this.MAX_ITER = f.MAX_ITER;
		this.name = f.name;
		
		this.zoom = f.zoom;
		this.translateX = f.translateX;
		this.translateY = f.translateY;
		this.img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		this.couleur=f.couleur;
	}
		
	
	public BufferedImage generateFractal() {
		//System.out.println(borneInfX+" "+borneSupX+" "+borneInfY+" "+borneSupY+" "+pas+" "+MAX_ITER+" "+couleur+" "+name+" "+zoom+" "+translateX+" "+translateY+" " );
		long start=System.currentTimeMillis();
		stream(0,width,0,height, couleur);
	    long end=System.currentTimeMillis();
	    System.out.println("Parallel stream took time : "+(end-start));
	    return img;
	}
	
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
		lignes.add("couleur:" + couleur); //jusqu'à couleur on utilise les builder, le reste faudra generer avec des setters/ OU TOUT EN BUILDER ?
		lignes.add("name:" + name);
		lignes.add("width:" + width);
		lignes.add("height:" + height);
		lignes.add("zoom:" + zoom);
		lignes.add("translateX:" + translateX);
		lignes.add("translateY:" + translateY);
		if (this instanceof Julia) {
			lignes.add(2,"reel:" + ((Julia) this).getReelC());
			lignes.add(3,"imaginaire:" + ((Julia) this).getImaginaireC());
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
	
	
	//Utilisation de parallel stream pour le calcul des fractales
	public void stream(int startX, int endX, int startY, int endY, int couleur) {
		double gapX = 1./zoom;
		double gapY = 1./zoom;
		IntStream.range(startX, endX)
        .parallel()
        .forEach(i -> IntStream.range(startY, endY)
                .parallel()
                .forEach(j -> iterStream(i,j, gapX, gapY, couleur)));
	}
	
	//pour un point dans la plan, calcule son nombre nombre d'iterations et lui associe une couleur en fonction
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) { }
	
	//Deplacement de la fractale en fonction de la direction dir
	public BufferedImage translate(char dir) {
		//bornes pour le calcul de la nouvelle bande de pixels, modifiees en fonction de la direction
		int startX = 0;
		int endX = width;
		int startY = 0;
		int endY = height;
		int color = couleur;
		
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
		stream(startX, endX, startY, endY, color);
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
