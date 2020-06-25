package xyz.poulton.tags;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TagCommand implements CommandExecutor {
    private final LunaticTags plugin;
    public TagCommand(LunaticTags _pl) {
        plugin = _pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1 && args.length != 2) return false;
        if (args[0].equals("reload")) {
            plugin.reader.reloadConfiguration(plugin.handler);
            sender.sendMessage("Reloading tags");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) return false;
        if (args.length == 2 && args[1].equals("legacy")) sender.sendMessage(plugin.handler.getPrefixString(Bukkit.getPlayer(args[0])));
        else sender.spigot().sendMessage(plugin.handler.getPrefix(Bukkit.getPlayer(args[0])));
        return true;
    }
}
