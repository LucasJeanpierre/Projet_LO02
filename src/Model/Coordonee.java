package Model;

import Vue.*;

public class Coordonee extends Observable{
	
	private int positionX;
	private int positionY;
	
	private Carte carte;
	
	
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public void poserUneCarte(Carte c) {
		this.carte = c;
		super.setChanged();
		super.notifyObservers();
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

	public Coordonee(int x, int y, Observer o) {
		this.positionX = x;
		this.positionY = y;
		this.carte = null;
		super.addObserver(o);
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
