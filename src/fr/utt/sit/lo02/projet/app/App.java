package fr.utt.sit.lo02.projet.app;

import fr.utt.sit.lo02.projet.model.*;
import fr.utt.sit.lo02.projet.controller.*;
import fr.utt.sit.lo02.projet.view.*;
import fr.utt.sit.lo02.projet.shared.*;

import java.util.Queue;
import java.util.Iterator;
import java.awt.EventQueue;

/**
 * Class App
 * 
 */
public class App {

    public static void start(Shared shared, VuePlateau vuePlateau, boolean modeAvance, boolean modePlateauLibre, boolean modeTroisJoueur) {
        Plateau plateau = new Plateau(shared, vuePlateau, modePlateauLibre, modeTroisJoueur);
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau, shared, vuePlateau.getFrame(), vuePlateau, modeAvance, modePlateauLibre);

        plateau.addObserver(vuePlateau);
        controleurPlateau.addObserver(vuePlateau);

        Queue<Joueur> list = plateau.getListJoueur();
        Iterator<Joueur> it = list.iterator();

        while (it.hasNext()) {
            Joueur j = it.next();
            j.addObserver(vuePlateau);
        }

        controleurPlateau.jouer();
    }

  /**
   * Main Method
   * Cette method creer l'objet shared et donne sa rérence a tout les objets qui en auront besoins
   * Elle definie les paramères du jeu (avance, trois joueur, plateauLibre)
   * @param args
   * @throws Exception
   */
    public static void main(String[] args) throws Exception {
        Shared shared = new Shared();
        boolean modeAvance = false;
        boolean modePlateauLibre = false;
        boolean modeTroisJoueur = false;

        // VuePlateau vuePlateau = new VuePlateau(shared);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VuePlateau vuePlateau = new VuePlateau(shared, modeAvance, modePlateauLibre, modeTroisJoueur);
                    start(shared, vuePlateau, modeAvance, modePlateauLibre, modeTroisJoueur);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
