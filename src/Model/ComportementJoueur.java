package Model;

public interface ComportementJoueur {
	public String choisirCarteABouger();
	public String choisirCoordoneeAPlacer(Carte carte);
	public String decisionBougerCarte();
	public Carte choisirCarteAPlacer(boolean modeAvance);
	public Carte choisieCarteVictoire();
}
