package by.dyagel.controller.commands.user;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.UserTableHandler;
import by.dyagel.model.entities.User;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for Signing Up user
 * implements ICommand interface
 */
public class SignUpCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ResultSet result = null;
        try {
            User user = (User) ois.readObject();
            UserTableHandler dbHandler = new UserTableHandler();
            result = dbHandler.findUserByEmail(user.getEmail());
            ConsoleView.printInfo("Поиск в базе данных");
            int counter = 0;
            while (result.next()) {
                counter++;
                ConsoleView.printInfo("Электронная почта уже занята");
                oos.writeObject(ServerResponse.NOT_ACCEPTED);
            }
            if (counter == 0) {
                dbHandler.add(user);
                ConsoleView.printInfo("Элекстронная почта свободна");
                ConsoleView.printInfo("Добавление информации в базу данных");
                oos.writeObject(ServerResponse.ACCEPTED);
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
            } catch (Exception e) { /* ignored */ }
        }
    }
}
