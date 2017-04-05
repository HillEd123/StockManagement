package com.edward.stockmanagement.VIEWS;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Edward on 2017/04/05.
 */

public class Stock_Pager_Adapter extends FragmentPagerAdapter {
    public Stock_Pager_Adapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                //Warnings
                return new fragment_warnings();

            case 1:
                //DB Views
                return new fragment_database_view();

            case 2:
                //Edit Database
                return new fragment_edit_stock();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
