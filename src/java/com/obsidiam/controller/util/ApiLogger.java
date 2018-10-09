/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.util;

import com.obsidiam.controller.helper.LogHelper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

final public class ApiLogger {
    private static volatile LogHelper logHelper = LogHelper.getControllerInstance();
    private static boolean canLogToFile = false;
    private static boolean wasInited = false;
    
    public static void init(){
        if(Files.exists(Paths.get(ConfigHandler.getInstance().getLogDirPath()))){
            canLogToFile = new File(ConfigHandler.getInstance().getLogDirPath()).canWrite();
        }else if(new File(ConfigHandler.getInstance().getLogDirPath()).getParentFile().canWrite()){
            try {
                Files.createDirectories(Paths.get(ConfigHandler.getInstance().getLogDirPath()));
                canLogToFile = true;
            } catch (IOException ex) {
                Logger.getLogger(ApiLogger.class.getName()).log(Level.SEVERE, null, ex);
                wasInited = false;
            }
        }
    }
   
    public static void printMessage(LogRecord logRecord){
        boolean shouldPrintSeparator = logRecord.getLevel() == Level.SEVERE || logRecord.getLevel() == Level.WARNING;
        if(shouldPrintSeparator) printSeparator();
            logHelper.publish(logRecord);
            printToLogFile(logRecord);
        if(shouldPrintSeparator) printSeparator();
    } 
    
    private static void printToLogFile(LogRecord logRecord){
        if(canLogToFile){
            try {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(ConfigHandler.getInstance().getLogDirPath()+"ms-"+logRecord.getLevel().getName().toLowerCase()+".log"))) {
                    bw.append(new StandardFormatter().format(logRecord));
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(ApiLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private static void printSeparator(){
        System.out.println("===================================================");
    }
    
    public static boolean wasInited(){
        return wasInited;
    }
}
