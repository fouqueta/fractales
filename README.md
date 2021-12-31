# Fractales

Projet de Compléments en Programmation Orientée Objet : Fractales.  
Année 2021-2022.  
L3 Informatique (Université de Paris).  


## Protocole
- Pour compiler, lancer : gradle build
- Pour avoir l’aide ci-dessous, lancer : gradle run --args=’-h’  
usage: Fractales [-c <arg>] [-cst <arg>] [-h] [-i <arg>] [-m <arg>] [-n <arg>] [-p <arg>] [-t <arg>] [-x <arg>] [-y <arg>]  
 -c,--couleur <arg>       Code couleur de la fractale  
 -cst,--constante <arg>   Constante pour les fractales Julia, de la forme "partieRéelle partieImaginaire"  
 -h,--help                Affiche l'aide  
 -i,--max_iter <arg>      Nombre maximal d'itérations  
 -m,--mode <arg>          Mode de lancement : terminal (t) / IG (i)  
 -n,--nom <arg>           Nom du fichier  
 -p,--pas <arg>           Pas de discrétisation  
 -t,--type <arg>          Type de la fractale : Julia (j) / Mandelbrot (m) / Burning ship (b) / Tricorn (t)  
 -x,--bornesX <arg>       Bornes des abscisses du plan complexe, de la forme "borneInf borneSup"  
 -y,--bornesY <arg>       Bornes des ordonnées du plan complexe, de la forme "borneInf borneSup"  


## Points forts
**Algorithme :**
- La fonction translate(char dir) de la classe Fractales qui permet le déplacement sur l’image sans recalcul de tous les points : il y a juste un calcul de la nouvelle bande de pixels avec la fonction stream(startX, endX, startY, endY, color) de Fractales, le reste est une translation des couleurs des pixels d’un certain pas avec les fonctions translate_pixels_RtoL ou translate_pixels_LtoR.

- Les fonctions du calcul des fractales en général : calcul prenant en compte le zoom/dézoom, les déplacements, le choix des couleurs, des bornes du plan complexe, etc.

- La fonction FInstanceTxt(String s) de la classe Vue permettant de visualiser des fractales déjà générées : elle lit un fichier .txt dans laquelle les attributs de la classe Fractales et leur valeur sont séparés par le séparateur “:”. Ces valeurs seront ensuite affectées au TextField correspondant. L’utilisateur pourra ainsi voir les paramètres déjà pré-remplis par les informations spécifiques à la fractale, le zoom et le déplacement sur l’image seront également appliqués.

**Bibliothèques :**
- Apache Common CLI pour gérer les paramètres passés en ligne de commande.

- JavaFX pour mettre en place l’interface graphique

**Technique de conception et de programmation :** 
- Builder permettant une construction plus flexible des classes héritant de Fractales.

- Gradle utilisé pour la compilation et l’exécution du programme en prenant compte les arguments passés dans la ligne de commande.

- Utilisation des Stream parallèles pour le calcul qui donne une meilleure performance. Pour générer une fractale Julia de constante -0.7269 + 0.1889i, de pas 0.0001, de nombre max d’itérations 1000, de plan complexe [-1;1]x[-1;1], un calcul avec Stream simples a pris 277566 ms (+ de 4 min) tandis qu’un calcul avec Stream parallèles a pris 103119 ms (- de 2 min).

- Implémentation de quatre types de fractales : Julia, Mandelbrot, Burning ship et Tricorn.

- Gestion des erreurs pour les fichiers et vérification du bon format des paramètres entrés par l’utilisateur. Ces erreurs sont détectées et traitées par l’implémentation de try/catch.