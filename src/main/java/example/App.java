package example;

import example.controller.Controller;
import example.model.Client;
import example.view.MainFrame;


public class App {
    public static void main(String[] args) {
        int base = 1234;
        int port = 1234;
        MainFrame mainFrame = new MainFrame(base);
        Client client = new Client(port);
        Controller controller = new Controller(mainFrame, client,base);
    }


}
