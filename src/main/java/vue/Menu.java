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

public class Menu {
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private AnchorPane paneMenu;
	private AnchorPane paneChoixFractale = new AnchorPane();

	private HBox HBoxBouton;
	
	private Button generer_frct;
	private Button visualisation_frct;
	private Button julia;
	private Button mandelbrot;
	private Button retour = new Button ("Retour");

	private Vue vue;
	private Controleur controleur;
	private Scene scene;
	
	
	public Menu(Vue vue, Controleur controleur) {
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
		vue.getRoot().getChildren().add(paneMenu);
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
			vue.getRoot().getChildren().remove(paneMenu);
			try {
				choixFractale();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void choixFractale() throws IOException {
		paneChoixFractale.setPrefSize(tailleEcran.width, tailleEcran.height);
		paneChoixFractale.setStyle("-fx-background-color: #E8EAEA");
		Label titre = new Label("Choisissez une fractale a generer");
		titre.setFont(new Font("Arial", 20));
		titre.setLayoutX((tailleEcran.width*43)/100);
		titre.setLayoutY((tailleEcran.height*20)/100);
		boutonChoix();
		imageFractale();
		paneChoixFractale.getChildren().add(titre);
		vue.getRoot().getChildren().add(paneChoixFractale);
	}
	
	public void boutonChoix(){
		HBoxBouton = new HBox(400);
		double paneChoixFractale_x = paneChoixFractale.getPrefWidth();
		double paneChoixFractale_y = paneChoixFractale.getPrefHeight();
		julia = new Button("Julia");
		mandelbrot = new Button("Mandelbrot");
		HBoxBouton.getChildren().addAll(julia,mandelbrot);
		HBoxBouton.setLayoutX((paneChoixFractale_x*36)/100);
		HBoxBouton.setLayoutY((paneChoixFractale_y*65)/100);
		paneChoixFractale.getChildren().add(HBoxBouton);	
		boutonRetour();
		
		julia.setOnAction(actionEvent->{
			vue.getRoot().getChildren().remove(paneChoixFractale);
			
			//TODO: initialiser une fractale type Julia par le biais du constructeur genre Fractales fractale = FractaleBuilder.newFractaleBuilder()
			//.width(2001)
			//.height(2001)
			//pas le pas, initiliser une fois qu'on aura entrer la valeur
			//.MAX_ITER(1000)
			//.buildJulia(c1)
			//;
			vue.initialisation_pane_fractale();
			vue.initialisation_pane_parametres("Julia");
			
		});
		mandelbrot.setOnAction(actionEvent->{
			vue.getRoot().getChildren().remove(paneChoixFractale);
			//TODO: initialiser une fractale type Mandelbrot du constructeur
			vue.initialisation_pane_fractale();
			vue.initialisation_pane_parametres("Mandelbrot");
		});
	}
	
	public void imageFractale() throws IOException{
		Image imageJulia = new Image(new FileInputStream("julia.jpg"));
	    ImageView imageView1 = new ImageView();
	    imageView1.setImage(imageJulia);
	    imageView1.setX((tailleEcran.width*25)/100);
	    imageView1.setY((tailleEcran.width*15)/100);
	    imageView1.setFitWidth(400);
      	imageView1.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView1);
      	Image imageMandelbrot = new Image(new FileInputStream("mandelbrot.png"));
	    ImageView imageView2 = new ImageView();
	    imageView2.setImage(imageMandelbrot);
	    imageView2.setX((tailleEcran.width*52)/100);
	    imageView2.setY((tailleEcran.width*15)/100);
	    imageView2.setFitWidth(422);
      	imageView2.setPreserveRatio(true);
      	paneChoixFractale.getChildren().add(imageView2);
	}
	
	public void boutonRetour() {
		Pane p = new Pane();
		p.relocate((tailleEcran.width*50)/100, (tailleEcran.height*80)/100);
        p.getChildren().add(retour);
        paneChoixFractale.getChildren().add(p);
        
        retour.setOnAction(actionEvent->{
			vue.getRoot().getChildren().remove(paneChoixFractale);
			initialisation_pane_accueil();

		});
		
	}

}
