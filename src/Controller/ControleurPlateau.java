package Controller;


import Model.*;
import View.VuePlateau;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import Shared.Shared;

import java.util.Iterator;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.io.BufferedReader;
import java.io.*;

/**
 * Le Controleur du Jeu, C'est un observateur du Model et de la vue, Il reçoit
 * les input du joueur via les bouttons ou la console et interagie avec le
 * modele et la vue
 * 
 * @author Lucas JEANPIERRE
 * 
 */

public class ControleurPlateau implements Runnable {

    private Plateau plateau;
    private Shared shared;
    private Thread t;
    private String saisie;
    private String[] commande;
    private boolean quitter;
    private JButton bouttonPiocher;
    private JButton bouttonChanger;
    private JButton bouttonBouger;
    private JPanel cartePioche1;
    private JPanel cartePioche2;
    private JPanel cartePioche3;

    public static String PROMPT = "> ";
    public static String PLACER = "placer";
    public static String QUITTER = "quitter";
    public static String PIOCHER = "piocher";
    public static String BOUGER = "bouger";
    public static String CHANGEMENT_JOUEUR = "changer_de_joueur";
    public static String CHOISIR = "choisir";

    private boolean aUneCarteEnMain;
    private boolean aJoue;
    private boolean aBouger;

    private boolean bougerChoixCarte;
    private boolean bougerChoixCoord;
    private Coordonee coordCarteABouger;

    private boolean tourOrdinateur;
    private boolean modeAvance;
    private int size;

    private int nbCarteJoue;

    private PropertyChangeSupport pcs;

    /**
     * Ajoute un observateur a l'objet
     * 
     * @param l l'observateur
     */

    public void addObserver(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }

    /**
     * 
     */
    public void propertyChange(PropertyChangeEvent evt) {
    }

    /**
     * notifie les observateur de l'objet
     * 
     * @param name le nom du changement
     */
    public void setProperty(String name) {
        this.pcs.firePropertyChange(name, 0, 1);
    }

