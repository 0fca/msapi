/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

final public class AuthHandler {
    private static volatile AuthHandler authHandlerInstance = new AuthHandler();
    
    private AuthHandler(){}
    
    public static synchronized AuthHandler getHandlerInstance(){
        return authHandlerInstance;
    }
    
    public boolean authenticate(HttpServletRequest req, HttpServletResponse resp, HttpServlet servlet){
        boolean isOk = false;
        try {
            isOk = req.authenticate(resp);

            if(isOk){
               ApiLogger.printMessage(new LogRecord(Level.INFO, "Authenticated user: "+req.getRemoteUser()+" while processing request: "+req.getMethod()+" : "+req.getRequestURI()));
            }
        } catch (IOException | ServletException ex) {
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
        return isOk;
    }
}
