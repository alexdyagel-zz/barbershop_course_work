package by.dyagel.controller.commands.services;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.DatabaseHandler;
import by.dyagel.model.DB.ServiceTableHandler;
import by.dyagel.model.entities.Service;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteServiceCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ConsoleView.printInfo("Удаление услуги");
            Service service = (Service) ois.readObject();
            ServiceTableHandler dbHandler = new ServiceTableHandler();
            dbHandler.remove(service.getId());
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
