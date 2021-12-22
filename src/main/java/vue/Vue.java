package vue;

import java.awt.Dimension;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import java.awt.Toolkit;

import controleur.*;
import modele.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Vue {
	
	private AnchorPane root;
	private Stage stage;
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private AnchorPane fond;
	private AnchorPane paneFractale;
	private AnchorPane paneParametres;
	private Scene scene_frct;
	
	private Controleur controleur;
	private Fractales fractale;
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		
		stage = new Stage();
		stage.setTitle("Fractales");
		//stage.setMaximized(true);
		root = new AnchorPane();
		
		fractale = controleur.getFractale();
		initilisation_scene_frct();
		
		initialisation_pane_parametres();
		initialisation_pane_fractale();
		stage.show();
	}
	
	void initilisation_scene_frct() {
		scene_frct = new Scene(root,900,600);
		stage.setScene(scene_frct);	
	}
	
	void initialisation_pane_fractale() {
		paneFractale = new AnchorPane();
		paneFractale.setPrefSize((tailleEcran.width*80)/100, tailleEcran.height);
		paneFractale.setStyle("-fx-background-color: #CD6155");
		root.getChildren().add(paneFractale);
	}

	void initialisation_pane_parametres() {
		paneParametres = new AnchorPane();
		paneParametres.setPrefSize((tailleEcran.width*20)/100, tailleEcran.height);

		paneParametres.setStyle("-fx-background-color:#979A9A");
		root.getChildren().add(paneParametres);
	}
	


	
}