package Controller;

import java.util.Scanner;

import Model.*;
import View.Observable;
import View.Observer;
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
import javax.swing.event.MouseInputListener;

import java.io.BufferedReader;
import java.io.*;

public class ControleurPlateau implements PropertyChangeListener, Runnable {

    private Scanner scanner;
    private Plateau plateau;
    private Paquet paquet;
    private VuePlateau view;
    private Shared shared;
    private JFrame frame;
    private Thread t;
    private String saisie;
    private String[] commande;
    private boolean quitter;
    private boolean fini;
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
    private boolean modePlateauLibre;
    private int size;

    private int nbCarteJoue;

    private PropertyChangeSupport pcs;

    public void addObserver(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }

    public void propertyChange(PropertyChangeEvent evt) {
    }

    public void setProperty(String name) {
        this.pcs.firePropertyChange(name, 0, 1);
    }

    private enum etatDuJeu {
        PLACER_CARTE, CHOIX_BOUGER_CARTE, BOUGER_CARTE
    };

    private int intEtatDuJeu = 0;

    public ControleurPlateau(Plateau plateau, Shared shared, JFrame frame, VuePlateau vuePlateau, boolean modeAvance,
            boolean modePlateauLibre) {
        this.scanner = new Scanner(System.in);
        this.plateau = plateau;
        this.paquet = this.plateau.getPaquet();
        this.pcs = new PropertyChangeSupport(this);
        this.shared = shared;
        this.frame = frame;
        this.aJoue = true;
        this.fini = false;
        this.bougerChoixCarte = false;
        this.bougerChoixCoord = false;
        this.tourOrdinateur = false;
        this.modeAvance = modeAvance;
        this.modePlateauLibre = modePlateauLibre;
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
                        ControleurPlateau.this.fini = ControleurPlateau.this.plateau
                                .isFini(ControleurPlateau.this.modeAvance);
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
                //ControleurPlateau.this.fini = ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
            }
        });

        this.bouttonChanger = vuePlateau.getButtonChanger();

        this.bouttonChanger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControleurPlateau.this.changerDeJoueur();
                ControleurPlateau.this.fini = ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
            }
        });

        this.bouttonBouger = vuePlateau.getButtonBouger();

        this.bouttonBouger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ControleurPlateau.this.bougerChoixCarte = true;
                ControleurPlateau.this.fini = ControleurPlateau.this.plateau.isFini(ControleurPlateau.this.modeAvance);
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

    public void ControllerJeu2() {
        boolean joue = false;

        // on donne les cartes victoire des joueurs
        Iterator<Joueur> it = this.plateau.getListJoueur().iterator();

        while (it.hasNext()) {
            Joueur joueur = it.next();
            joueur.piocherUneCarteVictoire(this.paquet.getRandomCarte());
            System.out
                    .println(joueur.toString() + " votre carte victoire est : " + joueur.getCarteVictoire().toString());
        }

        // tant qu'il reste des cartes a placer et que le plateau n'est pas rempli
        while ((this.paquet.getNombreDeCarte() > 0) && (!this.plateau.isPlateauRempli())) {
            this.plateau.changementDeTour();
            joue = false;

            Carte cartePioche = this.paquet.getRandomCarte();
            // poser une carte
            while (!joue) {

                String positionPoser;
                Carte carteJoue;
                // on donne la carte dans la main du joueur
                if (cartePioche != null) {
                    this.plateau.getListJoueur().element().piocherUneCarte(cartePioche);
                }
                // System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

                this.plateau.montrerLesCartesPosees();

                System.out.println("Vous avez piochez la carte : " + cartePioche.toString());
                carteJoue = this.plateau.getListJoueur().element().choisirCarteAPlacer(false);

                System.out.println("Ou voulez vous la poser ? ");
                // String positionPoser = scanner.nextLine();
                positionPoser = this.plateau.getListJoueur().element().choisirCoordoneeAPlacer(carteJoue);

                // le format �tant position = "x,y"
                // on r�cup�re s�par�ment les coordon�es en splitant la chaine de caract�re a la
                // virgule et ron r�cup�re chacune des 2 parties dans des variables
                try {
                    int x = Integer.parseInt(positionPoser.split(",")[0]);
                    int y = Integer.parseInt(positionPoser.split(",")[1]);
                    joue = this.plateau.poserUneCarte(carteJoue, x, y);
                } catch (Exception Error) {
                    System.out.println("Veuillez saisir des coordonee valides");
                }

                // System.out.println("Liste des cartes pos�e : ");

                if (!joue) {
                    // System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
                    // pos�e");
                    System.out.println("La carte n'a pas �tait pos�e");
                } else {
                    this.plateau.getListJoueur().element().poserUneCarte(carteJoue);
                }
            }

            this.plateau.montrerLesCartesPosees();

            joue = false;

            // bouger une carte
            while ((!joue) && (!this.plateau.isPlateauRempli())) {
                System.out.println("Voulez vous bouger une carte?");
                // String bouger = scanner.nextLine();
                String bouger = this.plateau.getListJoueur().element().decisionBougerCarte();

                if (bouger.equals("oui")) {
                    System.out.println("Donnez les coordonn� de la carte que vous voulez bouger");
                    // String positioneCarte = scanner.nextLine();
                    String positionCarte = this.plateau.getListJoueur().element().choisirCoordoneeCarteABouger();
                    int xPremiere = Integer.parseInt(positionCarte.split(",")[0]);
                    int yPremiere = Integer.parseInt(positionCarte.split(",")[1]);

                    Carte carteABouger = this.plateau.recupererCoord(xPremiere, yPremiere).getCarte();

                    System.out.println("Donnez les coordonn� de l'emplacement ou vous voulez la bouger");
                    // String positionFinal = scanner.nextLine();
                    String positionFinal = this.plateau.getListJoueur().element().bougerUneCarte();
                    int xFinal = Integer.parseInt(positionFinal.split(",")[0]);
                    int yFinal = Integer.parseInt(positionFinal.split(",")[1]);

                    joue = this.plateau.bougerUneCarte(carteABouger, xFinal, yFinal);

                    if (!joue) {
                        // System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
                        // boug�e");
                        System.out.println("La carte n'a pas �tait pos�e");
                    }

                } else {
                    joue = true;
                }
            }
        }

        // calcul des scores
        Iterator<Joueur> itjoueur = this.plateau.getListJoueur().iterator();

        while (itjoueur.hasNext()) {
            Joueur joueur = itjoueur.next();
            // chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
            // aura donc le choix entre 3 cartes a chaque tours
            // System.out.println(joueur.toString() + " " +
            // joueur.getCarteVictoire().toString() + " " +
            // this.compterLesPoints(joueur.getCarteVictoire()));
        }

    }

    public void placerLaCarte(String positionPoser, Carte carteJoue) {
        boolean joue = false;
        try {
            int x = Integer.parseInt(positionPoser.split(",")[0]);
            int y = Integer.parseInt(positionPoser.split(",")[1]);
            joue = this.plateau.poserUneCarte(carteJoue, x, y);
        } catch (Exception Error) {
            System.out.println("Veuillez saisir des coordonee valides");
        }

        // System.out.println("Liste des cartes pos�e : ");

        if (!joue) {
            // System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
            // pos�e");
            System.out.println("La carte n'a pas �tait pos�e");
        } else {
            this.plateau.getListJoueur().element().poserUneCarte(carteJoue);
        }
    }

    public void controllerJeu() {
        boolean joue = false;
        this.intEtatDuJeu = 0;

        this.plateau.placerCarteCachee();
        this.plateau.donnerCarteVictoire();

        while ((this.paquet.getNombreDeCarte() > 0) && (!this.plateau.isPlateauRempli())) {
            this.plateau.changementDeTour();
            joue = false;

            Carte cartePioche = this.paquet.getRandomCarte();
            // poser une carte
            while (!joue) {

                String positionPoser;
                Carte carteJoue;
                // on donne la carte dans la main du joueur
                if (cartePioche != null) {
                    this.plateau.getListJoueur().element().piocherUneCarte(cartePioche);
                }
                // System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

                // this.plateau.montrerLesCartesPosees();

                // System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

                carteJoue = this.plateau.getListJoueur().element().choisirCarteAPlacer(false);

                System.out.println("Ou voulez vous la poser ? ");
                positionPoser = this.scanner.nextLine();

                positionPoser = this.plateau.getListJoueur().element().choisirCoordoneeAPlacer(carteJoue);

                // le format �tant position = "x,y"
                // on r�cup�re s�par�ment les coordon�es en splitant la chaine de caract�re a la
                // virgule et ron r�cup�re chacune des 2 parties dans des variables
                try {
                    int x = Integer.parseInt(positionPoser.split(",")[0]);
                    int y = Integer.parseInt(positionPoser.split(",")[1]);
                    joue = this.plateau.poserUneCarte(carteJoue, x, y);
                } catch (Exception Error) {
                    System.out.println("Veuillez saisir des coordonee valides");
                }

                // System.out.println("Liste des cartes pos�e : ");

                if (!joue) {
                    // System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
                    // pos�e");
                    System.out.println("La carte n'a pas �tait pos�e");
                } else {
                    this.plateau.getListJoueur().element().poserUneCarte(carteJoue);
                }
            }

            this.plateau.montrerLesCartesPosees();

            joue = false;

            // bouger une carte
            while ((!joue) && (!this.plateau.isPlateauRempli())) {
                System.out.println("Voulez vous bouger une carte?");
                // String bouger = scanner.nextLine();
                String bouger = this.plateau.getListJoueur().element().decisionBougerCarte();

                if (bouger.equals("oui")) {
                    System.out.println("Donnez les coordonn� de la carte que vous voulez bouger");
                    // String positioneCarte = scanner.nextLine();
                    String positionCarte = this.plateau.getListJoueur().element().choisirCoordoneeCarteABouger();
                    int xPremiere = Integer.parseInt(positionCarte.split(",")[0]);
                    int yPremiere = Integer.parseInt(positionCarte.split(",")[1]);

                    Carte carteABouger = this.plateau.recupererCoord(xPremiere, yPremiere).getCarte();

                    System.out.println("Donnez les coordonn� de l'emplacement ou vous voulez la bouger");
                    // String positionFinal = scanner.nextLine();
                    String positionFinal = this.plateau.getListJoueur().element().bougerUneCarte();
                    int xFinal = Integer.parseInt(positionFinal.split(",")[0]);
                    int yFinal = Integer.parseInt(positionFinal.split(",")[1]);

                    joue = this.plateau.bougerUneCarte(carteABouger, xFinal, yFinal);

                    if (!joue) {
                        // System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
                        // boug�e");
                        System.out.println("La carte n'a pas �tait pos�e");
                    }

                } else {
                    joue = true;
                }
            }
        }
    }

    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (o instanceof VuePlateau) {
                if (arg.toString().equals("positionCoordPlacerCarte")) {
                    this.placerLaCarte("0,0", new Carte("a", "a", false, null, false));
                }
            }
        }
    }

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

    public void run() {
        // this.plateau.placerCarteCachee();
        // this.plateau.jouerModeNormal();

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
            this.fini = this.plateau.isFini(this.modeAvance);
        } while ((quitter == false));
        // this.shared.setListCoord(this.plateau.getListeCoord());
        // System.out.println("Bientot la revelation");
        // this.setProperty("controleur-devoiler-cartes");
        // this.setProperty("plateau-montrer-score-joueurs");
        System.exit(0);

    }

    private void piocher() {
        if (!this.modeAvance) {
            if ((!this.aUneCarteEnMain) && (!this.aJoue)) {
                // Carte carte = (this.plateau.getPaquet().getRandomCarte());
                // CarteNormal carte = new
                // CarteNormal(this.plateau.getPaquet().getRandomCarte());
                // CarteCachee carte = new
                // CarteCachee(this.plateau.getPaquet().getRandomCarte());
                // Carte carte = (Carte) carteCachee;
                System.out.println(plateau.getListJoueur().element().getNbCarteJoue());
                Carte carte;
                //if (plateau.getListJoueur().element().getNbCarteJoue() != 0) {
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
                //if (plateau.getListJoueur().element().getNbCarteJoue() != 0) {
                if (this.nbCarteJoue != 0) {
                    if ( (plateau.getListJoueur().element().getNbCarteJoue() <= 1) && (plateau.getListJoueur().element().getNbCarteEnMain() == 0) )  {
                    //if (this.nbCarteJoue == 1) {
                        this.plateau.donnerDeuxCarteAuJoueur(plateau.getListJoueur().element());
                        carte = new CarteNormal(this.plateau.getPaquet().getRandomCarte());
                    } else {
                        carte = new CarteNormal(this.plateau.getPaquet().getRandomCarte());
                    }
                } else {
                    carte = new CarteCachee(this.plateau.getPaquet().getRandomCarte());

                }

                /*
                 * this.shared.setCarte(carte);
                 * this.setProperty("controleur-montrer-carte-pioche");
                 */
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

    private void placer(int x, int y) {
        if ((this.aUneCarteEnMain) && (!this.aJoue)) {
            // this.inter.appuyer();
            // Si la carte a pu être poser on ne peut plus poser de carte durant ce tour
            if (this.plateau.poserUneCarte(this.shared.getCarte(), /* Integer.parseInt(commande[1]) */ x,
                    /* Integer.parseInt(commande[2]) */y)) {
                this.aUneCarteEnMain = false;
                this.aJoue = true;
                this.nbCarteJoue++;
            }

            // this.setProperty("controleur-montrer-les-cartes");

        }

    }

    private void bouger(int x1, int y1, int x2, int y2) {
        if ((this.aJoue) && (!this.aBouger)) {
            System.out.println("bouger");
            Carte carte = this.plateau.recupererCoord(x1, y1).getCarte();
            if (this.plateau.bougerUneCarte(carte, x2, y2)) {
                this.aBouger = true;
            }

        }
    }

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

            if (this.plateau.getListJoueur().element() instanceof JoueurIA) {
                this.tourOrdinateur = true;
                this.faireJouerOrdi();
            }
        }
    }

    private void choisirCarteAPlacer(int y) {

        // if input come from console
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
