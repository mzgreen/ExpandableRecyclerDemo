package pl.michalz.expandablerecyclerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import pl.michalz.expandablerecyclerdemo.adapter.DrawerItemChild;
import pl.michalz.expandablerecyclerdemo.adapter.DrawerItemParent;
import pl.michalz.expandablerecyclerdemo.adapter.ExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_ITEMS_KEY = "$LIST_ITEMS";
    private List<Object> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //I was too lazy to implement parcelable :P
        Object object = getLastCustomNonConfigurationInstance();
        if(object!=null) {
            listItems = (ArrayList<Object>) object;
        } else {
            generateListObjects();
        }

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ExpandableAdapter expandableDrawerAdapter = new ExpandableAdapter(listItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(expandableDrawerAdapter);
    }

    private void generateListObjects() {
        listItems = new ArrayList<>();
        for(int i=0;i<20;i++) {
            DrawerItemParent drawerItemParent = new DrawerItemParent("Parent title "+i);
            List<Object> childObjects = new ArrayList<>();
            if(i%2==0) {
                for (int j = 0; j < 2; j++) {
                    DrawerItemChild drawerItemChild = new DrawerItemChild("Child title " + j);
                    childObjects.add(drawerItemChild);
                }
            }
            drawerItemParent.setChildObjectList(childObjects);
            listItems.add(drawerItemParent);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return listItems;
    }
}
