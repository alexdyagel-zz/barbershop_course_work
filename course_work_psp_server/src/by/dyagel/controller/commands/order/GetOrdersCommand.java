package by.dyagel.controller.commands.order;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.controller.commands.seance.GetSeancesCommand;
import by.dyagel.controller.commands.services.GetServicesCommand;
import by.dyagel.controller.commands.user.GetUsersCommand;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.OrderTableHandler;
import by.dyagel.model.entities.Order;
import by.dyagel.model.entities.Seance;
import by.dyagel.model.entities.Service;
import by.dyagel.model.entities.User;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetOrdersCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ConsoleView.printInfo("Получение списка записей");
        ResultSet result = null;
        try {
            ArrayList<Order> orders = new ArrayList<>();
            OrderTableHandler dbHandler = new OrderTableHandler();
            result = dbHandler.getAll();
            while (result.next()) {
                int id = result.getInt(Const.ORDER_ID);
                int seanceId = result.getInt(Const.ORDER_SEANCE_ID);
                int serviceId = result.getInt(Const.ORDER_SERVICE_ID);
                int userId = result.getInt(Const.ORDER_USER_ID);
                User user = new GetUsersCommand().getUser(userId);
                Seance seance = new GetSeancesCommand().getSeance(seanceId);
                Service service = new GetServicesCommand().getService(serviceId);
                Order order = new Order(id, service, seance, user);
                orders.add(order);
            }
            oos.writeObject(orders);
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


}
