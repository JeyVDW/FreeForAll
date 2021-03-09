package dev.minecode.freeforall.api;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.Language;
import dev.minecode.core.api.object.LanguageAbstract;
import dev.minecode.freeforall.api.manager.FileManager;
import dev.minecode.freeforall.api.manager.PlayerManager;
import dev.minecode.freeforall.api.manager.ReplaceManager;
import net.md_5.bungee.api.chat.BaseComponent;

public abstract class FFAAPI {

    // Instance
    private static FFAAPI instance;

    /**
     * Gets the instance of this class
     *
     * @return instance
     */
    public static FFAAPI getInstance() {
        return instance;
    }

    /**
     * Sets an instance of this class
     *
     * @param ffaAPI instance of FFAAPI
     */
    public static void setInstance(FFAAPI ffaAPI) {
        FFAAPI.instance = ffaAPI;
    }


    // CoreAPI

    /**
     * Gets the relocated CoreAPI of this plugin
     *
     * @return relocated CoreAPI
     */
    public static CoreAPI getCoreAPI() {
        return CoreAPI.getInstance();
    }


    // Manager

    /**
     * Access to FileManager
     *
     * @return FileManager
     */
    public abstract FileManager getFileManager();

    /**
     * Access to PlayerManager
     *
     * @return PlayerManager
     */
    public abstract PlayerManager getPlayerManager();

    /**
     * Access to ReplaceManager
     * To replace the placeholders
     *
     * @param message message with placeholders
     * @return ReplaceManager to choose the replacement methods
     */
    public abstract ReplaceManager getReplaceManager(String message);

    /**
     * Access to ReplaceManager
     * To replace the placeholders
     *
     * @param message message with placeholders
     * @return ReplaceManager to choose the replacement methods
     */
    public abstract ReplaceManager getReplaceManager(BaseComponent[] message);

    /**
     * Access to ReplaceManager
     * To replace the placeholders
     *
     * @param language player's language
     * @param path     Path to the configuration node in the message files
     * @return ReplaceManager to choose the replacement methods
     */
    public abstract ReplaceManager getReplaceManager(Language language, LanguageAbstract path);
}
