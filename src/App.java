import Model.*;
import Controller.*;
import View.*;

public class App {
    public static void main(String[] args) throws Exception {
        Plateau plateau = new Plateau();
        VuePlateau vuePlateau = new VuePlateau();
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau);

        plateau.addObserver(controleurPlateau);
        plateau.addObserver(vuePlateau);

        vuePlateau.addObserver(controleurPlateau);
        vuePlateau.addObserver(plateau);

        controleurPlateau.addObserver(vuePlateau);
        controleurPlateau.addObserver(plateau);
        
        controleurPlateau.jouer();

    }
}
