package View;

import Controller.ControleurPlateau;
import Controller.*;
import Model.*;
import Shared.Shared;

import java.util.Scanner;

import java.beans.*;

public class VuePlateau extends Observable implements Observer, PropertyChangeListener {

    private Scanner scanner;
    private PropertyChangeSupport pcs;
    private Shared shared;

    public VuePlateau(Shared shared) {
        this.scanner = new Scanner(System.in);
        this.pcs = new PropertyChangeSupport(this);
        this.shared = shared;
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
            System.out.println(this.shared.getJoueur().toString() + " votre carte victoire est : " + this.shared.getJoueur().getCarteVictoire().toString());
        } else if (evt.getPropertyName().equals("plateau-montrer-carte-poser")) {
            System.out.println("vous avez piocher la carte "+ this.shared.getCarte());
        } else if (evt.getPropertyName().equals("plateau-demande-ou-poser")) {
            System.out.println("Ou voulez vous la poser ?");
        } else if (evt.getPropertyName().equals("plateau-montrer-la-carte")) {
            this.shared.getCoordonee().afficher();
            this.shared.getCoordonee().getCarte().afficher();
            System.out.println("------");
        } else if (evt.getPropertyName().equals("plateau-demande-bouger-carte")) {
            System.out.println("Voulez vous bouger une carte ?");
        } else if (evt.getPropertyName().equals("joueur-demande-bouger")) {
            this.shared.setString(this.demanderString());
        } else if (evt.getPropertyName().equals("plateau-demande-coord-carte-bouger-1")) {
            System.out.println("Donnez la coordonee de la carte que vous voulez bouger");
        } else if (evt.getPropertyName().equals("plateau-demande-coord-carte-bouger-2")) {
            System.out.println("Donnez la coordonee ou vous voulez placer cette carte");
        } else if (evt.getPropertyName().equals("plateau-montrer-score-joueur")) {
            System.out.println(this.shared.getJoueur().toString() + " " + this.shared.getJoueur().getCarteVictoire().toString() + " "+ this.shared.getIntShared());
        }
    }

    public String demanderString() {
        System.out.print("> ");
        return this.scanner.nextLine();
    }

}
