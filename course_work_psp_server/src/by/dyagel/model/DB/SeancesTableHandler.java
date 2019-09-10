package by.dyagel.model.DB;

import by.dyagel.model.entities.Seance;
import by.dyagel.model.entities.Specialist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeancesTableHandler extends DatabaseHandler implements IQueryable<Seance> {
    @Override
    public void add(Seance seance) {
        System.out.println(seance.getDate());
        String sql = "INSERT INTO " + Const.SEANCES_TABLE +
                "(" + Const.SEANCES_ID + "," + Const.SEANCES_DATE + ","
                + Const.SEANCES_TIME + "," + Const.SEANCES_SPEC_ID + ")" +
                "VALUES(?,?,?,?);";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1, seance.getId());
            prSt.setDate(2, seance.getDate(),java.util.Calendar.getInstance());
            prSt.setTime(3, seance.getTime());
            prSt.setInt(4, seance.getSpecialist().getId());

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet getAll() {
        return super.getAll(Const.SEANCES_TABLE);
    }

    public ResultSet findSeancesBySpecialist(Specialist specialist) {
        String sql = "SELECT * FROM " + Const.SEANCES_TABLE + " WHERE " + Const.SEANCES_SPEC_ID + "=?";
        return super.get(specialist.getId(), sql);
    }

    @Override
    public void remove(int id) {
        String sql = "DELETE FROM " + Const.SEANCES_TABLE + " WHERE " + Const.SEANCES_ID + "=?";
        update(id, sql);
    }

    public ResultSet get(int id) {
        String sql = "SELECT * FROM " + Const.SEANCES_TABLE + " WHERE " + Const.SEANCES_ID + "=?";
        return super.get(id, sql);
    }

    @Override
    public void update(Seance seance) {

    }


}
