/*
*
*
*/

package fr.utt.sit.lo02.projet.model;

import fr.utt.sit.lo02.projet.view.*;

import javax.swing.*;

/**
 * Class carte
 * Les cartes sont les obets que l'on va poser sur le plateau
 * elles sont caracteriser par :
 * une couleur
 * une forme 
 * si elle sont rempli ou non
 * 
 */

public class Carte {
	
	
	private String forme;
	private boolean rempli;
	private String couleur;
	public ImagePanel image;
	private JFrame frame;
	private boolean modePlateauLibre;
	

	/**
	 * Methode pour recuperer la frame sur laquel la carte est afficher
	 * @return frame
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Methode pour recupere la forme de la carte
	 * @return String forme
	 */
	public String getForme() {
		return forme;
	}

	/**
	 * Methode pour definire la forme de la carte
	 * @param forme
	 */
	public void setForme(String forme) {
		this.forme = forme;
	}

	/**
	 * Methode pour recuperer si la carte est rempli sous la forme d'un boolean
	 * @return remlpi
	 */
	public boolean isRempli() {
		return rempli;
	}

	/**
	 * Methode pour definir le remplissage de la carte
	 * @param rempli
	 */
	public void setRempli(boolean rempli) {
		this.rempli = rempli;
	}
	/**
	 * Methode pour recuperer la couleur de la carte
	 * @return String couleur
	 */
	public String getCouleur() {
		return couleur;
	}

	/**
	 * Method pour defifnir la couleur de la carte
	 * @param couleur la couleur (String)
	 */
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * Method to String
	 * retourn un String avce les caracteristiques de la carte
	 */
	public String toString() {
		//return "Je suis une carte avec les parametres suivant -> <couleur:" + this.couleur + ">,<rempli:" + this.rempli + ">,<forme:" + this.forme + ">";
		return "<carte:forme="+this.forme+",couleur="+ this.couleur + ",rempli=" + this.rempli + ">";
	}
	
	/**
	 * Affiche la carte dans la console
	 */
	public void afficher() {
		System.out.println(this.toString());
	}

	/**
	 * Permet de recuperer l'image de la carte
	 * @return image l'image de la carte
	 */
	public ImagePanel getImage() {
		return this.image;
	}

	/**
	 * Permet de placer l'image de la carte
	 * @param x la coordonee x
	 * @param y la coordonee y
	 */
	public void setImageCoord(int x, int y) {
		this.image.setBounds(x,y,50,50);
	}

	/**
	 * Permet de savoir si le plateau est en modeLibre et donc les cartes doivent être petites
	 * @return modePlateauLibre boolean
	 */
	public boolean getModePlateauLibre() {
		return this.modePlateauLibre;
	}


	/**
	 * Constructeur de la carte
	 * @param forme la forme
	 * @param couleur la couleur
	 * @param rempli rempliisage
	 * @param frame la frame dans laquelle la carte sera afficher
	 * @param modePlateauLibre mode du Plateau
	 * 
	 * le contructeur gere le recuperation de l'image associe a la carte et a son ajout dans la frame
	 */
	public Carte(String forme, String couleur, boolean rempli, JFrame frame, boolean modePlateauLibre) {
		this.frame = frame;
		this.forme = forme;
		this.rempli = rempli;
		this.couleur = couleur;
		this.modePlateauLibre = modePlateauLibre;
		String tempnom;
		if (this.rempli) {
			tempnom = "plein";
		} else {
			tempnom = "vide";
		}
		String name = forme + "_" + couleur + "_" + tempnom;
		this.image = new ImagePanel(name, modePlateauLibre);
		if (this.modePlateauLibre) {
			this.image.setBounds(250,250,25,25);
		} else {
			this.image.setBounds(250,250,50,50);
		}
		
        this.image.setVisible(false);
		this.frame.getContentPane().add(this.image);
	}

	


}
