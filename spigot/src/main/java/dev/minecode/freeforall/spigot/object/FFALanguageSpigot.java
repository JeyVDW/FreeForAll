package dev.minecode.freeforall.spigot.object;

import dev.minecode.core.api.object.LanguageAbstract;

public enum FFALanguageSpigot implements LanguageAbstract {

    noPermission("noPermission"),
    noPlayers("noPlayers"),
    playerNotOnline("playerNotOnline"),
    playernotExists("playerNotExists"),
    syntax("syntax"),

    ffaCommandSyntaxBuild("ffa", "command", "syntax", "build"),
    ffaCommandSyntaxBuildOther("ffa", "command", "syntax", "buildOther"),
    ffaCommandSyntaxSetSpawn("ffa", "command", "syntax", "setspawn"),
    ffaCommandSyntaxSetKit("ffa", "command", "syntax", "setkit"),
    ffaCommandSyntaxRegionWand("ffa", "command", "syntax", "regionwand"),
    ffaCommandSyntaxSetRegion("ffa", "command", "syntax", "setregion"),
    ffaCommandSyntaxStats("ffa", "command", "syntax", "stats"),
    ffaCommandSyntaxStatsOther("ffa", "command", "syntax", "statsOther"),
    ffaCommandBuildOn("ffa", "command", "buildOn"),
    ffaCommandBuildOff("ffa", "command", "buildOff"),
    ffaCommandBuildOtherOn("ffa", "command", "other", "buildOn"),
    ffaCommandBuildOtherOff("ffa", "command", "other", "buildOff"),
    ffaCommandSetSpawn("ffa", "command", "setspawn"),
    ffaCommandSetKit("ffa", "command", "setkit"),
    ffaCommandRegionWand("ffa", "command", "regionwand"),
    ffaCommandSetRegionSuccess("ffa", "command", "setregion", "success"),
    ffaCommandSetRegionLocationMissing("ffa", "command", "setregion", "locationMissing"),
    ffaCommandStats("ffa", "command", "stats"),
    ffaEventJoinJoinMessage("ffa", "event", "join", "joinMessage"),
    ffaEventJoinSpawnNotSet("ffa", "event", "join", "spawnNotSet"),
    ffaEventQuitQuitMessage("ffa", "event", "quit", "quitMessage"),
    ffaEventKilled("ffa", "event", "killed"),
    ffaEventKilledBy("ffa", "event", "killedBy"),
    ffaEventDied("ffa", "event", "died"),
    ffaEventRegionSetPos1("ffa", "event", "region", "setPos1"),
    ffaEventRegionSetPos2("ffa", "event", "region", "setPos2");

    private String[] path;

    FFALanguageSpigot(String... path) {
        this.path = path;
    }

    @Override
    public String[] getPath() {
        return path;
    }
}
