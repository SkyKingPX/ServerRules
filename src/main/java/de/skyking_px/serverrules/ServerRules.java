package de.skyking_px.serverrules;

import de.skyking_px.serverrules.events.block.BellRingEvent;
import de.skyking_px.serverrules.events.block.BlockBreakEvent;
import de.skyking_px.serverrules.events.player.PlayerJoinEvent;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public final class ServerRules extends JavaPlugin implements Listener, CommandExecutor {

    double plugin_version = 1.0;

    FileConfiguration config = this.getConfig();

    List<String> EventList = config.getStringList("event-trigger");

    //Config Stuff
    public void loadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Setup config - Code by TechnicJelle

        if(getDataFolder().mkdirs()) getLogger().info("Created plugin config directory");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                getLogger().info("Creating config file");
                this.getConfig().options().copyDefaults(true);
                Files.copy(Objects.requireNonNull(getResource("config.yml")), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //bStats Metrics

        Metrics metrics = new Metrics(this, 20788);

        //Enable

        this.getLogger().info("\n[]=====[Enabling ServerRules]=====[]\n" +
                "| Information:\n" +
                "|   Name: ServerRules\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/sk_px-dc\n" +
                "|   GitHub: https://bit.ly/sk_px-gh\n" +
                "[]==================================[]\n");

        //Register Events based on Config

        /*if (config.getStringList("event-trigger").contains("PlayerJoinEvent")){
            getServer().getPluginManager().registerEvents(new PlayerJoinEvent(),this);
        }*/

        /*PluginManager pluginManager= getServer().getPluginManager();
        String[] configTrigger1 = {config.getString("event-trigger")};
        for (String i :  configTrigger1){
            switch (i){
                case "PlayerJoinEvent":
                    pluginManager.registerEvent(PlayerJoinEvent(), this);
                    getLogger().info(ChatColor.translateAlternateColorCodes('&' , "&b[&r&cServerRules&r&b]&r Event 'PlayerJoinEvent' was registered successfully."));
                case "BlockBreakEvent":
                    pluginManager.registerEvent(BlockBreakEvent(), this);
                    getLogger().info(ChatColor.translateAlternateColorCodes('&' , "&b[&r&cServerRules&r&b]&r Event 'BlockBreakEvent' was registered successfully."));
            }
        }*/

        String eventName = config.getString("event-trigger");
        try {
            Class<?> eventClass = switch (eventName) {
                case "BellRingEvent" -> BellRingEvent.class;
                case "PlayerJoinEvent" -> PlayerJoinEvent.class;
                case "BlockBreakEvent" -> BlockBreakEvent.class; //Not defined in Class yet
                default -> throw new Exception("Something bad happened while trying to register the Events.");
            };

            /*Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.HIGH, (listener1, event) -> {
                        if (eventClass.isInstance(event)){
                            // <- this is the method that gets called when the event is fired;
                        }
                    }, this, true);*/
        } catch (Exception e) {
                e.printStackTrace();
        }

        //ChatColor.translateAlternateColorCodes('&', "&b[&r&cServerRules&r&b]&r Event '" + eventName + "' could not be registered. Is it supported?")

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("\n[]=====[Disabling ServerRules]=====[]\n" +
                "| Information:\n" +
                "|   Name: ServerRules\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: https://bit.ly/sk_px-dc\n" +
                "|   GitHub: https://bit.ly/sk_px-gh\n" +
                "[]==================================[]\n");
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        //rules Command
        if (label.equalsIgnoreCase("rules")) {
            if (sender.hasPermission("serverrules.rules")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
            }
        }

        //Basic Command
        if (label.equalsIgnoreCase("serverrules")) {
            //message-receiving toggle Command
            if (args[0].equalsIgnoreCase("msgtoggle")) {
                if (sender.hasPermission("serverrules.msgtoggle")) {
                    String Status = config.getString("message-receiving");
                    if (Status == "PRIVATE") {
                        config.set("PUBLIC" , "message-receiving");
                        this.saveConfig();
                        this.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Message receiving set to PUBLIC.");
                    } else if (Status == "PUBLIC") {
                        config.set("PRIVATE" , "message-receiving");
                        this.saveConfig();
                        this.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Message receiving set to PRIVATE.");
                    }
                } else sender.sendMessage(ChatColor.RED + "You don't have the Permission (serverrules.msgtoggle) to run this Command!");
            } else if (args[0].equalsIgnoreCase("version")) {
                //version Command
                String ServerBukkitVersion = this.getServer().getBukkitVersion();
                String ServerVersion = this.getServer().getVersion();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou are running Version &r&d" + plugin_version + "&r&a of the Serverrules Plugin.\n\n&r&9&nServerinformation:\n&rRunning " + ServerBukkitVersion + ", " + ServerVersion + "\n&6Plugin created by SkyKing_PX"));
            } else if (args[0].equalsIgnoreCase("help")) {
                //help Command
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bUsage: /serverrules help | version | msgtoggle"));
            }
        }
        return true;
    }

}
