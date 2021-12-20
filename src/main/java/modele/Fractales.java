package modele;

public class Fractales {
	private double[] rectangle;
	private double pas;
	private double taille;
	private int MAX_ITER;
	private int RADIUS;
	
	
	public Fractales(double[] rectangle, double pas, double taille, int MAX_ITER, int RADIUS) {
		this.rectangle = rectangle;
		this.pas = pas;
		this.taille = taille;
		this.MAX_ITER = MAX_ITER;
		this.RADIUS = RADIUS;
	}//BUILDER

}
