package com.edward.stockmanagement.OBJECTS;

/**
 * Created by Edward on 2017/04/06.
 */

public class MEDICATION_OBJECT_ARRAY_LIST {

    private  int M_id;
    private  String M_name;
    private  String M_uuid;


    public MEDICATION_OBJECT_ARRAY_LIST(){}

    public MEDICATION_OBJECT_ARRAY_LIST(int id, String name,String uuid){
        this.M_id = id;
        this.M_name = name;
        this.M_uuid = uuid;

    }


    public  String getM_uuid() {
        return M_uuid;
    }

    public  void setM_uuid(String m_uuid) {
        M_uuid = m_uuid;
    }

    public  int getM_id() {
        return M_id;
    }

    public  void setM_id(int m_id) {
        M_id = m_id;
    }

    public  String getM_name() {
        return M_name;
    }

    public  void setM_name(String m_name) {
        M_name = m_name;
    }
}

