package pl.michalz.expandablerecyclerdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import pl.michalz.expandablerecyclerdemo.R;

public class ExpandableItemChildViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvChildTitle;
    private final View parent;

    public ExpandableItemChildViewHolder(View parent, TextView tvChildTitle) {
        super(parent);
        this.tvChildTitle = tvChildTitle;
        this.parent = parent;
    }

    public static ExpandableItemChildViewHolder newInstance(View parent) {
        TextView tvChildTitle = (TextView) parent.findViewById(R.id.tvChildTitle);
        return new ExpandableItemChildViewHolder(parent, tvChildTitle);
    }

    public void setChildText(CharSequence title) {
        tvChildTitle.setText(title);
    }
}
