package modele;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class Julia extends Fractales {
	private final Complexe c;

	public Julia(FractaleBuilder fb, Complexe c) {
		super(fb);
		this.c = c;
		f = (z,cst) -> z.multiply(z).add(cst);
	}
	
	
	@Override
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) {
		int i = 0;
		double zX = (((borneSupX - borneInfX)/width)*(x+translateX)*gapX) + borneInfX*gapX;
		double zY = (((borneSupY - borneInfY)/height)*(y+translateY)*gapY) + borneInfY*gapY;
		Complexe z = Complexe.newComplexe(zX, zY);
		while (z.module() <= 2 && i < MAX_ITER) {
			z = f.apply(z,c);
			i++;
		}
		int rgb;
		if (i == MAX_ITER) {
			rgb = 0;
		}
		else {
			rgb=Color.HSBtoRGB((float) (i+couleur)/MAX_ITER, 0.7f, 0.7f);
		}
		img.setRGB(x, y, rgb);
	}
	
	//Retourne la partie reelle de la constante
	public double getReelC() {
		return this.c.getReel();
	}
	
	//Retourne la partie imaginaire de la constante
	public double getImaginaireC() {
		return this.c.getImaginaire();
	}

}
