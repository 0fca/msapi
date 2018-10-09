/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller;

import com.obsidiam.controller.helper.JsonHelper;
import com.obsidiam.controller.util.AuthHandler;
import com.obsidiam.model.OperationResultModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lukas
 */
public class LoginController extends HttpServlet{  
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        boolean isOk = AuthHandler.getHandlerInstance().authenticate(req, resp, this);
        OperationResultModel o = new OperationResultModel();
        
        if(isOk){    
            o.setSuccess(isOk);
            o.setMessage("Successful login.");
        }
        resp.getWriter().write(JsonHelper.getInstance().serialize(o));
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        this.doPost(req, resp);
    }
}
