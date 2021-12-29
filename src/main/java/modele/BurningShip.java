package modele;

import java.awt.Color;

public class BurningShip extends Fractales {
	
	public BurningShip(FractaleBuilder fb) {
		super(fb);
		//f = (z,cst) -> z.multiply(z).add(cst);
		f = ((z,cst) -> { 
				Complexe zAbs = Complexe.newComplexe(Math.abs(z.getReel()), Math.abs(z.getImaginaire()));
				return zAbs.multiply(zAbs).add(cst);
		});
	}

	@Override
	public void iterStream(int x, int y, double gapX, double gapY, int couleur) {
		int i = 0;
		double zX = ((4.0/width)*(x+translateX)*gapX)-3.0*gapX;
		double zY = ((2.0/height)*(y+translateY)*gapY)-1.5*gapY;
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
