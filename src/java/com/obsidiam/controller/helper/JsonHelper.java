/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.helper;

import com.google.gson.Gson;

/**
 *
 * @author obsidiam
 */
final public class JsonHelper {
    private static volatile JsonHelper instance = new JsonHelper();
    private static Gson gsonInstance;
    private JsonHelper(){
        gsonInstance = new Gson().newBuilder().setPrettyPrinting().create();
    }
    
    public static synchronized JsonHelper getInstance(){
        return instance;
    }
    
    public <T> String serialize(T object){
        return gsonInstance.toJson(object, object.getClass());
    }
    
    public <T> T deserialize(String jsonStr, Class c){
        return (T)gsonInstance.fromJson(jsonStr, c);
    }
}
