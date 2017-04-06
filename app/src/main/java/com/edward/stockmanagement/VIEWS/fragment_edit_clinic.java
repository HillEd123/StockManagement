package com.edward.stockmanagement.VIEWS;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
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
    private CLINIC clinic;
    private int selected_position;
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
        clinic_edit_list_view = (ListView)rootview.findViewById(R.id.clinic_edit_list);
        delete_clinic_button.setVisibility(View.GONE);
        submit_clinic_button.setVisibility(View.GONE);
        add_clinic_button.setVisibility(View.VISIBLE);

        ArrayAdapter<String> country_array= new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_spinner_item, Global_Variables.getAfrican_Countries());
        country_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(country_array);
        clinic_edit_list_view_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,clinic_edit_list_view_items);
        clinic_edit_list_view_items.clear();
        db = new DataBaseHandler(MainActivity.get_M_Context());
        final int count = db.get_clinic_count();
        for (int i = 1;i < count + 1;i++){
            clinic = db.get_clinic(i);
            clinic_edit_list_view_items.add(clinic.getC_name() + "    " + clinic.getC_country());
        }
        clinic_edit_list_view_adapter.notifyDataSetChanged();
        clinic_edit_list_view.setAdapter(clinic_edit_list_view_adapter);
        clinic_edit_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clinic = db.get_clinic(i+1);
                clinic_name_edit_text.setText(clinic.getC_name());
                String country = clinic.getC_country();
                selected_position = i;
                delete_clinic_button.setVisibility(View.VISIBLE);
                submit_clinic_button.setVisibility(View.VISIBLE);
                add_clinic_button.setVisibility(View.GONE);
                for (int j = 0; j < Global_Variables.getAfrican_Countries().length;j++){
                    String country_list_entry = Global_Variables.getAfrican_Countries()[j];
                    if (country_list_entry.equals(country)){
                        country_spinner.setSelection(j);
                        j = Global_Variables.getAfrican_Countries().length+1;

                    }
                }
            }
        });

        submit_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_clinic_button.setVisibility(View.GONE);
                submit_clinic_button.setVisibility(View.GONE);
                add_clinic_button.setVisibility(View.VISIBLE);
                clinic = db.get_clinic(selected_position + 1);

                clinic.setC_name(clinic_name_edit_text.getText().toString());
                int spinner_selected = country_spinner.getSelectedItemPosition();
                String country_selected =  Global_Variables.getAfrican_Countries()[spinner_selected];
                clinic.setC_country(country_selected);
                db.update_clinic(clinic);
                clinic_edit_list_view_items.clear();
                final int count = db.get_clinic_count();
                for (int i = 1;i < count + 1;i++){
                    clinic = db.get_clinic(i);
                    clinic_edit_list_view_items.add(clinic.getC_name() + "    " + clinic.getC_country());
                }
                clinic_edit_list_view_adapter.notifyDataSetChanged();
                clinic_name_edit_text.setText("Name");
                country_spinner.setSelection(1);
                InputMethodManager inputManager =
                        (InputMethodManager) MainActivity.get_M_Context().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        fragment_edit_clinic.this.getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        cancel_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                MainActivity.Setpage(2);
            }
        });
        return rootview;
    }
}
