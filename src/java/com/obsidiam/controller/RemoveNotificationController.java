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
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author obsidiam
 */
public class RemoveNotificationController extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            if(AuthHandler.getHandlerInstance().authenticate(req, resp, this)){
                CharBuffer buffer = CharBuffer.allocate(req.getContentLength());
                req.getReader().read(buffer);
                OperationResultModel orm = DatabaseHelper.getInstance().removeNotification(Integer.parseInt(new String(buffer.array())));
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
