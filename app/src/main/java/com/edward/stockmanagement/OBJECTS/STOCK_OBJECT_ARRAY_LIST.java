package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/06.
 */

public class STOCK_OBJECT_ARRAY_LIST {
    public STOCK_OBJECT_ARRAY_LIST(){}

    private  int S_id;
    private  int S_clinic;
    private  int S_medication;
    private  String S_clinic_uuid;
    private  String S_medication_uuid;
    private  int S_stock_count;

    public STOCK_OBJECT_ARRAY_LIST(int id,int clinic,int medication,String clinic_uuid,String medication_uuid,int stock){
        this.S_id = id;
        this.S_clinic = clinic;
        this.S_medication = medication;
        this.S_clinic_uuid = clinic_uuid;
        this.S_medication_uuid = medication_uuid;
        this.S_stock_count = stock;
    }

    public  String getS_clinic_uuid() {
        return S_clinic_uuid;
    }

    public  void setS_clinic_uuid(String s_clinic_uuid) {
        S_clinic_uuid = s_clinic_uuid;
    }

    public  String getS_medication_uuid() {
        return S_medication_uuid;
    }

    public  void setS_medication_uuid(String s_medication_uuid) {
        S_medication_uuid = s_medication_uuid;
    }

    public  int getS_id() {
        return S_id;
    }

    public  void setS_id(int s_id) {
        S_id = s_id;
    }

    public  int getS_clinic() {
        return S_clinic;
    }

    public  void setS_clinic(int s_clinic) {
        S_clinic = s_clinic;
    }

    public  int getS_medication() {
        return S_medication;
    }

    public  void setS_medication(int s_medication) {
        S_medication = s_medication;
    }

    public  int getS_stock_count() {
        return S_stock_count;
    }

    public  void setS_stock_count(int s_stock_count) {
        S_stock_count = s_stock_count;
    }
}
