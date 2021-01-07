/*
*
*
*/

package Model;

import View.*;

import javax.swing.*;
import java.util.Random;

/**
 * Class carte
 * Les cartes sont les obets que l'on va poser sur le plateau
 * elles sont caractériser par :
 * une couleur
 * une forme 
 * si elle sont rempli ou non
 * 
 */

public class Carte extends Observable{
	
	
	private String forme;
	private boolean rempli;
	private String couleur;
	public ImagePanel image;
	private JFrame frame;
	private boolean modePlateauLibre;
	
	public void poser() {
		
	}
	
	public void estPosee() {
		
	}

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
	 * Methode pour définire la forme de la carte
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
	 * Methode pour récuperer la couleur de la carte
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
	 * retourn un String avce les caractéristiques de la carte
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
	 * Permet de récuperer l'image de la carte
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
	 * Constructeur de la class Carte sans affichage avec swing
	 * @param forme
	 * @param couleur
	 * @param rempli
	 */
	public Carte(String forme, String couleur, boolean rempli) {
		Random rand = new Random();
		this.forme = forme;
		this.rempli = rempli;
		this.couleur = couleur;
		String tempnom;
		if (this.rempli) {
			tempnom = "plein";
		} else {
			tempnom = "vide";
		}
		String name = forme + "_" + couleur + "_" + tempnom;
		//this.image = new ImagePanel(name);
		System.out.println(rand.nextInt(500));
        this.image.setBounds(rand.nextInt(500), rand.nextInt(500),500, 700);
		//this.image.setVisible(true);
	}

	/**
	 * Constructeur de la carte
	 * @param forme la forme
	 * @param couleur la couleur
	 * @param rempli rempliisage
	 * @param frame la frame dans laquelle la carte sera afficher
	 * @param modePlateauLibre mode du Plateau
	 * 
	 * le contructeur gère le récupération de l'image associé a la carte et a son ajout dans la frame
	 */
	public Carte(String forme, String couleur, boolean rempli, JFrame frame, boolean modePlateauLibre) {
		this.frame = frame;
		Random rand = new Random();
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
		//System.out.println(rand.nextInt(500));
		//int x = rand.nextInt(500);
		//this.image.setBounds(rand.nextInt(500), rand.nextInt(500),50, 50);
		if (this.modePlateauLibre) {
			this.image.setBounds(250,250,25,25);
		} else {
			this.image.setBounds(250,250,50,50);
		}
		
        this.image.setVisible(false);
		this.frame.getContentPane().add(this.image);
	}

	


}
