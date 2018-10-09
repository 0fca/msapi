/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.helper;

import com.obsidiam.controller.util.StandardFormatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHelper extends Handler{
    private State controllerState = State.OPENED;
    private static volatile LogHelper controllerInstance = null;
    
    private LogHelper(){
        controllerState = State.OPENED;
    }
    
    public static synchronized LogHelper getControllerInstance(){
        if(controllerInstance == null){
            controllerInstance = new LogHelper();
            controllerInstance.setFormatter(new StandardFormatter());
        }
        return controllerInstance;
    }
    
    @Override
    public void publish(LogRecord record) {
        if(controllerState != State.OPENED){
            throw new IllegalStateException("LogHelper was in bad state "+controllerState);
        }
        System.out.println(this.getFormatter().format(record));
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {
        this.controllerState = State.CLOSED;
    }
    
    private enum State{
        OPENED,
        CLOSED
    }
}
