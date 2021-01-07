package View;

import Controller.ControleurPlateau;
import Controller.*;
import Model.*;
import Shared.Shared;

import java.util.Scanner;
import java.util.Iterator;

import java.beans.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Class VuePlateau
 */

public class VuePlateau extends Observable implements Observer, PropertyChangeListener {

    private JFrame frame;
    private JButton boutton_piocher;
    private JLabel nom_joueur;
    private JButton changer_joueur;
    private JButton boutton_bouger;

    private Scanner scanner;
    private PropertyChangeSupport pcs;
    private Shared shared;

    private ImagePanel panel1;
    private ImagePanel panel2;
    private ImagePanel panel3;

    private JLabel label_panel1;
    private JLabel label_panel2;
    private JLabel label_panel3;

    private JPanel cartepioche1;
    private JPanel cartepioche2;
    private JPanel cartepioche3;

    private int delta;
    private int size;

    private boolean modePlateauLibre;
    private boolean modeTroisJoueur;

    /**
     * Constructeur
     * 
     * @param shared
     * @param modeAvance
     * @param modePlateauLibre
     * @param modeTroisJoueur
     */
    public VuePlateau(Shared shared, boolean modeAvance, boolean modePlateauLibre, boolean modeTroisJoueur) {
        this.scanner = new Scanner(System.in);
        this.pcs = new PropertyChangeSupport(this);
        this.shared = shared;
        this.modePlateauLibre = modePlateauLibre;
        this.modeTroisJoueur = modeTroisJoueur;
        if (modePlateauLibre) {
            this.size = 50;
        } else {
            this.size = 100;
        }
        initialize(modeAvance, modePlateauLibre);
        this.frame.setVisible(true);
    }

    /**
     * Renvoie la frame de la vue
     * 
     * @return la frame
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Ajoute un Observer a la vue
     * 
     * @param l oberver
     */
    public void addObserver(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }

    /**
     * Notifie les obsersers
     * 
     * @param name le nom de la notification
     */
    public void setProperty(String name) {
        this.pcs.firePropertyChange(name, 0, 1);
    }

    public void update(Observable o, Object arg) {

    }

