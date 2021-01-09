package Model;

/**
 * Interface pour le comprotement du joueur IA (patron de conception Strategie)
 */
public interface ComportementJoueur {
	public String choisirCarteABouger();
	public String choisirCoordoneeAPlacer(Carte carte);
	public String decisionBougerCarte();
	public Carte choisirCarteAPlacer(boolean modeAvance);
}
