package by.dyagel.controller.commands.user;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.DatabaseHandler;
import by.dyagel.model.DB.UserTableHandler;
import by.dyagel.model.entities.User;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteUserCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ConsoleView.printInfo("Удаление пользователя");
            User user = (User) ois.readObject();
            UserTableHandler dbHandler = new UserTableHandler();
            dbHandler.remove(user.getId());
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
