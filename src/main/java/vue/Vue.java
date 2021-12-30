package vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.awt.image.BufferedImage;

import controleur.*;
import modele.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Vue {
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Stage stage;
	private Scene scene_fenetre;
	
	private HBox HBox_fenetre;
	private AnchorPane paneFenetre;
	private AnchorPane paneParametres;
	private GridPane gridPaneParametres;
	private AnchorPane fractale_pane;

	
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
	private TextField TFinfX;
	private TextField TFsupX;
	private TextField TFinfY;
	private TextField TFsupY;
	private TextField TFfichier;
	
	
	private Scanner scan;
	private Slider sliderColor;
	private ImageView i;
	
	private String type;
	private double borneInfX;
	private double borneSupX;
	private double borneInfY;
	private double borneSupY;
	private double pas;
	private int MAX_ITER;
	private int couleur;
	private String name;
	private double zoom;
	private int translateX;
	private int translateY;
	private double reel;
	private double imaginaire;
	
	private Controleur controleur;
	private MenuPane menu;
	private Fractales fractale;
	private Scene fractale_scene;
	
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		this.menu = new MenuPane(this,controleur);
		
		
		stage = new Stage();
		stage.setTitle("Fractales");
		stage.setMaximized(true);

		paneFenetre = new AnchorPane();
		scene_fenetre = new Scene(paneFenetre);
		stage.setScene(scene_fenetre);
		menu.initialisation_pane_accueil();
		stage.show();
	}
	
	
	public void initialisation_HBox_principale() {
		HBox_fenetre = new HBox();
		initialisation_pane_fractale();
		initialisation_pane_parametres();
		HBox_fenetre.getChildren().addAll(fractale_pane, paneParametres);
		fractale_scene = new Scene(HBox_fenetre);
		stage.setScene(fractale_scene);
	}
	
	public void initialisation_pane_fractale() {
		fractale_pane = new AnchorPane();
		fractale_pane.setPrefSize(tailleEcran.width*0.77, tailleEcran.height);
		fractale_pane.setStyle("-fx-background-color:#618686");
	}

	public void initialisation_pane_parametres() {
		paneParametres = new AnchorPane();
		paneParametres.setPrefSize(tailleEcran.width*0.23, tailleEcran.height);
	    AnchorPane.setRightAnchor(paneParametres, 0.0);
		paneParametres.setStyle("-fx-background-color:#D7E5E5");
	}
	
		void pane_parametres(String s) {
		Label titre = new Label("Parametres");
		titre.setFont(new Font("Arial", 20));
		titre.setPadding(new Insets(40, 0, 0, 140));
		
		gridPaneParametres = new GridPane();
		gridPaneParametres.setPadding(new Insets(60, 0, 0, 15));
		gridPaneParametres.setHgap(7);
		gridPaneParametres.setVgap(10);
		gridPaneParametres.setMaxWidth(tailleEcran.width*0.23);
		
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
	    
	    Label nbIte = new Label("Nombre d'iteration maximal:"); //nombre iteration max 
	    gridPaneParametres.add(nbIte, 0, 6, 3 ,1);
	    TFite = new TextField();
	    TFite.setMaxSize(100,100);
	    gridPaneParametres.add(TFite, 3, 6);
	    
	    
	    Label Lborne = new Label("Bornes du plan complexe"); 
	    Lborne.setFont(new Font("Arial", 13));
	    Lborne.setLayoutX(paneParametres.getPrefWidth()*0.07);
	    Lborne.setLayoutY(paneParametres.getPrefHeight()*0.40);
	    paneParametres.getChildren().add(Lborne);
	    
	    Label abscisse = new Label("abscisses:"); //taille matrice 
	    gridPaneParametres.add(abscisse, 0, 22, 2, 1);
	    TFinfX = new TextField();
	    TFinfX.setPromptText("borne inf");
	    TFinfX.setMaxSize(100,100);
	    gridPaneParametres.add(TFinfX, 2, 22);
	    TFsupX = new TextField();
	    TFsupX.setPromptText("borne sup");
	    TFsupX.setMaxSize(100,100);
	    gridPaneParametres.add(TFsupX, 3, 22);
	    
	    Label ordonnee = new Label("ordonnees:"); //taille matrice 
	    gridPaneParametres.add(ordonnee, 0, 23, 2, 1);
	    TFinfY = new TextField();
	    TFinfY.setPromptText("borne inf");
	    TFinfY.setMaxSize(100,100);
	    gridPaneParametres.add(TFinfY, 2, 23);
	    TFsupY = new TextField();
	    TFsupY.setPromptText("borne sup");
	    TFsupY.setMaxSize(100,100);
	    gridPaneParametres.add(TFsupY, 3, 23);
	    
	    Label fichier = new Label("Nom du fichier:"); //Nom du fichier
	    gridPaneParametres.add(fichier, 0, 25, 2, 1);
		TFfichier = new TextField();
	    gridPaneParametres.add(TFfichier, 2, 25, 2,1);
	    Label nom = new Label("Si un fichier deja existant comporte le meme nom, \nil sera ecrase");
	    gridPaneParametres.add(nom, 0, 26, 4,1);
	    
	    paneParametres.getChildren().addAll(gridPaneParametres);
	    paneParametres.getChildren().addAll(titre);
	    
	    boutons(s);
	    buttons_zoom();
	    buttons_translate();
	    initSliderColor();
		//root.getChildren().add(paneParametres);
	}
	
	
	public void boutons(String s) {
		double paneParametres_x = paneParametres.getPrefWidth();
		double paneParametres_y = paneParametres.getPrefHeight();
		HBox HBoxBouton1= new HBox(15);
		valider = new Button("Valider");
		restaurer = new Button("Restaurer");
		sauvegarder = new Button("Sauvegarder");
		HBoxBouton1.getChildren().addAll(valider,restaurer,sauvegarder);
		HBoxBouton1.setLayoutX(paneParametres_x*0.2);
		HBoxBouton1.setLayoutY(paneParametres_y*0.78);
		
		HBox HBoxBouton2= new HBox(15);
		retourMenu = new Button("Retour au menu");
		choixFractale = new Button("Generer une autre fractale");
		quitter = new Button("Quitter");
		HBoxBouton2.getChildren().addAll(choixFractale, retourMenu, quitter);
		HBoxBouton2.setLayoutX(paneParametres_x*0.03);
		HBoxBouton2.setLayoutY(paneParametres_y*0.85);
		paneParametres.getChildren().addAll(HBoxBouton1,HBoxBouton2);
		
		retourMenu.setOnAction(actionEvent->{
			//	root.getChildren().remove(paneParametres);
				menu.initialisation_pane_accueil();
		});
		choixFractale.setOnAction(actionEvent->{
			//root.getChildren().remove(paneParametres);
			try {
				menu.choixFractale();
			} catch (IOException e) {
				System.out.println("Erreur lors d ouverture fichier:");
				e.printStackTrace();
				System.exit(1);
			}
		});
		sauvegarder.setOnAction(actionEvent->{
			controleur.saveFractale();
		});
		valider.setOnAction(actionEvent->{
			paneParametres.getChildren().remove(erreur);
			fractale_pane.getChildren().remove(i);
			//restoreSliderColor();
			if (TFpas.getText().isEmpty() || TFreel.getText().isEmpty() || TFimaginaire.getText().isEmpty() || TFite.getText().isEmpty() || 
					TFinfX.getText().isEmpty() || TFsupX.getText().isEmpty() || TFinfY.getText().isEmpty() || TFinfY.getText().isEmpty() || TFfichier.getText().isEmpty()) { 
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
			}else if (!(isDouble(TFinfX.getText()) && isDouble(TFsupX.getText()) && isDouble(TFinfY.getText()) && isDouble(TFsupY.getText()))){
				erreur = new Label("Veuillez entrer une largeur ou hauteur de type double");
				set_erreur();
			}else if ((Double.parseDouble(TFsupX.getText())>1.43)){
				erreur = new Label("Veuillez entrer une borne sup de X < 1.44");
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
			TFinfX.clear();
			TFsupX.clear();
			TFinfY.clear();
			TFsupY.clear();
		});
		quitter.setOnAction(actionEvent->{
			stage.close();
		});
	}
	
	
	public int initSliderColor() {
		Label labelColor = new Label("Choisir la couleur");
	    sliderColor = new Slider();
	    Label infoColor = new Label("-");
	  
	    sliderColor.setMin(0);
	    sliderColor.setMax(1000);
	    sliderColor.setValue(0);
	    sliderColor.setShowTickLabels(true);
	    sliderColor.setShowTickMarks(true);
	    sliderColor.setMajorTickUnit(150);
	    sliderColor.setBlockIncrement(150);
	    sliderColor.setSnapToTicks(true);
	    sliderColor.setPrefWidth(paneParametres.getPrefWidth()*0.9);
	    
		VBox VBoxCouleurs = new VBox();
		VBoxCouleurs.setPadding(new Insets(20));
	    VBoxCouleurs.setSpacing(10);
	    VBoxCouleurs.getChildren().addAll(labelColor, sliderColor, infoColor);
	    VBoxCouleurs.setLayoutX(paneParametres.getPrefWidth()*0.01);
	    VBoxCouleurs.setLayoutY(paneParametres.getPrefHeight()*0.25);
	    paneParametres.getChildren().add(VBoxCouleurs);
	    
	    sliderColor.valueProperty().addListener((obs, oldval, newVal) -> {
	    	sliderColor.setValue(newVal.intValue());
	    	infoColor.setText("Valeur : " + newVal.intValue());
	    	couleur = newVal.intValue();
	    });
	    return couleur;
	}
	
//	void FJulia() {
//		double reel = Double.parseDouble(TFreel.getText());
//		double imaginaire = Double.parseDouble(TFimaginaire.getText());
//		double pas = Double.parseDouble(TFpas.getText());
//		int iterateur = Integer.parseInt(TFite.getText());
//		int largeur = Integer.parseInt(TFwidth.getText());
//		int hauteur = Integer.parseInt(TFheight.getText());
//		generateFractale(controleur.generateJulia(reel,imaginaire,pas,iterateur,largeur,hauteur,couleur));
//	}
	
	
	public void FJulia() {
		double reel = Double.parseDouble(TFreel.getText());
		double imaginaire = Double.parseDouble(TFimaginaire.getText());
		double pas = Double.parseDouble(TFpas.getText());
		int iterateur = Integer.parseInt(TFite.getText());
		double infX = Double.parseDouble(TFinfX.getText());
		double supX = Double.parseDouble(TFsupX.getText());
		double infY = Double.parseDouble(TFinfY.getText());
		double supY = Double.parseDouble(TFsupY.getText());
		generateFractale(controleur.generateJulia(reel,imaginaire,pas,iterateur,infX,supX,infY,supY,couleur,TFfichier.getText()));
		System.out.println("zoom = " + this.zoom);
		
		System.out.println("x = " + this.translateX);
		
		System.out.println("y = " + this.translateY);
	}
	
	public void FJuliaTxt(String s) {
		new_scan("src/main/sauvegardes/"+s);
		while (scan.hasNextLine()) {
			String caract = scan.nextLine();
			String[] attributs = caract.split(":");
			switch (attributs[0]) {
				case "TYPE DE FRACTALE": type=attributs[1];
					break;
				case "borneInfX": borneInfX=Double.parseDouble(attributs[1]);
					;break;
				case "borneSupX":borneSupX=Double.parseDouble(attributs[1]);
					break;
				case "borneInfY": borneInfY=Double.parseDouble(attributs[1]); 	
					break;
				case "borneSupY": borneSupY=Double.parseDouble(attributs[1]);
					break;
				case "pas": pas=Double.parseDouble(attributs[1]); 
					break;
				case "MAX_ITER": MAX_ITER=Integer.parseInt(attributs[1]); 
					break;
				case "couleur": couleur=Integer.parseInt(attributs[1]); 
					break;
				case "name": name=attributs[1]; 
					break;
				case "zoom": zoom=Double.parseDouble(attributs[1]);
					break;
				case "translateX": translateX=Integer.parseInt(attributs[1]); 
					break;
				case "translateY": translateY=Integer.parseInt(attributs[1]); 
					break;
				case "reel": reel=Double.parseDouble(attributs[1]);
					break;
				case "imaginaire": imaginaire=Double.parseDouble(attributs[1]); 
					break;
			}
		}
		JuliaPredef();
		generateFractale(controleur.generateJuliaBis(type,borneInfX,borneSupX,borneInfY,borneSupY,pas,MAX_ITER,couleur,name,zoom,translateX,translateY,reel,imaginaire));
	}
	
	public void FMandelbrot(){
		
	}
	
	public void JuliaPredef() {
		pane_parametres(type);
		TFinfX.setText(String.valueOf(borneInfX));
		TFsupX.setText(String.valueOf(borneSupX));
		TFinfY.setText(String.valueOf(borneInfY));
		TFsupY.setText(String.valueOf(borneSupY));
		TFpas.setText(String.valueOf(pas));
		TFite.setText(String.valueOf(MAX_ITER));
		TFreel.setText(String.valueOf(reel));
		TFimaginaire.setText(String.valueOf(imaginaire));
		sliderColor.setValue(couleur);
		TFfichier.setText(name);
	}
	
	public void generateFractale(BufferedImage img) {
		Image image = SwingFXUtils.toFXImage(img, null);
		i = new ImageView(image);
		i.toBack();
		i.setFitHeight(tailleEcran.height*0.93);
		i.setPreserveRatio(true);
		i.setSmooth(true);
		fractale_pane.getChildren().add(i);
	}
	
	public void buttons_zoom() {
		Button zoom = new Button("+");
		Button dezoom = new Button("-");
		zoom.setOnAction(actionEvent->{
			generateFractale(controleur.zoom(true));
		});
		dezoom.setOnAction(actionEvent->{
			generateFractale(controleur.zoom(false));
		});
		HBox buttons_zoom = new HBox();
		buttons_zoom.setPrefSize(50, 100);
		buttons_zoom.setLayoutX(paneParametres.getPrefWidth()*0.4);
		buttons_zoom.setLayoutY(paneParametres.getPrefHeight()*0.65);
		buttons_zoom.toFront();
		buttons_zoom.getChildren().addAll(zoom,dezoom);
		paneParametres.getChildren().addAll(buttons_zoom);// Mettre la HBox de buttons pas dans fractale_pane mais le pane d'a cote avec tous les autres choix de parametres		
	}
	
	public void buttons_translate() {
		Button moveR = new Button(">");
		moveR.setOnAction(actionEvent->{
			generateFractale(controleur.translateX(true));
		});
		Button moveL = new Button("<");
        moveL.setOnAction(actionEvent->{
        	generateFractale(controleur.translateX(false));
		});
		Button moveUp = new Button("^");
		moveUp.setOnAction(actionEvent->{
			generateFractale(controleur.translateY(false));
		});
		Button moveDown = new Button("v");
		moveDown.setOnAction(actionEvent->{
			generateFractale(controleur.translateY(true));
		});
		VBox moveY = new VBox();
		moveY.getChildren().addAll(moveUp,moveDown);
		HBox buttons_translate = new HBox();
		buttons_translate.setLayoutX(paneParametres.getPrefWidth()*0.4);
		buttons_translate.setLayoutY(paneParametres.getPrefHeight()*0.70);
		buttons_translate.toFront();
		buttons_translate.getChildren().addAll(moveL,moveY,moveR);
		paneParametres.getChildren().addAll(buttons_translate);
	}
	
	//GETTER
	public Stage getStage() { return this.stage; }
	
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
	
	public void new_scan(String fichier) {
    	try {
    		scan = new Scanner(new File(fichier), "UTF-8");
    	}
    	catch(Exception e) {
    		System.out.println("Erreur lors d ouverture fichier:");
    		e.printStackTrace();
    		System.exit(1);
    	}
    }
	
	public void set_erreur() {
		erreur.setFont(new Font("Arial", 15));
		erreur.setLayoutX(paneParametres.getPrefWidth()*0.1);
		erreur.setLayoutY(paneParametres.getPrefHeight()*0.82);
		paneParametres.getChildren().add(erreur);
	}
	
}