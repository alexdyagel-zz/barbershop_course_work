package by.dyagel.controller.commands.services;

import by.dyagel.controller.commands.ICommand;
import by.dyagel.model.DB.Const;
import by.dyagel.model.DB.ServiceTableHandler;
import by.dyagel.model.entities.Service;
import by.dyagel.view.ConsoleView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetServicesCommand implements ICommand {
    @Override
    public void execute(ObjectInputStream ois, ObjectOutputStream oos) {
        ConsoleView.printInfo("Получение списка услуг");
        try {
            ArrayList<Service> services = new ArrayList<>();
            ServiceTableHandler dbHandler = new ServiceTableHandler();
            ResultSet result = dbHandler.getAll();
            while (result.next()) {
                Service service = new Service(
                        result.getInt(Const.SERVICE_ID),
                        result.getString(Const.SERVICE_CATEGORY),
                        result.getString(Const.SERVICE_NAME),
                        result.getBigDecimal(Const.SERVICE_PRICE)
                );
                services.add(service);
            }
            oos.writeObject(services);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Service getService(int id) throws SQLException {
        ServiceTableHandler dbHandler = new ServiceTableHandler();
        ResultSet result = dbHandler.get(id);
        Service service = null;
        while (result.next()) {
            service = new Service(
                    result.getInt(Const.SERVICE_ID),
                    result.getString(Const.SERVICE_CATEGORY),
                    result.getString(Const.SERVICE_NAME),
                    result.getBigDecimal(Const.SERVICE_PRICE)
            );
        }
        return service;
    }

}
