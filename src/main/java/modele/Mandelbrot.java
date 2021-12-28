package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import modele.Fractales.FractaleBuilder;

public class Mandelbrot extends Fractales{

	private FonctionFractale f;
	private BufferedImage img;
	

	public Mandelbrot(FractaleBuilder fb) {
		super(fb);
		f = (z,cst) -> z.multiply(z).add(cst);
		img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
	}
	
	
	@Override
	public BufferedImage createImg() {		
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
	}

	
	public void stream(int startX, int endX, int startY, int endY) {
		double gapX = 1./zoom;
		double gapY = 1./zoom;
		IntStream.range(startX, endX)
        .parallel()
        .forEach(i -> IntStream.range(startY, endY)
                .parallel()
                .forEach(j -> iterStream(i,j, gapX, gapY)));
	}
	

	public void iterStream(int x, int y, double gapX, double gapY) {
		int i = 0;
		double zX = ((3.0/width)*(x+translateX)*gapX)-1.5*gapX;
		double zY = ((2.0/height)*(y+translateY)*gapY)-1.0*gapY;
		Complexe z = Complexe.newComplexe(zX, zY);
		Complexe c = z;
//		int x = (int) (z.getReel()/1.5*(0.5*this.width) + 0.5*this.width);
//		int y = (int) (z.getImaginaire()*(0.5*this.height) + 0.5*this.height);
		//System.out.println("ITERATION x = " + x + " et y = " + y);
		while (z.module() <= 2 && i < MAX_ITER-1) {
			z = f.apply(z,c);
			i++;
		}
		int rgb;
		if (i == MAX_ITER-1) {
			rgb = 0;
		}
		else {
			rgb=Color.HSBtoRGB((float)(i+500)/MAX_ITER, 0.7f, 0.7f);
		}
		img.setRGB(x, y, rgb);
	}
	
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

}
