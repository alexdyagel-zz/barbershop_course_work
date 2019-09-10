package by.dyagel.controller.commands.specialists;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.SpecialistTableHandler;
import by.dyagel.model.entities.Specialist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AddSpecialistCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            Specialist specialist = (Specialist) ois.readObject();
            SpecialistTableHandler dbHandler = new SpecialistTableHandler();
            dbHandler.add(specialist);
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
