package com.edward.stockmanagement.GLOBAL;

import com.edward.stockmanagement.OBJECTS.STOCK;

/**
 * Created by Edward on 2017/04/04.
 */

public class Global_Variables {

    public static String[] getAfrican_Countries() {
        return African_Countries;
    }

    public static STOCK getWarning_stock() {
        return warning_stock;
    }

    public static void setWarning_stock(STOCK warning_stock) {
        Global_Variables.warning_stock = warning_stock;
    }

    public static int[] getWarning_stock_id() {
        return warning_stock_id;
    }

    public static void setWarning_stock_id(int[] warning_stock_id) {
        Global_Variables.warning_stock_id = warning_stock_id;
    }

    private static String[] African_Countries = {"AL","AN","BA","BE","BT","BF","BR","BU","BH","CR","CI","CV","CA","DH","EG","ER","ET","GB","GA","GH","GC","GU","HO","IC","KY","LE","LI","LB","MD","MW","ML","MN","MU","MA","MB","ME","MR","MZ","NM","NG","NI","PY","CH","RD","RE","RG","RS","SY","RC","RI","RW","SE","SN","SL","SO","SA","SS","SU","SH","SW","TA","TO","TN","UG","ZA","ZM","ZI"};
    private static STOCK warning_stock;
    private static int[] warning_stock_id;
}
