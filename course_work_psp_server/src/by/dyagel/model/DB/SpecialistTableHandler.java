package by.dyagel.model.DB;

import by.dyagel.model.entities.Specialist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialistTableHandler extends DatabaseHandler implements IQueryable<Specialist> {

    @Override
    public void add(Specialist specialist) {
        String sql = "INSERT INTO " + Const.SPECIALIST_TABLE + "("
                + Const.SPECIALIST_ID + ","
                + Const.SPECIALIST_POSITION + ","
                + Const.SPECIALIST_NAME + ")" +
                "VALUES(?,?,?);";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, specialist.getId());
            prSt.setString(2, specialist.getPosition());
            prSt.setString(3, specialist.getName());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAll() {
        return super.getAll(Const.SPECIALIST_TABLE);
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM " + Const.SPECIALIST_TABLE + " WHERE " + Const.SPECIALIST_ID + "=?";
        update(id, sql);
    }

    @Override
    public void update(Specialist specialist) {
        String sql = "UPDATE " + Const.SPECIALIST_TABLE + " SET "
                + Const.SPECIALIST_POSITION + "=\"" + specialist.getPosition() + "\","
                + Const.SPECIALIST_NAME + "=\"" + specialist.getName() + "\""
                + " WHERE " + Const.SPECIALIST_ID + "=?";
        update(specialist.getId(), sql);
    }

    public ResultSet get(int id) {
        String sql = "SELECT * FROM " + Const.SPECIALIST_TABLE + " WHERE " + Const.SPECIALIST_ID + "=?";
        return super.get(id, sql);
    }
}
