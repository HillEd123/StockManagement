package com.edward.stockmanagement.DATABASE;

import android.util.Log;

import com.edward.stockmanagement.Controller.MainActivity;
import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;

import java.util.Random;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Edward on 2017/04/04.
 */

public class DB_INIT {
    public void init(){
        Init_Clinics();
        Init_Medication();
        Init_Stock();
        db.close();

    }
    private DataBaseHandler db = new DataBaseHandler(MainActivity.get_M_Context());
    private void Init_Clinics(){

        for (int i =0; i < 10; i++){
            CLINIC clinic = new CLINIC();
            String name = get_Clinic_Name(i);
            clinic.setC_name(name);
            clinic.setC_country(get_Random_Country());
            db.add_clinic(clinic);
            CLINIC c = db.get_clinic(i+1);
            Log.d("Clinic", c.getC_name()+ " " + c.getC_country());
        }
    }
    private void Init_Medication(){

        MEDICATION med1 = new MEDICATION();
        med1.setM_name("NEVIRAPINE");
        db.add_medication(med1);
        MEDICATION med2 = new MEDICATION();
        med2.setM_name("STAVUDINE");
        db.add_medication(med2);
        MEDICATION med3 = new MEDICATION();
        med3.setM_name("ZIDOTABINE");
        db.add_medication(med3);
        MEDICATION t = db.get_medication(1);
        Log.d("Med 1 : ",""+ t.getM_name());
        MEDICATION t1 = db.get_medication(2);
        Log.d("Med 2 : ", ""+t1.getM_name());
        MEDICATION t2 = db.get_medication(3);
        Log.d("Med 3 : ",""+ t2.getM_name());

    }
    private void Init_Stock(){

        for (int i = 0;i <10;i++){
            for (int k = 0;k < 3;k++){
                STOCK stock = new STOCK();
                stock.setS_clinic(i+1);
                stock.setS_medication(k+1);
                stock.setS_stock_count(5);
                db.add_stock(stock);
            }
        }
        for (int j = 0;j < 30;j++ ){
            STOCK kk =  db.get_stock(j+1);
            CLINIC c = db.get_clinic(kk.getS_clinic());
            MEDICATION m = db.get_medication(kk.getS_medication());
            int count = kk.getS_stock_count();
            Log.d("To Follow", "Stock of " + c.getC_name() + " medication " + m.getM_name() + " is " + count);

        }
    }
    private String get_Clinic_Name(int x){
        String clinic_name = "";
        switch(x){
            case 0:
                clinic_name = "Clinic A";
                break;
            case 1:
                clinic_name = "Clinic B";
                break;
            case 2:
                clinic_name = "Clinic C";
                break;
            case 3:
                clinic_name = "Clinic D";
                break;
            case 4:
                clinic_name = "Clinic E";
                break;
            case 5:
                clinic_name = "Clinic F";
                break;
            case 6:
                clinic_name = "Clinic G";
                break;
            case 7:
                clinic_name = "Clinic H";
                break;
            case 8:
                clinic_name = "Clinic I";
                break;
            case 9:
                clinic_name = "Clinic J";
                break;
        }
        return clinic_name;

    }
    private String get_Random_Country(){
        String country = "";
        Random r = new Random();
        int low = 0;
        int high = Global_Variables.getAfrican_Countries().length;
        int Result = r.nextInt(high-low) + low;
        String[] countries = Global_Variables.getAfrican_Countries();
        country = countries[Result];
        return country;
    }

}
