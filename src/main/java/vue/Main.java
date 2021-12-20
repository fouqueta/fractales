package vue;
import modele.*;
import controleur.*;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
	
	private Vue vue;
	
	@Override
	public void start(Stage primaryStage) {
		double[] tab = {-1,1,-1,1};
		Fractales fractale = new Fractales(tab, 0.2,2.5,10,5);
		Controleur controleur = new Controleur(fractale);
		vue = new Vue(controleur);
		controleur.setVue(vue);
	}
	
	public static void main(String[] args) {
		launch(args);
		Complexe c1 = Complexe.newComplexe(-0.7269, 0.1889);
//		Julia yulia = new Julia();
//		yulia.createImg();
	}
	
	
	


}
