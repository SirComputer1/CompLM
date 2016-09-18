package com.escaperestart.sircomputer.CompLM;

import com.escaperestart.sircomputer.CompLM.commands.SetLoginMessage;
import com.escaperestart.sircomputer.CompLM.commands.SetLogoutMessage;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CompLM extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("setloginmessage").setExecutor(new SetLoginMessage(this));
        getCommand("setlogoutmessage").setExecutor(new SetLogoutMessage(this));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(getConfig().contains("messages." + event.getPlayer().getUniqueId() + ".login")) {
            String message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages." + event.getPlayer().getUniqueId() + ".login"));
            message = message.replaceAll("%player%", event.getPlayer().getDisplayName());
            event.setJoinMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(getConfig().contains("messages." + event.getPlayer().getUniqueId() + ".logout")) {
            String message = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages." + event.getPlayer().getUniqueId() + ".logout"));
            message = message.replaceAll("%player%", event.getPlayer().getDisplayName());
            event.setQuitMessage(message);
        }
    }
}
