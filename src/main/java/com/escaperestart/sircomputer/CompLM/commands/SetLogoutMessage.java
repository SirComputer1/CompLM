package com.escaperestart.sircomputer.CompLM.commands;

import com.escaperestart.sircomputer.CompLM.CompLM;
import com.evilmidget38.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetLogoutMessage implements CommandExecutor {

    private CompLM plugin;

    public SetLogoutMessage(CompLM plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length <= 1) {
            return false;
        }
        boolean own = true;
        if(sender.hasPermission("complm.setlogoutmessage.other")) {
            own = false;
        }
        UUID puuid = null;
        String player = args[0];
        if(!own && Bukkit.getPlayer(player) != null) {
            puuid = Bukkit.getPlayer(player).getUniqueId();
        } else if (!own) {
            try {
                puuid = UUIDFetcher.getUUIDOf(player);
            } catch (Exception e) {
                Player pl = (Player) sender;
                player = pl.getDisplayName();
                puuid = pl.getUniqueId();
            }
        } else {
            Player pl = (Player) sender;
            player = pl.getDisplayName();
            puuid = pl.getUniqueId();
        }
        StringBuilder sb = new StringBuilder();
        if(!own) {
            for (String s : args) {
                if (s.matches(player)) {
                    continue;
                } else {
                    sb.append(s + " ");
                }
            }
        } else {
            for (String s : args) {
                sb.append(s + " ");
            }
        }
        plugin.getConfig().set("messages." + puuid.toString() + ".logout", sb.toString());
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Logout message of " + ChatColor.YELLOW + player + ChatColor.GREEN + " successfully set.");
        return true;
    }
}
