package modele;

import java.awt.Color;


public class Tricorn extends Fractales {
	
	public Tricorn(FractaleBuilder fb) {
		super(fb);
		f = ((z,cst) -> { 
			Complexe zConj = z.conjugate();
			return zConj.multiply(zConj).add(cst);
		});
	}
	

	@Override
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) {
		int i = 0;
		double zX = (((borneSupX - borneInfX)/width)*(x+translateX)*gapX) + borneInfX*gapX;
		double zY = (((borneSupY - borneInfY)/height)*(y+translateY)*gapY) + borneInfY*gapY;
		Complexe z = Complexe.newComplexe(zX, zY);
		Complexe c = z;
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
	
	
}
