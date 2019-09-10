package by.dyagel.controller.commands.order;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.messages.ServerResponse;
import by.dyagel.model.DB.OrderTableHandler;
import by.dyagel.model.entities.Order;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeleteOrderCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            ConsoleView.printInfo("Удаление записи");
            Order order = (Order) ois.readObject();
            OrderTableHandler dbHandler = new OrderTableHandler();
            dbHandler.remove(order.getId());
            oos.writeObject(ServerResponse.ACCEPTED);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
