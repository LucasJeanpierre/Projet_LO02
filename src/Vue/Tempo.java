package Vue;

import javax.swing.JCheckBox;

public class Tempo implements Runnable {
    private JCheckBox checkbox;
    private Thread t;
    private int y;

    public Tempo(JCheckBox checkbox) {
        this.checkbox = checkbox;
        this.t = new Thread(this);
        this.y = 0;
        t.start();
    }

    public void run() {
        while (true) {
            this.checkbox.setBounds(26, this.y, 100, 20);
            y++;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
