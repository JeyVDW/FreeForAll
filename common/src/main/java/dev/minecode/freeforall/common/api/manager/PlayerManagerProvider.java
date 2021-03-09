package dev.minecode.freeforall.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.freeforall.api.manager.PlayerManager;
import dev.minecode.freeforall.api.object.FFAPlayer;
import dev.minecode.freeforall.common.api.object.FFAPlayerProvider;

import java.util.UUID;

public class PlayerManagerProvider implements PlayerManager {

    @Override
    public FFAPlayer getFFAPlayer(CorePlayer corePlayer) {
        if (corePlayer == null) return null;

        return new FFAPlayerProvider(corePlayer);
    }

    @Override
    public FFAPlayer getFFAPlayer(int id) {
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(id);
        return getFFAPlayer(corePlayer);
    }

    @Override
    public FFAPlayer getFFAPlayer(UUID uuid) {
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(uuid);
        return getFFAPlayer(corePlayer);
    }

    @Override
    public FFAPlayer getFFAPlayer(String name) {
        CorePlayer corePlayer = CoreAPI.getInstance().getPlayerManager().getCorePlayer(name);
        return getFFAPlayer(corePlayer);
    }

}
