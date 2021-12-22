package modele;

public abstract class Fractales {
	protected final int width;
	protected final int height;
	protected double pas;
	protected int MAX_ITER;
	
	
	
	public Fractales(int width, int height, double pas, int MAX_ITER) {
		this.width = width;
		this.height = height;
		this.pas = pas;
		this.MAX_ITER = MAX_ITER;
	}//BUILDER
	
	
//	int divergenceIndex(Complexe z0) {
//	int ite = 0; Complex zn = z0;
//	// sortie de boucle si divergence
//	while (ite < MAX_ITER-1 && |zn| <= RADIUS)
//	zn = f(zn); ite++;
//	return ite;
//	}

}
