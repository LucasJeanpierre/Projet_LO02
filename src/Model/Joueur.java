package Model;

import java.util.ArrayList;
import java.util.Scanner;

import Shared.Shared;
import View.Observable;

import java.util.Iterator;
import java.beans.*;

public class Joueur implements ObjetVisite, PropertyChangeListener {

	private String nom;
	//temporaire
	public Carte carteVictoire;
	//private Carte carteVictoire;
	private Scanner scanner;
	private ArrayList<Carte> main;
	private PropertyChangeSupport pcs;
	private Shared shared;

	public void acceptVisit(Visiteur v) {
		v.visit(this);
	}

	public String toString() {
		return "Joueur : " + this.nom;
	}

	public Joueur(String nom, Shared shared) {
		this.nom = nom;
		this.scanner = new Scanner(System.in);
		this.main = new ArrayList<Carte>();
		this.shared = shared;
		this.pcs = new PropertyChangeSupport(this);
	}

	public void piocherUneCarte(Carte carte) {
		this.main.add(carte);
	}


	public String bougerUneCarte() {
		System.out.print("> ");
		String positionBouger = scanner.nextLine();
		return positionBouger;
	}

	public String choisirCarteABouger() {
		System.out.print("> ");
		String choixCarte = scanner.nextLine();
		return choixCarte;
	}

	public void poserUneCarte(Carte carte) {
		main.remove(carte);
	}

	public void piocherUneCarteVictoire(Carte carte) {
		this.carteVictoire = carte;
	}

	public Carte getCarteVictoire() {
		return this.carteVictoire;
	}


	public String choisirCoordoneeAPlacer(Carte carte) {
		/*System.out.print("> ");
		String positionPoser = scanner.nextLine();
		return positionPoser;*/
		this.setProperty("joueur-demande-coord");
		return this.shared.getString();
	}
	
	public String choisirCoordoneeCarteABouger() {
		System.out.print("> ");
		String positionPoser = scanner.nextLine();
		return positionPoser;
	}


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


	public Carte choisirCarteVictoire() {
		boolean bonneValeur = false;
		while (!bonneValeur) {
			System.out.print("> ");
			String carteVictoireString = scanner.nextLine();
			try {
				int carteVictoire = Integer.parseInt(carteVictoireString);
				this.carteVictoire = this.main.get(carteVictoire);
				bonneValeur = true;
			} catch (Exception e) {
				System.out.println("Saisi non correct");
			}

		}
		return null;
	}

	public ArrayList<Carte> getMain() {
		return this.main;
	}
	
	public void setCarteVictoire(Carte carte) {
		this.carteVictoire = carte;
	}

	public String decisionBougerCarte() {
		System.out.print("> ");
		String decision = scanner.nextLine();
		return decision;
	}

	public void montrerLaMain() {
		Iterator<Carte> it = main.iterator();

		while (it.hasNext()) {
			Carte carte = it.next();
			System.out.println(carte.toString());
		}
	}

	public Shared getShared() {
		return this.shared;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
	}

	public void addObserver(PropertyChangeListener l) {
		this.pcs.addPropertyChangeListener(l);
	}

	public void setProperty(String name) {
		this.pcs.firePropertyChange(name, 0, 1);
	}


}
