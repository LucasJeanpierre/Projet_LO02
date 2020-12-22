import Model.*;

import java.util.Queue;
import java.util.Iterator;

import Controller.*;
import View.*;
import Shared.Shared;

public class App {
    public static void main(String[] args) throws Exception {
        Shared shared = new Shared();
        Plateau plateau = new Plateau(shared);
        VuePlateau vuePlateau = new VuePlateau(shared);
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau, shared);

        plateau.addObserver(controleurPlateau);
        plateau.addObserver(vuePlateau);

        vuePlateau.addObserver(controleurPlateau);
        vuePlateau.addObserver(plateau);

        controleurPlateau.addObserver(vuePlateau);
        controleurPlateau.addObserver(plateau);

        Queue<Joueur> list = plateau.getListJoueur();
        Iterator<Joueur> it = list.iterator();

        while (it.hasNext()) {
            Joueur j = it.next();
            j.addObserver(vuePlateau);
            j.addObserver(controleurPlateau);
        }
        
        controleurPlateau.jouer();

    }
}
