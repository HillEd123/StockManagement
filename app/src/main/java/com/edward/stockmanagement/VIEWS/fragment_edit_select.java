package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.R;

/**
 * Created by Edward on 2017/04/06.
 */

public class fragment_edit_select extends Fragment {
    public fragment_edit_select(){}
    private View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_edit_select,container,false);
        Button open_stock_edit = (Button) rootview.findViewById(R.id.button_edit_stock);
        Button open_clinic_edit = (Button) rootview.findViewById(R.id.button_edit_clinic);
        Button open_medication_edit = (Button) rootview.findViewById(R.id.button_edit_medication);
        Button close_edit_view = (Button) rootview.findViewById(R.id.button_close_edit_window);
        open_stock_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(3);
            }
        });
        open_clinic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(4);
            }
        });
        open_medication_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(5);
            }
        });
        close_edit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(0);
            }
        });
        return rootview;
    }
}
