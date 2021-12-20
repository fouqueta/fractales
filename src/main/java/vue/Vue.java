package vue;

import controleur.*;
import modele.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Vue {
	private AnchorPane root;
	private Stage stage;
	private Fractales fractale;
	private Scene scene_frct;
	private Controleur controleur;
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		
		stage = new Stage();
		stage.setTitle("Fractales");
		//stage.setMaximized(true);
		root = new AnchorPane();
		
		fractale = controleur.getFractale();
		initilisation_scene_frct();
		stage.show();
	}
	
	void initilisation_scene_frct() {
		scene_frct = new Scene(root,900,600);
		stage.setScene(scene_frct);	
	}

}