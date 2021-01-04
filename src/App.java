import Model.*;

import java.util.Queue;
import java.util.Iterator;

import Controller.*;
import View.*;
import Shared.Shared;
import java.awt.EventQueue;


public class App {

    public static void start(Shared shared, VuePlateau vuePlateau, boolean modeAvance, boolean modePlateauLibre, boolean modeTroisJoueur) {
        Plateau plateau = new Plateau(shared, vuePlateau, modePlateauLibre, modeTroisJoueur);
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau, shared, vuePlateau.getFrame(), vuePlateau, modeAvance, modePlateauLibre);

        plateau.addObserver(controleurPlateau);
        plateau.addObserver(vuePlateau);

        vuePlateau.addObserver(controleurPlateau);
        vuePlateau.addObserver(plateau);

        controleurPlateau.addObserver(vuePlateau);
        controleurPlateau.addObserver(plateau);

        /*ImagePanel panel = new ImagePanel("vide");
        panel.setVisible(true);
        vuePlateau.getFrame().getContentPane().add(panel);*/

        Queue<Joueur> list = plateau.getListJoueur();
        Iterator<Joueur> it = list.iterator();

        while (it.hasNext()) {
            Joueur j = it.next();
            j.addObserver(vuePlateau);
            j.addObserver(controleurPlateau);
        }


        //System.out.println(vuePlateau.getFrame().countComponents());

        controleurPlateau.jouer();
    }

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
