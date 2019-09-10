package by.dyagel.controller.commands.seance;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.SeancesTableHandler;
import by.dyagel.model.entities.Seance;
import by.dyagel.model.entities.Specialist;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FindAvailableSeancesCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ConsoleView.printInfo("Получение списка сеансов по специалисту");
        ResultSet result = null;
        try {
            Specialist specialist = (Specialist) ois.readObject();
            ArrayList<Seance> seances = new ArrayList<>();
            SeancesTableHandler dbHandler = new SeancesTableHandler();
            result = dbHandler.findSeancesBySpecialist(specialist);
            while (result.next()) {
                Seance seance = new Seance(
                        result.getInt(Const.SEANCES_ID),
                        result.getDate(Const.SEANCES_DATE),
                        result.getTime(Const.SEANCES_TIME),
                        specialist
                );
                seances.add(seance);
            }
            oos.writeObject(seances);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) { /* ignored */ }
        }
    }
}
