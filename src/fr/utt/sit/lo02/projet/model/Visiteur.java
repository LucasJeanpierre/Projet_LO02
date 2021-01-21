package fr.utt.sit.lo02.projet.model;

import java.util.*;

/**
 * Class visiteur
 */
public class Visiteur {

    private ArrayList<Coordonee> listeCoord;
    private Carte carteVictoire;

    /**
     * Visite le plateau 
     * @param p le plateau
     */
    public void visit(Plateau p) {
        this.listeCoord = p.getListeCoord();
    }

    /**
     * visite le joueur
     * @param j le joueur
     */
    public void visit(Joueur j) {
        this.carteVictoire = j.getCarteVictoire();
    }

    /**
     * Recupere une coord en fonction de sa position
     * @param x coord x 
     * @param y coord y
     * @return la coord
     */
    private Coordonee recupererCoord(int x, int y) {
        Iterator<Coordonee> it = listeCoord.iterator();

        // parcour les coordonee jusqu'a ce qu'il trouve celle correspondante
        while (it.hasNext()) {
            Coordonee coord = it.next();
            if ((coord.getPositionX() == x) && (coord.getPositionY() == y)) {
                return coord;
            }
        }
        System.out.println(x +"  "+y);
        return null;
    }
    /**
     * 
     * @param j le joueur
     * @return la carte victoire du joueur
     */
    private void getCarteVictoire(Joueur j) {
        this.visit(j);
    }

    public int compterLesPoints(Joueur j) {

        this.getCarteVictoire(j);

        // on récupère les caractéristique de la carte victoire
        String forme = this.carteVictoire.getForme();
        String couleur = this.carteVictoire.getCouleur();
        boolean rempli = this.carteVictoire.isRempli();

        int nbRecurenceForme = 0;
        int nbRecurenceCouleur = 0;
        int nbRecurenceRempli = 0;

        int nbRecurenceFormeMax = 0;
        int nbRecurenceCouleurMax = 0;
        int nbRecurenceRempliMax = 0;

        int score = 0;

        // test des point sur la premi�re ligne
        // pour chaque carte de la ligne on regarde si elle correspond a une
        // caract�ritique de la carte victoire
        // si oui on incr�mente le nombre de carte d'affil� qui ont cette caract�ritique
        // si non on repart de 0
        // a la fin on regarde quel est le maximum de nbReference ce qui d�termine le
        // nombre de carte d'affill� possedant une caract�ritique de la carte victoire

        // calcul du score pour toute les lignes
        for (int y = 0; y < 3; y++) {
            nbRecurenceForme = 0;
            nbRecurenceCouleur = 0;
            nbRecurenceRempli = 0;

            nbRecurenceFormeMax = 0;
            nbRecurenceCouleurMax = 0;
            nbRecurenceRempliMax = 0;

            for (int x = 0; x < 5; x++) {
                Coordonee coord = recupererCoord(x, y);
                Carte carte = coord.getCarte();

                // calcul pour les formes
                if (carte.getForme() == forme) {
                    nbRecurenceForme++;
                    if (nbRecurenceForme > nbRecurenceFormeMax) {
                        nbRecurenceFormeMax = nbRecurenceForme;
                    }
                } else {
                    nbRecurenceForme = 0;
                }

                if (carte.getCouleur() == couleur) {
                    nbRecurenceCouleur++;
                    if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
                        nbRecurenceCouleurMax = nbRecurenceCouleur;
                    }
                } else {
                    nbRecurenceCouleur = 0;
                }

                if (carte.isRempli() == rempli) {
                    nbRecurenceRempli++;
                    if (nbRecurenceRempli > nbRecurenceRempliMax) {
                        nbRecurenceRempliMax = nbRecurenceRempli;
                    }
                } else {
                    nbRecurenceRempli = 0;
                }
            }

            /*
             * System.out.println("forme  :" +nbRecurenceFormeMax);
             * System.out.println("couleur : "+nbRecurenceCouleurMax);
             * System.out.println("rempli : "+nbRecurenceRempliMax);
             */

            if (nbRecurenceFormeMax > 1) {
                score += nbRecurenceFormeMax - 1;
            }

            if (nbRecurenceCouleurMax > 2) {
                score += nbRecurenceCouleurMax + 1;
            }

            if (nbRecurenceRempliMax > 2) {
                score += nbRecurenceRempliMax;
            }
            /* System.out.println("score : " + score); */
        }

