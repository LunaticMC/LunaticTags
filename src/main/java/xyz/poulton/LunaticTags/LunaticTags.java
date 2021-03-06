// Copyright © Lucy Poulton 2020. All rights reserved.

package xyz.poulton.LunaticTags;

import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;
import xyz.poulton.LunaticTags.extensions.TagsPlaceholderExpansion;

public final class LunaticTags extends JavaPlugin {

    public TagHandler handler;
    public ConfigReader reader;
    public Permission permission;
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        this.handler = new TagHandler(permission);
        this.reader = new ConfigReader(this);
        this.getCommand("ltags").setExecutor(new TagCommand(this));
        new TagsPlaceholderExpansion(this).register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
