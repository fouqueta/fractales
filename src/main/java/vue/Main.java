package vue;
import modele.*;
import modele.Fractales.FractaleBuilder;
import controleur.*;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
	
	private Vue vue;
	
	@Override
	public void start(Stage primaryStage) {

		Controleur controleur = new Controleur();

		vue = new Vue(controleur);
		controleur.setVue(vue);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
