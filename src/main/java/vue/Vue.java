package vue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import controleur.*;
import modele.*;
import modele.Fractales.FractaleBuilder;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Vue {
	//private AnchorPane root;
	private Stage stage;
	private Scene fractale_scene;
	private Controleur controleur;
	
	private AnchorPane fractale_pane;
	
	private Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Vue(Controleur controleur){
		this.controleur = controleur;
		
		stage = new Stage();
		stage.setTitle("Fractales");
		stage.setMaximized(true);
		//stage.setFullScreen(true);
		
		init_scene_fractale();
		
		
		stage.show();
	}
	
	public void init_scene_fractale() {
		fractale_pane = new AnchorPane();
		fractale_pane.setPrefSize(tailleEcran.width*0.8,tailleEcran.height*0.8);
		fractale_pane.setStyle("-fx-background-color: #BAEEB4");
		buttons_zoom();
		buttons_translate();
		generateFractale(controleur.generateJulia());
		fractale_scene = new Scene(fractale_pane);
		stage.setScene(fractale_scene);	
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
		buttons_zoom.setLayoutX(tailleEcran.width*0.88);
		buttons_zoom.setLayoutY(200);
		buttons_zoom.toFront();
		buttons_zoom.getChildren().addAll(zoom,dezoom);
		fractale_pane.getChildren().addAll(buttons_zoom);// Mettre la HBox de buttons pas dans fractale_pane mais le pane d'a cote avec tous les autres choix de parametres		
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
		buttons_translate.setLayoutX(tailleEcran.width*0.87);
		buttons_translate.setLayoutY(300);
		buttons_translate.toFront();
		buttons_translate.getChildren().addAll(moveL,moveY,moveR);
		fractale_pane.getChildren().addAll(buttons_translate);
	}
	

	
}