package dev.minecode.freeforall.common.api.manager;

import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.freeforall.api.manager.ReplaceManager;
import dev.minecode.freeforall.api.object.FFAPlayer;
import net.md_5.bungee.api.chat.BaseComponent;

public class ReplaceManagerProvider extends dev.minecode.core.common.api.manager.ReplaceManagerProvider implements ReplaceManager {
    public ReplaceManagerProvider(String message) {
        super(message);
    }

    public ReplaceManagerProvider(BaseComponent[] message) {
        super(message);
    }

    public ReplaceManagerProvider(Language language, LanguageAbstract path) {
        super(language, path);
    }

    @Override
    public dev.minecode.core.api.manager.ReplaceManager ffaPlayer(FFAPlayer ffaPlayer, String replacement) {
        return replaceAll("%" + replacement + "UUID%", ffaPlayer.getCorePlayer().getUuid().toString())
                .replaceAll("%" + replacement + "Name%", String.valueOf(ffaPlayer.getCorePlayer().getName()))
                .replaceAll("%" + replacement + "Kills%", String.valueOf(ffaPlayer.getKills()))
                .replaceAll("%" + replacement + "Deaths%", String.valueOf(ffaPlayer.getDeaths()))
                .replaceAll("%" + replacement + "KD%", String.valueOf(ffaPlayer.getKD()))
                .replaceAll("%" + replacement + "Points%", String.valueOf(ffaPlayer.getPoints()))
                .replaceAll("%" + replacement + "Rank%", String.valueOf(ffaPlayer.getRank()));
    }
}
