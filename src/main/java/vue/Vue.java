package vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import controleur.*;
import modele.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue {
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Stage stage;
	private Scene scene_frct;
	
	private AnchorPane root;
	private AnchorPane paneFractale;
	private AnchorPane paneParametres;
	private GridPane gridPaneParametres;
	
	private Button retourMenu;
	private Button choixFractale;
	private Button valider;
	private Button restaurer;
	private Button sauvegarder;
	
	private Label erreur;
	private TextField TFconstante;
	private TextField TFpas;
	
	private Controleur controleur;
	private Menu menu;
	private Fractales fractale;
	
	
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		menu = new Menu(this);
		
		stage = new Stage();
		stage.setTitle("Fractales");
		stage.setMaximized(true);
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
	

	void initialisation_pane_parametres(String s) {
		paneParametres = new AnchorPane();
		paneParametres.setPrefSize((tailleEcran.width*20)/100, tailleEcran.height);
	    AnchorPane.setRightAnchor(paneParametres, 0.0);
		paneParametres.setStyle("-fx-background-color:#D7E5E5");
		
		Label titre = new Label("Parametres");
		titre.setFont(new Font("Arial", 20));
		titre.setPadding(new Insets(100, 0, 0, 140));
		
		gridPaneParametres = new GridPane();
		gridPaneParametres.setPadding(new Insets(120, 15, 0, 15));
		gridPaneParametres.setHgap(5);
		gridPaneParametres.setVgap(25);
		
		
		Label type = new Label("Type de fractale:"); 
		type.setFont(new Font("Arial", 15));
	    gridPaneParametres.add(type, 0, 3);
	    Label typeF = new Label(s); //TODO: plutot faire if fractale instance de Julia
		typeF.setFont(new Font("Arial", 15));
	    gridPaneParametres.add(typeF, 1, 3);
		
		Label pas = new Label("Entrez un pas de discretisation:");
	    TFpas = new TextField();
	    gridPaneParametres.add(pas, 0, 6);
	    gridPaneParametres.add(TFpas, 1, 6);
	    
	  	if (s.equals("Julia")) {
	  		parametre_julia(gridPaneParametres);
	  	}else if (s.equals("Mandelbrot")) {
	  		parametre_mandelbrot();
	  	}
		
		Text taille = new Text();      
		taille.setText("Taille de ma matrice de pixels:"); 
	    gridPaneParametres.add(taille, 0, 7);
	    Text matriceTaille = new Text(); 
	    matriceTaille.setText(controleur.getMatriceSize());
	    gridPaneParametres.add(matriceTaille, 1, 7);
	    
	    Text nbIte = new Text();      
		nbIte.setText("Nombre d'iteration maximum:");  
	    gridPaneParametres.add(nbIte, 0, 8);
	    Text nbIteration = new Text(); 
	    nbIteration.setText(String.valueOf(controleur.getIte()));
	    gridPaneParametres.add(nbIteration, 1, 8);
	    
	    Text Fcomplexe = new Text(); //TODO: Afficher fonction complexe

	    
	    //TODO: choix de couleur 
	    
	    Label Ldescription = new Label();      
	    Ldescription.setText("Description de la fractale:");  
	    Ldescription.setLayoutX((paneParametres.getPrefWidth()*7)/100);
	    Ldescription.setLayoutY((paneParametres.getPrefHeight()*50)/100);
	    paneParametres.getChildren().add(Ldescription);
	    Text description = new Text(); //TODO: rajouter texte de description + gerer taille du texte au moment d'afficher
	    description.setText("Bonjour je suis kk");  
	    gridPaneParametres.add(description, 0, 9, 2, 1);
	    
	    paneParametres.getChildren().addAll(gridPaneParametres);
	    paneParametres.getChildren().addAll(titre);
	    
	    boutons();
		root.getChildren().add(paneParametres);
		
	}
	
	public void boutons() {
		double paneParametres_x = paneParametres.getPrefWidth();
		double paneParametres_y = paneParametres.getPrefHeight();
		HBox HBoxBouton1= new HBox(15);
		valider = new Button("Valider");
		restaurer = new Button("Restaurer");
		sauvegarder = new Button("Sauvegarder");
		HBoxBouton1.getChildren().addAll(valider,restaurer,sauvegarder);
		HBoxBouton1.setLayoutX((paneParametres_x*17)/100);
		HBoxBouton1.setLayoutY((paneParametres_y*75)/100);
		
		HBox HBoxBouton2= new HBox(15);
		retourMenu = new Button("Retour au menu");
		choixFractale = new Button("Choisir un autre type de fractale");
		HBoxBouton2.getChildren().addAll(choixFractale, retourMenu);
		HBoxBouton2.setLayoutX((paneParametres_x*10)/100);
		HBoxBouton2.setLayoutY((paneParametres_y*85)/100);
		paneParametres.getChildren().addAll(HBoxBouton1,HBoxBouton2);
		
		retourMenu.setOnAction(actionEvent->{
				root.getChildren().remove(paneParametres);
				menu.initialisation_pane_accueil();

		});
		choixFractale.setOnAction(actionEvent->{
			root.getChildren().remove(paneParametres);
			try {
				menu.choixFractale();
			} catch (IOException e) {
				System.out.println("Erreur lors d ouverture fichier:");
				e.printStackTrace();
				System.exit(1);
			}
		});
		valider.setOnAction(actionEvent->{//TODO: erreur quand c'est mandelbrot: si on entre des non chiffres pour la 1ere fois, nullpointerexception
			paneParametres.getChildren().remove(erreur);
			if (TFpas.getText().isEmpty() || TFconstante.getText().isEmpty()) { 
				erreur = new Label("Veuillez remplir tous les champs");
				erreur.setFont(new Font("Arial", 20));
				erreur.setLayoutX((paneParametres.getPrefWidth()*10)/100);
				erreur.setLayoutY((paneParametres.getPrefHeight()*80)/100);
				paneParametres.getChildren().add(erreur);
			}else if (!isDouble(TFpas.getText())) {
				erreur = new Label("Veuillez entrer un pas de type double");
				erreur.setFont(new Font("Arial", 20));
				erreur.setLayoutX((paneParametres.getPrefWidth()*10)/100);
				erreur.setLayoutY((paneParametres.getPrefHeight()*80)/100);
				paneParametres.getChildren().add(erreur);
			//TODO: autre else if la constante n'est pas un instanceof Complexe
			}else {
				//TODO: afficher la fractale + fractale.setPas(Double.parseDouble(TFpas.getText())) jsp quoi*/
			}
		});
		restaurer.setOnAction(actionEvent->{
			TFconstante.clear();
			TFpas.clear();
			//TODO: fractale.set(comme en haut)
		});
	}
	
	void parametre_julia(GridPane gridPaneParametres) {
		Text constanteC = new Text();      
		constanteC.setText("Entrez une constante c:"); 
	    gridPaneParametres.add(constanteC, 0, 5);
	    TFconstante = new TextField();
	    gridPaneParametres.add(TFconstante, 1, 5);
	}
	
	public void parametre_mandelbrot(){
		
	}

	//GETTER
	public AnchorPane getRoot() { return this.root; }
	
	//FONTIONS AUXILIAIRES
	public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
        	System.out.println("La valeur entree n'est pas de type double");
            return false;
        }
    }

	
}