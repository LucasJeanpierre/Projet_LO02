package Model;

import View.*;
import View.ImagePanel;
import javax.swing.*;

public class Coordonee {
	
	private int positionX;
	private int positionY;
	
	private Carte carte;
	private JFrame frame;
	private ImagePanel panel;
	
	
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public void poserUneCarte(Carte c) {
		this.carte = c;
	}

	public boolean enleverLaCarte() {
		//si la carte n'est pas une carte cach�e
		if (!(this.carte instanceof CarteCachee)) {
			this.carte = null;
			return true;
		} else {
			return false;
		}
	}


	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public Carte getCarte() {
		return this.carte;
	}


	public int getPositionY() {
		return positionY;
	}



	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public Coordonee(int x, int y) {
		this.positionX = x;
		this.positionY = y;
		this.carte = null;
	}

	public Coordonee(int x, int y, JFrame frame) {
		this.positionX = x;
		this.positionY = y;
		this.carte = null;
		this.frame = frame;
		this.panel = new ImagePanel("vide");
		this.panel.setBounds(x * 100, y * 100 ,50, 50);
		this.panel.setVisible(true);
		this.frame.getContentPane().add(this.panel);
	}
	
	public String toString() {
		//return "Je une coordonee plac� en -> <x:" + this.positionX + ">,<y:" + this.positionY +">";
		return "<coordonee:x="+this.positionX+",y="+this.positionY+">";
	}
	
	public void afficher() {
		System.out.println(this.toString());
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
