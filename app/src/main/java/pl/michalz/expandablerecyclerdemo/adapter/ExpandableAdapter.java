package pl.michalz.expandablerecyclerdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.michalz.expandablerecyclerdemo.R;

import java.util.List;

public class ExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_PARENT = 0;
    private static final int VIEW_TYPE_CHILD = 1;

    private List<Object> itemsList;

    public ExpandableAdapter(List<Object> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_PARENT) {
            return this.onCreateParentViewHolder(parent);
        } else if(viewType == VIEW_TYPE_CHILD) {
            return this.onCreateChildViewHolder(parent);
        } else {
            throw new IllegalStateException("Incorrect ViewType found");
        }
    }

    public DrawerItemParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_parent, parent, false);
        return DrawerItemParentViewHolder.newInstance(view);
    }

    public DrawerItemChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_child, parent, false);
        return DrawerItemChildViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(this.itemsList.get(position) instanceof DrawerItemParent) {
            DrawerItemParentViewHolder parentViewHolder = (DrawerItemParentViewHolder)holder;
            this.onBindParentViewHolder(parentViewHolder, position, this.itemsList.get(position));
        } else {
            if(this.itemsList.get(position) == null) {
                throw new IllegalStateException("Incorrect ViewHolder found");
            }
            DrawerItemChildViewHolder childViewHolder = (DrawerItemChildViewHolder)holder;
            this.onBindChildViewHolder(childViewHolder, position, this.itemsList.get(position));
        }

    }

    public void onBindParentViewHolder(DrawerItemParentViewHolder drawerItemParentViewHolder, int position, Object drawerItem) {
        final DrawerItemParent drawerItemParent = (DrawerItemParent) drawerItem;
        drawerItemParentViewHolder.setParentText(drawerItemParent.getTitle());
        drawerItemParentViewHolder.setExpandButtonGone(drawerItemParent.getChildObjectList().isEmpty());
        //Omitted arrow animation for simplicity
        drawerItemParentViewHolder.setExpandClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerItemParent.isExpanded()) {
                    int count = drawerItemParent.getChildObjectList().size();
                    itemsList.removeAll(drawerItemParent.getChildObjectList());
                    int index = itemsList.indexOf(drawerItemParent);
                    notifyItemRangeRemoved(index+1, count);
                } else {
                    List<Object> children = drawerItemParent.getChildObjectList();
                    int index = itemsList.indexOf(drawerItemParent);
                    itemsList.addAll(index + 1, children);
                    notifyItemRangeInserted(index + 1, children.size());
                }
                drawerItemParent.setExpanded(!drawerItemParent.isExpanded());
            }
        });
    }

    public void onBindChildViewHolder(DrawerItemChildViewHolder drawerItemParentViewHolder, int position, Object drawerItem) {
        DrawerItemChild drawerItemChild = (DrawerItemChild) drawerItem;
        drawerItemParentViewHolder.setChildText(drawerItemChild.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemsList !=null ? itemsList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(this.itemsList.get(position) instanceof DrawerItemParent) {
            return VIEW_TYPE_PARENT;
        } else if(this.itemsList.get(position) == null) {
            throw new IllegalStateException("Null object added");
        } else {
            return VIEW_TYPE_CHILD;
        }
    }
}

