package dev.minecode.freeforall.spigot.command;

import dev.minecode.core.api.CoreAPI;
import dev.minecode.core.api.object.CorePlayer;
import dev.minecode.freeforall.api.FFAAPI;
import dev.minecode.freeforall.api.object.FFAPlayer;
import dev.minecode.freeforall.spigot.FFASpigot;
import dev.minecode.freeforall.spigot.object.FFALanguageSpigot;
import dev.minecode.freeforall.spigot.object.LocationSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.List;

public class FFACommand implements CommandExecutor, TabCompleter {

    public FFACommand(PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    /*
    * ffa - kleine Info über das Plugin                                     - ffa.use
    * ffa help - alle Befehle                                               - ffa.use
    ffa setSpawn - setzt den Spawn                                          - ffa.setup
    ffa build <Spieler> - setzt build-mode                                  - ffa.build
    ffa setKit - setzt das Kit anhand des eigenen Inventars                 - ffa.setup
    ffa regionWand - gibt den Gegenstand zum setzen der Locations           - ffa.setup
    ffa setRegion - setzt die Spawnregion (anhand von angeklickten Blöcken) - ffa.setup
    ffa stats - seigt die Stats des Ausführers                              - ffa.use
    * ffa stats [player] - seigt die Stats eines Spielers                   - ffa.stats.other
    */

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        CorePlayer coreExecuter = CoreAPI.getInstance().getPlayerManager().getCorePlayer(commandSender.getName());

        if (args.length == 0) {
            BaseComponent bc = TextComponent.fromLegacyText("§b§l§nMineCode")[0];
            bc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecode.dev"));
            bc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to go to our website")));

            commandSender.sendMessage("§9FFA §8» §7You are using §6" + FFASpigot.getInstance().getDescription().getName() + " §7version §6" + FFASpigot.getInstance().getDescription().getVersion());
            BaseComponent[] b = TextComponent.fromLegacyText("§8- §7This Plugin was developed by ");
            BaseComponent[] bcs = new BaseComponent[b.length + 1];
            for (int i = 0; i < b.length; i++)
                bcs[i] = b[i];
            bcs[b.length] = bc;
            if (commandSender instanceof Player)
                ((Player) commandSender).spigot().sendMessage(bcs);
            else
                commandSender.sendMessage(TextComponent.toLegacyText(bcs));
            return true;
        }

        if (!commandSender.hasPermission("ffa.use")) {
            sendHelp(commandSender, coreExecuter);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("stats")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                FFAPlayer ffaPlayer = FFAAPI.getInstance().getPlayerManager().getFFAPlayer(coreExecuter.getID());
                List<Object> messages = CoreAPI.getInstance().getLanguageManager().getList(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandStats);
                for (Object message : messages) {
                    commandSender.sendMessage(FFAAPI.getInstance().getReplaceManager(String.valueOf(message))
                            .ffaPlayer(ffaPlayer, "target")
                            .chatcolorAll().getMessage());
                }

                return true;
            }

            if (args[0].equalsIgnoreCase("build")) {
                if (!commandSender.hasPermission("ffa.build")) {
                    sendHelp(commandSender, coreExecuter);
                    return true;
                }
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                if (FFASpigot.getInstance().getBuildManager().isBuild(coreExecuter.getUuid())) {
                    FFASpigot.getInstance().getBuildManager().removePlayer(coreExecuter.getUuid());
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandBuildOff).chatcolorAll().getMessage());
                    return true;
                }

