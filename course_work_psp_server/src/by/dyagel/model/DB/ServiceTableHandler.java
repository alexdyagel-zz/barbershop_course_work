package by.dyagel.model.DB;

import by.dyagel.model.entities.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTableHandler extends DatabaseHandler implements IQueryable<Service> {
    @Override
    public void add(Service service) {
        String sql = "INSERT INTO " + Const.SERVICE_TABLE +
                "(" + Const.SERVICE_ID + "," + Const.SERVICE_CATEGORY + ","
                + Const.SERVICE_NAME + "," + Const.SERVICE_PRICE + ")" +
                "VALUES(?,?,?,?);";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, service.getId());
            prSt.setString(2, service.getCategory());
            prSt.setString(3, service.getName());
            prSt.setBigDecimal(4, service.getPrice());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAll() {
        return super.getAll(Const.SERVICE_TABLE);
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM " + Const.SERVICE_TABLE + " WHERE " + Const.SERVICE_ID + "=?";
        update(id, sql);
    }

    @Override
    public void update(Service service) {
        String sql = "UPDATE " + Const.SERVICE_TABLE + " SET "
                + Const.SERVICE_CATEGORY + "=\"" + service.getCategory() + "\","
                + Const.SERVICE_NAME + "=\"" + service.getName() + "\","
                + Const.SERVICE_PRICE + "=" + service.getPrice()
                + " WHERE " + Const.SERVICE_ID + "=?";
        update(service.getId(), sql);
    }

    public ResultSet get(int id) {
        String sql = "SELECT * FROM " + Const.SERVICE_TABLE + " WHERE " + Const.SERVICE_ID + "=?";
        return super.get(id, sql);
    }
}
