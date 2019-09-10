package by.dyagel.controller.commands.specialists;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.SpecialistTableHandler;
import by.dyagel.model.entities.Specialist;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetSpecialistsCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ConsoleView.printInfo("Получение списка специалистов");
        ResultSet result = null;
        try {
            ArrayList<Specialist> specialists = new ArrayList<>();
            SpecialistTableHandler dbHandler = new SpecialistTableHandler();
            result = dbHandler.getAll();
            while (result.next()) {
                Specialist specialist = new Specialist(
                        result.getInt(Const.SPECIALIST_ID),
                        result.getString(Const.SPECIALIST_POSITION),
                        result.getString(Const.SPECIALIST_NAME)
                );
                specialists.add(specialist);
            }
            oos.writeObject(specialists);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) { /* ignored */ }
        }
    }

    public Specialist getSpecialist(int id) throws SQLException {
        SpecialistTableHandler dbHandler = new SpecialistTableHandler();
        ResultSet result = dbHandler.get(id);
        Specialist specialist = null;
        while (result.next()) {
            specialist = new Specialist(
                    result.getInt(Const.SPECIALIST_ID),
                    result.getString(Const.SPECIALIST_POSITION),
                    result.getString(Const.SPECIALIST_NAME)
            );
        }
        return specialist;
    }
}
