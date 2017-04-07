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
 * Created by Edward on 2017/04/07.
 */

public class fragment_about extends Fragment{
    public fragment_about(){}
    private View rootview;
    private Button back_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_about,container,false);
        back_button = (Button)rootview.findViewById(R.id.button_about_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Setpage(0);
            }
        });
    return rootview;}
}
