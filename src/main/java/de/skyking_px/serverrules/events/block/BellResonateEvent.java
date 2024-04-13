package de.skyking_px.serverrules.events.block;

import de.skyking_px.serverrules.ServerRules;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BellResonateEvent implements Listener {

    FileConfiguration config = JavaPlugin.getPlugin(ServerRules.class).getConfig();


}
