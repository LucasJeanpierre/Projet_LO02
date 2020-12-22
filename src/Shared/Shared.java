package Shared;

import Model.*;

public class Shared {
    private Carte carte;
    private CarteCachee carteCachee;
    private String string;

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
}
