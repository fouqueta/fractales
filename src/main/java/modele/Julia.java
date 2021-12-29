package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

//3/(width*x)-1.5
//3=borne à droite - ma borne à gauche || DIVISER par var = width - 0 = width || ca c'est mon a || fois x || donc on obtient f(x) = ax || mtnt il faut b, donc on fait f(x) - ax = b || ici b = -1.5 si on prend f(x) =  -1.5 et donc x = 0
//(yb-ya)/(xb-xa) = a

public class Julia extends Fractales {
	private final Complexe c;
	

	public Julia(FractaleBuilder fb, Complexe c) {
		super(fb);
		this.c = c;
		f = (z,cst) -> z.multiply(z).add(cst);
	}
	
	/*public void choisirNom() {
	System.out.println("Nom du fichier?");
	Scanner sc = new Scanner(System.in);
	String s = sc.next();
	if (!s.contentEquals("")) {
		createImg(s);
	}else { 
		System.out.println("Veuillez taper au moins un caractere");
		choisirNom();
	}
}*/


//	public BufferedImage createImg() {		
////		int r = 64; int g = 224; int b = 208; //turquoise
////		int col = (r << 16) | (g << 8) | b;
////		img.setRGB(30,40,col);
////		for (int y = -1; y < 1; y+=0.01) {
////			for (int x = -1; x < 1; x+=0.01) {
//				//iterPts(img,f);
////			}
////		}
////		long start=System.currentTimeMillis();
////		iterPts(img,f);
////	    long end=System.currentTimeMillis();
////	    System.out.println("NOT thread took time : "+(end-start));
//	
//		long start=System.currentTimeMillis();
//		stream(0,width,0,height);
//	    long end=System.currentTimeMillis();
//	    System.out.println("Parallel stream took time : "+(end-start));
//	    //TODO: separer en 2 fonctions
//	    File f = new File("MyFile.png");
//		try {
//			ImageIO.write(img, "PNG", f);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return img;
//	}

	
	@Override
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) {
		int i = 0;
		double zX = ((3.0/width)*(x+translateX)*gapX)-1.5*gapX;
		double zY = ((2.0/height)*(y+translateY)*gapY)-1.0*gapY;
		Complexe z = Complexe.newComplexe(zX, zY);
		while (z.module() <= 2 && i < MAX_ITER-1) {
			z = f.apply(z,c);
			i++;
		}
		int rgb;
		if (i == MAX_ITER-1) {
			rgb = 0;
		}
		else {
			rgb=Color.HSBtoRGB((float) (i+couleur)/MAX_ITER, 0.7f, 0.7f);
		}
		img.setRGB(x, y, rgb);
	}
	
	
	
	//ton pas c'est le nombre de point que tu veux entre 2 points
	//pour l'avoir tu fais (nombre intervalles divise par le nombre total de point que je veux)
	//exemple de 0 a 5, j'ai 6 points donc 5 intervalles, je veux passer de 5 points à 7, je fais 5/7 qui me donne mon pas
	//pour i de 0 à mon nombre de points
	//posX = i*pas
	
	//zomm initial = 1
	//la valeur du zoom pour les calcul que je veux = zoom que j'ai/zoom que je veux
	//exemple, j'ai un zoom de 1, je veux un zoom x2 => 1/2=0.5
	//si je veux un zoom x2 de mon zoom x2 => 0.5/2=0.25 !
	//la valeur pour les calcul c'est ce que j'utilise a la place de mon i++, je fais 

	
	
	public void iterPts(BufferedImage img, FonctionFractale f) {
		double zX = 0;
		double zY = 0;
		int w = img.getWidth();
		int h = img.getHeight();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
//				zX = 1.5 * (x - 0.5*w) / (0.5 * w);
//				zY = (y - 0.5*h) / (0.5 * h);
				zX = (2.0/width)*x-1.0;
				zY = (2.0/height)*y-1.0;
				//int h1 = h/2;
				//double h2 = 0.5*h;
				//System.out.println("ITERATION x = " + x + " et y = " + y);
//				System.out.println("h/2 = " + h1);
//				System.out.println("0.5*h = " + h2);
//				System.out.println("zX = " + zX);
//				System.out.println("zY = " + zY + "\n");
				Complexe z = Complexe.newComplexe(zX, zY);
				int i = 0;
				while (z.module() <= 2 && i < MAX_ITER) {
					z = f.apply(z,c);
					//z = z.multiply(z).add(c);
					//zX = Complexe.multiply(zn, zn).getReel() + cX;
					//zY = Complexe.multiply(zn, zn).getImaginaire() + cY;
//					double temp = zX * zX - zY * zY + cX;
//					zY = 2.0 * zX * zY + cY;
//					zX = temp;
					i++;
				}
				int rgb;
				if (i == MAX_ITER) {
					rgb = 0;
				}
				else {
					rgb=Color.HSBtoRGB((float)i+561/MAX_ITER, 0.7f, 0.7f);
				}
				//int r= (255*i)/MAX_ITER; int g=0; int b=0;
				img.setRGB(x, y, rgb);
				//img.setRGB(x, y, i | (i << 14));
//				int r = i / this.MAX_ITER * 180;
//				int g = 50; 
//				int b = i > 1 ? 40 : 100;
//				int color = (r << 16) | (g << 8) | b;
//				img.setRGB(x, y, color);
		    }
		}
	}
   

}
