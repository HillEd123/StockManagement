package com.edward.stockmanagement.VIEWS;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2017/04/06.
 */

public class fragment_edit_stock_refactor extends Fragment {
    public fragment_edit_stock_refactor(){}
    private View rootview;
    private Button close_stock_editing ;
    private Button submit_update_stock ;
    private ListView clinic_list_view;
    private ArrayList<String> data_list_items=new ArrayList<String>();
    private ArrayAdapter<String> data_list_adapter;
    private DataBaseHandler db;
    private int [] records;
    private LinearLayout stock_medication_edit_layout;
    private CLINIC selected_clinic;
    private int edit_text_count;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_stock,container,false);
        close_stock_editing = (Button) rootview.findViewById(R.id.button_close_stock_edit);
        submit_update_stock = (Button) rootview.findViewById(R.id.button_submit_update);
        stock_medication_edit_layout = (LinearLayout)rootview.findViewById(R.id.stock_medication_layout_editor);
        close_stock_editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(2);
            }
        });

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
                submit_update_stock.setOnClickListener(new View.OnClickListener()
                {
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
                            InputMethodManager inputManager =
                                    (InputMethodManager) MainActivity.get_M_Context().
                                            getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(
                                    fragment_edit_stock_refactor.this.getActivity().getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                });
            }
        });

        return rootview;
    }



}
