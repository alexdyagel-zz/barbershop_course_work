package by.dyagel.controller.commands.user;

import by.dyagel.controller.commands.ICommand;
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
import java.util.ArrayList;

public class GetUsersCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ConsoleView.printInfo("find users");
        ResultSet result = null;
        try {
            ArrayList<User> users = new ArrayList<>();
            UserTableHandler dbHandler = new UserTableHandler();
            result = dbHandler.getAll();
            while (result.next()) {
                User user = new User();
                user.setFIO(result.getString(Const.USER_FIO));
                user.setPhoneNumber(result.getString(Const.USER_PHONE_NUMBER));
                user.setId(result.getInt(Const.USER_ID));
                user.setEmail(result.getString(Const.USER_EMAIL));
                user.setRole(Role.valueOf(result.getString(Const.USER_ROLE).toUpperCase()));
                users.add(user);
            }
            oos.writeObject(users);
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

    public User getUser(int id) throws SQLException {
        UserTableHandler dbHandler = new UserTableHandler();
        ResultSet result = dbHandler.get(id);
        User user = null;
        while (result.next()) {
            user = new User(
                    result.getInt(Const.USER_ID),
                    result.getString(Const.USER_FIO),
                    result.getString(Const.USER_EMAIL),
                    result.getString(Const.USER_PASSWORD),
                    result.getString(Const.USER_PHONE_NUMBER),
                    Role.valueOf(result.getString(Const.USER_ROLE).toUpperCase())
            );
        }
        return user;
    }
}
