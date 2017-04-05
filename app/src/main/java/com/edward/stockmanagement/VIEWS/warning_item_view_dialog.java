package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.DATABASE.DataBaseHandler;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;
import com.edward.stockmanagement.R;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Edward on 2017/04/05.
 */

public class warning_item_view_dialog extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_warning_dialog,container,false);
        DataBaseHandler db = new DataBaseHandler(MainActivity.get_M_Context());
        CLINIC clinic = db.get_clinic(Global_Variables.getWarning_stock().getS_clinic());

        LinearLayout warning_layout = (LinearLayout)rootView.findViewById(R.id.warning_layout);
        getDialog().setTitle(clinic.getC_name());

        TextView clinic_textview = new TextView(rootView.getContext());
        clinic_textview.setText("Clinic Name : "+clinic.getC_name());
        TextView country_textview = new TextView(rootView.getContext());
        country_textview.setText("Country : "+clinic.getC_country());
        warning_layout.addView(clinic_textview);
        warning_layout.addView(country_textview);

        for (int i = 0; i < Global_Variables.getWarning_stock_id().length;i++){
            TextView medication_row_textview = new TextView(rootView.getContext());
            STOCK stock = db.get_stock(Global_Variables.getWarning_stock_id()[i]);
            MEDICATION medication = db.get_medication(stock.getS_medication());
            medication_row_textview.setText(medication.getM_name() + " : " + stock.getS_stock_count());
            medication_row_textview.setId(i);
            warning_layout.addView(medication_row_textview);
        }

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss_warning);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
