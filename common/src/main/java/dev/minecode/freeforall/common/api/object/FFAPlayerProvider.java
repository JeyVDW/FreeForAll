package dev.minecode.freeforall.common.api.object;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.object.FFAPlayer;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FFAPlayerProvider implements FFAPlayer {

    private static FileObject statsFileObject = FFAAPI.getInstance().getFileManager().getStats();
    private static ConfigurationNode statsConf = statsFileObject.getConf();

    private int rank, points, kills, deaths;
    private double kd;
    private CorePlayer corePlayer;
    private boolean exists;
    private Statement statement;
    private ResultSet resultSet;

    public FFAPlayerProvider(CorePlayer corePlayer) {
        this.corePlayer = corePlayer;
        load();
    }

    private static void create(int id, int points, int kills, int deaths) {
        try {
            if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("INSERT INTO minecode_freeforall (ID, POINTS, KILLS, DEATHS) VALUES (" + id + ", " + points + ", " + kills + ", " + deaths + ")");
            } else {
                statsConf.node(String.valueOf(id), "points").set(points);
                statsConf.node(String.valueOf(id), "kills").set(kills);
                statsConf.node(String.valueOf(id), "deaths").set(deaths);
            }
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
        }
    }

    public void load() {
        try {
            if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
                statement = CoreAPI.getInstance().getDatabaseManager().getStatement();
                resultSet = statement.executeQuery("SELECT * FROM minecode_freeforall WHERE ID = " + corePlayer.getID() + "");
                exists = resultSet.next();
            } else
                exists = !statsConf.node(String.valueOf(corePlayer.getID())).empty();

            if (!exists) {
                create(corePlayer.getID(), 0, 0, 0);
            }

            reload();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean reload() {
        try {
            if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
                resultSet = statement.executeQuery("SELECT * FROM minecode_freeforall WHERE ID = '" + corePlayer.getID() + "'");
                if (resultSet.next()) {
                    points = resultSet.getInt("POINTS");
                    kills = resultSet.getInt("KILLS");
                    deaths = resultSet.getInt("DEATHS");
                    kd = loadKD();
                    rank = loadRank();
                    return true;
                } else load();
            } else {
                points = statsConf.node(String.valueOf(corePlayer.getID()), "points").getInt();
                kills = statsConf.node(String.valueOf(corePlayer.getID()), "kills").getInt();
                deaths = statsConf.node(String.valueOf(corePlayer.getID()), "deaths").getInt();
                kd = loadKD();
                rank = loadRank();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean save() {
        try {
            corePlayer.reload();

            if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
                resultSet.updateInt("POINTS", points);
                resultSet.updateInt("KILLS", kills);
                resultSet.updateInt("DEATHS", deaths);
                resultSet.updateRow();
            } else {
                statsConf.node(String.valueOf(corePlayer.getID()), "points").set(points);
                statsConf.node(String.valueOf(corePlayer.getID()), "kills").set(kills);
                statsConf.node(String.valueOf(corePlayer.getID()), "deaths").set(deaths);
                statsFileObject.save();
            }
            return true;
        } catch (SQLException | SerializationException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private int loadRank() {
        if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
            try {
                resultSet = statement.executeQuery("SELECT * FROM minecode_freeforall ORDER BY POINTS DESC");
                int counter = 0;
                while (resultSet.next()) {
                    counter++;
                    if (resultSet.getInt("ID") == corePlayer.getID())
                        return counter;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            //      points  , ids der einzelnen CorePlayer
            HashMap<Integer, ArrayList<Integer>> unsorted = new HashMap<Integer, ArrayList<Integer>>();
            for (Map.Entry<Object, ? extends ConfigurationNode> uuidNode : statsConf.childrenMap().entrySet()) {
                if (!uuidNode.getValue().empty()) {
                    int p = uuidNode.getValue().node("points").getInt();
                    ArrayList<Integer> cache = new ArrayList<Integer>();
                    if (unsorted.containsKey(p)) {
                        cache.addAll(unsorted.get(p));
                    }
                    cache.add(CoreAPI.getInstance().getPlayerManager().getCorePlayer(Integer.parseInt(String.valueOf(uuidNode.getValue().key()))).getID());
                    unsorted.put(p, cache);
                }
            }
            TreeMap<Integer, ArrayList<Integer>> tm = new TreeMap<Integer, ArrayList<Integer>>(unsorted);
            Iterator itr = tm.keySet().iterator();
            int counter = tm.keySet().size();
            while (itr.hasNext()) {
                int key = (int) itr.next();
                for (int id : tm.get(key)) {
                    if (id == corePlayer.getID())
                        return counter;
                }
                counter--;
            }
        }
        return -1;
    }

    private double loadKD() {
        double kd = 0;
        double kills = this.kills;
        double deaths = this.deaths;
        if (deaths == 0) {
            kd = kills;
        } else if (kills == 0) {
            kd = 0.0;
        } else {
            kd = kills / deaths;
        }
        kd = Math.round(100.0 * kd) / 100.0;
        return kd;
    }

    @Override
    public CorePlayer getCorePlayer() {
        return corePlayer;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean setPoints(int points) {
        if (points < 0) return false;
        this.points = points;
        return true;
    }

    @Override
    public boolean addPoints(int points) {
        return setPoints(this.points + points);
    }

    @Override
    public boolean removePoints(int points) {
        return setPoints(this.points - points);
    }

    @Override
    public int getKills() {
        return kills;
    }

    @Override
    public boolean setKills(int kills) {
        if (kills < 0) return false;
        this.kills = kills;
        return true;
    }

    @Override
    public boolean addKills(int kills) {
        return setKills(getKills() + kills);
    }

    @Override
    public boolean removeKills(int kills) {
        return setKills(getKills() - kills);
    }

    @Override
    public int getDeaths() {
        return deaths;
    }

    @Override
    public boolean setDeaths(int deaths) {
        if (deaths < 0) return false;
        this.deaths = deaths;
        return true;
    }

    @Override
    public boolean addDeaths(int deaths) {
        return setDeaths(getDeaths() + deaths);
    }

    @Override
    public boolean removeDeaths(int deaths) {
        return setDeaths(getDeaths() - deaths);
    }

    @Override
    public double getKD() {
        return kd;
    }


}