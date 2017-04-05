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
                //Less 5
                break;
            case 2:
                //DB complete
                break;
            case 3:
                //Edit Stock
                break;
            case 4:
                //Edit Medication
                break;
            case 5:
                //Edit Clinic
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
