package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;


public class Julia extends Fractales {
	private Complexe c;
	private FonctionFractale f;
	//ForkJoinPool forkJoinPool = PoolUtil.forkJoinPool;
	//Builder
	
	public Julia(int width, int height, double pas, int MAX_ITER, Complexe c) {
		super(width, height, pas, MAX_ITER);
		this.c = c;
		f = (z,cst) -> z.multiply(z).add(c);
	}

	public void createImg() {
		var img=new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		
//		int r = 64; int g = 224; int b = 208; //turquoise
//		int col = (r << 16) | (g << 8) | b;
//		img.setRGB(30,40,col);
//		for (int y = -1; y < 1; y+=0.01) {
//			for (int x = -1; x < 1; x+=0.01) {
				iterPts(img,f);
//			}
//		}
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
	
	
	
	public void iterPts(BufferedImage img, FonctionFractale f) {
		double zX = 0;
		double zY = 0;
//		double cX = this.c.getReel();
//		double cY = this.c.getImaginaire();
		int w = img.getWidth();
		int h = img.getHeight();
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				zX = 1.5 * (x - 0.5*w) / (0.5 * w);
				zY = (y - 0.5*h) / (0.5 * h);
				//int h1 = h/2;
				//double h2 = 0.5*h;
				System.out.println("ITERATION x = " + x + " et y = " + y);
//				System.out.println("h/2 = " + h1);
//				System.out.println("0.5*h = " + h2);
//				System.out.println("zX = " + zX);
//				System.out.println("zY = " + zY + "\n");
				Complexe z = Complexe.newComplexe(zX, zY);
				int i = 0;
				while (z.module() <= 2 && i < MAX_ITER) {
					//z = f.apply(z,c);
					z = z.multiply(z).add(c);
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
