package by.dyagel.controller;

import by.dyagel.view.ConsoleView;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Entry point of server
 * Creates a new thread for every new connected user
 */
class ServerTCP {

    public static void main(String args[]) {
        ConsoleView.printInfo("Сервер работает");
        ServerSocket sock;
        try {
            sock = new ServerSocket(1024);
            while (true) {
                Socket client = sock.accept();
                ConsoleView.printInfo("Соединение установлено");
                new Thread(new ClientHandler(client)).start();
            }
        } catch (Exception e) {
            ConsoleView.printInfo("Error " + e.toString());
        }

    }
}