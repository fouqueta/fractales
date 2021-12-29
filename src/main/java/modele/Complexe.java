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
	
	
	public Complexe add(Complexe c2) {
		double partR = this.reel + c2.reel;
		double partI = this.imaginaire + c2.imaginaire;
		return newComplexe(partR, partI);
	}
	
	public Complexe multiply(Complexe c2) {
		double partR = this.reel*c2.reel - (this.imaginaire*c2.imaginaire);
		double partI = this.imaginaire*c2.reel + this.reel*c2.imaginaire;
		return newComplexe(partR, partI);
	}
	
	public double module() {
		return Math.sqrt(this.reel*this.reel + this.imaginaire*this.imaginaire);
	}
	
	public Complexe conjugate() {
		return newComplexe(this.reel, -this.imaginaire);
	}
	
	
	public double getReel () { return this.reel; }
	public double getImaginaire () { return this.imaginaire; }

}
