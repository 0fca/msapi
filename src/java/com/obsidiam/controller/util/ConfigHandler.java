/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.util;

import com.obsidiam.exceptions.IllegalEnvTypeException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

final public class ConfigHandler {
    final static Properties props = new Properties();
    private static ServletContext context;
    final private static ConfigHandler handler = new ConfigHandler();

    private static void initProperties() {
        try {
            props.load(context.getResourceAsStream("/config/environment.properties"));
        } catch (IOException ex) {
            Logger.getLogger(ConfigHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private ConfigHandler(){}
    
    public static ConfigHandler getInstance(){
        return handler;
    }
    
    public static boolean wasInited(){
        return !props.isEmpty();
    }
    
    public static void initHandler(ServletContext incomingContext){
        if(props.isEmpty()){
            context = incomingContext;
            initProperties();
            ApiLogger.printMessage(new LogRecord(Level.CONFIG, "Configuration inited successfully."));
            ApiLogger.printMessage(new LogRecord(Level.CONFIG, "API is working in "+props.getProperty("env.type")+" type of environment"));
        }else{
            ApiLogger.printMessage(new LogRecord(Level.CONFIG, "Configuration wasn't empty so not inited now."));
        }
    }
    
    public String getDatabasePath() throws IllegalEnvTypeException{
        if(props.getProperty("env.type").equals("development")){
            return props.getProperty("database.path.devel-"+getOSName());
        }else{
            return props.getProperty("database.path.prod");
        }
    }
    
    public String getNotificationsTableName(){
        return props.getProperty("database.notifications.table");
    }
    
    public String getLogDirPath(){
        return props.getProperty("log.dir-"+getOSName());
    }
    
    private String getOSName(){
        return System.getProperty("os.name").contains("Linux") ? "linux" : "windows";
    }
}
