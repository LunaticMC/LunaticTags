// Copyright © Lucy Poulton 2020. All rights reserved.

package xyz.poulton.tags;

import net.md_5.bungee.api.chat.BaseComponent;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TagHandler {

    private ArrayList<OrderGroup> tags = new ArrayList<>();
    private final Permission vaultHook;

    public TagHandler(Permission perm) {
        vaultHook = perm;
    }

    public static Comparator<TagAbstract> orderWeight = (o1, o2) ->
            (o2.getWeight() > o1.getWeight() ? -1 : (o2.getWeight() == o2.getWeight() ? 0 : 1));
    public static Comparator<OrderGroup> orderWeightGroups = (o1, o2) ->
            (o2.getOrder() > o1.getOrder() ? -1 : (o2.getOrder() == o2.getOrder() ? 0 : 1));

    /**
     * Converts a base component array tag to a legacy string. It's bad, don't use it
     * @param tag The tag to convert
     * @return An approximated legacy string
     */
    public static String tagToString(BaseComponent[] tag) {
        StringBuilder tagString = new StringBuilder();
        for (BaseComponent component: tag) {
            tagString.append(component.toLegacyText());
        }
        return tagString.toString();
    }

    /**
     * Clears the tag list and replaces it with what is supplied.
     * @param tags The new tags list
     */

    public void loadTags(HashMap<String, OrderGroup> tags) {
        this.tags = new ArrayList<>(tags.values());
        this.tags.sort(orderWeightGroups);
    }

    /**
     * Filters tags based on a predicate
     * @param criteria The predicate to filter by
     * @return A list of matching tags
     */
    public List<TagAbstract> filter(ArrayList<TagAbstract> list, Predicate<TagAbstract> criteria) {
        return list.stream().filter(criteria).collect(Collectors.toList());
    }

    /**
     * Finds a player's tag
     * @param player The player to locate the tag for
     * @return The player's tag
     */
    public ArrayList<TagAbstract> findPlayerTag(Player player) {
        String[] groups = vaultHook.getPlayerGroups(player);
        ArrayList<TagAbstract> discoveredTags = new ArrayList<>();
        ArrayList<String> usedGroups = new ArrayList<>();
        for (OrderGroup group : tags) {
            ArrayList<TagAbstract> matches = new ArrayList<>();
            for (String vaultGroup : groups) matches.addAll(filter(group.getTags(),
                    t -> t.getGroupName().equals(vaultGroup) && !usedGroups.contains(t.getGroupName())));
            matches.sort(orderWeight);
            try {
                discoveredTags.add(matches.get(0));
                usedGroups.add(matches.get(0).getGroupName());
            } catch (IndexOutOfBoundsException e) {
                if (group.getDefaultTag() != null) discoveredTags.add(group.getDefaultTag());
            }
        }
        return discoveredTags;
    }

    public BaseComponent[] getLongPrefix(Player player) {
        ArrayList<BaseComponent> components = new ArrayList<>();
        for (TagAbstract tag : findPlayerTag(player)) {
            components.addAll(Arrays.asList(tag.getFullPrefix()));
        }
        return components.toArray(new BaseComponent[0]);
    }

    public BaseComponent[] getShortPrefix(Player player) {
        ArrayList<BaseComponent> components = new ArrayList<>();
        for (TagAbstract tag : findPlayerTag(player)) {
            components.addAll(Arrays.asList(tag.getShortPrefix()));
        }
        return components.toArray(new BaseComponent[0]);
    }

    public BaseComponent[] getPrefix(Player player) {
        ArrayList<BaseComponent> components = new ArrayList<>();
        for (TagAbstract tag : findPlayerTag(player)) {
            components.addAll(Arrays.asList(tag.prefersShort ? tag.getShortPrefix() : tag.getFullPrefix()));
        }
        return components.toArray(new BaseComponent[0]);
    }

    public String getPrefixString(Player player) {
        return tagToString(getPrefix(player));
    }

    public String getShortPrefixString(Player player) {
        return tagToString(getShortPrefix(player));
    }

    public String getLongPrefixString(Player player) {
        return tagToString(getLongPrefix(player));
    }
}
