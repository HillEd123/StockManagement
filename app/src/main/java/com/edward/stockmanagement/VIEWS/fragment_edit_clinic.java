package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.R;

import java.util.ArrayList;

/**
 * Created by Edward on 2017/04/06.
 */

public class fragment_edit_clinic extends Fragment {
    public fragment_edit_clinic(){}
    private View rootview;
    private ListView clinic_edit_list_view;
    private ArrayList<String> clinic_edit_list_view_items=new ArrayList<String>();
    private ArrayAdapter<String> clinic_edit_list_view_adapter;
    private CLINIC selected_clinic;
    private int [] records;
    private DataBaseHandler db;
    private Button delete_clinic_button;
    private Button submit_clinic_button;
    private Button cancel_clinic_button;
    private Button add_clinic_button;
    private Spinner country_spinner;
    private EditText clinic_name_edit_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_clinic,container,false);
        country_spinner = (Spinner)rootview.findViewById(R.id.spinner_country);
        clinic_name_edit_text = (EditText)rootview.findViewById(R.id.editText_clinic_name);
        delete_clinic_button = (Button)rootview.findViewById(R.id.button_delete_clinic);
        submit_clinic_button = (Button)rootview.findViewById(R.id.button_submit_clinic_edit);
        cancel_clinic_button = (Button)rootview.findViewById(R.id.button_cancel_clinic_edit);
        add_clinic_button = (Button)rootview.findViewById(R.id.button_add_clinic);
        return rootview;
    }
}
