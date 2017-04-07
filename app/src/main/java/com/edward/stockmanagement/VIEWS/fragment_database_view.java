package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;

import java.util.ArrayList;

/**
 * Created by Edward on 2017/04/05.
 */

public class fragment_database_view extends Fragment {
    public fragment_database_view(){}
    private ArrayList<String> data_list_items=new ArrayList<String>();
    private ArrayAdapter<String> data_list_adapter;

    private View rootview;
    private ListView data_list_view;
    private DataBaseHandler db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_db_view,container,false);
        data_list_view = (ListView)rootview.findViewById(R.id.listView_Database_view);
        data_list_items.clear();
        data_list_adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,data_list_items);
        db = new DataBaseHandler(MainActivity.get_M_Context());
        data_list_view.setAdapter(data_list_adapter);
        Button close = (Button)rootview.findViewById(R.id.button_close_db_view);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(0);
            }
        });
        Button full_data_view = (Button)rootview.findViewById(R.id.button_full_db_view);
        full_data_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_full_database();
            }
        });
        Button low_stock_view = (Button)rootview.findViewById(R.id.button_low_stock);
        low_stock_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_low_stock_database();
            }
        });
        full_data_view.callOnClick();
        show_full_database();
        return rootview;
    }
    
    private void show_full_database(){
        data_list_items.clear();
        int clinic_count = db.get_clinic_count();
        for (int i = 1;i < clinic_count+1;i++){
            CLINIC clinic = db.get_clinic(i);
            int[] records = db.get_clinic_stock_records(clinic.getC_uuid());
            String list_item_string = clinic.getC_name() + "    " + clinic.getC_country();
            for (int j = 0;j < records.length;j++){
                STOCK stock = db.get_stock(records[j]);
                MEDICATION medication = db.get_medication(stock.getS_medication());
                list_item_string += "    "+ medication.getM_name() + "    " + stock.getS_stock_count();
            }
            data_list_items.add(list_item_string);
        }
        data_list_adapter.notifyDataSetChanged();
    }

    private void show_low_stock_database(){
        data_list_items.clear();
        int clinic_count = db.get_clinic_count();
        for (int i = 1;i < clinic_count+1;i++){
            CLINIC clinic = db.get_clinic(i);
            int[] records = db.get_clinic_stock_records(clinic.getC_uuid());
            String list_item_string = clinic.getC_name() + "    " + clinic.getC_country();
            for (int j = 0;j < records.length;j++){
                STOCK stock = db.get_stock(records[j]);
                MEDICATION medication = db.get_medication(stock.getS_medication());
                if (stock.getS_stock_count() < 5){
                list_item_string += "    "+ medication.getM_name() + "    " + stock.getS_stock_count();}
            }
            data_list_items.add(list_item_string);
        }
        data_list_adapter.notifyDataSetChanged();
    }
}
