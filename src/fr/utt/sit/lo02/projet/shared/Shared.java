package fr.utt.sit.lo02.projet.shared;

import fr.utt.sit.lo02.projet.model.*;
import java.util.*;


/***
 * Class Shared
 * Shared est un objet avec une régérence a chaque type d'ojbet utiliser dans le programe
 * Shared est utilisé pour transférer des objet et/ou et listes d'objets lors de la communication entre les partie du model MVC
 * 
 */
public class Shared {
    private Carte carte;
    private CarteNormal carteNormal;
    private CarteCachee carteCachee;
    private Joueur joueur;
    private Coordonee coordonee;
    private ArrayList<Coordonee> listCoord;
    private String string;
    private int intShared;
    private Queue<Joueur> listJoueur;
    private ArrayList<Carte> listCarte;


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

    public Queue<Joueur> getListJoueur() {
        return this.listJoueur;
    }

    public void setListJoueur(Queue<Joueur> listJoueur) {
        this.listJoueur = listJoueur;
    }

    public ArrayList<Carte> getListCarte() {
        return this.listCarte;
    }

    public void setListCarte(ArrayList<Carte> listCarte) {
        this.listCarte = listCarte;
    }

}
