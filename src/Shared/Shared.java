package Shared;

import Model.*;
import java.util.*;

public class Shared {
    private Carte carte;
    private CarteNormal carteNormal;
    private CarteCachee carteCachee;
    private CarteVictoire cartreVictoire;
    private Joueur joueur;
    private Coordonee coordonee;
    private ArrayList<Coordonee> listCoord;
    private String string;
    private int intShared;


    public Shared() {

    }

    public Carte getCarte() {
        return this.carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public CarteCachee getCarteCachee() {
        return this.carteCachee;
    }

    public void setCarteCachee(CarteCachee carteCachee) {
        this.carteCachee = carteCachee;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public CarteVictoire getCartreVictoire() {
        return this.cartreVictoire;
    }

    public void setCartreVictoire(CarteVictoire cartreVictoire) {
        this.cartreVictoire = cartreVictoire;
    }

    public Joueur getJoueur() {
        return this.joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Coordonee getCoordonee() {
        return this.coordonee;
    }

    public void setCoordonee(Coordonee coordonee) {
        this.coordonee = coordonee;
    }

    public int getIntShared() {
        return this.intShared;
    }

    public void setIntShared(int intSharerd) {
        this.intShared = intSharerd;
    }

    public CarteNormal getCarteNormal() {
        return this.carteNormal;
    }

    public void setCarteNormal(CarteNormal carteNormal) {
        this.carteNormal = carteNormal;
    }

    public ArrayList<Coordonee> getListCoord() {
        return this.listCoord;
    }

    public void setListCoord(ArrayList<Coordonee> listCoord) {
        this.listCoord = listCoord;
    }

}
