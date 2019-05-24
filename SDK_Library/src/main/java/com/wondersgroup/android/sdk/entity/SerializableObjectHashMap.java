package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:SerializableObjectHashMap
 */
public class SerializableObjectHashMap implements Serializable {

    private HashMap<String, Object> map;

    public SerializableObjectHashMap() {
    }

    public SerializableObjectHashMap(HashMap<String, Object> map) {
        this.map = map;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

}
