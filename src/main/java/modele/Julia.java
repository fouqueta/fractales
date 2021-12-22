package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Julia extends Fractales{
	private Complexe c;
	//Builder
	
	public Julia(FractaleBuilder f, Complexe c) {
		//super(weight, height, pas, MAX_ITER);
		super (f);
		this.c = c;
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
		var img=new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		
		int r = 64; int g = 224; int b = 208; //turquoise
		int col = (r << 16) | (g << 8) | b;
		img.setRGB(30,40,col);
		
		File f = new File("MyFile");
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
