package by.dyagel.controller.commands.specialists;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.SpecialistTableHandler;
import by.dyagel.model.entities.Specialist;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteSpecialistCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ConsoleView.printInfo("Удаление специалиста");
            Specialist specialist = (Specialist) ois.readObject();
            SpecialistTableHandler dbHandler = new SpecialistTableHandler();
            dbHandler.remove(specialist.getId());
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
