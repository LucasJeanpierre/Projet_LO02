package Model;


public class CarteCachee extends Carte {
	
	/*public CarteCachee(String forme, String couleur, boolean rempli) {
		super(forme, couleur, rempli);
	}*/
	
	public CarteCachee(Carte carte) {
		super(carte.getForme(), carte.getCouleur(), carte.isRempli(), carte.getFrame());
	}
	
	public String toString() {
		//return "Je suis une carte Cachee";
		return "<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">" + "cachee";
	}
	
	public void reveler() {
		System.out.println("<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
