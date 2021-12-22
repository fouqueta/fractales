package modele;

public abstract class Fractales {
	protected final int width;
	protected final int height;
	protected double pas;
	protected int MAX_ITER;
	
	
	
	/*public Fractales(int width, int height, double pas, int MAX_ITER) {
		this.width = width;
		this.height = height;
		this.pas = pas;
		this.MAX_ITER = MAX_ITER;
	}*///BUILDER
	
	public static class FractaleBuilder{
		private int width;
		private int height;
		private double pas;
		private int MAX_ITER;
		
		private FractaleBuilder() {}
		
		public static FractaleBuilder newFractaleBuilder() {
			return new FractaleBuilder();
		}
		
		public FractaleBuilder width(int largeur) {
			this.width=largeur;
			return this;
		}
		
		public FractaleBuilder height(int hauteur) {
			this.height=hauteur;
			return this;
		}
		public FractaleBuilder pas(double pasDiscretisation) {
			this.pas=pasDiscretisation;
			return this;
		}
		
		public FractaleBuilder MAX_ITER(int maxIter) {
			this.MAX_ITER=maxIter;
			return this;
		}
		
		/*public Fractales build() {
			return new Fractales(this);
		}*/
		public Julia buildJulia(Complexe c) {
			return new Julia(this,c);
		}
		public Mandelbrot buildMandelbrot(Complexe c) {
			return new Mandelbrot(this,c);
		}
		
	}
	
	protected Fractales (FractaleBuilder f) {
		this.width=f.width;
		this.height=f.height;
		this.pas=f.pas;
		this.MAX_ITER=f.MAX_ITER;
	}

	
	
//	int divergenceIndex(Complexe z0) {
//	int ite = 0; Complex zn = z0;
//	// sortie de boucle si divergence
//	while (ite < MAX_ITER-1 && |zn| <= RADIUS)
//	zn = f(zn); ite++;
//	return ite;
//	}

}
