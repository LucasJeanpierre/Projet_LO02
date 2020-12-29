package Model;

import View.ImagePanel;

public class CarteCachee extends Carte {
	
	/*public CarteCachee(String forme, String couleur, boolean rempli) {
		super(forme, couleur, rempli);
	}*/

	private ImagePanel dos;
	
	public CarteCachee(Carte carte) {
		super(carte.getForme(), carte.getCouleur(), carte.isRempli(), carte.getFrame());
		this.dos = new ImagePanel("dos");
		//this.dos.setBounds(rand.nextInt(500), rand.nextInt(500),50, 50);
		this.dos.setBounds(250,250,50,50);
        this.dos.setVisible(false);
		super.getFrame().getContentPane().add(this.dos);
	}
	
	public String toString() {
		//return "Je suis une carte Cachee";
		return "<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">" + "cachee";
	}

	public ImagePanel getImage() {
		return this.dos;
	}

	public void setImageCoord(int x, int y) {
		this.dos.setBounds(x,y,50,50);
	}
	
	public void reveler() {
		System.out.println("<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">");
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
