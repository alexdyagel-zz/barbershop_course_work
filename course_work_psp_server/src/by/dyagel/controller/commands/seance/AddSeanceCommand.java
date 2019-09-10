package by.dyagel.controller.commands.seance;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.SeancesTableHandler;
import by.dyagel.model.entities.Seance;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddSeanceCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            Seance seance = (Seance) ois.readObject();
            SeancesTableHandler dbHandler = new SeancesTableHandler();
            System.out.println(seance.getDate());
            dbHandler.add(seance);
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
