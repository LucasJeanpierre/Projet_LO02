/*
*
*
*/

package Model;

import View.Observable;

public class Carte extends Observable{
	
	
	private String forme;
	private boolean rempli;
	private String couleur;
 
	
	public void poser() {
		
	}
	
	public void estPosee() {
		
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


	
	public Carte(String forme, String couleur, boolean rempli) {
		this.forme = forme;
		this.rempli = rempli;
		this.couleur = couleur;
		
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
