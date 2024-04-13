package de.skyking_px.serverrules.events.player;

import de.skyking_px.serverrules.ServerRules;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerAdvancementDoneEvent implements Listener {

    FileConfiguration config = JavaPlugin.getPlugin(ServerRules.class).getConfig();
    @EventHandler
    public void onPlayerAdvancementDoneEvent(org.bukkit.event.player.PlayerAdvancementDoneEvent event){
        if (config.getString("message-recieving") == "PRIVATE") {
            org.bukkit.entity.Player p = event.getPlayer();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("rules")));
        } else if (config.getString("message-recieving") == "PUBLIC") {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , config.getString("rules")));
        }
    }

}
