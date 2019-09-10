package by.dyagel.controller.commands.user;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.UserTableHandler;
import by.dyagel.model.entities.Role;
import by.dyagel.model.entities.User;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateUserCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            User user = (User) ois.readObject();
            UserTableHandler dbHandler = new UserTableHandler();
            ResultSet result = dbHandler.findUserByEmail(user.getEmail());
            ConsoleView.printInfo("Поиск в базе данных");
            int counter = 0;
            while (result.next()) {
                User foundUser = new User();
                foundUser.setFIO(result.getString(Const.USER_FIO));
                foundUser.setPhoneNumber(result.getString(Const.USER_PHONE_NUMBER));
                foundUser.setId(result.getInt(Const.USER_ID));
                foundUser.setEmail(result.getString(Const.USER_EMAIL));
                foundUser.setRole(Role.valueOf(result.getString(Const.USER_ROLE).toUpperCase()));
                if (foundUser.getId() != user.getId()) {
                    counter++;
                    ConsoleView.printInfo("Электронная почта уже занята");
                    oos.writeObject(ServerResponse.NOT_ACCEPTED);
                }
            }
            if (counter == 0) {
                ConsoleView.printInfo("Элекстронная почта свободна");
                ConsoleView.printInfo("Изменение информации в базе данных");
                dbHandler.update(user);
                oos.writeObject(ServerResponse.ACCEPTED);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
