package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

//3/(width*x)-1.5
//3=borne à droite - ma borne à gauche || DIVISER par var = width - 0 = width || ca c'est mon a || fois x || donc on obtient f(x) = ax || mtnt il faut b, donc on fait f(x) - ax = b || ici b = -1.5 si on prend f(x) =  -1.5 et donc x = 0
//(yb-ya)/(xb-xa) = a

public class Julia extends Fractales {
	private final Complexe c;
	private FonctionFractale f;
	//ForkJoinPool forkJoinPool = PoolUtil.forkJoinPool;
	//Builder
	
	public Julia(FractaleBuilder fb, Complexe c) {
		super(fb);
		this.c = c;
		f = (z,cst) -> z.multiply(z).add(c);
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


	public void createImg() {
		var img=new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		
//		int r = 64; int g = 224; int b = 208; //turquoise
//		int col = (r << 16) | (g << 8) | b;
//		img.setRGB(30,40,col);
//		for (int y = -1; y < 1; y+=0.01) {
//			for (int x = -1; x < 1; x+=0.01) {
				//iterPts(img,f);
//			}
//		}
		long start=System.currentTimeMillis();
		iterPts(img,f);
	    long end=System.currentTimeMillis();
	    System.out.println("NOT thread took time : "+(end-start));
	    
//	   long  start=System.currentTimeMillis();
//		try {
//			thread(img,f);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	    long end=System.currentTimeMillis();
//	    System.out.println("Thread took time : "+(end-start));
	
		start=System.currentTimeMillis();
		stream(img,f);
		end=System.currentTimeMillis();
	    System.out.println("Parallel stream took time : "+(end-start));
    
	    File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 

//	int divergenceIndex(Complexe z0) {
//	int ite = 0; Complexe zn = z0;
//	// sortie de boucle si divergence
//	while (ite < MAX_ITER-1 && zn.module() <= 2)
//		//zn = f.apply(zn); ite++;
//	return ite;
//	}
	
	public void thread(BufferedImage img, FonctionFractale f) throws InterruptedException {
		Julia j = this;
		Thread t1 = new Thread(() -> j.iterThread(0,width/2,0,height/2, img, f));
		Thread t2 = new Thread(() -> j.iterThread(0,width/2,height/2,height, img, f));
		Thread t3 = new Thread(() -> j.iterThread(width/2,width,0,height/2, img, f));
		Thread t4 = new Thread(() -> j.iterThread(width/2,width,height/2,height, img, f));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
	
	
	public void iterThread(int xI, int xF, int yI, int yF, BufferedImage img, FonctionFractale f) {
		double zX = 0;
		double zY = 0;
		for (int x = xI; x < xF; x++) {
			for (int y = yI; y < yF; y++) {
//				zX = 1.5 * (x - 0.5*width) / (0.5 * width);
//				zY = (y - 0.5*height) / (0.5 * height);
				zX = (3.0/width)*x-1.5;
				zY = (2.0/height)*y-1.0;
				Complexe z = Complexe.newComplexe(zX, zY);
				int i = 0;
				while (z.module() <= 2 && i < MAX_ITER) {
					z = f.apply(z,c);
					i++;
				}
				int rgb;
				if (i == MAX_ITER) {
					rgb = 0;
				}
				else {
					rgb=Color.HSBtoRGB((float)i/MAX_ITER, 0.7f, 0.7f);
				}
				img.setRGB(x, y, rgb);
			}
		}
	}
	
	public void stream(BufferedImage img, FonctionFractale f) {
//		double zX = 0;
//		double zY = 0;
//		for (int x = 0; x < this.width; x++) {
//			for (int y = 0; y < this.height; y++) {
//				zX = (3.0/width)*x-1.5;
//				zY = (2.0/height)*y-1.0;
////				zX = 1.5 * (x - 0.5*this.width) / (0.5 * this.width);
////				zY = (y - 0.5*this.height) / (0.5 * this.height);
//				Complexe point = Complexe.newComplexe(zX, zY);
//				listPts.add(point);
//			}
//		}
//		listPts.parallelStream().forEach(point -> iter(point,img,f));
		IntStream.range(0, width)
        .parallel()
        .forEach(i -> IntStream.range(0,height)
                .parallel()
                .forEach(j -> iter(i,j,img, f)))
        ;
	}
	
	public void iter(int x, int y, BufferedImage img, FonctionFractale f) {
		int i = 0;
//		double zX = 1.5 * (x - 0.5*this.width) / (0.5 * this.width);
//		double zY = (y - 0.5*this.height) / (0.5 * this.height);
		double zX = (2.0/width)*x-1.0;
		double zY = (2.0/height)*y-1.0;
		Complexe z = Complexe.newComplexe(zX, zY);
//		int x = (int) (z.getReel()/1.5*(0.5*this.width) + 0.5*this.width);
//		int y = (int) (z.getImaginaire()*(0.5*this.height) + 0.5*this.height);
		//System.out.println("ITERATION x = " + x + " et y = " + y);
		while (z.module() <= 2 && i < MAX_ITER) {
			z = f.apply(z,c);
			i++;
		}
		int rgb;
		if (i == MAX_ITER) {
			rgb = 0;
		}
		else {
			rgb=Color.HSBtoRGB((float)(i+500)/MAX_ITER, 0.7f, 0.7f);
		}
		img.setRGB(x, y, rgb);
	}
	
	
	public void iterPts(BufferedImage img, FonctionFractale f) {
		double zX = 0;
		double zY = 0;
//		double cX = this.c.getReel();
//		double cY = this.c.getImaginaire();
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
					rgb=Color.HSBtoRGB((float)i/MAX_ITER, 0.7f, 0.7f);
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
