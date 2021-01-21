package fr.utt.sit.lo02.projet.model;

import fr.utt.sit.lo02.projet.view.ImagePanel;
/**
 * Class CarteCachee
 * Herite de carte
 * redefini l'affichage carte la carte doit être afficher de dos
 */
public class CarteCachee extends Carte {
	
	private ImagePanel dos;
	
	/**
	 * Constructeur a partir d'un carte
	 * @param carte la carte d'origine
	 */
	public CarteCachee(Carte carte) {
		super(carte.getForme(), carte.getCouleur(), carte.isRempli(), carte.getFrame(), carte.getModePlateauLibre());
		this.dos = new ImagePanel("dos",carte.getModePlateauLibre());
		//this.dos.setBounds(rand.nextInt(500), rand.nextInt(500),50, 50);
		this.dos.setBounds(250,250,50,50);
        this.dos.setVisible(false);
		super.getFrame().getContentPane().add(this.dos);
	}
	
	/**
	 * Method toString
	 */
	public String toString() {
		//return "Je suis une carte Cachee";
		return "<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">" + "cachee";
	}

	/**
	 * redefinition de la mothod mere
	 * cette fois ci on revoie le dos de la carte pour qu'elle reste cachee
	 * @return ImagePanel dos
	 */
	public ImagePanel getImage() {
		return this.dos;
	}

	/**
	 * Permet de placer la carte (de dos) aux coordonnes voulue
	 * @param x
	 * @param y
	 */
	public void setImageCoord(int x, int y) {
		this.dos.setBounds(x,y,50,50);
	}

	/**
	 * Permet de devoiler la carte cachee en recuperant sa "vrai" face
	 * @return ImagePanel
	 */
	public ImagePanel getImageDevoile() {
		return super.image;
	}

	public void setImageDevoileCoord(int x, int y) {
		if (super.getModePlateauLibre()) {
			super.image.setBounds(x,y,25,25);
		} else {
			super.image.setBounds(x,y,50,50);
		}
		;
	}
	
	/**
	 * Revele les caracteristque de la carte dans la console
	 */
	public void reveler() {
		System.out.println("<carte:forme="+super.getForme() +",couleur="+ super.getCouleur() + ",rempli=" + super.isRempli() + ">");
	}



}