        // calculer score colonnes

        for (int x = 0; x < 5; x++) {
            nbRecurenceForme = 0;
            nbRecurenceCouleur = 0;
            nbRecurenceRempli = 0;

            nbRecurenceFormeMax = 0;
            nbRecurenceCouleurMax = 0;
            nbRecurenceRempliMax = 0;

            for (int y = 0; y < 3; y++) {
                Coordonee coord = recupererCoord(x, y);
                Carte carte = coord.getCarte();

                // calcul pour les formes
                if (carte.getForme() == forme) {
                    nbRecurenceForme++;
                    if (nbRecurenceForme > nbRecurenceFormeMax) {
                        nbRecurenceFormeMax = nbRecurenceForme;
                    }
                } else {
                    nbRecurenceForme = 0;
                }

                if (carte.getCouleur() == couleur) {
                    nbRecurenceCouleur++;
                    if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
                        nbRecurenceCouleurMax = nbRecurenceCouleur;
                    }
                } else {
                    nbRecurenceCouleur = 0;
                }

                if (carte.isRempli() == rempli) {
                    nbRecurenceRempli++;
                    if (nbRecurenceRempli > nbRecurenceRempliMax) {
                        nbRecurenceRempliMax = nbRecurenceRempli;
                    }
                } else {
                    nbRecurenceRempli = 0;
                }
            }

            /*
             * System.out.println("forme  :" +nbRecurenceFormeMax);
             * System.out.println("couleur : "+nbRecurenceCouleurMax);
             * System.out.println("rempli : "+nbRecurenceRempliMax);
             */

            if (nbRecurenceFormeMax > 1) {
                score += nbRecurenceFormeMax - 1;
            }

            if (nbRecurenceCouleurMax > 2) {
                score += nbRecurenceCouleurMax + 1;
            }

