package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:SerializableHashMap
 */
public class SerializableHashMap implements Serializable {

    private HashMap<String, String> map;

    public SerializableHashMap() {
    }

    public SerializableHashMap(HashMap<String, String> map) {
        this.map = map;
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

}
