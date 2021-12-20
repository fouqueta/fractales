package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Julia extends Fractales{
	private Complexe c;
	//Builder
	
	public Julia(double[] rectangle, double pas, double taille, int MAX_ITER, int RADIUS, Complexe c) {
		super(rectangle, pas, taille, MAX_ITER, RADIUS);
		this.c = c;
	}

	public void createImg() {
		var img=new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		
		int r = 64; int g = 224; int b = 208; //turquoise
		int col = (r << 16) | (g << 8) | b;
		img.setRGB(30,40,col);
		
		File f = new File("MyFile.png");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
