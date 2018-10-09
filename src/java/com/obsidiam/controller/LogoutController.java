/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller;

import com.obsidiam.controller.helper.JsonHelper;
import com.obsidiam.controller.util.ApiLogger;
import com.obsidiam.model.OperationResultModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        OperationResultModel orm = new OperationResultModel();
        try {
            orm.setSuccess(true);
            orm.setMessage("Successfully logged out.");
            resp.getWriter().write(JsonHelper.getInstance().serialize(orm));
        } catch (IOException ex) {
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        this.doPost(req, resp);
    }
}
