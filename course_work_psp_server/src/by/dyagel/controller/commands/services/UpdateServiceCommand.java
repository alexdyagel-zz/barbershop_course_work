package by.dyagel.controller.commands.services;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.DatabaseHandler;
import by.dyagel.model.DB.ServiceTableHandler;
import by.dyagel.model.entities.Service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class UpdateServiceCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            Service service = (Service) ois.readObject();
            ServiceTableHandler dbHandler = new ServiceTableHandler();

            dbHandler.update(service);
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
