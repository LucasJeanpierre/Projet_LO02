package fr.utt.sit.lo02.projet.model;

import java.util.ArrayList;
import java.util.Scanner;

import fr.utt.sit.lo02.projet.shared.Shared;
import java.beans.*;

/**
 * Class Joueur
 * 
 */
public class Joueur implements ObjetVisite {

	private String nom;
	private Carte carteVictoire;
	private Scanner scanner;
	private ArrayList<Carte> main;
	private PropertyChangeSupport pcs;
	private Shared shared;
	private int nbCarteJoue;

	/**
	 * Accepte la visite du visiteur
	 * @param visiteur
	 */
	public void acceptVisit(Visiteur v) {
		v.visit(this);
	}

	/**
	 * Method toString
	 * retourne une chaine de caractere avec le nom du joueur
	 */
	public String toString() {
		return this.nom;
	}

	/**
	 * Constructeur de la class
	 * @param nom le nom du joueur
	 * @param shared l'objet partage entre tout les objet pour echanger des informations
	 */
	public Joueur(String nom, Shared shared) {
		this.nom = nom;
		this.scanner = new Scanner(System.in);
		this.main = new ArrayList<Carte>();
		this.shared = shared;
		this.pcs = new PropertyChangeSupport(this);
		this.nbCarteJoue = 0;
	}

	/**
	 * Permet au joueur de piocher une carte
	 * @param carte la carte a pioche
	 */
	public void piocherUneCarte(Carte carte) {
		this.main.add(carte);
	}

	/**
	 * Permet de recuperer le nom du joueur
	 * @return nom (String)
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Permet de connaitre le nombre de carte dans la main du joueur
	 * @return nbCarteEnMain (int)
	 */
	public int getNbCarteEnMain() {
		return this.main.size();
	}

	/**
	 * Method bouger une carte
	 * Permet au joueur de prendre un decision pour bouger une carte
	 */
	public String bougerUneCarte() {
		/*System.out.print("> ");
		String positionBouger = scanner.nextLine();
		return positionBouger;*/
		this.setProperty("joueur-demande-coord");
		return this.shared.getString();
	}

	/**
	 * Permet au joueur de choisir de bouger une carte
	 * @return
	 */
	public String choisirCarteABouger() {
		System.out.print("> ");
		String choixCarte = scanner.nextLine();
		return choixCarte;
	}

	/**
	 * Permet de joueur de poser une carte
	 * @param carte la carte a poser
	 */
	public void poserUneCarte(Carte carte) {
		main.remove(carte);
		this.nbCarteJoue++;
	}

	/**
	 * Permet d'obtenir la nombre de carte que le joueur a joue
	 * @return
	 */
	public int getNbCarteJoue() {
		return this.nbCarteJoue;
	}

	/**
	 * Permet au joueur de piocher une carte Victoire
	 * @param carte la carte victoire
	 */
	public void piocherUneCarteVictoire(Carte carte) {
		this.carteVictoire = carte;
	}

	/**
	 * Permet de recupere la carte victoire du joueur
	 * @return CarteVictoire
	 */
	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}

	/**
	 * Permet au joueur de choisir a quelle coordonee placer une carte
	 * @param carte la carte ca placer
	 * @return une chaine de caratere definissant la coordonee
	 */
	public String choisirCoordoneeAPlacer(Carte carte) {
		/*System.out.print("> ");
		String positionPoser = scanner.nextLine();
		return positionPoser;*/
		this.setProperty("joueur-demande-coord");
		return this.shared.getString();
	}

	/**
	 * Permet au joueur de choisir une carte a bouger
	 * 
	 * @return une chaine de caratere definissant la coordonee
	 */
	public String choisirCoordoneeCarteABouger() {
		/*System.out.print("> ");
		String positionPoser = scanner.nextLine();
		return positionPoser;*/
		this.setProperty("joueur-demande-coord");
		return this.shared.getString();
	}


	/**
	 * Permet au joueur de choisir une carte placer
	 * @param modeAvance si le jeu est en mode avance (boolean)
	 */
	public Carte choisirCarteAPlacer(boolean modeAvance) {
		if (modeAvance) {
			boolean bonneValeur = false;
			while (!bonneValeur) {
				System.out.print("> ");
				String carteAPlacerString = scanner.nextLine();
				try {
					int carteAPlacer = Integer.parseInt(carteAPlacerString);
					return this.main.get(carteAPlacer);
				} catch (Exception e) {
					System.out.println("Saisi non correct");
				}
			}
		} else {
			return main.get(0);
		}
		return null;
	}


	/**
	 * Retourne la premiere carte de la main du joueur
	 * pour donner sa carte victoire a la fin du jeu en mode Avance
	 * @return
	 */
	public Carte choisirCarteVictoire() {
		Carte carte = this.carteVictoire = this.main.get(0);
		return carte;
	}

	/**
	 * Recupere la main de joueur
	 * @return la main du joueur
	 */
	public ArrayList<Carte> getMain() {
		return this.main;
	}
	
	/**
	 * Definie la carte victoire du joueur
	 * @param carte la carte victoire
	 */
	public void setCarteVictoire(Carte carte) {
		this.carteVictoire = carte;
	}

	/**
	 * Permet au joueur de choisir de bouger une carte
	 * 
	 * @return une chaine de caratere definissant la coordonee
	 */
	public String decisionBougerCarte() {
		/*System.out.print("> ");
		String decision = scanner.nextLine();
		return decision;*/
		this.setProperty("joueur-demande-bouger");
		return this.shared.getString();
	}

	/**
	 * Montre la main du joueur
	 */
	public void montrerLaMain() {
		this.shared.setListCarte(main);
		this.setProperty("joueur-montrer-la-main");
	}

	/**
	 * Recuperer l'objet partage
	 * @return l'objet partage
	 */
	public Shared getShared() {
		return this.shared;
	}
	

	/**
	 * Ajoute un observer au joueur
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


}
