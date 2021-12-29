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
	public void iterStream(int x, int y, double gapX, double gapY) {
		int i = 0;
		double zX = ((4.0/width)*(x+translateX)*gapX)-2.0*gapX;
		double zY = ((4.0/height)*(y+translateY)*gapY)-2.0*gapY;
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
			rgb=Color.HSBtoRGB((float)(i+500)/MAX_ITER, 0.7f, 0.7f);
		}
		img.setRGB(x, y, rgb);
	}
	
	
}
