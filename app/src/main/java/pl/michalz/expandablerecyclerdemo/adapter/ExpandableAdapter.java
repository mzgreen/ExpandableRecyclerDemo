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

    public ExpandableItemParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item_parent, parent, false);
        return ExpandableItemParentViewHolder.newInstance(view);
    }

    public ExpandableItemChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_item_child, parent, false);
        return ExpandableItemChildViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(this.itemsList.get(position) instanceof ExpandableItemParent) {
            ExpandableItemParentViewHolder parentViewHolder = (ExpandableItemParentViewHolder)holder;
            this.onBindParentViewHolder(parentViewHolder, position, this.itemsList.get(position));
        } else {
            if(this.itemsList.get(position) == null) {
                throw new IllegalStateException("Incorrect ViewHolder found");
            }
            ExpandableItemChildViewHolder childViewHolder = (ExpandableItemChildViewHolder)holder;
            this.onBindChildViewHolder(childViewHolder, position, this.itemsList.get(position));
        }

    }

    public void onBindParentViewHolder(ExpandableItemParentViewHolder expandableItemParentViewHolder, int position, Object expandableItem) {
        final ExpandableItemParent expandableItemParent = (ExpandableItemParent) expandableItem;
        expandableItemParentViewHolder.setParentText(expandableItemParent.getTitle());
        expandableItemParentViewHolder.setExpandButtonGone(expandableItemParent.getChildObjectList().isEmpty());
        //Omitted arrow animation for simplicity
        expandableItemParentViewHolder.setExpandClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableItemParent.isExpanded()) {
                    int count = expandableItemParent.getChildObjectList().size();
                    itemsList.removeAll(expandableItemParent.getChildObjectList());
                    int index = itemsList.indexOf(expandableItemParent);
                    notifyItemRangeRemoved(index+1, count);
                } else {
                    List<Object> children = expandableItemParent.getChildObjectList();
                    int index = itemsList.indexOf(expandableItemParent);
                    itemsList.addAll(index + 1, children);
                    notifyItemRangeInserted(index + 1, children.size());
                }
                expandableItemParent.setExpanded(!expandableItemParent.isExpanded());
            }
        });
    }

    public void onBindChildViewHolder(ExpandableItemChildViewHolder expandableItemParentViewHolder, int position, Object expandableItem) {
        ExpandableItemChild expandableItemChild = (ExpandableItemChild) expandableItem;
        expandableItemParentViewHolder.setChildText(expandableItemChild.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemsList !=null ? itemsList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(this.itemsList.get(position) instanceof ExpandableItemParent) {
            return VIEW_TYPE_PARENT;
        } else if(this.itemsList.get(position) == null) {
            throw new IllegalStateException("Null object added");
        } else {
            return VIEW_TYPE_CHILD;
        }
    }
}

