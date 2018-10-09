/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller;

import com.obsidiam.controller.helper.DatabaseHelper;
import com.obsidiam.controller.helper.JsonHelper;
import com.obsidiam.controller.util.ApiLogger;
import com.obsidiam.controller.util.AuthHandler;
import com.obsidiam.model.OperationResultModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListNotificationController extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            if(AuthHandler.getHandlerInstance().authenticate(req, resp, this)){
                resp.getWriter().write(JsonHelper.getInstance().serialize(DatabaseHelper.getInstance().getNotificationList()));
            }else{
                OperationResultModel orm = new OperationResultModel();
                orm.setSuccess(false);
                orm.setMessage("Couldn't authenticate the user.");
                resp.getWriter().write(JsonHelper.getInstance().serialize(orm));
            }
        } catch (IOException ex) {
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        this.doPost(req, resp);
    }
}
