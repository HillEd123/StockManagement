package com.edward.stockmanagement.VIEWS;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.MEDICATION_OBJECT_ARRAY_LIST;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.OBJECTS.STOCK_OBJECT_ARRAY_LIST;
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
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(fragment_edit_medication.this.getActivity())
                        .setTitle("Delete Medication")
                        .setMessage("Are you sure you would like to delete this medication? doing so will remove all Stock items related to Clinics!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                CUSTOM_MEDICATION_DELETE_FUNCTION(selected_position+1);
                                update_list();
                                Toast.makeText(MainActivity.get_M_Context(), "Removing Medication", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

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


    private void CUSTOM_MEDICATION_DELETE_FUNCTION(int position){
        ArrayList<STOCK_OBJECT_ARRAY_LIST>stockArrayList = new ArrayList<>();
        ArrayList<MEDICATION_OBJECT_ARRAY_LIST>medicationArrayList = new ArrayList<>();
        int medication_count = db.get_medication_count();
        int stock_count = db.get_stock_count();
        for (int i = 0;i < medication_count+1;i++){
            MEDICATION medication = db.get_medication(i+1);
            MEDICATION_OBJECT_ARRAY_LIST new_medication = new MEDICATION_OBJECT_ARRAY_LIST(
                medication.getM_id(),medication.getM_name(),medication.getM_uuid());
            if (new_medication.getM_id() != position){
                medicationArrayList.add(new_medication);
            }


        }
        MEDICATION selected_medication = db.get_medication(position);
        records = db.medication_record_set(selected_medication.getM_uuid());
        Log.d("RECORDS","records " + records[0]);
        Log.d("RECORDS","records " + records[1]);
        Log.d("RECORDS","records " + records[2]);
        Log.d("RECORDS","records " + records[3]);
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
        for (int delete_medication =1; delete_medication < medication_count + 1;delete_medication++){
            MEDICATION medication = db.get_medication(delete_medication);
            db.delete_medication(medication);
        }
        for (int delete_stock = 1; delete_stock < stock_count + 1;delete_stock++){
            STOCK stock = db.get_stock(delete_stock);
            db.delete_stock(stock);
        }

        for (int new_count = 0;new_count < medicationArrayList.size();new_count++){
            MEDICATION_OBJECT_ARRAY_LIST new_medication = medicationArrayList.get(new_count);
            MEDICATION medication = new MEDICATION();
            medication.setM_name(new_medication.getM_name());
            medication.setM_uuid(new_medication.getM_uuid());
            db.add_medication(medication);
        }

        for (int new_stock = 0; new_stock < stockArrayList.size();new_stock++){
        STOCK_OBJECT_ARRAY_LIST stock_object_array_list = stockArrayList.get(new_stock);
        Log.d("STOCK","STOCK : " + stock_object_array_list.getS_clinic());
        STOCK stock_new = new STOCK();
        String check_uuid = stock_object_array_list.getS_medication_uuid();
        MEDICATION updated_MEDICATION = db.get_medication_by_uuid(check_uuid);
        stock_new.setS_clinic(stock_object_array_list.getS_clinic());
        stock_new.setS_clinic_uuid(stock_object_array_list.getS_clinic_uuid());
        stock_new.setS_medication(updated_MEDICATION.getM_id());
        stock_new.setS_medication_uuid(check_uuid);
        stock_new.setS_stock_count(stock_object_array_list.getS_stock_count());
        Log.d("STOCK","STOCK : " + stock_new.getS_clinic());
        db.add_stock(stock_new);
        }
        update_list();
    }
}

