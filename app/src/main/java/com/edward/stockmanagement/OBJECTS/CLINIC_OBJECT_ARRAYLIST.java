package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/06.
 */

public class CLINIC_OBJECT_ARRAYLIST {

    private int C_id;
    private String C_name;
    private String C_country;
    private String C_uuid;


    public CLINIC_OBJECT_ARRAYLIST(){}

    public CLINIC_OBJECT_ARRAYLIST(int id, String name, String country,String uuid){
        this.C_id = id;
        this.C_name  = name;
        this.C_country = country;
        this.C_uuid = uuid;
    }

    public int getC_id() {
        return C_id;
    }

    public void setC_id(int c_id) {
        C_id = c_id;
    }

    public String getC_name() {
        return C_name;
    }

    public void setC_name(String c_name) {
        C_name = c_name;
    }

    public String getC_country() {
        return C_country;
    }

    public void setC_country(String c_country) {
        C_country = c_country;
    }

    public String getC_uuid() {
        return C_uuid;
    }

    public void setC_uuid(String c_uuid) {
        C_uuid = c_uuid;
    }
}
