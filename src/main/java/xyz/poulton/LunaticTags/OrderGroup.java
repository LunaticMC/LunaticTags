package xyz.poulton.LunaticTags;

import xyz.poulton.LunaticTags.tag.TagAbstract;

import java.util.ArrayList;

public class OrderGroup {
    private final String name;
    private final int order;
    private TagAbstract defaultTag;
    public ArrayList<TagAbstract> tags = new ArrayList<>();
    private final boolean isShort;

    public OrderGroup(String _name, int _order, boolean isShort) {
        name = _name;
        order = _order;
        this.isShort = isShort;
    }

    public ArrayList<TagAbstract> getTags() {
        return tags;
    }

    public void addTag(TagAbstract tag) {
        tags.add(tag);
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public TagAbstract getDefaultTag() {
        return defaultTag;
    }

    public void setDefaultTag(TagAbstract defaultTag) {
        this.defaultTag = defaultTag;
    }

    public boolean isShort() {
        return isShort;
    }
}
