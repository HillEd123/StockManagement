package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;

import java.util.ArrayList;

/**
 * Created by Edward on 2017/04/06.
 */

public class fragment_edit_medication extends Fragment {
    public fragment_edit_medication(){}
    private View rootview;
    private ListView medication_edit_list;
    private ArrayList<String> medication_edit_list_items=new ArrayList<String>();
    private ArrayAdapter<String> medication_edit_list_adapter;
    private MEDICATION selected_medication;
    private int [] records;
    private DataBaseHandler db;
    private int selected_position;
    private Button cancel_button;
    private Button submit_button;
    private Button add_button;
    private Button delete_button;
    private EditText medication_edit_text;
    private MEDICATION medication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_medicine,container,false);
        cancel_button = (Button)rootview.findViewById(R.id.button_cancel_medication);
        submit_button = (Button)rootview.findViewById(R.id.button_medication_submit);
        add_button = (Button) rootview.findViewById(R.id.button_medication_add);
        delete_button = (Button)rootview.findViewById(R.id.button_medication_delete);
        medication_edit_text = (EditText)rootview.findViewById(R.id.editText_medication);
        db = new DataBaseHandler(MainActivity.get_M_Context());
        medication_edit_list = (ListView)rootview.findViewById(R.id.list_view_medication);
        medication_edit_list_items.clear();
        medication_edit_list_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,medication_edit_list_items);
        update_list();
        medication_edit_list.setAdapter(medication_edit_list_adapter);
        submit_button.setVisibility(View.GONE);
        delete_button.setVisibility(View.GONE);
        add_button.setVisibility(View.VISIBLE);

        medication_edit_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                submit_button.setVisibility(View.VISIBLE);
                delete_button.setVisibility(View.VISIBLE);
                add_button.setVisibility(View.GONE);
                selected_position = i;
                selected_medication = db.get_medication(i+1);
                medication_edit_text.setText(selected_medication.getM_name().toString());
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medication = new MEDICATION();
                medication.setM_name(medication_edit_text.getText().toString());
                medication.setM_uuid(db.Clinic_UUID_GENERATOR());
                db.add_medication(medication);
                update_list();

                int medication_count = db.get_medication_count();
                medication = db.get_medication(medication_count +1);
                int clinic_count = db.get_clinic_count();
                for (int i = 0; i < clinic_count;i++){
                    CLINIC clinic = db.get_clinic(i+1);
                    STOCK stock = new STOCK();
                    stock.setS_clinic(clinic.getC_id());
                    stock.setS_clinic_uuid(clinic.getC_uuid());
                    stock.setS_medication(medication.getM_id());
                    stock.setS_medication_uuid(medication.getM_uuid());
                    stock.setS_stock_count(0);
                    db.add_stock(stock);
                }
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_button.setVisibility(View.GONE);
                delete_button.setVisibility(View.GONE);
                add_button.setVisibility(View.VISIBLE);
                medication = selected_medication;
                medication.setM_name(medication_edit_text.getText().toString());
                db.update_medication(medication);
                update_list();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                MainActivity.Setpage(2);
            }
        });
        return rootview;
    }

    private void update_list(){
        medication_edit_list_items.clear();
        final int medication_count = db.get_medication_count();
        for (int i = 0; i < medication_count;i++){
            MEDICATION medication = db.get_medication(i+1);
            medication_edit_list_items.add(medication.getM_name());
        }
        medication_edit_list_adapter.notifyDataSetChanged();
    }
}