                if (!FFASpigot.getInstance().getBuildManager().isBuild(coreExecuter.getUuid())) {
                    FFASpigot.getInstance().getBuildManager().addPlayer(coreExecuter.getUuid());
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandBuildOn).chatcolorAll().getMessage());
                    return true;
                }
            }

            if (!commandSender.hasPermission("ffa.setup")) {
                sendHelp(commandSender, coreExecuter);
                return true;
            }

            if (args[0].equalsIgnoreCase("setspawn")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                try {
                    Location loc = ((Player) commandSender).getLocation();
                    FFAAPI.getInstance().getFileManager().getData().getConf().node("spawn").set(LocationSerializer.locationToString(loc));
                    FFAAPI.getInstance().getFileManager().getData().save();
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandSetSpawn).chatcolorAll().getMessage());
                } catch (SerializationException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("setkit")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                PlayerInventory inv = ((Player) commandSender).getInventory();

                FFASpigot.getInstance().getKitManager().setKit(inv.getContents(), inv.getArmorContents());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    FFASpigot.getInstance().getKitManager().setKitForPlayer(CoreAPI.getInstance().getPlayerManager().getCorePlayer(player.getUniqueId()));
                }
                commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandSetKit).chatcolorAll().getMessage());
                return true;
            }

            if (args[0].equalsIgnoreCase("regionwand")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                ((Player) commandSender).getInventory().addItem(FFASpigot.getInstance().getItemManager().getWandItem());
                commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandRegionWand).chatcolorAll().getMessage());
                return true;
            }

            if (args[0].equalsIgnoreCase("setregion")) {
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.noPlayers).chatcolorAll().getMessage());
                    return true;
                }

                if (FFASpigot.getInstance().getRegionManager().createRegion(coreExecuter.getUuid())) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandSetRegionSuccess).chatcolorAll().getMessage());
                } else {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandSetRegionLocationMissing).chatcolorAll().getMessage());
                }
                return true;
            }

        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("stats")) {
                if (!commandSender.hasPermission("ffa.stats.other")) {
                    sendHelp(commandSender, coreExecuter);
                    return true;
                }

                FFAPlayer ffaPlayer = FFAAPI.getInstance().getPlayerManager().getFFAPlayer(args[1]);

                if (ffaPlayer == null) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.playernotExists)
                            .args(command.getName(), args, "arg")
                            .replaceAll("%targetName%", args[1]).chatcolorAll().getMessage());
                    return true;
                }

                List<Object> messages = CoreAPI.getInstance().getLanguageManager().getList(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandStats);
                for (int i = 0; i < messages.size(); i++) {
                    commandSender.sendMessage(FFAAPI.getInstance().getReplaceManager(String.valueOf(messages.get(i)))
                            .ffaPlayer(ffaPlayer, "target")
                            .chatcolorAll().getMessage());
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("build")) {
                if (!commandSender.hasPermission("ffa.build.other")) {
                    sendHelp(commandSender, coreExecuter);
                    return true;
                }

                CorePlayer coreTarget = CoreAPI.getInstance().getPlayerManager().getCorePlayer(args[1]);
                if (coreTarget == null) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.playernotExists)
                            .args(command.getName(), args, "arg")
                            .replaceAll("%targetName%", args[1]).chatcolorAll().getMessage());
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.playerNotOnline)
                            .corePlayer(coreTarget, "arg").chatcolorAll().getMessage());
                    return true;
                }

                if (FFASpigot.getInstance().getBuildManager().isBuild(target.getUniqueId())) {
                    FFASpigot.getInstance().getBuildManager().removePlayer(target.getUniqueId());
                    target.sendMessage(CoreAPI.getInstance().getReplaceManager(coreTarget.getLanguage(), FFALanguageSpigot.ffaCommandBuildOff).chatcolorAll().getMessage());
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandBuildOtherOff)
                            .corePlayer(coreTarget, "target").chatcolorAll().getMessage());
                    return true;
                }

                if (!FFASpigot.getInstance().getBuildManager().isBuild(target.getUniqueId())) {
                    FFASpigot.getInstance().getBuildManager().addPlayer(target.getUniqueId());
                    target.sendMessage(CoreAPI.getInstance().getReplaceManager(coreTarget.getLanguage(), FFALanguageSpigot.ffaCommandBuildOn).chatcolorAll().getMessage());
                    commandSender.sendMessage(CoreAPI.getInstance().getReplaceManager(coreExecuter.getLanguage(), FFALanguageSpigot.ffaCommandBuildOtherOn)
                            .corePlayer(coreTarget, "target").chatcolorAll().getMessage());
                    return true;
                }
                return true;
            }

        }

        sendHelp(commandSender, coreExecuter);
        return true;
    }


    private void sendHelp(CommandSender sender, CorePlayer corePlayer) {

        if (!sender.hasPermission("ffa.use") && !sender.hasPermission("ffa.setup") && !sender.hasPermission("ffa.stats.other")) {
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.noPermission).chatcolorAll().getMessage());
            return;
        }

        sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.syntax).chatcolorAll().getMessage());
        if (sender.hasPermission("ffa.build.other")) {
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxBuildOther).chatcolorAll().getMessage());
        } else
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxBuild).chatcolorAll().getMessage());
        if (sender.hasPermission("ffa.setup")) {
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxSetSpawn).chatcolorAll().getMessage());
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxSetKit).chatcolorAll().getMessage());
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxRegionWand).chatcolorAll().getMessage());
            sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxSetRegion).chatcolorAll().getMessage());
            if (sender.hasPermission("ffa.stats.other")) {
                sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxStatsOther).chatcolorAll().getMessage());
            } else
                sender.sendMessage(CoreAPI.getInstance().getReplaceManager(corePlayer.getLanguage(), FFALanguageSpigot.ffaCommandSyntaxStats).chatcolorAll().getMessage());
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        ArrayList<String> tab = new ArrayList<>();
        List<String> list = new ArrayList<>();
        String search = null;

        if (!commandSender.hasPermission("ffa.use")) {
            return tab;
        }

        if (args.length == 1) {
            list.add("help");
            list.add("stats");

            if (commandSender.hasPermission("ffa.setup")) {
                list.add("build");
                list.add("setSpawn");
                list.add("setKit");
                list.add("setRegion");
                list.add("regionWand");
            }

            search = args[0].toLowerCase();
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("stats"))
                if (commandSender.hasPermission("ffa.stats.other"))
                    for (Player player : Bukkit.getOnlinePlayers())
                        list.add(player.getName());
            if (args[0].equalsIgnoreCase("build"))
                if (commandSender.hasPermission("ffa.build.other"))
                    for (Player player : Bukkit.getOnlinePlayers())
                        list.add(player.getName());

            search = args[1].toLowerCase();
        }

        for (String start : list) {
            if (start.toLowerCase().startsWith(search))
                tab.add(start);
        }

        return tab;
    }
}
