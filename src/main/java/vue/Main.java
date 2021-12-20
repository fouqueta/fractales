package vue;

import javafx.application.Application;
import javafx.stage.Stage;

import modele.*;
import controleur.*;

public class Main extends Application{
	
	private Vue vue;
	
	@Override
	public void start(Stage primaryStage) {
		
		Fractales fractale = new Fractales();
		Controleur controleur = new Controleur(fractale);
		vue = new Vue(controleur);
		controleur.setVue(vue);
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
