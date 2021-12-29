package vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
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
import javafx.scene.text.Text;
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
	private TextField TFwidth;
	private TextField TFheight;
	
	private int couleur;
	
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
		//initilisation_scene_frct();
		menu.initialisation_pane_accueil();

		//stage.setFullScreen(true);
		
		//init_scene_fractale();

		stage.show();
	}
	
	/*public void init_scene_fractale() {
		fractale_pane = new AnchorPane();
		fractale_pane.setPrefSize(tailleEcran.width*0.8,tailleEcran.height*0.8);
		fractale_pane.setStyle("-fx-background-color: #BAEEB4");
		buttons_zoom();
		buttons_translate();
		//generateFractale(controleur.generateJulia());
		generateFractale(controleur.generateBurningShip());
		fractale_scene = new Scene(fractale_pane);
		stage.setScene(fractale_scene);	
	}*/
	
	void initialisation_HBox_principale(String s) {
		HBox_fenetre = new HBox();
		initialisation_pane_fractale();
		initialisation_pane_parametres(s);
		HBox_fenetre.getChildren().addAll(fractale_pane, paneParametres);
		fractale_scene = new Scene(HBox_fenetre);
		stage.setScene(fractale_scene);
	}
	
	void initialisation_pane_fractale() {
		fractale_pane = new AnchorPane();
		fractale_pane.setPrefSize((tailleEcran.width*77)/100, tailleEcran.height);
		fractale_pane.setStyle("-fx-background-color:#618686");
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
			controleur.saveFractaleControleur();
		});
		valider.setOnAction(actionEvent->{
			paneParametres.getChildren().remove(erreur);
			//restoreSliderColor();
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
	
	public void restoreSliderColor() {
		
	}
	
	public int initSliderColor() {
		Label labelColor = new Label("Choisir la couleur");
	    Slider sliderColor = new Slider();
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
	    VBoxCouleurs.setLayoutY(paneParametres.getPrefHeight()*0.43);
	    paneParametres.getChildren().add(VBoxCouleurs);
	    
	    sliderColor.valueProperty().addListener((obs, oldval, newVal) -> {
	    	sliderColor.setValue(newVal.intValue());
	    	infoColor.setText("Valeur : " + newVal.intValue());
	    	couleur = newVal.intValue();
	    });
	    return couleur;
//		couleurMenu = new MenuButton("Couleur");
//		CheckMenuItem rouge = new CheckMenuItem("Rouge");
//        CheckMenuItem bleu = new CheckMenuItem("Bleu");
//        CheckMenuItem vert = new CheckMenuItem("Vert");
//        CheckMenuItem jaune = new CheckMenuItem("Jaune");
//        CheckMenuItem violet = new CheckMenuItem("Violet");
//        couleurMenu.setLayoutX(paneParametres.getPrefWidth()*0.05);
//        couleurMenu.setLayoutY(paneParametres.getPrefHeight()*0.43);
//        couleurMenu.getItems().addAll(rouge,bleu,vert,jaune,violet);
//        paneParametres.getChildren().add(couleurMenu);
//        rouge.setOnAction(actionEvent->{
//			couleur = 0;
//		});
//        bleu.setOnAction(actionEvent->{
//			couleur = 600;
//		});
//        vert.setOnAction(actionEvent->{
//			couleur = 400;
//		});
//        jaune.setOnAction(actionEvent->{
//			couleur = 100;
//		});
//        violet.setOnAction(actionEvent->{
//			couleur = 700;
//		});
//        return couleur;
	}
	
	void FJulia() {
		double reel = Double.parseDouble(TFreel.getText());
		double imaginaire = Double.parseDouble(TFimaginaire.getText());
		double pas = Double.parseDouble(TFpas.getText());
		int iterateur = Integer.parseInt(TFite.getText());
		int largeur = Integer.parseInt(TFwidth.getText());
		int hauteur = Integer.parseInt(TFheight.getText());
		generateFractale(controleur.generateJulia(reel,imaginaire,pas,iterateur,largeur,hauteur,couleur));
	}
	
	public void FMandelbrot(){
		
	}
	
	public void generateFractale(BufferedImage img) {
		Image image = SwingFXUtils.toFXImage(img, null);
		ImageView i = new ImageView(image);
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
	
	public void set_erreur() {
		erreur.setFont(new Font("Arial", 15));
		erreur.setLayoutX((paneParametres.getPrefWidth()*10)/100);
		erreur.setLayoutY((paneParametres.getPrefHeight()*82)/100);
		paneParametres.getChildren().add(erreur);
	}
	
}