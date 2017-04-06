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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Edward on 2017/04/05.
 */

public class fragment_edit_stock extends Fragment
{
    private View rootview;
    private ListView clinic_list_view;
    private ArrayList<String> data_list_items=new ArrayList<String>();
    private ArrayAdapter<String> data_list_adapter;
    private ListView clinic_edit_list_view;

    private LinearLayout stock_layout;
    private LinearLayout clinic_layout;
    private LinearLayout medication_layout;
    private LinearLayout button_layout;
    private LinearLayout stock_medication_edit_layout;
    private CLINIC selected_clinic;
    private int [] records;
    private Button submit_update_stock;
    private DataBaseHandler db;
    private int edit_text_count;
    private Button delete_clinic_button;
    private Button submit_clinic_button;
    private Button cancel_clinic_button;
    private Button add_clinic_button;
    private Spinner country_spinner;
    private EditText clinic_name_edit_text;
    public fragment_edit_stock(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_stock,container,false);
        Button open_stock_edit = (Button) rootview.findViewById(R.id.button_edit_stock);
        Button open_clinic_edit = (Button) rootview.findViewById(R.id.button_edit_clinic);
        Button open_medication_edit = (Button) rootview.findViewById(R.id.button_edit_medication);
        Button close_edit_view = (Button) rootview.findViewById(R.id.button_close_edit_window);
        Button close_stock_editing = (Button) rootview.findViewById(R.id.button_close_stock_edit);
        submit_update_stock = (Button) rootview.findViewById(R.id.button_submit_update);
        button_layout = (LinearLayout)rootview.findViewById(R.id.edit_button_layout);
        stock_layout = (LinearLayout)rootview.findViewById(R.id.stock_edit_layout);
        clinic_layout = (LinearLayout)rootview.findViewById(R.id.clinic_edit_layout);
        clinic_layout.setVisibility(View.GONE);
        stock_layout.setVisibility(View.GONE);
        button_layout.setVisibility(View.VISIBLE);
        country_spinner = (Spinner)rootview.findViewById(R.id.spinner_country);
        clinic_name_edit_text = (EditText)rootview.findViewById(R.id.editText_clinic_name);
        stock_medication_edit_layout = (LinearLayout)rootview.findViewById(R.id.stock_medication_layout_editor);
        delete_clinic_button = (Button)rootview.findViewById(R.id.button_delete_clinic);
        submit_clinic_button = (Button)rootview.findViewById(R.id.button_submit_clinic_edit);
        cancel_clinic_button = (Button)rootview.findViewById(R.id.button_cancel_clinic_edit);
        add_clinic_button = (Button)rootview.findViewById(R.id.button_add_clinic);
        cancel_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_layout.setVisibility(View.VISIBLE);
                clinic_layout.setVisibility(View.GONE);

            }
        });
        //TODO DELETE FUNCTION CLINIC
        delete_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(rootview.getContext())
                        .setTitle("Delete Clinic?")
                        .setMessage("Do you really want to whatever?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(MainActivity.get_M_Context(), "Yaay", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        open_clinic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clinic_layout.setVisibility(View.VISIBLE);
                stock_layout.setVisibility(View.GONE);
                button_layout.setVisibility(View.GONE);
                function_edit_clinic();
            }
        });
        close_stock_editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock_medication_edit_layout.removeAllViews();
                button_layout.setVisibility(View.VISIBLE);
                stock_layout.setVisibility(View.GONE);
                submit_update_stock.setVisibility(View.GONE);

            }
        });
        open_stock_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_layout.setVisibility(View.GONE);
                stock_layout.setVisibility(View.VISIBLE);
                function_edit_stock();
            }
        });
        close_edit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                MainActivity.Setpage(0);
            }
        });

        return rootview;
    }

    private void function_edit_stock(){
        clinic_list_view = (ListView) rootview.findViewById(R.id.edit_stock_clinics_list);
        data_list_items.clear();
        data_list_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,data_list_items);
        db = new DataBaseHandler(MainActivity.get_M_Context());
        int clinic_count = db.get_clinic_count();
        for (int i = 1; i < clinic_count + 1;i++){
            CLINIC clinic = db.get_clinic(i);
            data_list_items.add(clinic.getC_name() + "     " + clinic.getC_country());
        }
        data_list_adapter.notifyDataSetChanged();
        clinic_list_view.setAdapter(data_list_adapter);
        clinic_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                records = db.clinic_record_set(i+1);
                selected_clinic = db.get_clinic(i+1);
                stock_medication_edit_layout.removeAllViews();
                Log.d("POSITION", "POSITION " + i);
                final List<EditText> all_edit_text = new ArrayList<EditText>();
                all_edit_text.clear();
                for (int count =0; count < records.length;count++){
                    STOCK stock = db.get_stock(records[count]);
                    MEDICATION medication = db.get_medication(stock.getS_medication());

                    TextView medication_textview = new TextView(rootview.getContext());
                    medication_textview.setText(medication.getM_name());
                    medication_textview.setId(i+count);
                    EditText medication_stock_edit_text = new EditText(rootview.getContext());
                    medication_stock_edit_text.setText(String.valueOf(stock.getS_stock_count()));
                    medication_stock_edit_text.setId(count);
                    stock_medication_edit_layout.addView(medication_textview);
                    stock_medication_edit_layout.addView(medication_stock_edit_text);
                    all_edit_text.add(medication_stock_edit_text);
                    Log.d("Edittext","editText" + medication_stock_edit_text.getId());

                }
                submit_update_stock.setVisibility(View.VISIBLE);

                edit_text_count = records.length;
                submit_update_stock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submit_update_stock.setVisibility(View.GONE);
                        for (int j = 0;j < records.length;j++ ){
                           String updated_data = all_edit_text.get(j).getText().toString();
                           String update_spaces =  updated_data.replaceAll("\\s+","");
                            Log.d("updates", " " + update_spaces);
                            int int_update = Integer.parseInt(update_spaces);
                            STOCK updated_stock = db.get_stock(records[j]);
                            updated_stock.setS_stock_count(int_update);
                            db.update_stock(updated_stock);
                            stock_medication_edit_layout.removeAllViews();

                        }
                    }
                });
            }
        });
    }
    private void function_edit_clinic(){
        clinic_edit_list_view = (ListView)rootview.findViewById(R.id.clinic_edit_list);
        db = new DataBaseHandler(MainActivity.get_M_Context());
        data_list_items.clear();
        data_list_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,data_list_items);
        ArrayAdapter<String> country_array= new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_spinner_item, Global_Variables.getAfrican_Countries());
        country_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(country_array);
        int count = db.get_clinic_count();
        for (int i = 1;i < count + 1;i++){
            CLINIC clinic = db.get_clinic(i);
            data_list_items.add(clinic.getC_name() + "    " + clinic.getC_country());


        }
        delete_clinic_button.setVisibility(View.GONE);
        submit_clinic_button.setVisibility(View.GONE);
        add_clinic_button.setVisibility(View.VISIBLE);
        data_list_adapter.notifyDataSetChanged();
        clinic_edit_list_view.setAdapter(data_list_adapter);
        clinic_edit_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                delete_clinic_button.setVisibility(View.VISIBLE);
                submit_clinic_button.setVisibility(View.VISIBLE);
                add_clinic_button.setVisibility(View.GONE);
                CLINIC clinic1 = db.get_clinic(i+1);
                String country = clinic1.getC_country();
                selected_clinic = clinic1;
                for (int j = 0; j < Global_Variables.getAfrican_Countries().length;j++){
                    String country_list_entry = Global_Variables.getAfrican_Countries()[j];
                    if (country_list_entry.equals(country)){
                        country_spinner.setSelection(j);
                        j = Global_Variables.getAfrican_Countries().length+1;

                    }
                }
                clinic_name_edit_text.setText(clinic1.getC_name());
                Toast.makeText(MainActivity.get_M_Context(), "Yaay " + (i+1) , Toast.LENGTH_SHORT).show();
            }
        });
        submit_clinic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_clinic_button.setVisibility(View.GONE);
                submit_clinic_button.setVisibility(View.GONE);
                add_clinic_button.setVisibility(View.VISIBLE);
                clinic_name_edit_text.setText("Name");
                country_spinner.setSelection(1);
                function_edit_clinic();
            }
        });
    }
    private void function_edit_medicatdion(){}

}
