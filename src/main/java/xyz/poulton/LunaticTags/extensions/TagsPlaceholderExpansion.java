package xyz.poulton.LunaticTags.extensions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.OfflinePlayer;
import xyz.poulton.LunaticTags.LunaticTags;

public class TagsPlaceholderExpansion extends PlaceholderExpansion {
    private final LunaticTags plugin;
    public TagsPlaceholderExpansion(LunaticTags pl) {
        plugin = pl;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "__lucyy";
    }

    @Override
    public String getIdentifier() {
        return "ltags";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    public String toTabFormat(BaseComponent[] tags) {
        StringBuilder output = new StringBuilder();
        for (BaseComponent tag : tags) {
            if (tag.getColor().name().startsWith("#")) output.append(tag.getColor().name());
            else output.append(tag.getColor().toString());
            if (tag.isBold()) output.append("&l");
            if (tag.isItalic()) output.append("&o");
            if (tag.isUnderlined()) output.append("&n");
            if (tag.isStrikethrough()) output.append("&m");
            if (tag.isObfuscated()) output.append("&k");
            output.append(tag.toPlainText());
        }
        return output.toString();
    }

    @Override
    public String onRequest(OfflinePlayer p, String identifier) {
        String returnValue;
        if (identifier.equals("prefix")) {
            return ComponentSerializer.toString(plugin.handler.getPrefix(p.getPlayer()));
        }
        if (identifier.equals("shortprefix")) {
            return ComponentSerializer.toString(plugin.handler.getShortPrefix(p.getPlayer()));
        }
        if (identifier.equals("longprefix")) {
            return ComponentSerializer.toString(plugin.handler.getLongPrefix(p.getPlayer()));
        }
        if (identifier.equals("prefix_legacy")) {
            return plugin.handler.getPrefixString(p.getPlayer());
        }
        if (identifier.equals("shortprefix_legacy")) {
            return plugin.handler.getShortPrefixString(p.getPlayer());
        }
        if (identifier.equals("longprefix_legacy")) {
            return plugin.handler.getLongPrefixString(p.getPlayer());
        }
        if (identifier.equals("prefix_tab")) {
            return toTabFormat(plugin.handler.getPrefix(p.getPlayer()));
        }
        if (identifier.equals("longprefix_tab")) {
            return toTabFormat(plugin.handler.getLongPrefix(p.getPlayer()));
        }
        if (identifier.equals("shortprefix_tab")) {
            return toTabFormat(plugin.handler.getShortPrefix(p.getPlayer()));
        }
        return null;
    }
}
