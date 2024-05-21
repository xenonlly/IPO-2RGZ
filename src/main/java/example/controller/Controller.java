package example.controller;

import example.model.Client;
import example.model.Convertor;
import example.view.MainFrame;

import org.json.JSONObject;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


public class Controller {
    private MainFrame mainFrame;
    private Client client;
    private JSONObject json;
    private int base;

    public Controller(MainFrame mainFrame, Client client,int base) {
        this.client = client;
        this.mainFrame = mainFrame;
        this.base=base;
        mainFrame.addListenerFileChooser(new FileChooserListener());
        mainFrame.addListenerSend(new SendListener());
        mainFrame.addListenerReceiveMap(new ReceiveMapListener());
        mainFrame.addListenerReceiveJson(new ReceiveJsonListener());
    }

    private class FileChooserListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.setImage();
        }
    }


    private class SendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            json = Convertor.JpegToJson(mainFrame.getFile());
            client.sendJSONObject(json);
        }
    }

    private class ReceiveMapListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            HashMap<String, Integer> serverObjects = client.getPrimaryIndex();
            if (serverObjects != null && !serverObjects.isEmpty()) {
                String message = " Имя :  Номер в списке" + "\n";
                for (String key : serverObjects.keySet()) {
                    message += key + " : " + serverObjects.get(key) + "\n";
                }
                JOptionPane.showMessageDialog(null, message, "Server Objects", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No server objects found", "Server Objects", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private class ReceiveJsonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog(null, "Введите число:");
            json = client.getJSONObjectByIndex(Integer.parseInt(input));
            Convertor.JsonToJpeg(json.toString(),base);
            mainFrame.reloadFileChooser();
        }
    }

}
