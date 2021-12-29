package vue;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;

import controleur.Controleur;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import modele.Fractales;
import modele.Fractales.FractaleBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MenuPane {
	
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Scene menu_scene;
	private Scene choix_scene;
	private Scene sauvegardes_scene;
	
	private AnchorPane paneMenu;
	private AnchorPane paneChoixFractale;
	private AnchorPane paneChoixSauvegardes;

	private HBox HBoxBouton;
	
	private Button generer_frct;
	private Button visualisation_frct;
	private Button julia;
	private Button mandelbrot;
	private Button retour = new Button ("Retour");

	private Vue vue;
	private Controleur controleur;
	
	
	public MenuPane(Vue vue, Controleur controleur) {
		this.vue=vue;
		this.controleur=controleur;
	}
	
	public void initialisation_pane_accueil() {
		paneMenu = new AnchorPane();
		paneMenu.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneMenu.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("FRACTALES");
		titre.setFont(new Font("Arial", 50));
		titre.setLayoutX((tailleEcran.width*43)/100);
		titre.setLayoutY((tailleEcran.height*20)/100);
		paneMenu.getChildren().add(titre);
		boutons_menu();
		menu_scene = new Scene(paneMenu);
		vue.getStage().setScene(menu_scene);
	}
	
	public void boutons_menu() {
		HBoxBouton= new HBox(15);
		double paneMenu_x = paneMenu.getPrefWidth();
		double paneMenu_y = paneMenu.getPrefHeight();
		generer_frct = new Button("Generer une fractale");
		visualisation_frct = new Button("Visualiser nos fractales");
		HBoxBouton.getChildren().addAll(generer_frct,visualisation_frct);
		HBoxBouton.setLayoutX((paneMenu_x*43)/100);
		HBoxBouton.setLayoutY((paneMenu_y*50)/100);
		paneMenu.getChildren().add(HBoxBouton);
		
		generer_frct.setOnAction(actionEvent->{
			try {
				choixFractale();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		visualisation_frct.setOnAction(actionEvent->{
			try {
				fractale_sauvegarde();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void choixFractale() throws IOException {
		paneChoixFractale = new AnchorPane();
		paneChoixFractale.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneChoixFractale.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("Choisissez une fractale a generer");
		titre.setFont(new Font("Arial", 20));
		titre.setLayoutX(tailleEcran.width*0.43);
		titre.setLayoutY(tailleEcran.height*0.2);
		boutonChoix();
		imageFractale();
		paneChoixFractale.getChildren().add(titre);
		choix_scene = new Scene(paneChoixFractale);
		vue.getStage().setScene(choix_scene);
	}
	
	public void boutonChoix(){
		HBoxBouton = new HBox(400);
		double paneChoixFractale_x = paneChoixFractale.getPrefWidth();
		double paneChoixFractale_y = paneChoixFractale.getPrefHeight();
		julia = new Button("Julia");
		mandelbrot = new Button("Mandelbrot");
		HBoxBouton.getChildren().addAll(julia,mandelbrot);
		HBoxBouton.setLayoutX(paneChoixFractale_x*0.36);
		HBoxBouton.setLayoutY(paneChoixFractale_y*0.65);
		paneChoixFractale.getChildren().add(HBoxBouton);	
		paneChoixFractale.getChildren().add(boutonRetour());
		
		julia.setOnAction(actionEvent->{	
			vue.initialisation_HBox_principale("Julia");
			
		});
		mandelbrot.setOnAction(actionEvent->{
			//TODO: initialiser une fractale type Mandelbrot du constructeur
			vue.initialisation_pane_fractale();
			vue.initialisation_pane_parametres("Mandelbrot");
		});
	}
	
	public void imageFractale() throws IOException{
		Image imageJulia = new Image(new FileInputStream("src/main/ressources/julia.jpg"));
	    ImageView imageView1 = new ImageView();
	    imageView1.setImage(imageJulia);
	    imageView1.setX(tailleEcran.width*0.25);
	    imageView1.setY(tailleEcran.width*0.15);
	    imageView1.setFitWidth(400);
      	imageView1.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView1);
      	Image imageMandelbrot = new Image(new FileInputStream("src/main/ressources/mandelbrot.png"));
	    ImageView imageView2 = new ImageView();
	    imageView2.setImage(imageMandelbrot);
	    imageView2.setX(tailleEcran.width*0.52);
	    imageView2.setY(tailleEcran.width*0.15);
	    imageView2.setFitWidth(422);
      	imageView2.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView2);
	}
	
	public void fractale_sauvegarde() throws IOException{
		paneChoixSauvegardes = new AnchorPane();
		paneChoixSauvegardes.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneChoixSauvegardes.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("FRACTALES SAUVEGARDEES");
		titre.setFont(new Font("Arial", 20));
		titre.setLayoutX((tailleEcran.width*43)/100);
		titre.setLayoutY((tailleEcran.height*5)/100);
		paneChoixSauvegardes.getChildren().add(titre);
		sauvegardes_scene = new Scene(paneChoixSauvegardes);
		paneChoixSauvegardes.getChildren().add(boutonRetour());
		vue.getStage().setScene(sauvegardes_scene);
	}
	
	public Pane boutonRetour() {
		Pane p = new Pane();
		p.relocate(tailleEcran.width*0.5, tailleEcran.height*0.8);
        p.getChildren().add(retour);
        
        retour.setOnAction(actionEvent->{
        	initialisation_pane_accueil();
		});
        return p;
		
	}

}
