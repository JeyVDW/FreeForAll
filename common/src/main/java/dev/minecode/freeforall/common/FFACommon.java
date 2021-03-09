package dev.minecode.freeforall.common;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.manager.UpdateManager;
import dev.minecode.freeforall.common.api.FFAAPIProvider;

import java.sql.SQLException;

public class FFACommon {

    private static FFACommon instance;

    public FFACommon() {
        makeInstances();
        new FFAAPIProvider();

        if (CoreAPI.getInstance().getPluginManager().isUsingSQL()) {
            try {
                CoreAPI.getInstance().getDatabaseManager().getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS minecode_freeforall (ID INT, POINTS INT, KILLS INT, DEATHS INT, PRIMARY KEY (ID))");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        UpdateManager updateManager = CoreAPI.getInstance().getUpdateManager();
        if (updateManager.updateAvailable()) {
            System.out.println("[" + CoreAPI.getInstance().getPluginManager().getPluginName() + "] There is a newer Version available! You can download it at " + updateManager.getReleaseURL(updateManager.getMatchingRelease()));
        }
    }

    public static FFACommon getInstance() {
        return instance;
    }

    private void makeInstances() {
        instance = this;
    }

}
