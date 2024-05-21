package example.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    private Panel panel;

    public MainFrame(int serverPort) {
        panel = new Panel(serverPort);


        this.add(panel);
        int Width = 1000;
        int Height = 1000;
        this.setSize(Width, Height);
        this.repaint();
        this.setVisible(true);

        this.repaint();
    }

    public void addListenerFileChooser(ActionListener actionListener) {
        panel.addListenerFileChooser(actionListener);
    }


    public void addListenerSend(ActionListener actionListener) {
        panel.addListenerSend(actionListener);
    }

    public void addListenerReceiveMap(ActionListener actionListener) {
        panel.addListenerReceiveMap(actionListener);
    }
    public void addListenerReceiveJson(ActionListener actionListener) {
        panel.addListenerReceiveJson(actionListener);
    }


    public void setImage() {
        panel.setImage();
    }

    public File getFile() {
        return panel.getFile();
    }

    public void reloadFileChooser(){
        panel.reloadFileChooser();
    }
}
