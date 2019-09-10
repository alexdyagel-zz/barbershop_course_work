package by.dyagel.controller.commands.seance;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.commands.specialists.GetSpecialistsCommand;
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

public class GetSeancesCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        ConsoleView.printInfo("Получение списка сеансов");
        ResultSet result = null;
        try {
            ArrayList<Seance> seances = new ArrayList<>();
            SeancesTableHandler dbHandler = new SeancesTableHandler();
            result = dbHandler.getAll();
            while (result.next()) {
                int specId = result.getInt(Const.SEANCES_SPEC_ID);
                Specialist specialist = new GetSpecialistsCommand().getSpecialist(specId);
                Seance seance = new Seance(
                        result.getInt(Const.SEANCES_ID),
                        result.getDate(Const.SEANCES_DATE),
                        result.getTime(Const.SEANCES_TIME),
                        specialist
                );
                seances.add(seance);
            }
            oos.writeObject(seances);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) { /* ignored */ }
        }
    }

    public Seance getSeance(int id) throws SQLException {
        SeancesTableHandler dbHandler = new SeancesTableHandler();
        ResultSet result = dbHandler.get(id);
        Seance seance = null;
        while (result.next()) {
            seance = new Seance(
                    result.getInt(Const.SEANCES_ID),
                    result.getDate(Const.SEANCES_DATE),
                    result.getTime(Const.SEANCES_TIME),
                    new GetSpecialistsCommand().getSpecialist(result.getInt(Const.SEANCES_SPEC_ID))
            );
        }
        return seance;
    }
}
