package dev.minecode.freeforall.common.api.manager;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.FileObject;
import dev.minecode.freeforall.api.manager.FileManager;

public class FileManagerProvider implements FileManager {

    private FileObject data, stats, config;

    public FileManagerProvider() {
        makeInstances();
    }

    private void makeInstances() {
        data = CoreAPI.getInstance().getFileManager().getFileObject("data.yml", CoreAPI.getInstance().getPluginManager().getPluginName());
        stats = CoreAPI.getInstance().getFileManager().getFileObject("stats.yml", CoreAPI.getInstance().getPluginManager().getPluginName());
        config = CoreAPI.getInstance().getFileManager().getFileObject("config.yml", CoreAPI.getInstance().getPluginManager().getPluginName());
    }

    @Override
    public boolean saveDatas() {
        return data.save() && stats.save();
    }

    @Override
    public FileObject getData() {
        return data;
    }

    @Override
    public FileObject getStats() {
        return stats;
    }

    @Override
    public FileObject getConfig() {
        return config;
    }
}
