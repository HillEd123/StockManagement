package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/04.
 */

public class CLINIC {

    private static int C_id;
    private static String C_name;
    private static String C_country;
    private static String C_uuid;


    public CLINIC(){}

    public CLINIC(int id, String name, String country,String uuid){
        this.C_id = id;
        this.C_name  = name;
        this.C_country = country;
        this.C_uuid = uuid;
    }

    public static String getC_uuid() {
        return C_uuid;
    }

    public static void setC_uuid(String c_uuid) {
        C_uuid = c_uuid;
    }

    public static int getC_id() {
        return C_id;
    }

    public static void setC_id(int c_id) {
        C_id = c_id;
    }

    public static String getC_name() {
        return C_name;
    }

    public static void setC_name(String c_name) {
        C_name = c_name;
    }

    public static String getC_country() {
        return C_country;
    }

    public static void setC_country(String c_country) {
        C_country = c_country;
    }
}
