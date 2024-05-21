package example.model;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Client {
    private String serverAddress = "127.0.0.1"; // Адрес сервера
    private int serverPort;

    public Client(int port) {
        serverPort = port;
    }

    public void sendJSONObject(JSONObject jsonObject) {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(jsonObject.toString());

            // Получаем ответ от сервера
            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + serverAddress);
        }
    }

    public HashMap<String, Integer> getPrimaryIndex() {
        HashMap<String, Integer> primaryIndex = null;
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Отправляем запрос на получение PrimaryIndex на сервер
            out.println("getPrimaryIndex");

            // Получаем ответ от сервера
            String response = in.readLine();
            if (response != null && !response.equals("null")) {
                primaryIndex = new HashMap<>();
                // Преобразуем строку в HashMap
                String[] entries = response.split(",");
                for (String entry : entries) {
                    String[] keyValue = entry.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().replace("{", "");
                        int value = Integer.parseInt(keyValue[1].trim().replace("}", ""));
                        primaryIndex.put(key, value);
                    }
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Error while communicating with the server: " + e.getMessage());
        }
        return primaryIndex;
    }

    public JSONObject getJSONObjectByIndex(int index) {
        JSONObject jsonObject = null;
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Отправляем запрос на получение JSON-объекта с указанным индексом на сервер
            out.println(index);

            // Получаем ответ от сервера
            String response = in.readLine();
            if (response != null && !response.equals("null")) {
                jsonObject = new JSONObject(response);
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Error while communicating with the server: " + e.getMessage());
        }
        return jsonObject;
    }

}
