package Model;

import java.util.*;
import java.beans.*;
import Shared.Shared;
import View.VuePlateau;

/**
 * Class Plateau
 */
public class Plateau implements ObjetVisite {

	// private boolean formePlateauLibre = false;

	private ArrayList<Coordonee> listeCoord;
	private Queue<Joueur> listeJoueur;
	private Paquet paquet;

	private PropertyChangeSupport pcs;
	private Shared shared;
	private boolean modePlateauLibre;
	private boolean modeTroisJoueur;

	/**
	 * Ajoute un observer
	 * 
	 * @param l l'observer
	 */
	public void addObserver(PropertyChangeListener l) {
		this.pcs.addPropertyChangeListener(l);
	}

	/**
	 * notifie les observers
	 * 
	 * @param name le nom de la notification
	 */
	public void setProperty(String name) {
		this.pcs.firePropertyChange(name, 0, 1);
	}

	/**
	 * Accepte la viste du visiteur
	 * 
	 * @param v le visiteur
	 */
	public void acceptVisit(Visiteur v) {
		v.visit(this);
	}

	/**
	 * Pose une carte sur le plateau
	 * 
	 * @param carte la carte a poser
	 * @param x     la coord x
	 * @param y     la coord y
	 * 
	 * @return la carte a bien ete posee (boolean)
	 */
	public boolean poserUneCarte(Carte carte, int x, int y) {

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

	}

	/**
	 * Renvoie la liste des coordonee du plateau
	 * 
	 * @return liste de coordonee
	 */
	public ArrayList<Coordonee> getListeCoord() {
		return this.listeCoord;
	}

	/**
	 * Renvoie la liste des joueurs
	 * 
	 * @return liste de joueurs
	 */
	public Queue<Joueur> getListJoueur() {
		return this.listeJoueur;
	}

	/**
	 * Revoie un boolean pour savoir si l'on peut poser une carte a une coordonee
	 * 
	 * @param carte la carte a poser
	 * @param x     coord x
	 * @param y     coord y
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
	 * 
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
	 * 
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

	}

	/**
	 * Bouge une carte aux position en parametres
	 * 
	 * @param carte la carte a bouger
	 * @param x     coord x
	 * @param y     coord y
	 * @return vrai si la carte a ete bouger sinon faux
	 */
	public boolean bougerUneCarte(Carte carte, int x, int y) {

		// on enleve d'abord la carte de la ou elle �tait
		if (trouverCoordoneeDUneDarte(carte) != null) {
			Coordonee oldCoord = trouverCoordoneeDUneDarte(carte);
			// puis on la place au nouvelle endroit
			Coordonee coord = recupererCoord(x, y);
			// oldCoord.enleverLaCarte();
			if (oldCoord.enleverLaCarte() && (coord.getCarte() == null)) {

				// si l'emplacement ou bouge la carte est vide
				// oldCoord.enleverLaCarte();
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
				this.shared.setCoordonee(coord);
				this.shared.setCarte(coord.getCarte());
				this.setProperty("plateau-montrer-la-carte");
			}
		}
	}

