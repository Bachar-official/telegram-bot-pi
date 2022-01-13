package database;
import measure.Measure;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {
    private static final String DB_CONNECTION = "jdbc:sqlite:measures.db";

    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(DB_CONNECTION);
    }

    public List<Measure> getAllMeasures() {
        try (Statement statement = this.connection.createStatement()) {
            List<Measure> result = new ArrayList<>();
            ResultSet set = statement.executeQuery("SELECT id, date, temperature, humidity, pressure FROM measure");
            while (set.next()) {
                result.add(new Measure(set.getInt("id"), set.getString("date"), set.getDouble("temperature"),
                        set.getDouble("humidity"), set.getDouble("pressure")));
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Measure> getMeasures(int count) {
        try (Statement statement = this.connection.createStatement()) {
            List<Measure> result = new ArrayList<>();
            ResultSet set = statement
                    .executeQuery("SELECT id, date, temperature, humidity, pressure FROM measure ORDER BY ID DESC LIMIT " + count);
            while (set.next()) {
                result.add(new Measure(set.getInt("id"), set.getString("date"), set.getDouble("temperature"),
                        set.getDouble("humidity"), set.getDouble("pressure")));
            }
            List<Measure> sublist = result.subList(0, result.size());
            Collections.reverse(sublist);
            System.out.println(String.format("Got dataset from %s to %s", sublist.get(0).getTime(), sublist.get(sublist.size() - 1).getTime()));
            return sublist;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Measure getLastMeasure() {
        try (Statement statement = this.connection.createStatement()) {
            List<Measure> result = new ArrayList<>();
            ResultSet set = statement
                    .executeQuery("SELECT id, date, temperature, humidity, pressure FROM measure ORDER BY ID DESC LIMIT 1");
            while (set.next()) {
                result.add(new Measure(set.getInt("id"), set.getString("date"), set.getDouble("temperature"),
                        set.getDouble("humidity"), set.getDouble("pressure")));
            }
            System.out.println(String.format("Got result from %s", result.get(0).getTime()));
            return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Measure(0, 0.0, 0.0);
        }
    }

    public void addMeasure(Measure measure) {
        try (PreparedStatement ps = this.connection
                .prepareStatement("INSERT INTO measure(`date`, `temperature`, `humidity`, `pressure`) " + "VALUES(?, ?, ?)")) {
            ps.setObject(1, measure.getDate());
            ps.setObject(2, measure.getTemperature());
            ps.setObject(3, measure.getHumidity());
            ps.setObject(4, measure.getPressure());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
