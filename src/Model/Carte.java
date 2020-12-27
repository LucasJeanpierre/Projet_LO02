/*
*
*
*/

package Model;

import View.*;

import javax.swing.*;
import java.util.Random;

public class Carte extends Observable{
	
	
	private String forme;
	private boolean rempli;
	private String couleur;
	private ImagePanel image;
	private JFrame frame;
	
	public void poser() {
		
	}
	
	public void estPosee() {
		
	}

	public JFrame getFrame() {
		return this.frame;
	}
	

	public String getForme() {
		return forme;
	}

	public void setForme(String forme) {
		this.forme = forme;
	}

	public boolean isRempli() {
		return rempli;
	}

	public void setRempli(boolean rempli) {
		this.rempli = rempli;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	public String toString() {
		//return "Je suis une carte avec les parametres suivant -> <couleur:" + this.couleur + ">,<rempli:" + this.rempli + ">,<forme:" + this.forme + ">";
		return "<carte:forme="+this.forme+",couleur="+ this.couleur + ",rempli=" + this.rempli + ">";
	}
	
	public void afficher() {
		System.out.println(this.toString());
	}

	public ImagePanel getImage() {
		return this.image;
	}

	public void setImageCoord(int x, int y) {
		this.image.setBounds(x,y,50,50);
	}


	
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
		this.image = new ImagePanel(name);
		System.out.println(rand.nextInt(500));
        this.image.setBounds(rand.nextInt(500), rand.nextInt(500),500, 700);
		//this.image.setVisible(true);
	}

	public Carte(String forme, String couleur, boolean rempli, JFrame frame) {
		this.frame = frame;
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
		this.image = new ImagePanel(name);
		//System.out.println(rand.nextInt(500));
		//int x = rand.nextInt(500);
		this.image.setBounds(rand.nextInt(500), rand.nextInt(500),50, 50);
		//this.image.setBounds(250,250,50,50);
        this.image.setVisible(false);
		this.frame.getContentPane().add(this.image);
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
