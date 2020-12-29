package View;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel{

    //private BufferedImage image;
    private Image image;

    public ImagePanel(String nom) {
       try {                
          image = ImageIO.read(new File("data/" + nom + ".png"));
          image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
       } catch (IOException ex) {
            // handle exception...
       }
    }

    public void changeImage(String nom) {
        try {                
            image = ImageIO.read(new File("data/" + nom + ".png"));
            image = image.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
         } catch (IOException ex) {
              // handle exception...
         }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

}