package Shared;

import Model.*;


public class Shared {
    private Carte carte;
    private CarteCachee carteCachee;
    private CarteVictoire cartreVictoire;
    private Joueur joueur;
    private Coordonee coordonee;


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

}
