package com.example.goldfinder.client;

import java.io.*;
import java.net.*;

import com.example.goldfinder.Controller;
import com.example.goldfinder.utils.move;

import javafx.scene.shape.SVGPath;

public class Client {
    private static final String serverHost = "localhost";
    private static final int serverPort = 1234;
    private String name;
    BufferedReader in;
    PrintWriter out;

    public Client(String name) {

        try {
            Socket socket = new Socket(serverHost, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            send("GAME_JOIN:" + name);
            send("GAME_START");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendDirection(String direction) {
        send("MOVE:" + direction);
        String response = receive();
        return response;
    }

    public String sendSurroudingInfo(){
        send("SURROUNDING");
        String response = receive();
        return response;
    }

}
