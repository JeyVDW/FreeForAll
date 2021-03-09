package dev.minecode.freeforall.api.manager;

import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.freeforall.api.object.FFAPlayer;

import java.util.UUID;

public interface PlayerManager {

    /**
     * Gets a FFAPlayer via the inherent CorePlayer
     *
     * @param corePlayer
     * @return FFAPlayer
     */
    FFAPlayer getFFAPlayer(CorePlayer corePlayer);

    /**
     * Gets a FFAPlayer via the inherent player's ID
     *
     * @param id of the player
     * @return FFAPlayer
     */
    FFAPlayer getFFAPlayer(int id);

    /**
     * Gets a FFAPlayer via the inherent player's UUID
     *
     * @param uuid of the player
     * @return FFAPlayer
     */
    FFAPlayer getFFAPlayer(UUID uuid);

    /**
     * Gets a FFAPlayer via the inherent player's name
     *
     * @param name of the player
     * @return FFAPlayer
     */
    FFAPlayer getFFAPlayer(String name);

}
