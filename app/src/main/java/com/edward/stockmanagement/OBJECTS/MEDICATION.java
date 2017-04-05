package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/04.
 */

public class MEDICATION {

    private static int M_id;
    private static String M_name;


    public MEDICATION(){}

    public MEDICATION(int id, String name){
        this.M_id = id;
        this.M_name = name;

    }

    public static int getM_id() {
        return M_id;
    }

    public static void setM_id(int m_id) {
        M_id = m_id;
    }

    public static String getM_name() {
        return M_name;
    }

    public static void setM_name(String m_name) {
        M_name = m_name;
    }
}
