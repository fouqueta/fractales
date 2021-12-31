package vue;
import controleur.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Complexe;
import modele.Fractales;
import modele.Fractales.FractaleBuilder;


public class Main extends Application{
	private Vue vue;
	
	@Override
	public void start(Stage primaryStage) {

		Controleur controleur = new Controleur();

		vue = new Vue(controleur);
		controleur.setVue(vue);
	}
	
	
	public static void main(String[] args) throws ParseException {
		double borneInfX = -1.;
    	double borneSupX = 1.;
		double borneInfY = -1.;
    	double borneSupY = 1.;
    	double cstReelle = -0.7269;
    	double cstImaginaire = 0.1889;

        final Options premOptions = configPremParametres();
        final Options options = configParametres(premOptions);

        //On parse l'aide
        final CommandLineParser parser = new DefaultParser();
        final CommandLine firstLine = parser.parse(premOptions, args, true);

        //Si on veut l'aide
        boolean modeHelp = firstLine.hasOption("help");
        if (modeHelp) {
            final HelpFormatter hFormatter = new HelpFormatter();
            hFormatter.printHelp("Fractales", options, true);
            System.exit(0);
        }
        
        //Si on veut le mode IG
        final String mode = firstLine.getOptionValue("mode"); //Mode terminal ou IG
        if (mode.equals("i")) {	
        	launch(args);
        }
        //Si on veut le mode terminal
        else if (mode.equals("t")) {
	        //On parse le reste des options
	        final CommandLine line = parser.parse(options, args);
	
	//      final String mode = line.getOptionValue("mode"); //Mode terminal ou IG
	        
	        final String type = line.getOptionValue("type","j"); //Par defaut, on genere Julia
	        
	        final String name = line.getOptionValue("nom","MaFractale"); //Par defaut, le nom du fichier sera MaFractale
	        
	        final String pasFromParam = line.getOptionValue("pas","0.001"); //Par defaut, le pas sera de 0.001
	        double pas = 0.;
	        try {
	            pas = Double.valueOf(pasFromParam);
	        } catch (Exception e) {
	            System.out.println("Le pas doit etre un double");
	            System.exit(3);
	        }
	        
	        final String max_iterFromParam = line.getOptionValue("max_iter","1000"); //Par defaut, le nombre max d'iterations sera de 1000
	        int MAX_ITER = 0;
	        try {
	            MAX_ITER = Integer.valueOf(max_iterFromParam);
	        } catch (Exception e) {
	            System.out.println("Le nombre maximal d'iterations doit etre un int");
	            System.exit(3);
	        }
	        
	        final String couleurFromParam = line.getOptionValue("couleur","0"); //Par defaut, le code couleur sera de 0
	        int couleur = 0;
	        try {
	            couleur = Integer.valueOf(couleurFromParam);
	        } catch (Exception e) {
	            System.out.println("Le code couleur doit etre un int");
	            System.exit(3);
	        }
	        
	        if (line.hasOption("bornesX")) {	        	    
		        String borneInfXFromParam = line.getOptionValues("bornesX")[0]; //Par defaut, les bornes des abscisses seront [-1;1]
		        String borneSupXFromParam =  line.getOptionValues("bornesX")[1];
		        try {
		        	borneInfX = Double.valueOf(borneInfXFromParam);
		        	borneSupX = Double.valueOf(borneSupXFromParam);
		        } catch (Exception e) {
		            System.out.println("Les bornes abscisses doivent etre des double");
		            System.exit(3);
		        }
	        }
	        
	        if (line.hasOption("bornesY")) {
	        	String borneInfYFromParam = line.getOptionValues("bornesY")[0]; //Par defaut, les bornes des ordonnees seront [-1;1]
		        String borneSupYFromParam = line.getOptionValues("bornesY")[1];
		        try {
		        	borneInfY = Double.valueOf(borneInfYFromParam);
		        	borneSupY = Double.valueOf(borneSupYFromParam);
		        } catch (Exception e) {
		            System.out.println("Les bornes ordonnees doivent etre des double");
		            System.exit(3);
		        }
	        }
	        
	        if (line.hasOption("constante")) {
	        	String constReFromParam = line.getOptionValues("constante")[0]; //Par defaut, on donne une constante qui forme une fractale interessante
		        String constImFromParam = line.getOptionValues("constante")[1];
		        if (constReFromParam == null) { constReFromParam = "-0.7269"; }
		        if (constImFromParam == null) { constImFromParam = "0.1889"; }
		        try {
		        	cstReelle = Double.valueOf(constReFromParam);
		        	cstImaginaire = Double.valueOf(constImFromParam);
		        } catch (Exception e) {
		            System.out.println("Les parties de la constante doivent etre des double");
		            System.exit(3);
		        }
	        }
	        constrTypeFractale(type, cstReelle, cstImaginaire, pas, MAX_ITER, borneInfX, borneSupX, borneInfY, borneSupY, couleur, name);
	        System.exit(0);
        }
        else {
        	System.out.println("Le mode de lancement est incorrect");
        }
        
      
	}
	
