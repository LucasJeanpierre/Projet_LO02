package fr.utt.sit.lo02.projet.view;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class ImagePanel
 */

public class ImagePanel extends JPanel {

    /**
    *
    */
    private static final long serialVersionUID = 1L;
    private Image image;

    /**
     * Constructeur
     * 
     * @param nom              le nom de l'image voulue
     * @param modePlateauLibre le mode de jeu
     */
    public ImagePanel(String nom, boolean modePlateauLibre) {
        try {
            image = ImageIO.read(new File("data/" + nom + ".png"));
            if (modePlateauLibre) {
                image = image.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
            } else {
                image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Change l'image de la carte en cours de partie utile lors de la révalation de
     * la carte cachee en fin de partie
     * 
     * @param nom
     */
    public void changeImage(String nom) {
        try {
            image = ImageIO.read(new File("data/" + nom + ".png"));
            image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            repaint();
        } catch (IOException ex) {
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}