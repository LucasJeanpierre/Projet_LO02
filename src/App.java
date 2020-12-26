import Model.*;

import java.util.Queue;
import java.util.Iterator;

import Controller.*;
import View.*;
import Shared.Shared;
import java.awt.EventQueue;
import javax.swing.*;

public class App {

    public static void start(Plateau plateau, Shared shared, VuePlateau vuePlateau) {
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau, shared, vuePlateau.getFrame());

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

    public static void main(String[] args) throws Exception {
        Shared shared = new Shared();
        Plateau plateau = new Plateau(shared);

        // VuePlateau vuePlateau = new VuePlateau(shared);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VuePlateau vuePlateau = new VuePlateau(shared);
                    start(plateau, shared, vuePlateau);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
