package Model;

import java.util.ArrayList;
import java.util.Iterator;
import Shared.Shared;

/**
 * Class joueur IA
 * Hérite de Joueur
 */
public class JoueurIA extends Joueur implements ComportementJoueur{

	private Plateau plat;

	/**
	 * Contructeur
	 * @param plat le plateau de jeu
	 * @param shared l'objet partage
	 */
	public JoueurIA(Plateau plat, Shared shared) {
		super("Ordinateur", shared);
		this.plat = plat;
	}

	/**
	 * Choisie la coordonee ou placer la carte
	 * @param carte la carte a placer
	 * @return l'emplacement ou poser la carte (String)
	 */
	public String choisirCoordoneeAPlacer(Carte carte) {
		//return "0,0";
		ArrayList<Coordonee> listeCoord = this.plat.getListeCoord();

		Iterator<Coordonee> it = listeCoord.iterator();

		while (it.hasNext()) {
			Coordonee coord = it.next();
			//System.out.println(coord.getPositionX() + "," + coord.getPositionY());
			if (coord.getCarte() == null) {
				if (this.plat.peutPoserUneCarte(carte, coord.getPositionX(), coord.getPositionY())) {
					super.getShared().setString(coord.getPositionX() + "," + coord.getPositionY());
					return coord.getPositionX() + "," + coord.getPositionY();
				}
			}
		}

		return null;
	}


	public String choisirCarteABouger() {
		return "0";
	}


	public String decisionBougerCarte() {
		return "non";
	}
	public Carte choisirCarteAPlacer(boolean modeAvance) {
		if (!modeAvance) {
			return super.getMain().get(0);
		} else {
			return new Carte("a", "a", false);
		}
	}
	public Carte choisieCarteVictoire() {
		return super.getMain().get(0);
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
