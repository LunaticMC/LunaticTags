package xyz.poulton.tags;

import net.md_5.bungee.api.chat.BaseComponent;

public class Tag extends TagAbstract {
    private BaseComponent[] fullPrefix;
    private BaseComponent[] shortPrefix;


    public Tag(String group, BaseComponent[] full, BaseComponent[] _short, int _weight, String order, boolean isShort) {
        super(group, _weight, order, isShort);
        fullPrefix = full.clone();
        shortPrefix = _short.clone();

    }

    public BaseComponent[] getFullPrefix() {
        return fullPrefix.clone();
    }

    public BaseComponent[] getShortPrefix() {
        return shortPrefix.clone();
    }
}