            if (nbRecurenceRempliMax > 2) {
                score += nbRecurenceRempliMax;
            }
            /* System.out.println("score : " + score); */
        }

        return score;
    }

    /**
     * Compte les points d'un joueur
     * @param j le joueur
     * @param modePlateauLibre le mode de jeu
     * @return le score du joueur
     */
    public int compterLesPointsAlternatif(Joueur j, boolean modePlateauLibre) {
        int nbcartesx;
        int nbcartesy;
        if (modePlateauLibre) {
            nbcartesx = 10;
            nbcartesy = 10;
        } else {
            nbcartesx = 5;
            nbcartesy = 3;
        }
        

        this.getCarteVictoire(j);
        //System.out.println(this.carteVictoire);

        // on récupère les caractéristique de la carte victoire
        String forme = this.carteVictoire.getForme();
        String couleur = this.carteVictoire.getCouleur();
        boolean rempli = this.carteVictoire.isRempli();

        int nbRecurenceForme = 0;
        int nbRecurenceCouleur = 0;
        int nbRecurenceRempli = 0;

        int nbRecurenceFormeMax = 0;
        int nbRecurenceCouleurMax = 0;
        int nbRecurenceRempliMax = 0;

        int score = 0;

        // test des point sur la premi�re ligne
        // pour chaque carte de la ligne on regarde si elle correspond a une
        // caract�ritique de la carte victoire
        // si oui on incr�mente le nombre de carte d'affil� qui ont cette caract�ritique
        // si non on repart de 0
        // a la fin on regarde quel est le maximum de nbReference ce qui d�termine le
        // nombre de carte d'affill� possedant une caract�ritique de la carte victoire

        // calcul du score pour toute les lignes
        for (int y = 0; y < nbcartesy; y++) {
            nbRecurenceForme = 0;
            nbRecurenceCouleur = 0;
            nbRecurenceRempli = 0;

            nbRecurenceFormeMax = 0;
            nbRecurenceCouleurMax = 0;
            nbRecurenceRempliMax = 0;

            for (int x = 0; x < nbcartesx; x++) {
                Coordonee coord = recupererCoord(x, y);
                if (coord.getCarte() != null) {
                    Carte carte = coord.getCarte();

                    // calcul pour les formes
                    if (carte.getForme() == forme) {
                        nbRecurenceForme++;
                        if (nbRecurenceForme > nbRecurenceFormeMax) {
                            nbRecurenceFormeMax = nbRecurenceForme;
                        }
                    } else {
                        nbRecurenceForme = 0;
                    }

                    if (carte.getCouleur() == couleur) {
                        nbRecurenceCouleur++;
                        if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
                            nbRecurenceCouleurMax = nbRecurenceCouleur;
                        }
                    } else {
                        nbRecurenceCouleur = 0;
                    }

                    if (carte.isRempli() == rempli) {
                        nbRecurenceRempli++;
                        if (nbRecurenceRempli > nbRecurenceRempliMax) {
                            nbRecurenceRempliMax = nbRecurenceRempli;
                        }
                    } else {
                        nbRecurenceRempli = 0;
                    }
                }
            }

            /*
             * System.out.println("forme  :" +nbRecurenceFormeMax);
             * System.out.println("couleur : "+nbRecurenceCouleurMax);
             * System.out.println("rempli : "+nbRecurenceRempliMax);
             */

            if (nbRecurenceFormeMax > 1) {
                score += nbRecurenceFormeMax - 1;
            }

            if (nbRecurenceCouleurMax > 2) {
                score += nbRecurenceCouleurMax + 1;
            }

            if (nbRecurenceRempliMax > 2) {
                score += nbRecurenceRempliMax;
            }
            /* System.out.println("score : " + score); */
        }

        // calculer score colonnes

        for (int x = 0; x < nbcartesx; x++) {
            nbRecurenceForme = 0;
            nbRecurenceCouleur = 0;
            nbRecurenceRempli = 0;

            nbRecurenceFormeMax = 0;
            nbRecurenceCouleurMax = 0;
            nbRecurenceRempliMax = 0;

            for (int y = 0; y < nbcartesy; y++) {
                Coordonee coord = recupererCoord(x, y);
                if (coord.getCarte() != null) {
                    Carte carte = coord.getCarte();

                    // calcul pour les formes
                    if (carte.getForme() == forme) {
                        nbRecurenceForme++;
                        if (nbRecurenceForme > nbRecurenceFormeMax) {
                            nbRecurenceFormeMax = nbRecurenceForme;
                        }
                    } else {
                        nbRecurenceForme = 0;
                    }

                    if (carte.getCouleur() == couleur) {
                        nbRecurenceCouleur++;
                        if (nbRecurenceCouleur > nbRecurenceCouleurMax) {
                            nbRecurenceCouleurMax = nbRecurenceCouleur;
                        }
                    } else {
                        nbRecurenceCouleur = 0;
                    }

                    if (carte.isRempli() == rempli) {
                        nbRecurenceRempli++;
                        if (nbRecurenceRempli > nbRecurenceRempliMax) {
                            nbRecurenceRempliMax = nbRecurenceRempli;
                        }
                    } else {
                        nbRecurenceRempli = 0;
                    }
                }
            }

            /*
             * System.out.println("forme  :" +nbRecurenceFormeMax);
             * System.out.println("couleur : "+nbRecurenceCouleurMax);
             * System.out.println("rempli : "+nbRecurenceRempliMax);
             */

            if (nbRecurenceFormeMax > 1) {
                score += nbRecurenceFormeMax - 1;
            }

            if (nbRecurenceCouleurMax > 2) {
                score += nbRecurenceCouleurMax + 1;
            }

            if (nbRecurenceRempliMax > 2) {
                score += nbRecurenceRempliMax;
            }
            /* System.out.println("score : " + score); */
        }
        //System.out.println(score);
        //System.out.println("-----------------");
        return score;
    }

}
