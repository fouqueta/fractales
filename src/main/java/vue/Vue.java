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
	private Button quitter;
	
	private Label erreur;
	private Label typeF;
	private TextField TFreel;
	private TextField TFimaginaire;
	private TextField TFpas;
	private TextField TFite;
	private TextField TFwidth;
	private TextField TFheight;
	
	private Controleur controleur;
	private Menu menu;
	private Fractales fractale;
	
	
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		this.menu = new Menu(this,controleur);
		
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
		paneParametres.setPrefSize((tailleEcran.width*23)/100, tailleEcran.height);
	    AnchorPane.setRightAnchor(paneParametres, 0.0);
		paneParametres.setStyle("-fx-background-color:#D7E5E5");
		
		Label titre = new Label("Parametres");
		titre.setFont(new Font("Arial", 20));
		titre.setPadding(new Insets(40, 0, 0, 140));
		
		gridPaneParametres = new GridPane();
		gridPaneParametres.setPadding(new Insets(60, 0, 0, 15));
		gridPaneParametres.setHgap(7);
		gridPaneParametres.setVgap(10);
		gridPaneParametres.setMaxWidth((tailleEcran.width*23)/100);
		
		Label type = new Label("Type de fractale:"); //type de fractale
		type.setFont(new Font("Arial", 13));
	    gridPaneParametres.add(type, 0, 3, 2, 1);
	    if (s.equals("Julia")) {
	    	typeF = new Label("Julia"); 
	    }else if (s.equals("Mandelbrot")) {
	    	typeF = new Label("Mandelbrot");
	    }
		typeF.setFont(new Font("Arial", 13));
	    gridPaneParametres.add(typeF, 2, 3, 2, 1);
	    
	    Label constanteC = new Label("constante c:"); //constante c
	    gridPaneParametres.add(constanteC, 0, 4, 2, 1);
	    TFreel = new TextField();
	    TFreel.setPromptText("reel");
	    TFreel.setMaxSize(100,100);
	    gridPaneParametres.add(TFreel, 2, 4);
	    TFimaginaire = new TextField();
	    TFimaginaire.setPromptText("imaginaire");
	    TFimaginaire.setMaxSize(100,100);
	    gridPaneParametres.add(TFimaginaire, 3, 4);
		
		Label pas = new Label("Pas de discretisation:"); //pas de discretisation
	    gridPaneParametres.add(pas, 0, 5, 2, 1);
		TFpas = new TextField();
	    gridPaneParametres.add(TFpas, 2, 5,2,1);
	    
		Label taille = new Label("Taille matrice de pixels:"); //taille matrice 
	    gridPaneParametres.add(taille, 0, 6, 2, 1);
	    TFwidth = new TextField();
	    TFwidth.setPromptText("largeur");
	    TFwidth.setMaxSize(100,100);
	    gridPaneParametres.add(TFwidth, 2, 6);
	    TFheight = new TextField();
	    TFheight.setPromptText("hauteur");
	    TFheight.setMaxSize(100,100);
	    gridPaneParametres.add(TFheight, 3, 6);
	    
	    Label nbIte = new Label("Nombre d'iteration maximal:"); //nombre iteration max 
	    //nbIte.setFont(new Font(10));
	    gridPaneParametres.add(nbIte, 0, 7, 3 ,1);
	    TFite = new TextField();
	    TFite.setMaxSize(100,100);
	    gridPaneParametres.add(TFite, 3, 7);
	   
	    //TODO: choix de couleur 
	    
	    Label Ldescription = new Label();      
	    Ldescription.setText("Description de la fractale:");  
	    Ldescription.setLayoutX((paneParametres.getPrefWidth()*7)/100);
	    Ldescription.setLayoutY((paneParametres.getPrefHeight()*32)/100);
	    paneParametres.getChildren().add(Ldescription);
	    Text description = new Text(); //TODO: rajouter texte de description + gerer taille du texte au moment d'afficher
	    description.setText("Bonjour je suis kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");  
	    description.setWrappingWidth(300);
	    gridPaneParametres.add(description, 0, 11, 4,1);
	    
	    paneParametres.getChildren().addAll(gridPaneParametres);
	    paneParametres.getChildren().addAll(titre);
	    
	    boutons(s);
		root.getChildren().add(paneParametres);
	}
	
	
	public void boutons(String s) {
		double paneParametres_x = paneParametres.getPrefWidth();
		double paneParametres_y = paneParametres.getPrefHeight();
		HBox HBoxBouton1= new HBox(15);
		valider = new Button("Valider");
		restaurer = new Button("Restaurer");
		sauvegarder = new Button("Sauvegarder");
		HBoxBouton1.getChildren().addAll(valider,restaurer,sauvegarder);
		HBoxBouton1.setLayoutX((paneParametres_x*20)/100);
		HBoxBouton1.setLayoutY((paneParametres_y*78)/100);
		
		HBox HBoxBouton2= new HBox(15);
		retourMenu = new Button("Retour au menu");
		choixFractale = new Button("Generer une autre fractale");
		quitter = new Button("Quitter");
		HBoxBouton2.getChildren().addAll(choixFractale, retourMenu, quitter);
		HBoxBouton2.setLayoutX((paneParametres_x*3)/100);
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
		valider.setOnAction(actionEvent->{
			paneParametres.getChildren().remove(erreur);
			if (TFpas.getText().isEmpty() || TFreel.getText().isEmpty() || TFimaginaire.getText().isEmpty() || TFite.getText().isEmpty()|| TFwidth.getText().isEmpty()|| TFheight.getText().isEmpty()) { 
				erreur = new Label("Veuillez remplir tous les champs");
				set_erreur();
			}else if (!(isDouble(TFreel.getText()) && isDouble(TFimaginaire.getText()))){
				erreur = new Label("Veuillez entrer un reel ou imaginaire de type double");
				set_erreur();	
			}else if (!isDouble(TFpas.getText())) {
				erreur = new Label("Veuillez entrer un pas de type double");
				set_erreur();
			}else if (!isInt(TFite.getText())){
				erreur = new Label("Veuillez entrer un iterateur de type int");
				set_erreur();
			}else if (!(isInt(TFwidth.getText()) && isInt(TFheight.getText()))){
				erreur = new Label("Veuillez entrer une largeur ou hauteur de type int");
				set_erreur();
			}else {
				if (s.equals("Julia")) {
					System.out.println("gne");
					FJulia();
				} else if (s.equals("Mandelbrot")) {
					FMandelbrot();
				}
			}
		});
		restaurer.setOnAction(actionEvent->{
			paneParametres.getChildren().remove(erreur);
			TFreel.clear();
			TFimaginaire.clear();
			TFpas.clear();
			TFite.clear();
			TFwidth.clear();
			TFheight.clear();
		});
		quitter.setOnAction(actionEvent->{
			stage.close();
		});
	}
	
	void FJulia() {
		double reel = Double.parseDouble(TFreel.getText());
		double imaginaire = Double.parseDouble(TFimaginaire.getText());
		double pas = Double.parseDouble(TFpas.getText());
		int iterateur = Integer.parseInt(TFite.getText());
		int largeur = Integer.parseInt(TFwidth.getText());
		int hauteur = Integer.parseInt(TFheight.getText());
		controleur.create_Julia(reel,imaginaire,pas,iterateur,largeur,hauteur);
	}
	
	public void FMandelbrot(){
		
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
	
	public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
        	System.out.println("La valeur entree n'est pas de type int");
            return false;
        }
    }
	
	public void set_erreur() {
		erreur.setFont(new Font("Arial", 15));
		erreur.setLayoutX((paneParametres.getPrefWidth()*10)/100);
		erreur.setLayoutY((paneParametres.getPrefHeight()*82)/100);
		paneParametres.getChildren().add(erreur);
	}

	
}