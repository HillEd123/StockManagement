package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/04.
 */

public class STOCK {

    public STOCK(){}

    private static int S_id;
    private static int S_clinic;
    private static int S_medication;
    private static int S_stock_count;

    public STOCK(int id,int clinic,int medication,int stock){
        this.S_id = id;
        this.S_clinic = clinic;
        this.S_medication = medication;
        this.S_stock_count = stock;
    }

    public static int getS_id() {
        return S_id;
    }

    public static void setS_id(int s_id) {
        S_id = s_id;
    }

    public static int getS_clinic() {
        return S_clinic;
    }

    public static void setS_clinic(int s_clinic) {
        S_clinic = s_clinic;
    }

    public static int getS_medication() {
        return S_medication;
    }

    public static void setS_medication(int s_medication) {
        S_medication = s_medication;
    }

    public static int getS_stock_count() {
        return S_stock_count;
    }

    public static void setS_stock_count(int s_stock_count) {
        S_stock_count = s_stock_count;
    }
}
