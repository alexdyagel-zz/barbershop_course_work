package by.dyagel.controller;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.commands.factory.CommandFactory;
import by.dyagel.controller.messages.UserMessage;
import by.dyagel.view.ConsoleView;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Class handles clients messages
 */
public class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket s) {
        client = s;
    }

    public void run() {
        try {
            OutputStream os = client.getOutputStream();
            InputStream is = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            CommandFactory commandsCreator = new CommandFactory();
            boolean flag = true;
            while (flag) {
                UserMessage userChoice = (UserMessage) ois.readObject();
                ICommand command = commandsCreator.createCommand(userChoice);
                command.execute(ois, oos);
                flag = false;
            }
            is.close();
            os.close();
            ois.close();
            oos.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConsoleView.printInfo("Разрыв соединения");
    }
}
