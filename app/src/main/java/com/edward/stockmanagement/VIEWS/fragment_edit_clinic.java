package com.edward.stockmanagement.VIEWS;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.Toast;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.CLINIC_OBJECT_ARRAYLIST;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.OBJECTS.STOCK_OBJECT_ARRAY_LIST;
import com.edward.stockmanagement.R;

import java.util.ArrayList;
import java.util.Arrays;

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

        add_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clinic_name_edit_text.getText().toString().equals("Name")){
                    Toast.makeText(MainActivity.get_M_Context(), "You need to enter a valid Clinic Name", Toast.LENGTH_SHORT).show();
                }
                else {
                    int clinic_count = db.get_clinic_count();
                    clinic = new CLINIC();
                    clinic.setC_name(clinic_name_edit_text.getText().toString());
                    int spinner_selected = country_spinner.getSelectedItemPosition();
                    String country_selected =  Global_Variables.getAfrican_Countries()[spinner_selected];
                    clinic.setC_country(country_selected);
                    clinic.setC_uuid(db.Clinic_UUID_GENERATOR());
                    db.add_clinic(clinic);

                    clinic = db.get_clinic(clinic_count+1);
                    int medication_count = db.get_medication_count();
                    for (int i = 0; i < medication_count;i++){
                        MEDICATION medication = db.get_medication(i+1);
                        STOCK stock = new STOCK();
                        stock.setS_clinic(clinic.getC_id());
                        Log.d("COUNT", "CLINIC : " + clinic.getC_id());
                        stock.setS_clinic_uuid(clinic.getC_uuid());
                        stock.setS_medication(medication.getM_id());
                        stock.setS_medication_uuid(medication.getM_uuid());
                        stock.setS_stock_count(0);
                        db.add_stock(stock);
                    }
                    clinic_edit_list_view_items.clear();
                    final int count = db.get_clinic_count();
                    Log.d("COUNT","COUNT : " + count);
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
            }
        });

        delete_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(fragment_edit_clinic.this.getActivity())
                        .setTitle("Title")
                        .setMessage("Are you sure you would like to delete this Clinic? all stock items related to the clinic will also be removed!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                positive_delete_function();
                                Toast.makeText(MainActivity.get_M_Context(), "Records Will be Deleted!", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
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

    private void positive_delete_function(){
        clinic = db.get_clinic(selected_position +1);
        CLINIC_OBJECT_ARRAYLIST new_clinic  = new CLINIC_OBJECT_ARRAYLIST(clinic.getC_id(),clinic.getC_name(),clinic.getC_country(),clinic.getC_uuid());

        //  db.delete_clinic(new_clinic.getC_id());
        CUSTOM_DATABASE_DELETE_FUNCTION(selected_position + 1);

        clinic_edit_list_view_items.clear();
        final int count = db.get_clinic_count();
        Log.d("COUNT","COUNT : " + count);
        for (int i = 1;i < count + 1;i++){
            clinic = db.get_clinic(i);
            clinic_edit_list_view_items.add(clinic.getC_name() + "    " + clinic.getC_country());
        }
        clinic_edit_list_view_adapter.notifyDataSetChanged();
        clinic_name_edit_text.setText("Name");
        country_spinner.setSelection(1);
        clinic = null;

    }
    //////\\\\Delete Function for SQLite seems to be broken and I have sat with this problem for about 7 hours.
    //////\\\\ when deleting something it deletes the row, duplicates the previous row and throws away the last row.
    //////\\\\I hope this patch is not too nasty
    private void CUSTOM_DATABASE_DELETE_FUNCTION(int position){
        ArrayList<CLINIC_OBJECT_ARRAYLIST> clinicArrayList = new ArrayList<CLINIC_OBJECT_ARRAYLIST>();
        int clinic_count = db.get_clinic_count();
        for (int i = 0; i < clinic_count;i++){
            CLINIC clinic = db.get_clinic(i+1);
            CLINIC_OBJECT_ARRAYLIST new_clinic  = new CLINIC_OBJECT_ARRAYLIST(clinic.getC_id(),
                    clinic.getC_name(),clinic.getC_country(),clinic.getC_uuid());
            Log.d("ARRAY_CLINIC","ARRAY : " + clinic.getC_id());
            Log.d("ARRAY_CLINIC","ARRAY : " + clinic.getC_name());
            if (new_clinic.getC_id() != position){
            clinicArrayList.add(new_clinic);}
        }
        records = db.clinic_record_set(position);
        ArrayList<STOCK_OBJECT_ARRAY_LIST> stockArrayList = new ArrayList<>();
        int stock_count = db.get_stock_count();

        for (int i = 0;i < stock_count;i++){
            STOCK stock = db.get_stock(i+1);
            STOCK_OBJECT_ARRAY_LIST stock_object_array_list = new STOCK_OBJECT_ARRAY_LIST(stock.getS_id(),
                    stock.getS_clinic(),stock.getS_medication(),stock.getS_clinic_uuid(),
                    stock.getS_medication_uuid(),stock.getS_stock_count());

            boolean test_records = false;
            for (int j = 0; j < records.length;j++){
                if (records[j] == stock.getS_id()){
                    test_records = true;
                    j = records.length + 1;
                }
                else test_records = false;
            }

            if (!test_records){
                stockArrayList.add(stock_object_array_list);
            }
        }
        for (int delete_count = 0; delete_count < clinic_count;delete_count++){
            clinic = db.get_clinic(delete_count+1);
            db.delete_clinic(clinic.getC_id());
        }
        for(int k =0; k < clinicArrayList.size();k++){
            Log.d("ARRAYLIST","ARRAY 1 " + clinicArrayList.get(k).getC_name());
            CLINIC_OBJECT_ARRAYLIST clinic_object_arraylist = clinicArrayList.get(k);
            CLINIC new_clinic = new CLINIC();
            new_clinic.setC_name(clinic_object_arraylist.getC_name());
            new_clinic.setC_country(clinic_object_arraylist.getC_country());
            new_clinic.setC_uuid(clinic_object_arraylist.getC_uuid());
            db.add_clinic(new_clinic);
        }
        for (int delete_stock_count = 0;delete_stock_count < stock_count;delete_stock_count++){
            STOCK deleted_stock = db.get_stock(delete_stock_count + 1);
            db.delete_stock(deleted_stock);
        }
        for (int new_stock = 0; new_stock < stockArrayList.size();new_stock++){
            STOCK_OBJECT_ARRAY_LIST stock_object_array_list = stockArrayList.get(new_stock);
            Log.d("STOCK","STOCK : " + stock_object_array_list.getS_clinic());
            STOCK stock_new = new STOCK();
            String check_uuid = stock_object_array_list.getS_clinic_uuid();
            CLINIC updated_clinic = db.get_clinic_by_uuid(check_uuid);
            stock_new.setS_clinic(updated_clinic.getC_id());
            stock_new.setS_clinic_uuid(check_uuid);
            stock_new.setS_medication(stock_object_array_list.getS_medication());
            stock_new.setS_medication_uuid(stock_object_array_list.getS_medication_uuid());
            stock_new.setS_stock_count(stock_object_array_list.getS_stock_count());
            Log.d("STOCK","STOCK : " + stock_new.getS_clinic());
            db.add_stock(stock_new);
        }

        clinic_edit_list_view_items.clear();
        final int count = db.get_clinic_count();
        Log.d("COUNT","COUNT : " + count);
        for (int i = 1;i < count + 1;i++){
            clinic = db.get_clinic(i);
            clinic_edit_list_view_items.add(clinic.getC_name() + "    " + clinic.getC_country());
        }
        clinic_edit_list_view_adapter.notifyDataSetChanged();
    }
}
