package Model;

import java.util.ArrayList;
import java.util.Iterator;
import Shared.Shared;

/**
 * Class joueur IA
 * Herite de Joueur
 * 
 * Cette IA regarde toute les coordonee du plateau
 * Si on peut poser une carte sur la coordonee teste, l'IA la pose
 */
public class JoueurIA extends Joueur implements ComportementJoueur {

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
		ArrayList<Coordonee> listeCoord = this.plat.getListeCoord();

		Iterator<Coordonee> it = listeCoord.iterator();

		while (it.hasNext()) {
			Coordonee coord = it.next();
			if (coord.getCarte() == null) {
				if (this.plat.peutPoserUneCarte(carte, coord.getPositionX(), coord.getPositionY())) {
					super.getShared().setString(coord.getPositionX() + "," + coord.getPositionY());
					return coord.getPositionX() + "," + coord.getPositionY();
				}
			}
		}

		return null;
	}


}
