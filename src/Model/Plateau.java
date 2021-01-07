package Model;

import java.util.*;
import java.beans.*;
import Shared.Shared;
import View.VuePlateau;


/**
 * Class Plateau
 */
public class Plateau implements ObjetVisite, PropertyChangeListener {

	// private String aQuiLeTour;

	// temporaire
	private boolean formePlateauLibre = false;

	private ArrayList<Coordonee> listeCoord;
	private Queue<Joueur> listeJoueur;
	private Paquet paquet;

	private PropertyChangeSupport pcs;
	private Shared shared;
	private boolean modePlateauLibre;
	private boolean modeTroisJoueur;

	/**
	 * Ajoute un observer
	 * @param l l'observer
	 */
	public void addObserver(PropertyChangeListener l) {
		this.pcs.addPropertyChangeListener(l);
	}

	/**
	 * notifie les observers
	 * @param name le nom de la notification
	 */
	public void setProperty(String name) {
		this.pcs.firePropertyChange(name, 0, 1);
	}

	public void propertyChange(PropertyChangeEvent evt) {

	}

	/**
	 * Accepte la viste du visiteur
	 * @param v le visiteur
	 */
	public void acceptVisit(Visiteur v) {
		v.visit(this);
	}

	/**
	 * Pose une carte sur le plateau
	 * @param carte la carte a poser
	 * @param x la coord x
	 * @param y la coord y
	 * 
	 * @return la carte a bien été posé (boolean)
	 */
	public boolean poserUneCarte(Carte carte, int x, int y) {
		// recup�re la coordonee correspondante a la position demand�
		// appel la m�thode poser une carte de la coordonee qui va associ� l'attribut
		// carte de la coordonne a l'objet carte donn� en argument

		/*
		 * if (carte instanceof CarteCachee) { // si la coordonee est valide on cherche
		 * l'objet coordonee qui correspond if ((recupererCoord(x, y) != null)) {
		 * Coordonee coord = recupererCoord(x, y);
		 * 
		 * // si l'emplacement ou pose la carte est vide if (coord.getCarte() == null) {
		 * coord.poserUneCarte(carte); this.montrerLesCartesPosees(); return true; }
		 * else { return false; } } else { // sinon on revoie false pour que la fonction
		 * ne s'est terminier coorectement return false; } } else {
		 */

		// si la coordonee est valide on cherche l'objet coordonee qui correspond
		if ((recupererCoord(x, y) != null) && (isAdjacent(x, y))) {
			Coordonee coord = recupererCoord(x, y);

			// si l'emplacement ou pose la carte est vide
			if (coord.getCarte() == null) {
				coord.poserUneCarte(carte);
				listeJoueur.element().poserUneCarte(carte);
				this.montrerLesCartesPosees();
				return true;
			} else {
				return false;
			}
		} else { // sinon on revoie false pour que la fonction ne s'est terminier coorectement
			return false;
		}
		// }

	}

	/**
	 * Renvoie la liste des coordonee du plateau
	 * @return liste de coordonee
	 */
	public ArrayList<Coordonee> getListeCoord() {
		return this.listeCoord;
	}

	/**
	 * Renvoie la liste des joueurs
	 * @return liste de joueurs
	 */
	public Queue<Joueur> getListJoueur() {
		return this.listeJoueur;
	}

	/**
	 * Revoie un boolean pour savoir si l'on peut poser une carte a une coordonee
	 * @param carte la carte a poser
	 * @param x coord x
	 * @param y coord y
	 */
	public boolean peutPoserUneCarte(Carte carte, int x, int y) {
		if (carte instanceof CarteCachee) {
			// si la coordonee est valide on cherche l'objet coordonee qui correspond
			if ((recupererCoord(x, y) != null)) {
				Coordonee coord = recupererCoord(x, y);

				// si l'emplacement ou pose la carte est vide
				if (coord.getCarte() == null) {
					return true;
				} else {
					return false;
				}
			} else { // sinon on revoie false pour que la fonction ne s'est terminier coorectement
				return false;
			}
		} else {

			// si la coordonee est valide on cherche l'objet coordonee qui correspond
			if ((recupererCoord(x, y) != null) && (isAdjacent(x, y))) {
				Coordonee coord = recupererCoord(x, y);

				// si l'emplacement ou pose la carte est vide
				if (coord.getCarte() == null) {
					return true;
				} else {
					return false;
				}
			} else { // sinon on revoie false pour que la fonction ne s'est terminier coorectement
				return false;
			}
		}
	}

	/**
	 * Renvoie un boolean en fonction de si la coordonee est adjacente a une carte
	 * @param x coord x
	 * @param y coord y
	 * @return vrai si adjacent et faux si non
	 */
	public boolean isAdjacent(int x, int y) {

		if (this.getNombreDeCarteEnJeu() == 0) {
			return true;
		}

		// regle de l'adjacence
		if (this.recupererCoord(x - 1, y) != null) {
			if (this.recupererCoord(x - 1, y).getCarte() != null) {
				return true;
			}
		}
		if (this.recupererCoord(x + 1, y) != null) {
			if (this.recupererCoord(x + 1, y).getCarte() != null) {
				return true;
			}
		}
		if (this.recupererCoord(x, y - 1) != null) {
			if (this.recupererCoord(x, y - 1).getCarte() != null) {
				return true;
			}
		}
		if (this.recupererCoord(x, y + 1) != null) {
			if (this.recupererCoord(x, y + 1).getCarte() != null) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Permet de decaler toutes les cartes du plateau
	 * @param deltax le decalage selon x
	 * @param deltay le decalage selon y
	 */
	public void decalerPlateau(int deltax, int deltay) {
		// on parcour le plateau en partant le la fin pour ne pas elimnier des pi�ces

		if ((deltay == 1) && (deltax == 0)) {
			for (int x = 0; x < 5; x++) {
				for (int y = 2; y >= 0; y--) {
					Coordonee coord = this.recupererCoord(x, y);
					if (coord.getCarte() != null) {
						Carte carte = coord.getCarte();
						Coordonee newcoord = this.recupererCoord(coord.getPositionX() + deltax,
								coord.getPositionY() + deltay);
						if (newcoord != null) {
							coord.enleverLaCarte();
							newcoord.poserUneCarte(carte);
						}
					}
				}
			}
		} else if ((deltay == -1) && (deltax == 0)) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y <= 2; y++) {
					Coordonee coord = this.recupererCoord(x, y);
					if (coord.getCarte() != null) {
						Carte carte = coord.getCarte();
						Coordonee newcoord = this.recupererCoord(coord.getPositionX() + deltax,
								coord.getPositionY() + deltay);
						if (newcoord != null) {
							coord.enleverLaCarte();
							newcoord.poserUneCarte(carte);
						}
					}
				}
			}
		}

