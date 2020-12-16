package Controler;

import java.util.Scanner;

import Model.*;

import java.util.Iterator;

public class ControleurPlateau {

    private Scanner scanner;
    private Plateau plateau;
    private Paquet paquet;

    public ControleurPlateau(Plateau plateau) {
        this.scanner = new Scanner(System.in);
        this.plateau = plateau;
        this.paquet = this.plateau.getPaquet();
    }

    public void demanderString() {
        System.out.print("> ");
        String positionBouger = this.scanner.nextLine();
    }

    public void controlerJeu() {
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

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        Plateau plat = new Plateau();

        // on place les carte cachées
        Iterator<Joueur> it = plat.getListJoueur().iterator();

        while (it.hasNext()) {
            Joueur joueur = it.next();
            // chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
            // aura donc le choix entre 3 cartes a chaque tours

            CarteCachee carteCachee = new CarteCachee(plat.getPaquet().getRandomCarte());
            joueur.piocherUneCarte(carteCachee);

            System.out.println(joueur.toString() + " vous avez pioché une carte cachée");
            carteCachee.reveler();

            boolean joue = false;

            while (!joue) {

                System.out.println("Ou voulez vous la poser ?");
                // String positionPoser = scanner.nextLine();
                String positionPoser = joueur.choisirCoordoneeAPlacer(carteCachee);
                // System.out.println("Bonsoir a tous");

                try {
                    int x = Integer.parseInt(positionPoser.split(",")[0]);
                    int y = Integer.parseInt(positionPoser.split(",")[1]);
                    joue = plat.poserUneCarte(carteCachee, x, y);
                } catch (Exception Error) {
                    System.out.println("Veuillez saisir des coordonee valides");
                }

                if (!joue) {
                    System.out.println("L'emplacement n'�tait pas vide");
                } else {
                    joueur.poserUneCarte(carteCachee);
                }
            }
        }

        plat.changementDeTour();

        plat.jouerModeNormal();
        // plat.jouerModeAvance();

    }

}
