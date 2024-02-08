package mc.skykingpx.serverrules;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public final class ServerRules extends JavaPlugin implements Listener, CommandExecutor {

    double plugin_version = 1.0;

    public void loadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    FileConfiguration config = this.getConfig();

    List<String> EventList = config.getStringList("event-trigger");

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

        //register Events

        this.getServer().getPluginManager().registerEvents(this, this);

        //bStats Metrics

        Metrics metrics = new Metrics(this, 20788);

        System.out.println( "\n[]=====[Enabling ServerRules]=====[]\n" +
                "| Information:\n" +
                "|   Name: ServerRules\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: coming soon!\n" +
                "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                "[]==================================[]\n");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println( "\n[]=====[Disabling ServerRules]=====[]\n" +
                "| Information:\n" +
                "|   Name: ServerRules\n" +
                "|   Developer: SkyKing_PX\n" +
                "|   Version: " + plugin_version + "\n" +
                "| Support:\n" +
                "|   Discord: SkyKing_PX\n" +
                "|      Server: coming soon!\n" +
                "|   GitHub: https://bit.ly/3ZZ8cCF\n" +
                "[]==================================[]\n");
    }

    public void registerEvent(Event EventList){

    }


    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender.hasPermission("serverrules.rules")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
        }
        return true;
    }
}
