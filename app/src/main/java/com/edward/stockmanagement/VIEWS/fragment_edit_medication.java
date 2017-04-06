package com.edward.stockmanagement.VIEWS;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edward.stockmanagement.R;

/**
 * Created by Edward on 2017/04/06.
 */

public class fragment_edit_medication extends Fragment {
    public fragment_edit_medication(){}
    private View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_medicine,container,false);
        return rootview;
    }
}
