package fr.utt.sit.lo02.projet.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import fr.utt.sit.lo02.projet.view.VuePlateau;

/**
 * Class paquet
 */
public class Paquet {

	private ArrayList<Carte> paquetDeCarte;
	public String[] listeCouleur = {"rouge", "bleu", "vert"};
	public String[] listeForme = {"rond", "triangle", "carre"};

	

	/**
	 * Constructeur
	 * @param vuePlateau la vue
	 * @param modePlateauLibre le mode du plateau
	 * 
	 * le contructeur creer les carte du jeu
	 * 
	 * et enleve une pour respecter les regles du jeu
	 */
	public Paquet(VuePlateau vuePlateau, boolean modePlateauLibre) {
		this.paquetDeCarte = new ArrayList<Carte>();

		//creation des cartes
		for (int icouleur = 0; icouleur < listeCouleur.length; icouleur ++) {
			for (int iforme = 0; iforme < listeForme.length; iforme ++) {
				paquetDeCarte.add(new Carte(listeForme[iforme], listeCouleur[icouleur], true, vuePlateau.getFrame(),modePlateauLibre));
				paquetDeCarte.add(new Carte(listeForme[iforme], listeCouleur[icouleur], false, vuePlateau.getFrame(),modePlateauLibre));
			}
		}

		//suppresion d'un des cartes (pour respecter les regles)
		Random rand = new Random();
		paquetDeCarte.remove(rand.nextInt(paquetDeCarte.size()));
	}

	/**
	 * revoie la liste des cartes du paquet
	 * @return la liste des carte <ArrayList>
	 */
	public ArrayList<Carte> getPaquetDeCarte() {
		return paquetDeCarte;
	}

	/**
	 * Renvoie le nombre de carte restante dans le paquet
	 * @return nombre de carte
	 */
	public int getNombreDeCarte() {
		return paquetDeCarte.size();
	}



	/**
	 * Definie le paquet de carte
	 * @param paquetDeCarte
	 */
	public void setPaquetDeCarte(ArrayList<Carte> paquetDeCarte) {
		this.paquetDeCarte = paquetDeCarte;
	}

	/**
	 * Renvoie une carte aleatoirement choisie dans le paquet
	 * @return un carte
	 */
	public Carte getRandomCarte() {
		if (paquetDeCarte.size() > 0) {
			Random rand = new Random();
			int indexCarte = rand.nextInt(paquetDeCarte.size());
			if (this.paquetDeCarte.size() > 0) {
				Carte carte = paquetDeCarte.get(indexCarte);
				paquetDeCarte.remove(indexCarte);
				return carte;
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Recupere une carte du paquet en fonction de ces caracteristiques
	 * @return la carte voulue
	 */
	public Carte recupererCarte(String forme, String couleur, boolean rempli) {
		Iterator<Carte> it = paquetDeCarte.iterator();

		//les cartes jusqu'a ce qu'il trouve celle qui correspond
		while(it.hasNext()) {
			Carte carte = it.next();
			if ( (carte.getCouleur() == couleur) && (carte.getForme() == forme) && (carte.isRempli() == rempli) ) {
				paquetDeCarte.remove(carte);
				return carte;
			}
		}
		return null;
	}

}
