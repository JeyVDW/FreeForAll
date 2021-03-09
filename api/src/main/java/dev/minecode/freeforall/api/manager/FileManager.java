package dev.minecode.freeforall.api.manager;

import dev.minecode.core.api.object.FileObject;

public interface FileManager {

    /**
     * Saves the current changes of data.yml and stats.yml
     *
     * @return true, if successful
     */
    boolean saveDatas();


    /**
     * Gets the FileObject of data.yml
     *
     * @return FileObject of data.yml
     */
    FileObject getData();

    /**
     * Gets the FileObject of stats.yml
     *
     * @return FileObject of stats.yml
     */
    FileObject getStats();

    /**
     * Gets the FileObject of config.yml
     *
     * @return FileObject of config.yml
     */
    FileObject getConfig();

}
