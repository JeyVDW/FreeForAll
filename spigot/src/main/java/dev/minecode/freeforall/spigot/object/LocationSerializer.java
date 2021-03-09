package dev.minecode.freeforall.spigot.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class LocationSerializer {

    public static String locationToString(Location location) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(location.serialize());
            oos.flush();
            return DatatypeConverter.printBase64Binary(bos.toByteArray());
        } catch (Exception ignored) {
        }
        return "";
    }

    public static Location stringToLocation(String s) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    DatatypeConverter.parseBase64Binary(s));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return deserializeLocation(
                    (Map<String, Object>) ois.readObject());
        } catch (Exception ignored) {
        }
        return new Location(Bukkit.getWorlds().get(0), 0, 0, 0, 0, 0);
    }

    @SuppressWarnings("unchecked")
    private static Location deserializeLocation(Map<String, Object> map) {
        Location location;
        if (map.size() == 0) {
            location = null;
        } else {
            try {
                location = Location.deserialize(map);
            } catch (Exception ignored) {
                location = null;
            }
        }
        return location;
    }

}
