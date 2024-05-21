package example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class Panel extends JPanel {
    private JLabel portTextField;
    private JButton buttonOpenImage;
    private JButton buttonSend;

    private JButton buttonReceiveMap;
    private JButton buttonReceiveJson;

    private JFileChooser fileChooser;
    private JLabel image;

    private JLabel imageName;
    private JLabel port;

    public Panel(int serverPort) {
        super(); // вызываем конструктор суперкласса
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        // инициализируем компоненты
        port = new JLabel("base");
        portTextField = new JLabel(Integer.toString(serverPort));
        portTextField.setPreferredSize(new Dimension(100, 25));


        buttonOpenImage = new JButton("Open Image");
        buttonSend = new JButton("Send Image");
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/main/resources/" + serverPort));
        image = new JLabel();
        imageName = new JLabel("ImageName");
        buttonReceiveMap = new JButton("Get ids");
        buttonReceiveJson = new JButton("Get Image");



        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(port, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(portTextField, gbc);


        gbc.gridx = 2;
        gbc.gridy = 2;
        this.add(buttonSend, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(fileChooser, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(buttonOpenImage, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(imageName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        this.add(image, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(buttonReceiveMap, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = 1;
        this.add(buttonReceiveJson, gbc);

        this.repaint();
    }

    public void addListenerFileChooser(ActionListener actionListener) {
        buttonOpenImage.addActionListener(actionListener);
    }


    public void addListenerSend(ActionListener actionListener) {
        buttonSend.addActionListener(actionListener);
    }

    public void addListenerReceiveMap(ActionListener actionListener) {
        buttonReceiveMap.addActionListener(actionListener);
    }
    public void addListenerReceiveJson(ActionListener actionListener) {
        buttonReceiveJson.addActionListener(actionListener);
    }

    public void setImage() {
        ImageIcon imageIcon = new ImageIcon(fileChooser.getSelectedFile().getPath());
        String fileName = (fileChooser.getSelectedFile().getName());
        image.setIcon(imageIcon);
        imageName.setText(fileName);
        this.repaint();
    }

    public File getFile() {
        return fileChooser.getSelectedFile();
    }

    public void reloadFileChooser(){
        fileChooser.rescanCurrentDirectory();
    }
}