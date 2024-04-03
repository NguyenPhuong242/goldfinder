package com.example.goldfinder.server;

import java.util.Random;

import com.example.goldfinder.utils.dir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class AppServer {

    public static final int ROW_COUNT = 20;
    public static final int COLUMN_COUNT = 20;
    final static int serverPort = 1234;

    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        Grid grid = new Grid(COLUMN_COUNT, ROW_COUNT, new Random());
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server is listening on port " + serverPort);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostName());
                new Thread(new AppServer.ServerHandler(clientSocket, grid)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static class ServerHandler implements Runnable {
        private BufferedReader in;
        private PrintWriter out;
        private Grid grid;
        private int playerColumn;
        private int playerRow;

        public ServerHandler(Socket socket, Grid grid) {
            try {
                this.grid = grid;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String input;
                while ((input = in.readLine()) != null) {
                    switch(input){
                        case "MOVE:UP":
                            if (playerRow > 0 && !grid.upWall(playerColumn, playerRow)) {
                                playerRow--;
                                out.println("VALID_MOVE");
                            } else {
                                out.println("INVALID_MOVE");
                            }
                            break;
                        case "MOVE:DOWN":
                            if (playerRow < ROW_COUNT - 1 && !grid.downWall(playerColumn, playerRow)) {
                                playerRow++;
                                out.println("VALID_MOVE");
                            } else {
                                out.println("INVALID_MOVE");
                            }
                            break;
                        case "MOVE:LEFT":
                            if (playerColumn > 0 && !grid.leftWall(playerColumn, playerRow)) {
                                playerColumn--;
                                out.println("VALID_MOVE");
                            } else {
                                out.println("INVALID_MOVE");
                            }
                            break;
                        case "MOVE:RIGHT":
                            if (playerColumn < COLUMN_COUNT - 1 && !grid.rightWall(playerColumn, playerRow)) {
                                playerColumn++;
                                out.println("VALID_MOVE");
                            } else {
                                out.println("INVALID_MOVE");
                            }
                            break;
                        case "SURROUNDING":
                            out.println(getSurroundingInfo(playerColumn, playerRow));
                            break;
                        default:
                            break;
                    
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getSurroundingInfo(int column, int row) {
            StringBuilder info = new StringBuilder();
            boolean hasLeftWall = grid.leftWall(column, row);
            boolean hasRightWall = grid.rightWall(column, row);
            boolean hasUpWall = grid.upWall(column, row);
            boolean hasDownWall = grid.downWall(column, row);
            boolean hasGold = grid.hasGold(column, row);


            if (hasLeftWall) {
                info.append("LEFTWALL,");
            }
            if (hasRightWall) {
                info.append("RIGHTWALL,");
            }
            if (hasUpWall) {
                info.append("UPWALL,");
            }
            if (hasDownWall) {
                info.append("DOWNWALL,");
            }
            if (hasGold) {
                info.append("GOLD,");
            }


            return info.toString();
        }
    }

}
