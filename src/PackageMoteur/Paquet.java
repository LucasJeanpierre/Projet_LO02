package PackageMoteur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Paquet {

	private ArrayList<Carte> paquetDeCarte;
	public String[] listeCouleur = {"rouge", "bleu", "vert"};
	public String[] listeForme = {"rond", "triangle", "carre"};


	public Paquet() {
		this.paquetDeCarte = new ArrayList<Carte>();

		//cr�ation des cartes
		for (int icouleur = 0; icouleur < listeCouleur.length; icouleur ++) {
			for (int iforme = 0; iforme < listeForme.length; iforme ++) {
				paquetDeCarte.add(new Carte(listeForme[iforme], listeCouleur[icouleur], true));
				paquetDeCarte.add(new Carte(listeForme[iforme], listeCouleur[icouleur], false));
			}
		}
	}


	public ArrayList<Carte> getPaquetDeCarte() {
		return paquetDeCarte;
	}

	public int getNombreDeCarte() {
		return paquetDeCarte.size();
	}




	public void setPaquetDeCarte(ArrayList<Carte> paquetDeCarte) {
		this.paquetDeCarte = paquetDeCarte;
	}

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




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
