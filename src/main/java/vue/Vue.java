package vue;

import java.awt.Dimension;
import java.awt.Toolkit;

import controleur.*;
import modele.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Vue {
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Stage stage;
	private Scene scene_frct;
	
	private AnchorPane root;
	private AnchorPane paneFractale;
	private AnchorPane paneParametres;
	
	private Controleur controleur;
	private Menu menu;
	private Fractales fractale;
	
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		menu = new Menu(this);
		
		stage = new Stage();
		stage.setTitle("Fractales");
		root = new AnchorPane();
		
		fractale = controleur.getFractale();
		initilisation_scene_frct();
		menu.initialisation_pane_accueil();
		stage.show();
	}
	
	void initilisation_scene_frct() {
		scene_frct = new Scene(root,900,600);
		stage.setScene(scene_frct);	
	}
	
	void initialisation_pane_fractale() {
		paneFractale = new AnchorPane();
		paneFractale.setPrefSize((tailleEcran.width*80)/100, tailleEcran.height);
		root.getChildren().add(paneFractale);
	}

	void initialisation_pane_parametres() {
		paneParametres = new AnchorPane();
		paneParametres.setPrefSize((tailleEcran.width*20)/100, tailleEcran.height);
	    AnchorPane.setRightAnchor(paneParametres, 0.0);
		paneParametres.setStyle("-fx-background-color:#D7E5E5");
		root.getChildren().add(paneParametres);
	}

	
	public AnchorPane getRoot() { return this.root; }

	
}