package de.skyking_px.serverrules.events.block;

import de.skyking_px.serverrules.ServerRules;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BellRingEvent implements Listener {

    FileConfiguration config = JavaPlugin.getPlugin(ServerRules.class).getConfig();
    @EventHandler
    public void onBellRingEvent(org.bukkit.event.block.BellRingEvent event) {
        if (config.getString("message-recieving") == "PRIVATE") {
            Entity e = event.getEntity();
            e.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
        } else if (config.getString("message-recieving") == "PUBLIC") {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , config.getString("rules")));
        }
    }

}
