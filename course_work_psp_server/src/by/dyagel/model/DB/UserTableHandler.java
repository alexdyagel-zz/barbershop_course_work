package by.dyagel.model.DB;

import by.dyagel.model.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTableHandler extends DatabaseHandler implements IQueryable<User> {
    @Override
    public void add(User user) {
        String sql = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIO + "," + Const.USER_EMAIL + "," +
                Const.USER_PASSWORD + "," + Const.USER_PHONE_NUMBER + "," + Const.USER_ROLE + ")" +
                "VALUES(?,?,?,?,?);";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, user.getFIO());
            prSt.setString(2, user.getEmail());
            prSt.setString(3, user.getPassword());
            prSt.setString(4, user.getPhoneNumber());
            prSt.setString(5, user.getRole().toString());
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAll() {
        return super.getAll(Const.USER_TABLE);
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + "=?";
        update(id, sql);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE " + Const.USER_TABLE + " SET "
                + Const.USER_FIO + "=\"" + user.getFIO() + "\","
                + Const.USER_PHONE_NUMBER + "=\"" + user.getPhoneNumber() + "\","
                + Const.USER_EMAIL + "=\"" + user.getEmail() + "\","
                + Const.USER_PASSWORD + "=\"" + user.getPassword()
                + "\" WHERE " + Const.USER_ID + "=?";
        System.out.println(sql);
        update(user.getId(), sql);
    }

    public ResultSet findUserByEmailAndPassword(User user) {
        ResultSet resSet = null;
        PreparedStatement prSt = null;
        String sql = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_EMAIL + "=? AND " +
                Const.USER_PASSWORD + "=?";
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, user.getEmail());
            prSt.setString(2, user.getPassword());
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet findUserByEmail(String Email) {
        ResultSet resSet = null;
        PreparedStatement prSt = null;
        String sql = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_EMAIL + "=?";
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, Email);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet get(int id) {
        String sql = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_ID + "=?";
        return super.get(id, sql);
    }
}
