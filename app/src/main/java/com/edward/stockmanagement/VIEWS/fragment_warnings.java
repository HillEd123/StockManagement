package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.R;

import static com.edward.stockmanagement.Controller.MainActivity.warnings_list_adapter;
import static com.edward.stockmanagement.Controller.MainActivity.warnings_list_items;
import static com.edward.stockmanagement.Controller.MainActivity.warnings_list_view;

/**
 * Created by Edward on 2017/04/05.
 */

public class fragment_warnings extends Fragment {

public fragment_warnings(){}

    View warning_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        warning_view = inflater.inflate(R.layout.fragment_warnings,container,false);
        warnings_list_view = (ListView)warning_view.findViewById(R.id.warning_list);
        warnings_list_items.clear();
        warnings_list_items.add("Test");
        warnings_list_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,warnings_list_items);
        if (warnings_list_view !=null){
            Log.d("LIST","LISTVIEW");
            warnings_list_view.setAdapter(warnings_list_adapter);}
        if (warnings_list_view == null){
            Log.d("LIST","LISTVIEW NULL");
        }
        MainActivity.check_stock();
        return warning_view;
    }

}
