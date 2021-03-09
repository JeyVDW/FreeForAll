package dev.minecode.freeforall.spigot.manager;

import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.spigot.object.Cuboid;
import dev.minecode.freeforall.spigot.object.LocationSerializer;
import org.bukkit.Location;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.HashMap;
import java.util.UUID;

public class RegionManager {

    private HashMap<UUID, Location> pos1;
    private HashMap<UUID, Location> pos2;

    private Cuboid spawnRegion;

    public RegionManager() {
        pos1 = new HashMap<>();
        pos2 = new HashMap<>();
        loadSpawnRegion();
    }

    private void loadSpawnRegion() {
        if (FFAAPI.getInstance().getFileManager().getData().getConf().node("pos1").empty()
                || FFAAPI.getInstance().getFileManager().getData().getConf().node("pos2").empty()) {
            spawnRegion = null;
            return;
        }

        Location pos1 = LocationSerializer.stringToLocation(FFAAPI.getInstance().getFileManager().getData().getConf().node("pos1").getString());
        Location pos2 = LocationSerializer.stringToLocation(FFAAPI.getInstance().getFileManager().getData().getConf().node("pos2").getString());
        spawnRegion = new Cuboid(pos1, pos2);
    }

    public boolean createRegion(UUID uuid) {
        if (pos1.containsKey(uuid) && pos2.containsKey(uuid)) {
            try {
                FFAAPI.getInstance().getFileManager().getData().getConf().node("pos1").set(LocationSerializer.locationToString(pos1.get(uuid)));
                FFAAPI.getInstance().getFileManager().getData().getConf().node("pos2").set(LocationSerializer.locationToString(pos2.get(uuid)));
                spawnRegion = new Cuboid(pos1.get(uuid), pos2.get(uuid));
                pos1.remove(uuid);
                pos2.remove(uuid);
            } catch (SerializationException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean isInRegion(Location location) {
        return spawnRegion != null && spawnRegion.contains(location);
    }

    public void setPos1(UUID uuid, Location loc) {
        pos1.put(uuid, loc);
    }

    public void setPos2(UUID uuid, Location loc) {
        pos2.put(uuid, loc);
    }

    public HashMap<UUID, Location> getPos1() {
        return pos1;
    }

    public HashMap<UUID, Location> getPos2() {
        return pos2;
    }
}
