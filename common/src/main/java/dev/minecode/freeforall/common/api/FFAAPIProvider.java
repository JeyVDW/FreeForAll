package dev.minecode.freeforall.common.api;

import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.manager.FileManager;
import dev.minecode.freeforall.api.manager.PlayerManager;
import dev.minecode.freeforall.api.manager.ReplaceManager;
import dev.minecode.freeforall.common.api.manager.FileManagerProvider;
import dev.minecode.freeforall.common.api.manager.PlayerManagerProvider;
import dev.minecode.freeforall.common.api.manager.ReplaceManagerProvider;
import net.md_5.bungee.api.chat.BaseComponent;

public class FFAAPIProvider extends FFAAPI {

    private FileManagerProvider fileManagerProvider;
    private PlayerManagerProvider playerManagerProvider;

    public FFAAPIProvider() {
        makeInstances();
    }

    private void makeInstances() {
        FFAAPI.setInstance(this);
        fileManagerProvider = new FileManagerProvider();
        playerManagerProvider = new PlayerManagerProvider();
    }

    @Override
    public FileManager getFileManager() {
        return fileManagerProvider;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return playerManagerProvider;
    }

    @Override
    public ReplaceManager getReplaceManager(String message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public ReplaceManager getReplaceManager(BaseComponent[] message) {
        return new ReplaceManagerProvider(message);
    }

    @Override
    public ReplaceManager getReplaceManager(Language language, LanguageAbstract path) {
        return new ReplaceManagerProvider(language, path);
    }

}
