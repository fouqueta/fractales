package vue;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import controleur.Controleur;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import modele.Fractales;
import modele.Fractales.FractaleBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
	private GridPane gridFractales;

	private HBox HBoxBouton;
	
	private Button generer_frct;
	private Button visualisation_frct;
	private Button julia;
	private Button mandelbrot;
	private Button tricorn;
	private Button burningship;
	private Button retour = new Button ("Retour");
	private Button nbFractale;
	
	private Image imagePredef;
	private ImageView imageView;
	private Scanner scan;

	private Vue vue;
	private Controleur controleur;
	
	
	public MenuPane(Vue vue, Controleur controleur) {
		this.vue=vue;
		this.controleur=controleur;
	}
	
	//initialisation du pane de l'accueil
	public void initialisation_pane_accueil() {
		paneMenu = new AnchorPane();
		paneMenu.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneMenu.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("FRACTALES");
		titre.setFont(new Font("Arial", 50));
		titre.setLayoutX(tailleEcran.width*0.43);
		titre.setLayoutY(tailleEcran.height*0.2);
		paneMenu.getChildren().add(titre);
		boutons_menu();
		menu_scene = new Scene(paneMenu);
		vue.getStage().setScene(menu_scene);
	}
	
	//boutons du menu
	public void boutons_menu() {
		HBoxBouton= new HBox(15);
		double paneMenu_x = paneMenu.getPrefWidth();
		double paneMenu_y = paneMenu.getPrefHeight();
		generer_frct = new Button("Generer une fractale");
		visualisation_frct = new Button("Visualiser mes fractales");
		HBoxBouton.getChildren().addAll(generer_frct,visualisation_frct);
		HBoxBouton.setLayoutX(paneMenu_x*0.43);
		HBoxBouton.setLayoutY(paneMenu_y*0.5);
		paneMenu.getChildren().add(HBoxBouton);
		
		generer_frct.setOnAction(actionEvent->{
				choixFractale();
		});
		visualisation_frct.setOnAction(actionEvent->{
				fractale_sauvegarde();
		});
	}
	
	//pane pour choisir le type de fractale a generer
	public void choixFractale()  {
		paneChoixFractale = new AnchorPane();
		paneChoixFractale.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneChoixFractale.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("Choisissez une fractale a generer");
		titre.setFont(new Font("Arial", 20));
		titre.setLayoutX(tailleEcran.width*0.43);
		titre.setLayoutY(tailleEcran.height*0.2);
		try {
			imageFractale();
		} catch (IOException e) {
			System.out.println("Erreur lors d ouverture fichier:");
    		e.printStackTrace();
    		System.exit(1);
		}
		boutonChoix();
		paneChoixFractale.getChildren().add(titre);
		choix_scene = new Scene(paneChoixFractale);
		vue.getStage().setScene(choix_scene);
	}
	
	public void imageFractale() throws IOException{
		Image imageJulia = new Image(new FileInputStream("src/main/ressources/julia.jpg"));
	    ImageView imageView1 = new ImageView();
	    imageView1.setImage(imageJulia);
	    imageView1.setX(tailleEcran.width*0.02);
	    imageView1.setY(tailleEcran.height*0.30);
	    imageView1.setFitWidth(320);
      	imageView1.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView1);
      	Image imageMandelbrot = new Image(new FileInputStream("src/main/ressources/mandelbrot.png"));
	    ImageView imageView2 = new ImageView();
	    imageView2.setImage(imageMandelbrot);
	    imageView2.setX(tailleEcran.width*0.24);
	    imageView2.setY(tailleEcran.height*0.30);
	    imageView2.setFitWidth(338);
      	imageView2.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView2);
      	Image imageTricorn = new Image(new FileInputStream("src/main/ressources/tricorn.png"));
	    ImageView imageView3 = new ImageView();
	    imageView3.setImage(imageTricorn);
	    imageView3.setX(tailleEcran.width*0.47);
	    imageView3.setY(tailleEcran.height*0.30);
	    imageView3.setFitWidth(359);
	    imageView3.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView3);
      	Image imageBurningShip = new Image(new FileInputStream("src/main/ressources/burningship.png"));
	    ImageView imageView4 = new ImageView();
	    imageView4.setImage(imageBurningShip);
	    imageView4.setX(tailleEcran.width*0.71);
	    imageView4.setY(tailleEcran.height*0.30);
	    imageView4.setFitWidth(425);
	    imageView4.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView4);
	}
	
	public void boutonChoix(){
		HBoxBouton = new HBox(308);
		double paneChoixFractale_x = paneChoixFractale.getPrefWidth();
		double paneChoixFractale_y = paneChoixFractale.getPrefHeight();
		julia = new Button("Julia");
		mandelbrot = new Button("Mandelbrot");
		tricorn = new Button("Tricorn");
		burningship = new Button("BurningShip");
		HBoxBouton.getChildren().addAll(julia,mandelbrot,tricorn,burningship);
		HBoxBouton.setLayoutX(paneChoixFractale_x*0.10);
		HBoxBouton.setLayoutY(paneChoixFractale_y*0.65);
		paneChoixFractale.getChildren().add(HBoxBouton);	
		paneChoixFractale.getChildren().add(boutonRetour());
		
		julia.setOnAction(actionEvent->{	
			vue.initialisation_HBox_principale();
			vue.pane_parametres("Julia");
			
		});
		mandelbrot.setOnAction(actionEvent->{
			vue.initialisation_HBox_principale();
			vue.pane_parametres("Mandelbrot");
		});
		tricorn.setOnAction(actionEvent->{
			vue.initialisation_HBox_principale();
			vue.pane_parametres("Tricorn");
		});
		burningship.setOnAction(actionEvent->{
			vue.initialisation_HBox_principale();
			vue.pane_parametres("BurningShip");
		});
	}	
	
	//pane pour visualiser les fractales deja generes
	public void fractale_sauvegarde(){
		paneChoixSauvegardes = new AnchorPane();
		paneChoixSauvegardes.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneChoixSauvegardes.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("FRACTALES SAUVEGARDEES");
		titre.setFont(new Font("Arial", 20));
		titre.setLayoutX(tailleEcran.width*0.42);
		titre.setLayoutY(tailleEcran.height*0.05);
		paneChoixSauvegardes.getChildren().add(titre);
		sauvegardes_scene = new Scene(paneChoixSauvegardes);
		paneChoixSauvegardes.getChildren().add(boutonRetour());
		try {
			affichage_sauvegarde();
		} catch (FileNotFoundException e) {
			System.out.println("Erreur lors d ouverture fichier:");
    		e.printStackTrace();
    		System.exit(1);
		}
		vue.getStage().setScene(sauvegardes_scene);
	}
	
	//affichage des fractales et des boutons permettant de les generer
	public void affichage_sauvegarde() throws FileNotFoundException {
		gridFractales = new GridPane();
		gridFractales.setHgap(20);
		gridFractales.setVgap(60);
		gridFractales.setMaxWidth(tailleEcran.width);
		gridFractales.setMaxHeight(tailleEcran.height);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setContent(gridFractales);
		scrollPane.setPrefSize(tailleEcran.width*0.9, tailleEcran.height*0.7);
		scrollPane.setLayoutX(tailleEcran.width*0.05);
		scrollPane.setLayoutY(tailleEcran.height*0.12);
		scrollPane.setStyle("-fx-background-color: white");
		
		paneChoixSauvegardes.getChildren().add(scrollPane);

		File path = new File("src/main/sauvegardes");
		File [] files = path.listFiles();
		int x=1;
		int y=2;
		for (int i = 0; i < files.length; i++){
	        if (files[i].isFile()){
	        	int k=files[i].getName().lastIndexOf('.');
	        	if (k>0) {
	        		if((files[i].getName().substring(k+1)).equals("png")) {
			            imagePredef = new Image(new FileInputStream(files[i]));
					    imageView = new ImageView();
					    imageView.setImage(imagePredef);
					    imageView.setX(tailleEcran.width*0.52);
					    imageView.setY(tailleEcran.width*0.15);
					    imageView.setFitWidth(300);
				      	imageView.setPreserveRatio(true);
				      	gridFractales.add(imageView, x,y);
				      	
				      	nbFractale = new Button(files[i].getName());
				      	gridFractales.add(nbFractale, x,y+1);  
	        		}
				      if((files[i].getName().substring(k+1)).equals("txt")) { 
				    	  String s = files[i].getName();
				      	nbFractale.setOnAction(actionEvent->{
				      		vue.initialisation_HBox_principale();
				      		vue.FInstanceTxt(s);
				      		});
				      	x++;	
				      }
	        	}
	        }
		}
	}

	public Pane boutonRetour() {
		Pane p = new Pane();
		p.relocate(tailleEcran.width*0.5, tailleEcran.height*0.85);
        p.getChildren().add(retour);
        
        retour.setOnAction(actionEvent->{
        	initialisation_pane_accueil();
		});
        return p;
	}
}
