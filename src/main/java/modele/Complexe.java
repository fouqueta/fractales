package modele;

public class Complexe {
	private double reel;
	private double imaginaire;
	
	private Complexe (double reel, double imaginaire) {
		this.reel = reel;
		this.imaginaire = imaginaire;
	}
	
	//Fabrique statique : retourne un nouveau complexe
	public static Complexe newComplexe(double reel, double imaginaire) {
		return new Complexe(reel, imaginaire);
	}
	
	
	public static Complexe add(Complexe c1, Complexe c2) {
		double partR = c1.reel + c2.reel;
		double partI = c1.imaginaire + c2.imaginaire;
		return newComplexe(partR, partI);
	}
	
	public static Complexe multiply(Complexe c1, Complexe c2) {
		double partR = c1.reel*c2.reel - (c1.imaginaire*c2.imaginaire);
		double partI = c1.imaginaire*c2.reel + c1.reel*c2.imaginaire;
		return newComplexe(partR, partI);
	}
	
	
	public double getReel () { return this.reel; }
	public double getImaginaire () { return this.imaginaire; }

}