	//Construit une fractale du type donne en argu, genere les fichiers png et txt correspondants
	private static void constrTypeFractale(String type, double cstR, double cstI, double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		Fractales fractale = new Fractales();
		switch(type) {
			case "j" :
				Complexe c = Complexe.newComplexe(cstR, cstI);
				fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildJulia(c);
				break;
			case "m" :
				fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildMandelbrot();
				break;
			case "b" :
				fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildBurningShip();
				break;
			case "t" :
				fractale = builder(pas, max_iter, infX, supX, infY, supY, couleur, name).buildTricorn();
				break;
		}
		fractale.generateFractal();
		fractale.saveFractal();
	}
	
	//return un FractaleBuilder avec les parametres (sauf constante pour Julia)
	private static FractaleBuilder builder(double pas, int max_iter, double infX, double supX, double infY, double supY, int couleur, String name) {
		return FractaleBuilder.newFractaleBuilder()
				.borneInfX(infX)
				.borneSupX(supX)
				.borneInfY(infY)
				.borneSupY(supY)
				.pas(pas)
				.MAX_ITER(max_iter)
				.couleur(couleur)
				.name(name);
	}
	
	//Creation de l'option help
	private static Options configPremParametres() {
	    final Option helpOption = Option.builder("h") 
	            .longOpt("help") 
	            .desc("Affiche l'aide") 
	            .build();
	    
	    final Option modeOption = Option.builder("m") 
				.longOpt("mode") 
		        .desc("Mode de lancement : terminal (t) / IG (i)") 
		        .hasArg(true) 
		        .build();

	    final Options firstOptions = new Options();

	    firstOptions.addOption(helpOption);
	    firstOptions.addOption(modeOption);

	    return firstOptions;
	}
	
	//Creation des options
	private static Options configParametres(final Options premOptions) {		 
	    final Option typeOption = Option.builder("t") 
	            .longOpt("type") //
	            .desc("Type de la fractale : Julia (j) / Mandelbrot (m) / Burning ship (b) / Tricorn (t)") 
	            .hasArg(true) 
	            .required(false) 
	            .build();

	    final Option nameOption = Option.builder("n") 
	            .longOpt("nom") 
	            .desc("Nom du fichier") 
	            .hasArg(true) 
	            .required(false) 
	            .build();

	    final Option pasOption = Option.builder("p") 
	            .longOpt("pas") 
	            .desc("Pas de discretisation") 
	            .hasArg(true) 
	            .required(false) 
	            .build();
	    
	    final Option max_iterOption = Option.builder("i") 
	            .longOpt("max_iter") 
	            .desc("Nombre maximal d'iterations") 
	            .hasArg(true) 
	            .required(false) 
	            .build();
	    
	    final Option couleurOption = Option.builder("c") 
	            .longOpt("couleur") 
	            .desc("Code couleur de la fractale") 
	            .hasArg(true) 
	            .required(false) 
	            .build();

	    final Option bornesXOption = Option.builder("x") 
	            .longOpt("bornesX") 
	            .desc("Bornes des abscisses du plan complexe, de la forme \"borneInf borneSup\"") 
	            .hasArg(true) 
//	            .valueSeparator(' ')
	            .required(false) 
	            .build();
	    bornesXOption.setArgs(2);
	    
	    final Option bornesYOption = Option.builder("y") 
	            .longOpt("bornesY") 
	            .desc("Bornes des ordonnees du plan complexe, de la forme \"borneInf borneSup\"") 
	            .hasArg(true) 
//	            .valueSeparator(' ')
	            .required(false) 
	            .build();
	    bornesYOption.setArgs(2);
	    
	    final Option constanteOption = Option.builder("cst") 
	            .longOpt("constante") 
	            .desc("Constante pour les fractales Julia, de la forme \"partieReelle partieImaginaire\"") 
	            .hasArg(true)
//	            .valueSeparator(' ')
	            .required(false) 
	            .build();
	    constanteOption.setArgs(2);
	    
	    final Options options = new Options();
	    
	    for (final Option o : premOptions.getOptions()) {
	        options.addOption(o);
	    }

	    options.addOption(typeOption);
	    options.addOption(nameOption);
	    options.addOption(pasOption);
	    options.addOption(max_iterOption);
	    options.addOption(couleurOption);
	    options.addOption(bornesXOption);
	    options.addOption(bornesYOption);
	    options.addOption(constanteOption);

	    return options;
	}
	
}
