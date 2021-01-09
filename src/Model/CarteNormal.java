package Model;

import View.ImagePanel;

/**
 * Class Carte normal
 * Herite de la class Carte
 * Permet de dissocie la carte du paquet et les carte qui sont sur le plateau
 */
public class CarteNormal extends Carte {

  /**
   * Constructeur
   * @param carte
   */
  public CarteNormal(Carte carte) {
    super(carte.getForme(), carte.getCouleur(), carte.isRempli(), carte.getFrame(), carte.getModePlateauLibre());
  }

  /**
   * @return la face de la carte
   */
  public ImagePanel getImage() {
    return super.getImage();
  }

  /**
   * Permet de changer les coord de la carte
   */
  public void setImageCoord(int x, int y) {
    if (super.getModePlateauLibre()) {
      super.image.setBounds(x, y, 25, 25);
    } else {
      super.image.setBounds(x, y, 50, 50);
    }
    
  }

  /**
   * @return les informations de la carte sous forme d'une chaine de caractere
   */
  public String toString() {
    return "<carte:forme=" + super.getForme() + ",couleur=" + super.getCouleur() + ",rempli=" + super.isRempli() + ">";
  }

}