    /**
     * Constructeur du Controleur
     * 
     * @param plateau
     * @param shared
     * @param frame
     * @param vuePlateau
     * @param modeAvance
     * @param modePlateauLibre
     * 
     *                         Dans le constructeur sont compris les fonction
     *                         d'ecoutes des bouttons
     */
    public ControleurPlateau(Plateau plateau, Shared shared, JFrame frame, VuePlateau vuePlateau, boolean modeAvance,
            boolean modePlateauLibre) {
        this.plateau = plateau;
        this.pcs = new PropertyChangeSupport(this);
        this.shared = shared;
        this.aJoue = true;
        this.bougerChoixCarte = false;
        this.bougerChoixCoord = false;
        this.tourOrdinateur = false;
        this.modeAvance = modeAvance;
        this.nbCarteJoue = 0;

        if (modePlateauLibre) {
            this.size = 50;
        } else {
            this.size = 100;
        }
        this.t = new Thread(this);

        Iterator<Coordonee> it = this.plateau.getListeCoord().iterator();

        while (it.hasNext()) {
            Coordonee coord = it.next();
            coord.getPanel().addMouseListener(new MouseInputAdapter() {

                public void mousePressed(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    if (!ControleurPlateau.this.bougerChoixCarte) {
                        ControleurPlateau.this.placer(panel.getX() / ControleurPlateau.this.size,
                                panel.getY() / ControleurPlateau.this.size);
                        ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
                    } else if ((ControleurPlateau.this.bougerChoixCarte)
                            && (!ControleurPlateau.this.bougerChoixCoord)) {
                        ControleurPlateau.this.coordCarteABouger = ControleurPlateau.this.plateau.recupererCoord(
                                panel.getX() / ControleurPlateau.this.size, panel.getY() / ControleurPlateau.this.size);
                        ControleurPlateau.this.bougerChoixCoord = true;
                    } else if ((ControleurPlateau.this.bougerChoixCarte) && (ControleurPlateau.this.bougerChoixCoord)) {
                        ControleurPlateau.this.bouger(ControleurPlateau.this.coordCarteABouger.getPositionX(),
                                ControleurPlateau.this.coordCarteABouger.getPositionY(),
                                panel.getX() / ControleurPlateau.this.size, panel.getY() / ControleurPlateau.this.size);
                        ControleurPlateau.this.bougerChoixCarte = false;
                        ControleurPlateau.this.bougerChoixCoord = false;
                    }
                }
            });
        }

        this.bouttonPiocher = vuePlateau.getButtonPiocher();

        this.bouttonPiocher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControleurPlateau.this.piocher();
            }
        });

        this.bouttonChanger = vuePlateau.getButtonChanger();

        this.bouttonChanger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControleurPlateau.this.changerDeJoueur();
                ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
            }
        });

        this.bouttonBouger = vuePlateau.getButtonBouger();

        this.bouttonBouger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControleurPlateau.this.bougerChoixCarte = true;
                ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
            }
        });
        if (this.modeAvance) {
            this.cartePioche1 = vuePlateau.getCartePioche1();

            this.cartePioche1.addMouseListener(new MouseInputAdapter() {
                public void mousePressed(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    ControleurPlateau.this.choisirCarteAPlacer(panel.getY());
                }
            });

            this.cartePioche2 = vuePlateau.getCartePioche2();

            this.cartePioche2.addMouseListener(new MouseInputAdapter() {
                public void mousePressed(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    ControleurPlateau.this.choisirCarteAPlacer(panel.getY());
                }
            });

            this.cartePioche3 = vuePlateau.getCartePioche3();

            this.cartePioche3.addMouseListener(new MouseInputAdapter() {
                public void mousePressed(MouseEvent e) {
                    JPanel panel = (JPanel) e.getSource();
                    ControleurPlateau.this.choisirCarteAPlacer(panel.getY());
                }
            });
        }
    }

    /**
     * Methode a appeler au debut de jeu Elle fait commance le jeu et l'ecoute des
     * boutton par le controleur
     */
    public void jouer() {
        if (!this.modeAvance) {
            this.plateau.donnerCarteVictoire();
            this.changerDeJoueur();
            this.t.start();
        } else {
            // this.plateau.donnerDeuxCarteAuxJoueurs();
            this.changerDeJoueur();
            this.t.start();
        }
    }

    /**
     * Methode run cette methode gere les commande faites par la console
     * 
     */
    public void run() {

        do {
            saisie = this.lireChaine();
            if (saisie != null) {
                commande = saisie.split("\\s+");
                if (commande[0].equals(ControleurPlateau.PLACER)) {
                    this.placer(Integer.parseInt(commande[1]), Integer.parseInt(commande[2]));
                } else if (commande[0].equals(ControleurPlateau.PIOCHER)) {
                    this.piocher();
                } else if (commande[0].equals(ControleurPlateau.QUITTER)) {
                    quitter = true;
                } else if (commande[0].equals(ControleurPlateau.CHANGEMENT_JOUEUR)) {
                    this.changerDeJoueur();
                } else if (commande[0].equals(ControleurPlateau.CHOISIR)) {
                    this.choisirCarteAPlacer(Integer.parseInt(commande[1]));
                } else if (commande[0].equals(ControleurPlateau.BOUGER)) {
                    if (commande[4] != null) {
                        this.bouger(Integer.parseInt(commande[1]), Integer.parseInt(commande[2]),
                                Integer.parseInt(commande[3]), Integer.parseInt(commande[4]));
                    }
                }
            }
            this.plateau.isFini(this.modeAvance);
        } while ((quitter == false));
        System.exit(0);

    }

    /**
     * Methode qui pioche un carte et la donne au joueur qui l'a pioche
     */
    private void piocher() {
        if (!this.modeAvance) {
            if ((!this.aUneCarteEnMain) && (!this.aJoue)) {
                System.out.println(plateau.getListJoueur().element().getNbCarteJoue());
                Carte carte;
                if (this.nbCarteJoue != 0) {
                    carte = new CarteNormal(this.plateau.getPaquet().getRandomCarte());
                } else {
                    carte = new CarteCachee(this.plateau.getPaquet().getRandomCarte());
                }

                this.shared.setJoueur(plateau.getListJoueur().element());
                this.shared.getJoueur().piocherUneCarte(carte);
                this.shared.setCarte(carte);
                this.setProperty("controleur-montrer-carte-pioche");
                this.aUneCarteEnMain = true;
            }
        } else {
            if ((!this.aUneCarteEnMain) && (!this.aJoue) && (this.plateau.getPaquet().getNombreDeCarte() > 0)) {
                Carte carte;
                if (this.nbCarteJoue != 0) {
                    if ((plateau.getListJoueur().element().getNbCarteJoue() <= 1)
                            && (plateau.getListJoueur().element().getNbCarteEnMain() == 0)) {
                        this.plateau.donnerDeuxCarteAuJoueur(plateau.getListJoueur().element());
                        carte = new CarteNormal(this.plateau.getPaquet().getRandomCarte());
                    } else {
                        carte = new CarteNormal(this.plateau.getPaquet().getRandomCarte());
                    }
                } else {
                    carte = new CarteCachee(this.plateau.getPaquet().getRandomCarte());

                }

                this.shared.setJoueur(plateau.getListJoueur().element());
                this.shared.getJoueur().piocherUneCarte(carte);
                this.shared.getJoueur().montrerLaMain();
                this.shared.setCarte(this.shared.getJoueur().getMain().get(0));
                this.aUneCarteEnMain = true;
            } else {
                this.shared.setJoueur(plateau.getListJoueur().element());
                this.shared.getJoueur().montrerLaMain();
                this.shared.setCarte(this.shared.getJoueur().getMain().get(0));
                this.aUneCarteEnMain = true;

            }
        }
    }

    /**
     * Place une carte au coordonnee donnee en argument
     * 
     * @param x
     * @param y
     */
    private void placer(int x, int y) {
        if ((this.aUneCarteEnMain) && (!this.aJoue)) {
            // Si la carte a pu être poser on ne peut plus poser de carte durant ce tour
            if (this.plateau.poserUneCarte(this.shared.getCarte(), x, y)) {
                this.aUneCarteEnMain = false;
                this.aJoue = true;
                this.nbCarteJoue++;
            }

        }

    }

    /**
     * Bouge la carte donnee par les premiere coordonee puis la place a l'endroit
     * indiquer par les deuxieme coordonee
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void bouger(int x1, int y1, int x2, int y2) {
        if ((this.aJoue) && (!this.aBouger)) {
            System.out.println("bouger");
            Carte carte = this.plateau.recupererCoord(x1, y1).getCarte();
            if (this.plateau.bougerUneCarte(carte, x2, y2)) {
                this.aBouger = true;
            }

        }
    }

    /**
     * Methode qui change de tour Si le prochain joueur est un ordinateur elle
     * appelle la methode faireJouerOrdi N'est exectuer que si le joueur precedant a
     * au moins placer une carte
     * 
     */
    private void changerDeJoueur() {
        if (this.aJoue) {

            // rendre non visible les cartes de la main du joueur
            Joueur j = this.plateau.getListJoueur().element();
            Iterator<Carte> it = j.getMain().iterator();

            while (it.hasNext()) {
                Carte carte = it.next();
                carte.getImage().setVisible(false);
            }

            this.plateau.changementDeTour();
            this.aUneCarteEnMain = false;
            this.aJoue = false;
            this.aBouger = false;

            this.plateau.isFini(this.modeAvance);

            if (this.plateau.getListJoueur().element() instanceof JoueurIA) {
                this.tourOrdinateur = true;
                this.faireJouerOrdi();
            }
        }
    }

    /**
     * Methode choisirCarteAPlacer permet (en mode avance uniquement) de choisir la
     * carte que l'on veut placer
     * 
     * @param y la coord de la carte que l'on veut jouer
     */
    private void choisirCarteAPlacer(int y) {

        if (y < 5) {
            if (y == 0) {
                y = 300;
            } else if (y == 1) {
                y = 350;
            } else if (y == 2) {
                y = 400;
            }
        }

        if (y == 300) {
            this.shared.setCarte(this.plateau.getListJoueur().element().getMain().get(0));
        } else if (y == 350) {
            this.shared.setCarte(this.plateau.getListJoueur().element().getMain().get(1));
        } else if (y == 400) {
            this.shared.setCarte(this.plateau.getListJoueur().element().getMain().get(2));
        }
    }

    /**
     * Fait jouer l'IA
     */
    private void faireJouerOrdi() {
        if (this.tourOrdinateur) {
            Joueur j = this.plateau.getListJoueur().element();
            this.piocher();
            String coord = j.choisirCoordoneeAPlacer(this.shared.getCarte());
            this.placer(Integer.parseInt(coord.split(",")[0]), Integer.parseInt(coord.split(",")[1]));
            this.tourOrdinateur = false;
            this.changerDeJoueur();
        }
    }

    /**
     * Retourne la chaine de caratere qu'a preciser l'utilisateur
     * 
     * @return
     */
    private String lireChaine() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String resultat = null;
        try {
            System.out.print(ControleurPlateau.PROMPT);
            resultat = br.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return resultat;
    }
}