		else if ((deltay == 0) && (deltax == -1)) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y <= 2; y++) {
					Coordonee coord = this.recupererCoord(x, y);
					if (coord.getCarte() != null) {
						Carte carte = coord.getCarte();
						Coordonee newcoord = this.recupererCoord(coord.getPositionX() + deltax,
								coord.getPositionY() + deltay);
						if (newcoord != null) {
							coord.enleverLaCarte();
							newcoord.poserUneCarte(carte);
						}
					}
				}
			}
		}

		else if ((deltay == 0) && (deltax == 1)) {
			for (int x = 4; x >= 0; x--) {
				for (int y = 0; y <= 2; y++) {
					Coordonee coord = this.recupererCoord(x, y);
					if (coord.getCarte() != null) {
						Carte carte = coord.getCarte();
						Coordonee newcoord = this.recupererCoord(coord.getPositionX() + deltax,
								coord.getPositionY() + deltay);
						if (newcoord != null) {
							coord.enleverLaCarte();
							newcoord.poserUneCarte(carte);
						}
					}
				}
			}
		}

		/*
		 * Iterator<Coordonee> it = listeCoord.iterator();
		 * 
		 * while(it.hasNext()) { Coordonee coord = it.next(); if (coord.getCarte() !=
		 * null) { Carte carte = coord.getCarte(); Coordonee newcoord =
		 * this.recupererCoord(coord.getPositionX()+x,coord.getPositionY()+y); if
		 * (newcoord != null) { coord.enleverLaCarte(); newcoord.poserUneCarte(carte); }
		 * } }
		 */

	}

	/**
	 * Bouge une carte aux position en parametres
	 * @param carte la carte a bouger
	 * @param x coord x
	 * @param y coord y
	 * @return vrai si la carte a ete bouger sinon faux
	 */
	public boolean bougerUneCarte(Carte carte, int x, int y) {

		// on enleve d'abord la carte de la ou elle �tait
		if (trouverCoordoneeDUneDarte(carte) != null) {
			Coordonee oldCoord = trouverCoordoneeDUneDarte(carte);
			// puis on la place au nouvelle endroit
			Coordonee coord = recupererCoord(x, y);
			//oldCoord.enleverLaCarte();
			if (oldCoord.enleverLaCarte() && (coord.getCarte() == null)) {

				// si l'emplacement ou bouge la carte est vide
				//oldCoord.enleverLaCarte();
				if (this.peutPoserUneCarte(carte, x, y)) {
					coord.poserUneCarte(carte);
				} else {
					oldCoord.poserUneCarte(carte);
					return false;
				}
				this.montrerLesCartesPosees();
				oldCoord.getPanel().setVisible(true);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * Montre les cartes poser sur le plateau
	 */
	public void montrerLesCartesPosees() {
		Iterator<Coordonee> it = listeCoord.iterator();

		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() != null) {
				// System.out.println(coord.getCarte().toString());
				// System.out.println(coord.toString());
				// coord.getCarte().afficher();
				// coord.afficher();
				// System.out.println("--------------");
				this.shared.setCoordonee(coord);
				this.shared.setCarte(coord.getCarte());
				this.setProperty("plateau-montrer-la-carte");
			} else {
				// this.setProperty("plateau-montrer-le-emplacement");
			}
		}

	}

	/**
	 * revoie vrai si le plateau est rempli sinon faux
	 * @return boolean
	 */
	public boolean isPlateauRempli() {
		Iterator<Coordonee> it = listeCoord.iterator();

		// parcour les coordonee jusqu'a ce qu'il trouve celle correspondante
		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Renvoie le nombre de carte posee
	 * @return le nombre de carte posee
	 */
	public int nbCartePosee() {
		int nb = 0;

		Iterator<Coordonee> it = listeCoord.iterator();

		// parcour les coordonee jusqu'a ce qu'il trouve celle correspondante
		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() != null) {
				nb++;
			}
		}
		return nb;
	}

	// permet de r�cup�rer un objet coordon�e a partir de sa position
	/**
	 * Recupère l'objet coordonee en fonction de sa postion
	 * @param x coord x
	 * @param y coord y
	 * @return la coord demande
	 */
	public Coordonee recupererCoord(int x, int y) {
		Iterator<Coordonee> it = listeCoord.iterator();

		// parcour les coordonee jusqu'a ce qu'il trouve celle correspondante
		while (it.hasNext()) {
			Coordonee coord = it.next();
			if ((coord.getPositionX() == x) && (coord.getPositionY() == y)) {
				return coord;
			}
		}
		return null;
	}

	/**
	 * Renvoie la coordonnee d'un carte
	 * @param carte la carte d'on on chercher la coord
	 * @return
	 */
	private Coordonee trouverCoordoneeDUneDarte(Carte carte) {
		Iterator<Coordonee> it = listeCoord.iterator();

		// parcour les coordonee jusqu'a ce qu'il trouve celle correspondante
		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() == carte) {
				return coord;
			}
		}
		return null;
	}

	public void listePositionCarte() {

	}

	// renvoie le nombre de carte pos�es sur la plateau
	/**
	 * @return le nombre de carte en jeu
	 */
	public int getNombreDeCarteEnJeu() {
		int nombreDeCarte = 0;
		Iterator<Coordonee> it = listeCoord.iterator();

		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() != null) {
				nombreDeCarte++;
			}
		}

		return nombreDeCarte;
	}

	/**
	 * @param carteVictoire la carteVictoire d'on on veut le score
	 * @return les points marquer par une carte victoire
	 * 
	 */
	public int compterLesPoints(Carte carteVictoire) {
		// on r�cup�re les caract�ristique de la carte victoire
		String forme = carteVictoire.getForme();
		String couleur = carteVictoire.getCouleur();
		boolean rempli = carteVictoire.isRempli();

		int nbRecurenceForme = 0;
		int nbRecurenceCouleur = 0;
		int nbRecurenceRempli = 0;

		int nbRecurenceFormeMax = 0;
		int nbRecurenceCouleurMax = 0;
		int nbRecurenceRempliMax = 0;

		int score = 0;

		// test des point sur la premi�re ligne
		// pour chaque carte de la ligne on regarde si elle correspond a une
		// caract�ritique de la carte victoire
		// si oui on incr�mente le nombre de carte d'affil� qui ont cette caract�ritique
		// si non on repart de 0
		// a la fin on regarde quel est le maximum de nbReference ce qui d�termine le
		// nombre de carte d'affill� possedant une caract�ritique de la carte victoire

		// calcul du score pour toute les lignes
		for (int y = 0; y < 3; y++) {
			nbRecurenceForme = 0;
			nbRecurenceCouleur = 0;
			nbRecurenceRempli = 0;

			nbRecurenceFormeMax = 0;
			nbRecurenceCouleurMax = 0;
			nbRecurenceRempliMax = 0;

			for (int x = 0; x < 5; x++) {
				Coordonee coord = recupererCoord(x, y);
				Carte carte = coord.getCarte();

				// calcul pour les formes
				if (carte.getForme() == forme) {
					nbRecurenceForme++;
					if (nbRecurenceForme > nbRecurenceFormeMax) {
						nbRecurenceFormeMax = nbRecurenceForme;
					}
				} else {
					nbRecurenceForme = 0;
				}

				if (carte.getCouleur() == couleur) {
					nbRecurenceCouleur++;
					if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
						nbRecurenceCouleurMax = nbRecurenceCouleur;
					}
				} else {
					nbRecurenceCouleur = 0;
				}

				if (carte.isRempli() == rempli) {
					nbRecurenceRempli++;
					if (nbRecurenceRempli > nbRecurenceRempliMax) {
						nbRecurenceRempliMax = nbRecurenceRempli;
					}
				} else {
					nbRecurenceRempli = 0;
				}
			}

			/*
			 * System.out.println("forme  :" +nbRecurenceFormeMax);
			 * System.out.println("couleur : "+nbRecurenceCouleurMax);
			 * System.out.println("rempli : "+nbRecurenceRempliMax);
			 */

			if (nbRecurenceFormeMax > 1) {
				score += nbRecurenceFormeMax - 1;
			}

			if (nbRecurenceCouleurMax > 2) {
				score += nbRecurenceCouleurMax + 1;
			}

			if (nbRecurenceRempliMax > 2) {
				score += nbRecurenceRempliMax;
			}
			/* System.out.println("score : " + score); */
		}

		// calculer score colonnes

		for (int x = 0; x < 5; x++) {
			nbRecurenceForme = 0;
			nbRecurenceCouleur = 0;
			nbRecurenceRempli = 0;

			nbRecurenceFormeMax = 0;
			nbRecurenceCouleurMax = 0;
			nbRecurenceRempliMax = 0;

			for (int y = 0; y < 3; y++) {
				Coordonee coord = recupererCoord(x, y);
				Carte carte = coord.getCarte();

				// calcul pour les formes
				if (carte.getForme() == forme) {
					nbRecurenceForme++;
					if (nbRecurenceForme > nbRecurenceFormeMax) {
						nbRecurenceFormeMax = nbRecurenceForme;
					}
				} else {
					nbRecurenceForme = 0;
				}

				if (carte.getCouleur() == couleur) {
					nbRecurenceCouleur++;
					if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
						nbRecurenceCouleurMax = nbRecurenceCouleur;
					}
				} else {
					nbRecurenceCouleur = 0;
				}

				if (carte.isRempli() == rempli) {
					nbRecurenceRempli++;
					if (nbRecurenceRempli > nbRecurenceRempliMax) {
						nbRecurenceRempliMax = nbRecurenceRempli;
					}
				} else {
					nbRecurenceRempli = 0;
				}
			}

			/*
			 * System.out.println("forme  :" +nbRecurenceFormeMax);
			 * System.out.println("couleur : "+nbRecurenceCouleurMax);
			 * System.out.println("rempli : "+nbRecurenceRempliMax);
			 */

			if (nbRecurenceFormeMax > 1) {
				score += nbRecurenceFormeMax - 1;
			}

			if (nbRecurenceCouleurMax > 2) {
				score += nbRecurenceCouleurMax + 1;
			}

			if (nbRecurenceRempliMax > 2) {
				score += nbRecurenceRempliMax;
			}
			/* System.out.println("score : " + score); */
		}

		return score;
	}

	/**
	 * Change de joueur
	 */
	public void changementDeTour() {

		Joueur dernierJoueur = listeJoueur.element();
		listeJoueur.remove();
		listeJoueur.add(dernierJoueur);

		this.shared.setJoueur(this.listeJoueur.element());
		this.shared.setIntShared(this.paquet.getNombreDeCarte());
		this.setProperty("plateau-changement-de-tour");
	}

	/**
	 * @return la somme des cartes en main des joueurs
	 */
	public int nombreDeCarteDansMains() {
		int nombre = 0;
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			nombre += joueur.getMain().size();
		}

		return nombre;
	}

	/**
	 * @return le paquet de carte
	 */
	public Paquet getPaquet() {
		return this.paquet;
	}


	/**
	 * Constructeur du Plateau
	 * @param shared l'objet partage
	 * @param vuePlateau la vue
	 * @param modePlateauLibre mode de jeu
	 * @param modeTroisJoueur mode de jeu
	 * 
	 * le constructeur creer les, les coordonee et les cartes
	 */
	public Plateau(Shared shared, VuePlateau vuePlateau, boolean modePlateauLibre, boolean modeTroisJoueur) {
		// cr�ation du paquet
		this.shared = shared;
		this.modePlateauLibre = modePlateauLibre;
		this.modeTroisJoueur = modeTroisJoueur;
		this.paquet = new Paquet(vuePlateau, modePlateauLibre);
		this.listeJoueur = new LinkedList<Joueur>();

		// listeJoueur.add(new Joueur("Patrick"));
		// listeJoueur.add(new Joueur("Julien", this.shared));
		// listeJoueur.add(new JoueurIA(this, this.shared));

		if (!modeTroisJoueur) {
			Joueur j1 = new Joueur("Joueur 1", this.shared);
			Joueur j2 = new Joueur("Joueur 2", this.shared);
			Joueur ordi = new JoueurIA(this, this.shared);
	
			listeJoueur.add(j1);
			//listeJoueur.add(j2);
			listeJoueur.add(ordi);
		} else {
			Joueur j1 = new Joueur("Joueur 1", this.shared);
			Joueur j2 = new Joueur("Joueur 2", this.shared);
			Joueur j3 = new Joueur("Joueur 3", this.shared);
			Joueur ordi = new JoueurIA(this, this.shared);
	
			listeJoueur.add(j1);
			listeJoueur.add(j2);
			listeJoueur.add(j3);
			//listeJoueur.add(ordi);
		}

		

		// System.out.println(listeJoueur.element().toString());

		this.listeCoord = new ArrayList<Coordonee>();

		// le pcs
		this.pcs = new PropertyChangeSupport(this);

		// Cr�ation du plateau
		int nblong;
		int nblarge;

		/*
		 * if (!this.formePlateauLibre) { nblong = 5; nblarge = 3; } else { nblong = 16;
		 * nblarge = 16; }
		 */

		if (modePlateauLibre) {
			nblong = 10;
			nblarge = 10;
		} else {
			nblong = 5;
			nblarge = 3;
		}

		for (int x = 0; x < nblong; x++) {
			for (int y = 0; y < nblarge; y++) {
				listeCoord.add(new Coordonee(x, y, vuePlateau.getFrame(), modePlateauLibre));
			}
		}

	}

	/**
	 * Donne 2 cartes a	chaque joueurs
	 */ 
	public void donnerDeuxCarteAuxJoueurs() {
		// distribution des cartes pour le mode avance
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			for (int i = 0; i < 2; i++) {
				joueur.piocherUneCarte(this.paquet.getRandomCarte());
			}
		}
	}

	/**
	 * Donne 2 carte a un joueur
	 * @param joueur
	 */
	public void donnerDeuxCarteAuJoueur(Joueur joueur) {
		for (int i = 0; i < 2; i++) {
			joueur.piocherUneCarte(new CarteNormal(this.paquet.getRandomCarte()));
		}
	}

	public void jouer(boolean modeAvance) {
		boolean joue = false;

		if (modeAvance) {
			// distribution des cartes pour le mode avance
			Iterator<Joueur> it = this.listeJoueur.iterator();

			while (it.hasNext()) {
				Joueur joueur = it.next();
				// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
				// aura donc le choix entre 3 cartes a chaque tours
				for (int i = 0; i < 2; i++) {
					joueur.piocherUneCarte(this.paquet.getRandomCarte());
				}
			}
		} else {// on donne les cartes victoire des joueurs
			Iterator<Joueur> it = this.listeJoueur.iterator();

			while (it.hasNext()) {
				Joueur joueur = it.next();
				// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
				// aura donc le choix entre 3 cartes a chaque tours
				joueur.piocherUneCarteVictoire(this.paquet.getRandomCarte());
				System.out.println(
						joueur.toString() + " votre carte victoire est : " + joueur.getCarteVictoire().toString());
			}
		}

		// tant qu'il reste des cartes a placer et que le plateau n'est pas rempli
		while (((this.paquet.getNombreDeCarte() > 0) || (!this.isPlateauRempli()))
				|| (this.nombreDeCarteDansMains() > 0)) {
			this.changementDeTour();
			joue = false;

			Carte cartePioche = this.paquet.getRandomCarte();
			// poser une carte
			while (!joue) {

				String positionPoser;
				Carte carteJoue;
				// on donne la carte dans la main du joueur
				if (cartePioche != null) {
					this.listeJoueur.element().piocherUneCarte(cartePioche);
				}
				// System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

				this.montrerLesCartesPosees();

				if (!modeAvance) {
					System.out.println("Vous avez piochez la carte : " + cartePioche.toString());
					carteJoue = this.listeJoueur.element().choisirCarteAPlacer(modeAvance);

					System.out.println("Ou voulez vous la poser ? ");
					// String positionPoser = scanner.nextLine();
					positionPoser = this.listeJoueur.element().choisirCoordoneeAPlacer(carteJoue);
				} else {
					System.out.println("Voici votre main");
					this.listeJoueur.element().montrerLaMain();

					System.out.println("Quelle carte voulez vous placer ?");

					carteJoue = this.listeJoueur.element().choisirCarteAPlacer(modeAvance);

					System.out.println("Ou voulez vous la poser ?");
					positionPoser = this.listeJoueur.element().choisirCoordoneeAPlacer(carteJoue);

				}

				// le format �tant position = "x,y"
				// on r�cup�re s�par�ment les coordon�es en splitant la chaine de caract�re a la
				// virgule et ron r�cup�re chacune des 2 parties dans des variables
				try {
					int x = Integer.parseInt(positionPoser.split(",")[0]);
					int y = Integer.parseInt(positionPoser.split(",")[1]);
					joue = this.poserUneCarte(carteJoue, x, y);
				} catch (Exception Error) {
					System.out.println("Veuillez saisir des coordonee valides");
				}

				// System.out.println("Liste des cartes pos�e : ");

				if (!joue) {
					System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre pos�e");
				} else {
					this.listeJoueur.element().poserUneCarte(carteJoue);
				}
			}

			this.montrerLesCartesPosees();

			joue = false;

			// bouger une carte
			while ((!joue) && (!this.isPlateauRempli())) {
				System.out.println("Voulez vous bouger une carte?");
				// String bouger = scanner.nextLine();
				String bouger = this.listeJoueur.element().decisionBougerCarte();

				if (bouger.equals("oui")) {
					System.out.println("Donnez les coordonn� de la carte que vous voulez bouger");
					// String positioneCarte = scanner.nextLine();
					String positionCarte = this.listeJoueur.element().choisirCoordoneeCarteABouger();
					int xPremiere = Integer.parseInt(positionCarte.split(",")[0]);
					int yPremiere = Integer.parseInt(positionCarte.split(",")[1]);

					Carte carteABouger = this.recupererCoord(xPremiere, yPremiere).getCarte();

					System.out.println("Donnez les coordonn� de l'emplacement ou vous voulez la bouger");
					// String positionFinal = scanner.nextLine();
					String positionFinal = this.listeJoueur.element().bougerUneCarte();
					int xFinal = Integer.parseInt(positionFinal.split(",")[0]);
					int yFinal = Integer.parseInt(positionFinal.split(",")[1]);

					joue = this.bougerUneCarte(carteABouger, xFinal, yFinal);

					if (!joue) {
						System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre boug�e");
					}

				} else {
					joue = true;
				}
			}
		}

		// calcul des scores
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			System.out.println(joueur.toString() + " " + joueur.getCarteVictoire().toString() + " "
					+ this.compterLesPoints(joueur.getCarteVictoire()));
		}

	}

	/**
	 * 
	 * @param modeAvance le mode de jeu
	 * @return vrai si la partie est fini
	 * 
	 * Si elle est fini elle appelle les method de fin de partie
	 */
	public boolean isFini(boolean modeAvance) {
		Visiteur v = new Visiteur();
		v.visit(this);
		if (!modeAvance) {
			// System.out.println(this.isPlateauRempli());
			if ((this.paquet.getNombreDeCarte() > 0) && (!this.isPlateauRempli())) {
				return false;
			} else {

				// devoiler les cartes cachee
				this.shared.setListCoord(this.getListeCoord());
				this.setProperty("plateau-devoiler-cartes");

				Iterator<Joueur> itjoueur = this.listeJoueur.iterator();

				String i = "0";

				while (itjoueur.hasNext()) {
					// montrer les scores
					Joueur joueur = itjoueur.next();
					this.shared.setJoueur(joueur);
					//this.shared.setIntShared(this.compterLesPoints(joueur.getCarteVictoire()));
					this.shared.setIntShared(v.compterLesPointsAlternatif(joueur, this.modePlateauLibre));
					this.shared.setString(i);
					this.setProperty("plateau-montrer-score-joueur");
					if (i == "0") {
						i = "1";
					} else {
						i = "2";
					}
					
				}

				return true;
			}
		} else {
			// si il reste des carte dans les mains de joueurs ou si la partie vien de
			// commence (pour eviter le moment on l'on pose les carte cache et que les main
			// sont vide)
			// quand 5 cartes sont posée c'est que toutes les cartes caché sont posée et que
			// les mains des joueur sont pleines
			int nbcarteMin;
			if (this.modeTroisJoueur) {
				nbcarteMin = 3;
			} else {
				nbcarteMin = 2;
			}
			if ((((this.nombreDeCarteDansMains() > nbcarteMin) && (!this.isPlateauRempli()))) || (this.nbCartePosee() < 5)) {
				return false;
			} else {
				// on donne les carte victoire -> la denière carte de la leur main
				Iterator<Joueur> itjoueur = this.listeJoueur.iterator();
				this.shared.setString("0");

				while (itjoueur.hasNext()) {
					// montrer les scores
					Joueur joueur = itjoueur.next();
					joueur.choisirCarteVictoire();
					this.shared.setJoueur(joueur);
					this.shared.setCarte(joueur.getCarteVictoire());
					this.setProperty("plateau-donner-carte-victoire");

				}

				//this.shared.setListJoueur(this.listeJoueur);
				//this.setProperty("plateau-donner-carte-victoire");

				// devoiler les cartes cachee
				this.shared.setListCoord(this.getListeCoord());
				this.setProperty("plateau-devoiler-cartes");

				itjoueur = this.listeJoueur.iterator();

				String i = "0";
				while (itjoueur.hasNext()) {
					// montrer les scores
					Joueur joueur = itjoueur.next();
					this.shared.setJoueur(joueur);
					//this.shared.setIntShared(this.compterLesPoints(joueur.getCarteVictoire()));
					this.shared.setString(i);
					this.shared.setIntShared(v.compterLesPointsAlternatif(joueur, modePlateauLibre));
					this.setProperty("plateau-montrer-score-joueur");

					//gerer affichage score
					if (i == "0") {
						i = "1";
					} else {
						i = "2";
					}
				}

				return true;
			}
		}
	}

	public void jouerModeNormal() {
		boolean joue = false;

		// on donne les cartes victoire des joueurs
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			joueur.piocherUneCarteVictoire(this.paquet.getRandomCarte());
			// System.out.println(joueur.toString() + " votre carte victoire est : " +
			// joueur.getCarteVictoire().toString());
			// remplacent mvc
			// this.shared.setJoueur(joueur);
		}

		this.shared.setListJoueur(this.listeJoueur);
		this.setProperty("plateau-donner-carte-victoire");

		// tant qu'il reste des cartes a placer et que le plateau n'est pas rempli
		while ((this.paquet.getNombreDeCarte() > 0) && (!this.isPlateauRempli())) {
			this.changementDeTour();
			joue = false;

			Carte cartePioche = this.paquet.getRandomCarte();
			this.montrerLesCartesPosees();
			// poser une carte
			while (!joue) {

				String positionPoser;
				Carte carteJoue;
				// on donne la carte dans la main du joueur
				if (cartePioche != null) {
					this.listeJoueur.element().piocherUneCarte(cartePioche);
				}
				// System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

				// this.montrerLesCartesPosees();

				// System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

				// remplacent mvc
				this.shared.setCarte(cartePioche);
				// this.setProperty("plateau-montrer-carte-poser");

				this.listeJoueur.element().choisirCarteAPlacer(false);
				carteJoue = this.shared.getCarte();
				// remplacent mvc

				// System.out.println("Ou voulez vous la poser ? ");
				// remplacent mvc

				this.setProperty("plateau-demande-ou-poser");
				// String positionPoser = scanner.nextLine();
				this.listeJoueur.element().choisirCoordoneeAPlacer(carteJoue);

				positionPoser = this.shared.getString();

				// le format �tant position = "x,y"
				// on r�cup�re s�par�ment les coordon�es en splitant la chaine de caract�re a la
				// virgule et ron r�cup�re chacune des 2 parties dans des variables
				try {
					int x = Integer.parseInt(positionPoser.split(",")[0]);
					int y = Integer.parseInt(positionPoser.split(",")[1]);
					joue = this.poserUneCarte(carteJoue, x, y);
				} catch (Exception Error) {
					System.out.println("Veuillez saisir des coordonee valides");
				}

				// System.out.println("Liste des cartes pos�e : ");

				if (!joue) {
					// System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
					// pos�e");
					System.out.println("La carte n'a pas �tait pos�e");
				} else {
					this.listeJoueur.element().poserUneCarte(carteJoue);
				}
			}

			this.montrerLesCartesPosees();

			joue = false;

			// bouger une carte
			while ((!joue) && (!this.isPlateauRempli())) {
				// System.out.println("Voulez vous bouger une carte?");
				// String bouger = scanner.nextLine();

				// remplacent mvc
				this.setProperty("plateau-demande-bouger-carte");

				// String bouger = this.listeJoueur.element().decisionBougerCarte();
				// remplacent mvc

				this.listeJoueur.element().decisionBougerCarte();

				String bouger = this.shared.getString();

				if (bouger.equals("oui")) {
					// System.out.println("Donnez les coordonn� de la carte que vous voulez
					// bouger");
					// String positioneCarte = scanner.nextLine();
					this.setProperty("plateau-demande-coord-carte-bouger-1");

					// String positionCarte =
					// this.listeJoueur.element().choisirCoordoneeCarteABouger();
					this.listeJoueur.element().choisirCoordoneeCarteABouger();
					String positionCarte = this.shared.getString();

					int xPremiere = Integer.parseInt(positionCarte.split(",")[0]);
					int yPremiere = Integer.parseInt(positionCarte.split(",")[1]);

					Carte carteABouger = this.recupererCoord(xPremiere, yPremiere).getCarte();

					// System.out.println("Donnez les coordonn� de l'emplacement ou vous voulez la
					// bouger");
					// String positionFinal = scanner.nextLine();
					// String positionFinal = this.listeJoueur.element().bougerUneCarte();

					this.setProperty("plateau-demande-coord-carte-bouger-2");
					this.listeJoueur.element().bougerUneCarte();
					String positionFinal = this.shared.getString();

					int xFinal = Integer.parseInt(positionFinal.split(",")[0]);
					int yFinal = Integer.parseInt(positionFinal.split(",")[1]);

					joue = this.bougerUneCarte(carteABouger, xFinal, yFinal);

					if (!joue) {
						// System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
						// boug�e");
						System.out.println("La carte n'a pas �tait pos�e");
					}

				} else {
					joue = true;
				}
			}
			this.montrerLesCartesPosees();
		}

		// calcul des scores
		Iterator<Joueur> itjoueur = this.listeJoueur.iterator();

		while (itjoueur.hasNext()) {
			Joueur joueur = itjoueur.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			// System.out.println(joueur.toString() + " " +
			// joueur.getCarteVictoire().toString() + " "+
			// this.compterLesPoints(joueur.getCarteVictoire()));
			this.shared.setJoueur(joueur);
			this.shared.setIntShared(this.compterLesPoints(joueur.getCarteVictoire()));
			this.setProperty("plateau-montrer-score-joueur");
		}

	}

	public void jouerModeAvance() {
		boolean joue = false;

		// distribution des cartes pour le mode avance
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			for (int i = 0; i < 2; i++) {
				joueur.piocherUneCarte(this.paquet.getRandomCarte());
			}
		}

		// tant qu'il reste des cartes a placer et que le plateau n'est pas rempli
		while ((!this.isPlateauRempli()) && (this.nombreDeCarteDansMains() > 0)) {
			this.changementDeTour();
			joue = false;

			Carte cartePioche = this.paquet.getRandomCarte();

			// poser une carte
			while (!joue) {

				String positionPoser;
				Carte carteJoue;
				// on donne la carte dans la main du joueur
				if (cartePioche != null) {
					this.listeJoueur.element().piocherUneCarte(cartePioche);
				}
				// System.out.println("Vous avez piochez la carte : " + cartePioche.toString());

				this.montrerLesCartesPosees();

				System.out.println("Voici votre main");
				this.listeJoueur.element().montrerLaMain();

				System.out.println("Quelle carte voulez vous placer ?");

				carteJoue = this.listeJoueur.element().choisirCarteAPlacer(true);

				System.out.println("Ou voulez vous la poser ?");
				positionPoser = this.listeJoueur.element().choisirCoordoneeAPlacer(carteJoue);

				// le format �tant position = "x,y"
				// on r�cup�re s�par�ment les coordon�es en splitant la chaine de caract�re a la
				// virgule et ron r�cup�re chacune des 2 parties dans des variables
				try {
					int x = Integer.parseInt(positionPoser.split(",")[0]);
					int y = Integer.parseInt(positionPoser.split(",")[1]);
					joue = this.poserUneCarte(carteJoue, x, y);
				} catch (Exception Error) {
					System.out.println("Veuillez saisir des coordonee valides");
				}

				// System.out.println("Liste des cartes pos�e : ");

				if (!joue) {
					// System.out.println("L'emplacement n'�tait pas vide la carte n'a pas pu �tre
					// pos�e");
					System.out.println("La carte n'a pas �tait pos�e");
				} else {
					this.listeJoueur.element().poserUneCarte(carteJoue);
				}
			}

			this.montrerLesCartesPosees();

			joue = false;

			// bouger une carte
			while ((!joue) && (!this.isPlateauRempli())) {
				System.out.println("Voulez vous bouger une carte?");
				// String bouger = scanner.nextLine();
				String bouger = this.listeJoueur.element().decisionBougerCarte();

				if (bouger.equals("oui")) {
					System.out.println("Donnez les coordonn� de la carte que vous voulez bouger");
					// String positioneCarte = scanner.nextLine();
					String positionCarte = this.listeJoueur.element().choisirCoordoneeCarteABouger();
					int xPremiere = Integer.parseInt(positionCarte.split(",")[0]);
					int yPremiere = Integer.parseInt(positionCarte.split(",")[1]);

					Carte carteABouger = this.recupererCoord(xPremiere, yPremiere).getCarte();

					System.out.println("Donnez les coordonn� de l'emplacement ou vous voulez la bouger");
					// String positionFinal = scanner.nextLine();
					String positionFinal = this.listeJoueur.element().bougerUneCarte();
					int xFinal = Integer.parseInt(positionFinal.split(",")[0]);
					int yFinal = Integer.parseInt(positionFinal.split(",")[1]);

					joue = this.bougerUneCarte(carteABouger, xFinal, yFinal);

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

		// fin de partie
		// calcul des scores
		Iterator<Joueur> itjoueur = this.listeJoueur.iterator();

		while (itjoueur.hasNext()) {
			Joueur joueur = itjoueur.next();
			if (joueur.getMain().size() > 1) {
				joueur.montrerLaMain();
				System.out.println(joueur.toString() + " Choissez votre carte victoire :");
				joueur.choisirCarteVictoire();
			} else {
				joueur.setCarteVictoire(joueur.getMain().get(0));
			}
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours
			System.out.println(joueur.toString() + " " + joueur.getCarteVictoire().toString() + " "
					+ this.compterLesPoints(joueur.getCarteVictoire()));
		}

	}

	public void placerCarteCachee() {
		Iterator<Joueur> it = this.listeJoueur.iterator();

		while (it.hasNext()) {
			Joueur joueur = it.next();
			// chaque joueur prend 2 carte puis a chaque tour il en pioche une nouvelle il
			// aura donc le choix entre 3 cartes a chaque tours

			// CarteCachee carteCachee = new CarteCachee(this.paquet.getRandomCarte());
			Carte carteCachee = this.paquet.getRandomCarte();
			joueur.piocherUneCarte(carteCachee);

			System.out.println(joueur.toString() + " vous avez pioché une carte cachée");

			// carteCachee.reveler();
			// this.shared.setCarteCachee(carteCachee);
			// on notifie la vue que l'on vien de placer une carte cachee
			// this.setProperty("plateau-revelerCarte");

			this.montrerLesCartesPosees();

			boolean joue = false;

			while (!joue) {

				// System.out.println("Ou voulez vous la poser ?");
				// remplacent mvc
				// this.shared.setCarteCachee(carteCachee);
				this.setProperty("plateau-question-ou-poser-carte-cachee");
				// String positionPoser = scanner.nextLine();

				// String positionPoser = joueur.choisirCoordoneeAPlacer(carteCachee);
				// System.out.println("Bonsoir a tous");
				// remplacent mvc

				// this.setProperty("plateau-demande-joueur-coord-carte-cachee");
				joueur.choisirCoordoneeAPlacer(carteCachee);

				String positionPoser = this.shared.getString();

				try {
					int x = Integer.parseInt(positionPoser.split(",")[0]);
					int y = Integer.parseInt(positionPoser.split(",")[1]);
					joue = this.poserUneCarte(carteCachee, x, y);
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
		// this.setProperty("plateau-revelerCarte");
		this.changementDeTour();
	}

	/**
	 * Donne les cartes victoires aux joueurs
	 */
	public void donnerCarteVictoire() {
		// on donne les cartes victoire des joueurs
		Iterator<Joueur> it = this.getListJoueur().iterator();
		this.shared.setString("0");

		while (it.hasNext()) {
			Joueur joueur = it.next();
			joueur.piocherUneCarteVictoire(this.paquet.getRandomCarte());
			this.shared.setCarte(joueur.getCarteVictoire());
			this.shared.setJoueur(joueur);
			this.setProperty("plateau-donner-carte-victoire");
		}

		this.changementDeTour();
		//this.shared.setListJoueur(this.listeJoueur);
		//this.setProperty("plateau-donner-carte-victoire");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Scanner scanner = new Scanner(System.in);
		// Plateau plat = new Plateau();
		// plat.placerCarteCachee();
		// plat.jouerModeNormal();

		// CarteCachee carteCachee = new CarteCachee(plat.paquet.getRandomCarte());
		// plat.poserUneCarte(carteCachee, 2, 2);

		// pour les tests de score
		/*
		 * Carte triangleRougeVide = plat.paquet.recupererCarte("triangle", "rouge",
		 * false); Carte rondRougeVide = plat.paquet.recupererCarte("rond", "rouge",
		 * false); //Carte carreRougeVide = plat.paquet.recupererCarte("carre", "rouge",
		 * false); //Carte carreRougePlein = plat.paquet.recupererCarte( "carre",
		 * "rouge", true); //Carte triangleBleuPlein =
		 * plat.paquet.recupererCarte("triangle", "bleu", true);
		 * 
		 * Carte triangleVertPlein = plat.paquet.recupererCarte("triangle", "vert",
		 * true); Carte rondVertPlein = plat.paquet.recupererCarte("rond", "vert",
		 * true); //Carte carreVertVide = plat.paquet.recupererCarte("carre", "vert",
		 * false); //Carte rondRougePlein = plat.paquet.recupererCarte( "rond", "rouge",
		 * true); //Carte carreBleuPlein = plat.paquet.recupererCarte("carre", "bleu",
		 * true);
		 * 
		 * //Carte triangleVertVide = plat.paquet.recupererCarte("triangle", "vert",
		 * false); //Carte rondBleuPlein = plat.paquet.recupererCarte("rond", "bleu",
		 * true); //Carte rondBleuVide = plat.paquet.recupererCarte("rond", "bleu",
		 * false); //Carte carreBleuVide = plat.paquet.recupererCarte( "carre", "bleu",
		 * false); //Carte carreVertPlein = plat.paquet.recupererCarte("carre", "vert",
		 * true);
		 * 
		 * //Carte carteVictoire = plat.paquet.recupererCarte("rond", "vert", false);
		 * //System.out.println(carteVictoire.toString());
		 * 
		 * plat.poserUneCarte(triangleRougeVide, 1, 1);
		 * plat.poserUneCarte(rondRougeVide, 2, 1); //plat.poserUneCarte(carreRougeVide,
		 * 2, 0); //plat.poserUneCarte(carreRougePlein, 3, 0);
		 * //plat.poserUneCarte(triangleBleuPlein, 4, 0);
		 * 
		 * plat.poserUneCarte(triangleVertPlein, 1, 2);
		 * plat.poserUneCarte(rondVertPlein, 2, 2);
		 */
		// plat.poserUneCarte(carreVertVide, 2, 1);
		// plat.poserUneCarte(rondRougePlein, 3, 1);
		// plat.poserUneCarte(carreBleuPlein, 4, 1);

		// plat.poserUneCarte(triangleVertVide, 0, 2);
		// plat.poserUneCarte(rondBleuPlein, 1, 2);
		// plat.poserUneCarte(rondBleuVide, 2, 2);
		// plat.poserUneCarte(carreBleuVide, 3, 2);
		// plat.poserUneCarte(carreVertPlein, 4, 2);

		// test d�calage plateau
		// plat.montrerLesCartesPosees();

		/*
		 * plat.decalerPlateau(0, 1); System.out.println("----");
		 * plat.montrerLesCartesPosees();
		 */

		// on place les carte cachées

		// Carte carteTest = plat.paquet.getRandomCarte();
		// plat.poserUneCarte(carteTest, 0, 0);
		// plat.jouer(true);
		// plat.jouerModeNormal();
		// plat.jouerModeAvance();

		/*
		 * System.out.println("nombre de carte dans le paquet : " +
		 * plat.paquet.getNombreDeCarte());
		 * System.out.println("nombre de carte en jeu : " +
		 * plat.getNombreDeCarteEnJeu()); Carte carte = plat.paquet.getRandomCarte();
		 * System.out.println(carte.toString()); plat.poserUneCarte(carte, 2, 2);
		 * System.out.println("nombre de carte dans le paquet : " +
		 * plat.paquet.getNombreDeCarte());
		 * System.out.println("nombre de carte en jeu : " +
		 * plat.getNombreDeCarteEnJeu());
		 * 
		 * System.out.println("-----");
		 */

		// essaie de simulation d'un partie
		/*
		 * while (plat.paquet.getNombreDeCarte() > 0) {
		 * System.out.println("---------------------");
		 * System.out.println("Changement de tour");
		 * System.out.println("---------------------");
		 * 
		 * Carte carte = plat.paquet.getRandomCarte();
		 * System.out.println("Vous avez piochez la carte : " + carte.toString());
		 * System.out.println("Ou voulez vous la poser ?"); String position =
		 * scanner.nextLine();
		 * 
		 * //le format �tant position = "x,y" //on r�cup�re s�par�ment les coordon�es en
		 * splitant la chaine de caract�re a la virgule et ron r�cup�re chacune des 2
		 * parties dans des variables int x = Integer.parseInt(position.split(",")[0]);
		 * int y = Integer.parseInt(position.split(",")[1]);
		 * 
		 * plat.poserUneCarte(carte, x, y);
		 * 
		 * 
		 * plat.montrerLesCartesPosees(); }
		 * 
		 * 
		 * System.out.println("Partie termin�e");
		 */

		// essai de comptage des point avec une forme differente d'un rectrangle 3*5

		/*
		 * Carte triangleRougeVide = plat.paquet.recupererCarte("triangle", "rouge",
		 * false); Carte rondRougeVide = plat.paquet.recupererCarte("rond", "rouge",
		 * false); Carte carreRougeVide = plat.paquet.recupererCarte("carre", "rouge",
		 * false); Carte carreRougePlein = plat.paquet.recupererCarte( "carre", "rouge",
		 * true); Carte triangleBleuPlein = plat.paquet.recupererCarte("triangle",
		 * "bleu", true);
		 * 
		 * Carte triangleVertPlein = plat.paquet.recupererCarte("triangle", "vert",
		 * true); Carte rondVertPlein = plat.paquet.recupererCarte("rond", "vert",
		 * true); Carte carreVertVide = plat.paquet.recupererCarte("carre", "vert",
		 * false); Carte rondRougePlein = plat.paquet.recupererCarte( "rond", "rouge",
		 * true); Carte carreBleuPlein = plat.paquet.recupererCarte("carre", "bleu",
		 * true);
		 * 
		 * Carte triangleVertVide = plat.paquet.recupererCarte("triangle", "vert",
		 * false); Carte rondBleuPlein = plat.paquet.recupererCarte("rond", "bleu",
		 * true); Carte rondBleuVide = plat.paquet.recupererCarte("rond", "bleu",
		 * false); Carte carreBleuVide = plat.paquet.recupererCarte( "carre", "bleu",
		 * false); Carte carreVertPlein = plat.paquet.recupererCarte("carre", "vert",
		 * true);
		 * 
		 * Carte carteVictoire = plat.paquet.recupererCarte("rond", "vert", false);
		 * 
		 * System.out.println(carteVictoire.toString());
		 * 
		 * plat.poserUneCarte(triangleRougeVide, 0, 0);
		 * plat.poserUneCarte(rondRougeVide, 1, 0); plat.poserUneCarte(carreRougeVide,
		 * 2, 0); plat.poserUneCarte(carreRougePlein, 3, 0);
		 * plat.poserUneCarte(triangleBleuPlein, 4, 0);
		 * 
		 * plat.poserUneCarte(triangleVertPlein, 0, 1);
		 * plat.poserUneCarte(rondVertPlein, 1, 1); plat.poserUneCarte(carreVertVide, 2,
		 * 1); plat.poserUneCarte(rondRougePlein, 3, 1);
		 * plat.poserUneCarte(carreBleuPlein, 4, 1);
		 * 
		 * plat.poserUneCarte(triangleVertVide, 0, 2); plat.poserUneCarte(rondBleuPlein,
		 * 1, 2); plat.poserUneCarte(rondBleuVide, 2, 2);
		 * plat.poserUneCarte(carreBleuVide, 3, 2); plat.poserUneCarte(carreVertPlein,
		 * 4, 2);
		 * 
		 * plat.poserUneCarte(carteVictoire, 0, 3);
		 * 
		 * //System.out.println("score : "+plat.compterLesPoints(carteVictoire));
		 * Visiteur v = new Visiteur();
		 * 
		 * Joueur j = new Joueur("Test"); j.carteVictoire = carteVictoire;
		 * 
		 * plat.acceptVisit(v); int score = v.compterLesPointsAlternatif(j);
		 * 
		 * System.out.println("score : " + score);
		 */

		// plat.montrerLesCartesPosees();

		// Test bougerUneCarte
		/*
		 * Carte carte = plat.paquet.getRandomCarte(); plat.poserUneCarte(carte, 0, 0);
		 * plat.montrerLesCartesPosees(); plat.bougerUneCarte(carte, 1, 1);
		 * plat.montrerLesCartesPosees();
		 */

	}

}
