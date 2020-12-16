package Vue;

import Model.Carte;
import Model.Coordonee;
import Model.Plateau;
import Controler.ControleurPlateau;
import Controler.*;

public class VuePlateau implements Observer {
    public void update(Observable o) {
        if (o instanceof ControleurPlateau) {
            System.out.println("Les cartes cachee ont été posées----");
        } else if (o instanceof Coordonee) {
            System.out.println("Une carte a été placée sur une coordonnee");
        }
    }

    public static void main(String[] args) {
        VuePlateau vuePlat = new VuePlateau();
        Plateau plat = new Plateau(vuePlat);

        new ControleurPlateau(plat, vuePlat);

        plat.placerCarteCachee();

        plat.jouerModeNormal();
    }
}