    /**
     * Equivalent method update pour le model observer/observable
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("plateau-revelerCarte")) {
            System.out.println("Une carte cachee a ete revele");
            this.shared.getCarteCachee().reveler();
        } else if (evt.getPropertyName().equals("plateau-question-ou-poser-carte-cachee")) {
            System.out.println("Ou voulez vous poser cette carte ?");
        } else if (evt.getPropertyName().equals("joueur-demande-coord")) {
            this.shared.setString(this.demanderString());
        } else if (evt.getPropertyName().equals("plateau-donner-carte-victoire")) {
            // System.out.println(this.shared.getJoueur().toString() + " votre carte
            // victoire est : "
            // + this.shared.getJoueur().getCarteVictoire().toString());
            Joueur j = this.shared.getJoueur();
            System.out.println(j.toString() + " votre carte victoire est : " + j.getCarteVictoire().toString());

            String tempnom;
            if (j.getCarteVictoire().isRempli()) {
                tempnom = "plein";
            } else {
                tempnom = "vide";
            }

            String name = j.getCarteVictoire().getForme() + "_" + j.getCarteVictoire().getCouleur() + "_" + tempnom;

            if (this.shared.getString().equals("0")) {
                this.panel1.changeImage(name);
                this.label_panel1.setText(j.getNom());
                this.shared.setString("1");
            } else if (this.shared.getString().equals("1")) {
                this.panel2.changeImage(name);
                this.label_panel2.setText(j.getNom());
                this.shared.setString("2");
            } else {
                this.panel3.changeImage(name);
                this.label_panel3.setText(j.getNom());
            }

        } else if (evt.getPropertyName().equals("plateau-montrer-carte-poser")) {
            System.out.println("vous avez piocher la carte " + this.shared.getCarte());
        } else if (evt.getPropertyName().equals("plateau-demande-ou-poser")) {
            System.out.println("Ou voulez vous la poser ?");
        } else if (evt.getPropertyName().equals("plateau-montrer-la-carte")) {
            this.shared.getCoordonee().afficher();
            this.shared.getCoordonee().getCarte().afficher();
            System.out.println("------");
            // this.shared.getCoordonee().getCarte().setImageCoord(this.shared.getCoordonee().getPositionX()
            // * 100,
            // this.shared.getCoordonee().getPositionY() * 100);
            // this.shared.getCarte().getImage().setVisible(false);
            this.shared.getCarte().setImageCoord(this.shared.getCoordonee().getPositionX() * this.size,
                    this.shared.getCoordonee().getPositionY() * this.size);

            // this.shared.getCarte().setImageCoord(20, 20);
            this.shared.getCarte().getImage().setVisible(true);
            // this.shared.getCoordonee().getPanel().setVisible(false);
            // this.frame.getContentPane().add(this.shared.getCoordonee().getCarte().getImage());
            // this.frame.getContentPane().add(this.shared.getCoordonee().getCarte().getImage());;
            // SwingUtilities.updateComponentTreeUI(frame);
        } else if (evt.getPropertyName().equals("plateau-demande-bouger-carte")) {
            System.out.println("Voulez vous bouger une carte ?");
        } else if (evt.getPropertyName().equals("joueur-demande-bouger")) {
            this.shared.setString(this.demanderString());
        } else if (evt.getPropertyName().equals("plateau-demande-coord-carte-bouger-1")) {
            System.out.println("Donnez la coordonee de la carte que vous voulez bouger");
        } else if (evt.getPropertyName().equals("plateau-demande-coord-carte-bouger-2")) {
            System.out.println("Donnez la coordonee ou vous voulez placer cette carte");
        } else if (evt.getPropertyName().equals("plateau-montrer-score-joueur")) {
            System.out.println(this.shared.getJoueur().toString() + " "
                    + this.shared.getJoueur().getCarteVictoire().toString() + " " + this.shared.getIntShared());

            if (this.shared.getString().equals("0")) {
                this.label_panel1
                        .setText(this.shared.getJoueur().toString() + " score : " + this.shared.getIntShared());
            } else if (this.shared.getString().equals("1")) {
                this.label_panel2
                        .setText(this.shared.getJoueur().toString() + " score : " + this.shared.getIntShared());
            } else if (this.shared.getString().equals("2")) {
                this.label_panel3
                        .setText(this.shared.getJoueur().toString() + " score : " + this.shared.getIntShared());
            }

        } else if (evt.getPropertyName().equals("controleur-montrer-carte-pioche")) {
            System.out.println("Vous avez pioche une carte");
            System.out.println(this.shared.getCarte().toString());

            this.shared.getCarte().setImageCoord(200, 300 + this.delta);
            this.shared.getCarte().getImage().setVisible(true);
        } else if (evt.getPropertyName().equals("plateau-changement-de-tour")) {
            System.out.println("---------------------");
            System.out.println("Changement de tour");
            System.out.println("Il reste : " + this.shared.getIntShared() + " cartes dans le paquet");
            System.out.println("---------------------");
            System.out.println("C'est au tour de " + this.shared.getJoueur());

            this.nom_joueur.setText(this.shared.getJoueur().toString());
        } else if (evt.getPropertyName().equals("plateau-devoiler-cartes")) {
            System.out.println("Revelation---------------");

            Iterator<Coordonee> it = this.shared.getListCoord().iterator();

            while (it.hasNext()) {
                Coordonee coord = it.next();
                if (coord.getCarte() != null) {
                    Carte carte = coord.getCarte();
                    if (carte instanceof CarteCachee) {
                        CarteCachee carteCachee = (CarteCachee) carte;
                        carteCachee.setImageDevoileCoord(coord.getPositionX() * this.size,
                                coord.getPositionY() * this.size);
                        carteCachee.getImageDevoile().setVisible(true);
                        carteCachee.getImage().setVisible(false);
                        coord.getPanel().setVisible(false);

                        coord.afficher();
                        carteCachee.reveler();
                    } else {
                        carte.setImageCoord(coord.getPositionX() * this.size, coord.getPositionY() * this.size);
                        carte.getImage().setVisible(true);
                        coord.getPanel().setVisible(false);

                        coord.afficher();
                        carte.afficher();
                    }
                }
            }

        } else if (evt.getPropertyName().equals("joueur-montrer-la-main")) {
            Iterator<Carte> it = this.shared.getListCarte().iterator();
            int y = 300;

            while (it.hasNext()) {
                Carte carte = it.next();
                System.out.println(carte);

                carte.setImageCoord(200, y + this.delta);
                carte.getImage().setVisible(true);
                y += 50;
            }

        }
    }

    /**
     * @return bouttonPiocher
     */
    public JButton getButtonPiocher() {
        return this.boutton_piocher;
    }

    /**
     * @return bouttonChanger
     */
    public JButton getButtonChanger() {
        return this.changer_joueur;
    }

    /**
     * @return bouttonBouger
     */
    public JButton getButtonBouger() {
        return this.boutton_bouger;
    }

    /**
     * 
     * @return l'emplacement 1 pour le choix des cartes en mode Avance
     */
    public JPanel getCartePioche1() {
        return this.cartepioche1;
    }

