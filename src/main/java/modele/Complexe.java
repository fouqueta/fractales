package modele;

public class Complexe {
	private int reel;
	private int imaginaire;
	
	public Complexe (int reel, int imaginaire) {
		this.reel=reel;
		this.imaginaire=imaginaire;
	}
	
	public int getReel (int reel) { return this.reel; }
	public int getImaginaire (int imaginaire) { return this.imaginaire; }
	
	//retourne un nouveau complexe
	public Complexe initComplexe(int reel, int imaginaire) {
		return new Complexe(reel, imaginaire);
	}

}
