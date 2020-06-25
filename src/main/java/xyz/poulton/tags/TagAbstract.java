package xyz.poulton.tags;

import net.md_5.bungee.api.chat.BaseComponent;

public abstract class TagAbstract {
    private int weight;
    private String groupName;
    private String orderGroup;
    public final boolean prefersShort;

    public TagAbstract(String group, int _weight, String order, boolean isShort) {
        groupName = group;
        weight = _weight;
        orderGroup = order;
        prefersShort = isShort;
    }

    public String getGroupName() { return groupName; }
    public int getWeight() { return weight; }
    public String getOrderGroup() { return orderGroup; }

    public abstract BaseComponent[] getFullPrefix();
    public abstract BaseComponent[] getShortPrefix();
}
