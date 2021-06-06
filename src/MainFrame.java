import javax.swing.*;

public class MainFrame extends JFrame {

    private InvadersPanel invaderPanel;

    MainFrame(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Games");
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        invaderPanel = new InvadersPanel();
        this.add(invaderPanel);

        this.pack();
        this.setVisible(true);
    }
}
