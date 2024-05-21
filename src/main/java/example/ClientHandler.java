package example;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private static ArrayList<JSONObject> sharedObjects = new ArrayList<>();
    private static HashMap<String, Integer> primaryIndex = new HashMap<>();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String receivedObject;
            while ((receivedObject = requestReader.readLine()) != null) {
                 if (receivedObject.equals("getPrimaryIndex")) {
                    // Если клиент запросил PrimaryIndex, отправляем его
                    synchronized (primaryIndex) {
                        responseWriter.println(primaryIndex);
                        responseWriter.flush();
                    }
                } else if (receivedObject.matches("\\d+")) {
                    // Если клиент запросил JSON-объект по индексу, отправляем его
                    int index = Integer.parseInt(receivedObject);
                    synchronized (sharedObjects) {
                        if (index >= 0 && index < sharedObjects.size()) {
                            responseWriter.println(sharedObjects.get(index));
                        } else {
                            responseWriter.println("null"); // Отправляем null, если индекс недопустим
                        }
                        responseWriter.flush();
                    }
                } else {
                    // Получение JSON-объекта от клиента
                    JSONObject receivedJSONObject = new JSONObject(receivedObject);

                    // Получение имени объекта из JSON
                    String objectName = receivedJSONObject.getString("name");

                    // Добавление объекта в общий список
                    synchronized (sharedObjects) {
                        sharedObjects.add(receivedJSONObject);
                    }

                    // Добавление связи имени объекта и его номера в ArrayList
                    synchronized (primaryIndex) {
                        primaryIndex.put(objectName, sharedObjects.size() - 1);
                    }

                    // Отправляем клиенту подтверждение получения JSON-объекта
                    responseWriter.println("JSON-объект успешно получен на сервере.");
                }
            }
            responseWriter.close();
            requestReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
