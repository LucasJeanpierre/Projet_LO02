package View;

import Controller.ControleurPlateau;
import Controller.*;
import Model.*;
import Shared.Shared;

import java.util.Scanner;

import java.beans.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class VuePlateau extends Observable implements Observer, PropertyChangeListener {

    private JFrame frame;
    private JButton boutton_piocher;
    private JLabel nom_joueur;
    private JButton changer_joueur;

    private Scanner scanner;
    private PropertyChangeSupport pcs;
    private Shared shared;

    public VuePlateau(Shared shared) {
        this.scanner = new Scanner(System.in);
        this.pcs = new PropertyChangeSupport(this);
        this.shared = shared;
        initialize();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public void addObserver(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }

    public void setProperty(String name) {
        this.pcs.firePropertyChange(name, 0, 1);
    }

    public void update(Observable o, Object arg) {

    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("plateau-revelerCarte")) {
            System.out.println("Une carte cachee a ete revele");
            this.shared.getCarteCachee().reveler();
        } else if (evt.getPropertyName().equals("plateau-question-ou-poser-carte-cachee")) {
            System.out.println("Ou voulez vous poser cette carte ?");
        } else if (evt.getPropertyName().equals("joueur-demande-coord")) {
            this.shared.setString(this.demanderString());
        } else if (evt.getPropertyName().equals("plateau-donner-carte-victoire")) {
            System.out.println(this.shared.getJoueur().toString() + " votre carte victoire est : "
                    + this.shared.getJoueur().getCarteVictoire().toString());
        } else if (evt.getPropertyName().equals("plateau-montrer-carte-poser")) {
            System.out.println("vous avez piocher la carte " + this.shared.getCarte());
        } else if (evt.getPropertyName().equals("plateau-demande-ou-poser")) {
            System.out.println("Ou voulez vous la poser ?");
        } else if (evt.getPropertyName().equals("plateau-montrer-la-carte")) {
            this.shared.getCoordonee().afficher();
            this.shared.getCoordonee().getCarte().afficher();
            System.out.println("------");
            this.shared.getCoordonee().getCarte().setImageCoord(this.shared.getCoordonee().getPositionX() * 100,
                    this.shared.getCoordonee().getPositionY() * 100);
            this.shared.getCoordonee().getCarte().getImage().setVisible(true);
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
        } else if (evt.getPropertyName().equals("controleur-montrer-carte-pioche")) {
            System.out.println("Vous avez pioche une carte");
            System.out.println(this.shared.getCarte().toString());

            this.shared.getCarte().setImageCoord(200, 300);
            this.shared.getCarte().getImage().setVisible(true);
        } else if (evt.getPropertyName().equals("plateau-changement-de-tour")) {
            System.out.println("---------------------");
            System.out.println("Changement de tour");
            System.out.println("Il reste : " + this.shared.getIntShared() + " cartes dans le paquet");
            System.out.println("---------------------");
            System.out.println("C'est au tour de " + this.shared.getJoueur());

            this.nom_joueur.setText(this.shared.getJoueur().toString());
        }
    }

    public JButton getButtonPiocher() {
        return this.boutton_piocher;
    }

    public JButton getButtonChanger() {
        return this.changer_joueur;
    }

    public void initialize() {
        this.frame = new JFrame();
        this.frame.setBounds(50, 50, 500, 500);

        this.boutton_piocher = new JButton("Piocher");
        this.boutton_piocher.setBounds(20, 300, 125, 25);
        this.frame.getContentPane().add(this.boutton_piocher);

        this.nom_joueur = new JLabel("Joueur : ");
        this.nom_joueur.setBounds(20, 350, 125, 75);
        this.frame.getContentPane().add(this.nom_joueur);

        this.changer_joueur = new JButton("Fin du tour");
        this.changer_joueur.setBounds(300,300,125,25);
        this.frame.getContentPane().add(this.changer_joueur);

        /*
         * panel = new ImagePanel("carre_rouge_plein"); panel.setBounds(0,700,50,50);
         * panel.setVisible(true); this.frame.getContentPane().add(panel);
         */

        /*
         * panel2 = new ImagePanel("carre_bleu_plein"); panel2.setBounds(10,250,50,50);
         * panel2.setVisible(true); this.frame.getContentPane().add(panel2);
         */

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
