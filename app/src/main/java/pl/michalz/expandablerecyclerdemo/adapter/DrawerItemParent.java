package pl.michalz.expandablerecyclerdemo.adapter;

import java.util.List;

public class DrawerItemParent {

    private final String title;
    private boolean expanded;
    private List<Object> childObjects;

    public DrawerItemParent(String title) {
        this.title = title;
        this.expanded = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<Object> getChildObjectList() {
        return childObjects;
    }

    public void setChildObjectList(List<Object> list) {
        this.childObjects = list;
    }
}
