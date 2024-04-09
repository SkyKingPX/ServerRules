package de.skyking_px.serverrules.events;

import de.skyking_px.serverrules.ServerRules;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Player implements Listener {
    FileConfiguration config = JavaPlugin.getPlugin(ServerRules.class).getConfig();
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        if (config.getString("message-recieving") == "PRIVATE") {
            org.bukkit.entity.Player p = event.getPlayer();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
        } else if (config.getString("message-recieving") == "PUBLIC") {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , config.getString("rules")));
        }
    }
    @EventHandler
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event){
        if (config.getString("message-recieving") == "PRIVATE") {
            org.bukkit.entity.Player p = event.getPlayer();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
        } else if (config.getString("message-recieving") == "PUBLIC") {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , config.getString("rules")));
        }
    }
}
