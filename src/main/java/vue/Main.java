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
//		double[] tab = {-1,1,-1,1};
		//Complexe c1 = Complexe.newComplexe(-0.7269, 0.1889);
		/*Fractales fractale = FractaleBuilder.newFractaleBuilder()
				.width(2001)
				.height(2001)
				.pas(0.0001)
				.MAX_ITER(1000)
				.buildJulia(c1)
				;*/
		//Fractales fractale =  new Fractales();
		Controleur controleur = new Controleur();
		vue = new Vue(controleur);
		controleur.setVue(vue);
	}
	
	public static void main(String[] args) {
		launch(args);
		Complexe c1 = Complexe.newComplexe(-0.7269, 0.1889);
		//Complexe c1 = Complexe.newComplexe(0.285,0.013);
		//Julia yulia = new Julia(3001, 2001, 0.001, 1000, c1);
		Julia yulia = FractaleBuilder.newFractaleBuilder()
				.width(4001)
				.height(4001)
				.pas(0.0001)
				.MAX_ITER(1000)
				.buildJulia(c1)
				;

		//yulia.createImg();
	}
	
	
	


}
