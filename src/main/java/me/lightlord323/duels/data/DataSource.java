package me.lightlord323.duels.data;

import me.lightlord323.duels.Duels;
import me.lightlord323.duels.duel.DPlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.*;
import java.util.UUID;

/**
 * Created by Khalid on 7/6/21.
 */
public class DataSource {

    private static final String TABLE = "stats";
    private static final String UUID_COL = "uuid";
    private static final String WINS_COL = "wins";
    private static final String DRAWS_COL = "draws";
    private static final String LOSSES_COL = "losses";

    private static final String QUERY_STAT = "SELECT " + WINS_COL + ", " + DRAWS_COL + ", " + LOSSES_COL + " FROM " + TABLE + " WHERE " + UUID_COL + "=?";
    private static final String INSERT_STAT = "INSERT INTO " + TABLE + " VALUES (?, ?, ?, ?)";
    private static final String UPDATE_STAT = "UPDATE " + TABLE + " SET " + WINS_COL + "=?, " + DRAWS_COL + "=?, " + LOSSES_COL + "=? " + " WHERE " + UUID_COL + "=?";

    private Connection connection;

    public DataSource(Duels plugin) {
        FileConfiguration settings = plugin.getSettingsFile().getConfig();
        try {
            if (!settings.getBoolean("mysql.enable")) {
                File dir = new File(plugin.getDataFolder() + File.separator + "database");
                if (!dir.exists())
                    dir.mkdirs();
                connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + File.separator + "database" + File.separator + TABLE + ".db");
            } else {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://"
                                + settings.getString("mysql.host") + ":"
                                + settings.getInt("mysql.port") + "/"
                                + settings.getString("mysql.database") + "?useSSL=false", settings.getString("mysql.username"), settings.getString("mysql.password"));
            }
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS stats(" + UUID_COL + " TEXT, " + WINS_COL + " INTEGER, " + DRAWS_COL + " INETEGER, " + LOSSES_COL + " INTEGER" + ")");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int[] queryPlayerStats(UUID uuid) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_STAT);
        ps.setString(1, uuid.toString());
        ResultSet results = ps.executeQuery();
        if (!results.next())
            return new int[]{0, 0, 0};
        int[] stats = new int[]{results.getInt(WINS_COL), results.getInt(DRAWS_COL), results.getInt(LOSSES_COL)};
        ps.close();
        return stats;
    }

    public void savePlayerStats(DPlayer dPlayer) throws SQLException {
        PreparedStatement queryStatement = connection.prepareStatement(QUERY_STAT);
        queryStatement.setString(1, dPlayer.getUniqueId().toString());
        if (queryStatement.executeQuery().next()) {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_STAT);
            updateStatement.setString(4, dPlayer.getUniqueId().toString());
            updateStatement.setInt(1, dPlayer.getWins());
            updateStatement.setInt(2, dPlayer.getDraws());
            updateStatement.setInt(3, dPlayer.getLosses());
            updateStatement.execute();
            updateStatement.close();
        } else {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_STAT);
            insertStatement.setString(1, dPlayer.getUniqueId().toString());
            insertStatement.setInt(2, dPlayer.getWins());
            insertStatement.setInt(3, dPlayer.getDraws());
            insertStatement.setInt(4, dPlayer.getLosses());
            insertStatement.execute();
            insertStatement.close();
        }
    }

}
