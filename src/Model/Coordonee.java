package Model;

import View.*;
import View.ImagePanel;

import java.awt.Color;

import javax.swing.*;

/**
 * Class coordonee
 * 
 */
public class Coordonee {

	private int positionX;
	private int positionY;

	private Carte carte;
	private JFrame frame;
	//private ImagePanel panel;
	private JPanel panel;

	/**
	 * Permet de recuperer la composante X de la coordonee
	 * @return positionX int
	 */
	public int getPositionX() {
		return this.positionX;
	}

	/**
	 * Permet de poser une carte sur cette coordonee
	 * @param c la carte a poser
	 */
	public void poserUneCarte(Carte c) {
		this.carte = c;
	}

	/**
	 * Permet d'enlever une carte de la coordonee
	 */
	public boolean enleverLaCarte() {
		// si la carte n'est pas une carte cach�e
		if (!(this.carte instanceof CarteCachee)) {
			this.carte = null;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Permet de definir la positionX de la coordonne
	 * @param positionX 
	 */
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	/**
	 * Permet de récuperer la carte posé sur la coordonne
	 * @return
	 */
	public Carte getCarte() {
		return this.carte;
	}

	
	/**
	 * Permet de recuperer la composante Y de la coordonee
	 * @return positionY int
	 */
	public int getPositionY() {
		return positionY;
	}

	/**
	 * 
	 * Permet de definir la positionY de la coordonne
	 * @param positionY
	 */
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	/**
	 * Constructeur simple
	 * @param x
	 * @param y
	 */
	public Coordonee(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.carte = null;
	}

	/**
	 * Constructeur pour affichagge dans une frame
	 * @param x x
	 * @param y y
	 * @param frame la frame ou elle sera afficher
	 * @param modePlateauLibre si le plateau est en mode libre ou non
	 */
	public Coordonee(int x, int y, JFrame frame, boolean modePlateauLibre) {
		this.positionX = x;
		this.positionY = y;
		this.carte = null;
		this.frame = frame;
		//this.panel = new ImagePanel("vide");
		this.panel = new JPanel();
		if (!modePlateauLibre) {
			this.panel.setBounds(x * 100, y * 100, 50, 50);
		} else {
			this.panel.setBounds(x * 50, y * 50, 25, 25);
		}
		this.panel.setVisible(true);
		this.panel.setBackground(new Color(0,0,0,25));
		this.frame.getContentPane().add(this.panel);
		// this.panel.addMouseListener();
	}

	/**
	 * Permet de récuperer le panel de la coordonee
	 * @return Jpanel
	 */
	public JPanel getPanel() {
		return this.panel;
	}

	/**
	 * Method toString
	 * retounr une chaine de caractère avec les information de la coordonee
	 */
	public String toString() {
		// return "Je une coordonee plac� en -> <x:" + this.positionX + ">,<y:" +
		// this.positionY +">";
		return "<coordonee:x=" + this.positionX + ",y=" + this.positionY + ">";
	}

	/**
	 * Affiche la coordonne dans la console
	 */
	public void afficher() {
		System.out.println(this.toString());
	}


}
