package View;


import Controller.ControleurPlateau;
import Controller.*;
import Model.*;
import java.util.Scanner;

import java.beans.*;

public class VuePlateau extends Observable implements Observer, PropertyChangeListener {

    private Scanner scanner;
    private PropertyChangeSupport pcs;

    public VuePlateau() {
        this.scanner = new Scanner(System.in);
        this.pcs = new PropertyChangeSupport(this);
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
        //if (evt.getPropertyName().equals("revelerCarte")) {
            System.out.println("Une carte cachee a ete revele");
        //}
    }

    
    public String demanderString() {
        System.out.print("> ");
        return this.scanner.nextLine();
    }

}
