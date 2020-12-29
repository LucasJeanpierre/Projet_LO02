package Model;

import View.ImagePanel;

public class CarteNormal extends Carte {

    public CarteNormal(Carte carte) {
		super(carte.getForme(), carte.getCouleur(), carte.isRempli(), carte.getFrame());
    }
    
    public ImagePanel getImage() {
        return super.getImage();
    }

    public void setImageCoord(int x, int y) {
		super.image.setBounds(x,y,50,50);
    }

	
	public String toString() {
		//return "Bonsoir a tous";
		return "<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">";
	}
	
}
