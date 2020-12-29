import Model.*;

import java.util.Queue;
import java.util.Iterator;

import Controller.*;
import View.*;
import Shared.Shared;
import java.awt.EventQueue;

import javax.swing.*;

public class App {

    public static void start(Shared shared, VuePlateau vuePlateau) {
        Plateau plateau = new Plateau(shared, vuePlateau);
        ControleurPlateau controleurPlateau = new ControleurPlateau(plateau, shared, vuePlateau.getFrame(), vuePlateau);

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

        // VuePlateau vuePlateau = new VuePlateau(shared);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VuePlateau vuePlateau = new VuePlateau(shared);
                    start(shared, vuePlateau);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
