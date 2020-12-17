package Vue;


import Controler.ControleurPlateau;
import Controler.*;
import Model.*;
import java.util.Scanner;

public class VuePlateau extends Observable implements Observer {

    private Scanner scanner;

    public VuePlateau() {
        this.scanner = new Scanner(System.in);
    }

    public void update(Observable o, Object arg) {
        if (o instanceof ControleurPlateau) {
            if (arg != null) {
                if (arg.toString().equals("choisirCoord")) {
                    System.out.println("Veullez choisir une coordonne pour placer cette carte");
                }
            } else {
                System.out.println("Vous avez piochez une carte");
            }

        } else if (o instanceof Coordonee) {
            System.out.println("Une carte a été placée sur une coordonnee");
        } else if (o instanceof Joueur) {
            System.out.print("> ");
            String positionPoser = this.scanner.nextLine();
            super.setChanged();

            //envoyer les inormation necessaire pour que le controleur puisse demander au module de poser la carte
            super.notifyObservers(new Object () {
                public String toString() {
                    return "positionCoordPlacerCarte";
                }
            });
        }
    }

    public static void main(String[] args) {
        VuePlateau vuePlat = new VuePlateau();

        Plateau plat = new Plateau(vuePlat);

        ControleurPlateau controleur = new ControleurPlateau(plat, vuePlat);

        vuePlat.addObserver(controleur);

        // plat.placerCarteCachee();

        // plat.jouerModeNormal();
        controleur.controlerJeu();
    }
}
