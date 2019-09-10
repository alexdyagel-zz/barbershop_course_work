package by.dyagel.controller.commands.user;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.DatabaseHandler;
import by.dyagel.model.DB.UserTableHandler;
import by.dyagel.model.entities.Role;
import by.dyagel.model.entities.User;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for Sign In command
 * implements ICommand interface
 */
public class SignInCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ResultSet result = null;
        try {
            User user = (User) ois.readObject();
            UserTableHandler dbHandler = new UserTableHandler();
            result = dbHandler.findUserByEmailAndPassword(user);
            int counter = 0;
            ConsoleView.printInfo("Поиск в базе данных");
            while (result.next()) {
                ConsoleView.printInfo("Информация найдена");
                counter++;
                oos.writeObject(ServerResponse.ACCEPTED);
                user.setFIO(result.getString(Const.USER_FIO));
                user.setPhoneNumber(result.getString(Const.USER_PHONE_NUMBER));
                user.setId(result.getInt(Const.USER_ID));
                user.setRole(Role.valueOf(result.getString(Const.USER_ROLE).toUpperCase()));
                oos.writeObject(user);
                break;
            }
            if (counter == 0) {
                ConsoleView.printInfo("Информация не найдена");
                oos.writeObject(ServerResponse.NOT_ACCEPTED);
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
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
