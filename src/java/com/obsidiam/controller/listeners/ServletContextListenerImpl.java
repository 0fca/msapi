/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.listeners;

import com.obsidiam.controller.util.ApiLogger;
import com.obsidiam.controller.util.ConfigHandler;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author obsidiam
 */
public class ServletContextListenerImpl implements javax.servlet.ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce){
        if(!ConfigHandler.wasInited()){
            ConfigHandler.initHandler(sce.getServletContext());
        }
        
        if(!ApiLogger.wasInited()){
            ApiLogger.init();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
