package by.dyagel.model.DB;

import by.dyagel.model.entities.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTableHandler extends DatabaseHandler implements IQueryable<Order> {
    @Override
    public void add(Order order) {
        PreparedStatement prSt = null;
        String sql = "INSERT INTO " + dbName + "." + Const.ORDER_TABLE +
                "(" + Const.ORDER_SEANCE_ID + "," + Const.ORDER_SERVICE_ID + "," + Const.ORDER_USER_ID + ")" +
                "VALUES(?,?,?);";
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, order.getSeance().getId());
            prSt.setInt(2, order.getService().getId());
            prSt.setInt(3, order.getUser().getId());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAll() {
        return super.getAll(dbName + "." + Const.ORDER_TABLE);
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM " + dbName + "." + Const.ORDER_TABLE + " WHERE " + Const.ORDER_ID + "=?";
        update(id, sql);
    }

    @Override
    public void update(Order entity) {

    }
}
