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
        }
    }

    public String demanderString() {
        System.out.print("> ");
        return this.scanner.nextLine();
    }

}