	/**
	 * revoie vrai si le plateau est rempli sinon faux
	 * 
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
	 * 
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

	/**
	 * Recupere l'objet coordonee en fonction de sa postion
	 * 
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
	 * 
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

	/**
	 * @return le nombre de carte posee sur le plateau
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
	/*
	 * public int compterLesPoints(Carte carteVictoire) { // on r�cup�re les
	 * caract�ristique de la carte victoire String forme = carteVictoire.getForme();
	 * String couleur = carteVictoire.getCouleur(); boolean rempli =
	 * carteVictoire.isRempli();
	 * 
	 * int nbRecurenceForme = 0; int nbRecurenceCouleur = 0; int nbRecurenceRempli =
	 * 0;
	 * 
	 * int nbRecurenceFormeMax = 0; int nbRecurenceCouleurMax = 0; int
	 * nbRecurenceRempliMax = 0;
	 * 
	 * int score = 0;
	 * 
	 * // calcul du score pour toute les lignes for (int y = 0; y < 3; y++) {
	 * nbRecurenceForme = 0; nbRecurenceCouleur = 0; nbRecurenceRempli = 0;
	 * 
	 * nbRecurenceFormeMax = 0; nbRecurenceCouleurMax = 0; nbRecurenceRempliMax = 0;
	 * 
	 * for (int x = 0; x < 5; x++) { Coordonee coord = recupererCoord(x, y); Carte
	 * carte = coord.getCarte();
	 * 
	 * // calcul pour les formes if (carte.getForme() == forme) {
	 * nbRecurenceForme++; if (nbRecurenceForme > nbRecurenceFormeMax) {
	 * nbRecurenceFormeMax = nbRecurenceForme; } } else { nbRecurenceForme = 0; }
	 * 
	 * if (carte.getCouleur() == couleur) { nbRecurenceCouleur++; if
	 * (nbRecurenceCouleur > nbRecurenceCouleurMax) { nbRecurenceCouleurMax =
	 * nbRecurenceCouleur; } } else { nbRecurenceCouleur = 0; }
	 * 
	 * if (carte.isRempli() == rempli) { nbRecurenceRempli++; if (nbRecurenceRempli
	 * > nbRecurenceRempliMax) { nbRecurenceRempliMax = nbRecurenceRempli; } } else
	 * { nbRecurenceRempli = 0; } }
	 * 
	 * if (nbRecurenceFormeMax > 1) { score += nbRecurenceFormeMax - 1; }
	 * 
	 * if (nbRecurenceCouleurMax > 2) { score += nbRecurenceCouleurMax + 1; }
	 * 
	 * if (nbRecurenceRempliMax > 2) { score += nbRecurenceRempliMax; } }
	 * 
	 * // calculer score colonnes
	 * 
	 * for (int x = 0; x < 5; x++) { nbRecurenceForme = 0; nbRecurenceCouleur = 0;
	 * nbRecurenceRempli = 0;
	 * 
	 * nbRecurenceFormeMax = 0; nbRecurenceCouleurMax = 0; nbRecurenceRempliMax = 0;
	 * 
	 * for (int y = 0; y < 3; y++) { Coordonee coord = recupererCoord(x, y); Carte
	 * carte = coord.getCarte();
	 * 
	 * // calcul pour les formes if (carte.getForme() == forme) {
	 * nbRecurenceForme++; if (nbRecurenceForme > nbRecurenceFormeMax) {
	 * nbRecurenceFormeMax = nbRecurenceForme; } } else { nbRecurenceForme = 0; }
	 * 
	 * if (carte.getCouleur() == couleur) { nbRecurenceCouleur++; if
	 * (nbRecurenceCouleur > nbRecurenceCouleurMax) { nbRecurenceCouleurMax =
	 * nbRecurenceCouleur; } } else { nbRecurenceCouleur = 0; }
	 * 
	 * if (carte.isRempli() == rempli) { nbRecurenceRempli++; if (nbRecurenceRempli
	 * > nbRecurenceRempliMax) { nbRecurenceRempliMax = nbRecurenceRempli; } } else
	 * { nbRecurenceRempli = 0; } }
	 * 
	 * 
	 * if (nbRecurenceFormeMax > 1) { score += nbRecurenceFormeMax - 1; }
	 * 
	 * if (nbRecurenceCouleurMax > 2) { score += nbRecurenceCouleurMax + 1; }
	 * 
	 * if (nbRecurenceRempliMax > 2) { score += nbRecurenceRempliMax; } }
	 * 
	 * return score; }
	 */

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
	 * 
	 * @param shared           l'objet partage
	 * @param vuePlateau       la vue
	 * @param modePlateauLibre mode de jeu
	 * @param modeTroisJoueur  mode de jeu
	 * 
	 *                         le constructeur creer les, les coordonee et les
	 *                         cartes
	 */
	public Plateau(Shared shared, VuePlateau vuePlateau, boolean modePlateauLibre, boolean modeTroisJoueur) {
		// creation du paquet
		this.shared = shared;
		this.modePlateauLibre = modePlateauLibre;
		this.modeTroisJoueur = modeTroisJoueur;
		this.paquet = new Paquet(vuePlateau, modePlateauLibre);
		this.listeJoueur = new LinkedList<Joueur>();

		if (!modeTroisJoueur) {
			Joueur j1 = new Joueur("Joueur 1", this.shared);
			Joueur j2 = new Joueur("Joueur 2", this.shared);
			// Joueur ordi = new JoueurIA(this, this.shared);

			listeJoueur.add(j1);
			listeJoueur.add(j2);
			// listeJoueur.add(ordi);
		} else {
			Joueur j1 = new Joueur("Joueur 1", this.shared);
			Joueur j2 = new Joueur("Joueur 2", this.shared);
			Joueur j3 = new Joueur("Joueur 3", this.shared);
			// Joueur ordi = new JoueurIA(this, this.shared);

			listeJoueur.add(j1);
			listeJoueur.add(j2);
			listeJoueur.add(j3);
			// listeJoueur.add(ordi);
		}

		this.listeCoord = new ArrayList<Coordonee>();

		// le pcs
		this.pcs = new PropertyChangeSupport(this);

		// Creation du plateau
		int nblong;
		int nblarge;

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
	 * Donne 2 cartes a chaque joueurs
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
	 * 
	 * @param joueur
	 */
	public void donnerDeuxCarteAuJoueur(Joueur joueur) {
		for (int i = 0; i < 2; i++) {
			joueur.piocherUneCarte(new CarteNormal(this.paquet.getRandomCarte()));
		}
	}


	/**
	 * 
	 * @param modeAvance le mode de jeu
	 * @return vrai si la partie est fini
	 * 
	 *         Si elle est fini elle appelle les method de fin de partie
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
					// this.shared.setIntShared(this.compterLesPoints(joueur.getCarteVictoire()));
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
			// quand 5 cartes sont posee c'est que toutes les cartes cache sont posee et que
			// les mains des joueur sont pleines
			int nbcarteMin;
			if (this.modeTroisJoueur) {
				nbcarteMin = 3;
			} else {
				nbcarteMin = 2;
			}
			if ((((this.nombreDeCarteDansMains() > nbcarteMin) && (!this.isPlateauRempli())))
					|| (this.nbCartePosee() < 5)) {
				return false;
			} else {
				// on donne les carte victoire -> la deniere carte de la leur main
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

				// devoiler les cartes cachee
				this.shared.setListCoord(this.getListeCoord());
				this.setProperty("plateau-devoiler-cartes");

				itjoueur = this.listeJoueur.iterator();

				String i = "0";
				while (itjoueur.hasNext()) {
					// montrer les scores
					Joueur joueur = itjoueur.next();
					this.shared.setJoueur(joueur);
					// this.shared.setIntShared(this.compterLesPoints(joueur.getCarteVictoire()));
					this.shared.setString(i);
					this.shared.setIntShared(v.compterLesPointsAlternatif(joueur, modePlateauLibre));
					this.setProperty("plateau-montrer-score-joueur");

					// gerer affichage score
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
		// this.shared.setListJoueur(this.listeJoueur);
		// this.setProperty("plateau-donner-carte-victoire");
	}

	public void testerCalculScore() {
		Visiteur v = new Visiteur();

		Carte triangleRougeVide = this.paquet.recupererCarte("triangle", "rouge", false);
		Carte rondRougeVide = this.paquet.recupererCarte("rond", "rouge", false);
		Carte carreRougeVide = this.paquet.recupererCarte("carre", "rouge", false);
		Carte carreRougePlein = this.paquet.recupererCarte("carre", "rouge", true);
		Carte triangleBleuPlein = this.paquet.recupererCarte("triangle", "bleu", true);

		Carte triangleVertPlein = this.paquet.recupererCarte("triangle", "vert", true);
		Carte rondVertPlein = this.paquet.recupererCarte("rond", "vert", true);
		Carte carreVertVide = this.paquet.recupererCarte("carre", "vert", false);
		Carte rondRougePlein = this.paquet.recupererCarte("rond", "rouge", true);
		Carte carreBleuPlein = this.paquet.recupererCarte("carre", "bleu", true);

		Carte triangleVertVide = this.paquet.recupererCarte("triangle", "vert", false);
		Carte rondBleuPlein = this.paquet.recupererCarte("rond", "bleu", true);
		Carte rondBleuVide = this.paquet.recupererCarte("rond", "bleu", false);
		Carte carreBleuVide = this.paquet.recupererCarte("carre", "bleu", false);
		Carte carreVertPlein = this.paquet.recupererCarte("carre", "vert", true);

		Carte carteVictoire = this.paquet.recupererCarte("rond", "vert", false);

		this.poserUneCarte(triangleRougeVide, 0, 0);
		this.poserUneCarte(rondRougeVide, 1, 0);
		this.poserUneCarte(carreRougeVide, 2, 0);
		this.poserUneCarte(carreRougePlein, 3, 0);
		this.poserUneCarte(triangleBleuPlein, 4, 0);

		this.poserUneCarte(triangleVertPlein, 0, 1);
		this.poserUneCarte(rondVertPlein, 1, 1);
		this.poserUneCarte(carreVertVide, 2, 1);
		this.poserUneCarte(rondRougePlein, 3, 1);
		this.poserUneCarte(carreBleuPlein, 4, 1);

		this.poserUneCarte(triangleVertVide, 0, 2);
		this.poserUneCarte(rondBleuPlein, 1, 2);
		this.poserUneCarte(rondBleuVide, 2, 2);
		this.poserUneCarte(carreBleuVide, 3, 2);
		this.poserUneCarte(carreVertPlein, 4, 2);

		this.poserUneCarte(carteVictoire, 0, 3);


		Joueur j = new Joueur("Test", null);
		j.piocherUneCarte(carteVictoire);

		this.acceptVisit(v);
		int score = v.compterLesPointsAlternatif(j, false);

		System.out.println("score : " + score);
	}

}
