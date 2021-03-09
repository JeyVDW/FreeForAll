package dev.minecode.freeforall.api.manager;

import dev.minecode.freeforall.api.object.FFAPlayer;

public interface ReplaceManager {

    /**
     * Addon to the ReplaceManage of Core (dev.minecode.core.api.manager.ReplaceManager) for following replacements:
     * - UUID
     * - Name
     * - Kills
     * - Deaths
     * - KD
     * - Points
     * - Rank
     *
     * @param ffaPlayer   the target, which data has to be replaced
     * @param replacement part of the whole replacement, which is like a additional placeholder
     * @return ReplaceManager of Core (dev.minecode.core.api.manager.ReplaceManager)
     */
    dev.minecode.core.api.manager.ReplaceManager ffaPlayer(FFAPlayer ffaPlayer, String replacement);
}
