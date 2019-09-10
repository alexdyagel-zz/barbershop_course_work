package by.dyagel.controller.commands.seance;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.SeancesTableHandler;
import by.dyagel.model.DB.SpecialistTableHandler;
import by.dyagel.model.entities.Seance;
import by.dyagel.model.entities.Specialist;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteSeanceCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ConsoleView.printInfo("Удаление сеанса");
            Seance seance = (Seance) ois.readObject();
            SeancesTableHandler dbHandler = new SeancesTableHandler();
            dbHandler.remove(seance.getId());
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
