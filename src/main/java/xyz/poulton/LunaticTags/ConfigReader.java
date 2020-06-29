package xyz.poulton.LunaticTags;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.poulton.LunaticTags.tag.Tag;

import java.io.*;
import java.util.*;

public class ConfigReader {
    LunaticTags plugin;

    public ConfigReader(LunaticTags pl) {
        plugin = pl;
        setDefaultConfig();
        reloadConfiguration(pl.handler);
    }

    public BaseComponent[] jsonToComponent(List<String> strings, FileConfiguration config) {
        StringBuilder components = new StringBuilder("[");
        for (String json : strings) {
            components.append(json);
            components.append(",");
        }
        if (components.charAt(components.length() - 1) == ',') components.deleteCharAt(components.length() - 1);
        components.append("]");

        return ComponentSerializer.parse(components.toString());
    }

    public void setDefaultConfig() {
        File defaults = new File(plugin.getDataFolder(), "config.yml");
        if (!defaults.exists()) {
            try {
                InputStream defaultStream = plugin.getResource("config.yml");
                byte[] buffer = new byte[defaultStream.available()];
                defaultStream.read(buffer);

                File targetFile = new File("src/main/resources/targetFile.tmp");
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);

            } catch (IOException e) {
                Bukkit.getLogger().severe("Unexpected error when creating default LunaticTags config!");
            }
        }
    }

    public void reloadConfiguration(TagHandler handler) {
        plugin.reloadConfig();
        try {
            FileConfiguration config = plugin.getConfig();
            HashMap<String, OrderGroup> parsedTags = new HashMap<>();
            for (String s : config.getConfigurationSection("ordergroups").getKeys(false)) {
                parsedTags.put(s, new OrderGroup(s, config.getInt("ordergroups." + s + ".order"),
                        config.getBoolean("ordergroups." + s + ".useShortTags")));
            }

            for (String s: config.getConfigurationSection("tags").getKeys(false)) {
                int weight = config.getInt("tags." + s + ".weight");
                BaseComponent[] fullTag = jsonToComponent(config.getStringList("tags." + s + ".tag"), config);
                BaseComponent[] shortTag = jsonToComponent(config.getStringList("tags." + s + ".tagshort"), config);
                String groupName = config.getString("tags." + s + ".group");
                String group = config.getString("tags." + s + ".ordergroup");
                OrderGroup orderGroup = parsedTags.get(group);
                Tag tag = new Tag(groupName, fullTag, shortTag, weight, group, orderGroup.isShort());

                if (s.equals("default")) orderGroup.setDefaultTag(tag);
                orderGroup.addTag(tag);
            }
            handler.loadTags(parsedTags);

        } catch (NullPointerException e) {
            Bukkit.getLogger().severe("An error occurred while loading the LunaticTags config file");
        }
    }
}