    /**
     * 
     * @return l'emplacement 2 pour le choix des cartes en mode Avance
     */
    public JPanel getCartePioche2() {
        return this.cartepioche2;
    }

    /**
     * 
     * @return l'emplacement 2 pour le choix des cartes en mode Avance
     */
    public JPanel getCartePioche3() {
        return this.cartepioche3;
    }

    /**
     * Method initialize
     * Creer et ajoutes les différent élément visible de la vue
     * @param modeAvance
     * @param modePlateauLibre
     */
    public void initialize(boolean modeAvance, boolean modePlateauLibre) {
        this.frame = new JFrame();
        this.frame.setBounds(50, 50, 500, 700);

        if (!modePlateauLibre) {
            this.delta = 0;
        } else {
            this.delta = 200;
        }

        this.boutton_piocher = new JButton("Piocher");
        this.boutton_piocher.setBounds(20, 300 + delta, 125, 25);
        this.frame.getContentPane().add(this.boutton_piocher);

        this.nom_joueur = new JLabel("Joueur : ");
        this.nom_joueur.setBounds(20, 350 + delta, 125, 75);
        this.frame.getContentPane().add(this.nom_joueur);

        this.changer_joueur = new JButton("Fin du tour");
        this.changer_joueur.setBounds(300, 300 + delta, 125, 25);
        this.frame.getContentPane().add(this.changer_joueur);

        this.panel1 = new ImagePanel("vide", this.modePlateauLibre);
        this.panel1.setBounds(20, 500 + delta, 100, 100);
        this.frame.getContentPane().add(this.panel1);

        this.panel2 = new ImagePanel("vide", this.modePlateauLibre);
        this.panel2.setBounds(300, 500 + delta, 100, 100);
        this.frame.getContentPane().add(this.panel2);

        this.label_panel1 = new JLabel("Joueur1");
        this.label_panel1.setBounds(20, 480 + delta, 300, 25);
        this.frame.getContentPane().add(this.label_panel1);

        this.label_panel2 = new JLabel("Joueur2");
        this.label_panel2.setBounds(300, 480 + delta, 300, 25);
        this.frame.getContentPane().add(this.label_panel2);

        if (this.modeTroisJoueur) {

            this.panel3 = new ImagePanel("vide", this.modePlateauLibre);
            this.panel3.setBounds(150, 550 + delta, 100, 100);
            this.frame.getContentPane().add(this.panel3);

            this.label_panel3 = new JLabel("Joueur3");
            this.label_panel3.setBounds(150, 530 + delta, 300, 25);
            this.frame.getContentPane().add(this.label_panel3);
        }

        this.boutton_bouger = new JButton("Bouger");
        this.boutton_bouger.setBounds(20, 330 + delta, 125, 25);
        this.frame.getContentPane().add(this.boutton_bouger);
        /*
         * panel = new ImagePanel("carre_rouge_plein"); panel.setBounds(0,700,50,50);
         * panel.setVisible(true); this.frame.getContentPane().add(panel);
         */

        /*
         * panel2 = new ImagePanel("carre_bleu_plein"); panel2.setBounds(10,250,50,50);
         * panel2.setVisible(true); this.frame.getContentPane().add(panel2);
         */

        // emplacement pour le choix des carte dans le mode avance
        if (modeAvance) {
            this.cartepioche1 = new JPanel();
            this.cartepioche1.setBounds(200, 300 + this.delta, this.size / 2, this.size / 2);
            this.cartepioche1.setBackground(new Color(0, 0, 0, 25));
            this.frame.getContentPane().add(this.cartepioche1);

            this.cartepioche2 = new JPanel();
            this.cartepioche2.setBounds(200, 350 + this.delta, this.size / 2, this.size / 2);
            this.cartepioche2.setBackground(new Color(0, 0, 0, 25));
            this.frame.getContentPane().add(this.cartepioche2);

            this.cartepioche3 = new JPanel();
            this.cartepioche3.setBounds(200, 400 + this.delta, this.size / 2, this.size / 2);
            this.cartepioche3.setBackground(new Color(0, 0, 0, 25));
            this.frame.getContentPane().add(this.cartepioche3);
        }

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setLayout(null);

    }

    public String demanderString() {
        System.out.print("> ");
        return this.scanner.nextLine();
    }
    /*
     * public static void main(String[] args) { EventQueue.invokeLater(new
     * Runnable() { public void run() { try { VuePlateau vuePlateau = new
     * VuePlateau(new Shared()); //start(plateau, shared, vuePlateau); } catch
     * (Exception e) { e.printStackTrace(); } } }); }
     */

}
