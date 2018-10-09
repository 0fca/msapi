/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.exceptions;

import javax.servlet.http.HttpServlet;

/**
 *
 * @author obsidiam
 */
public class NotInitializedException extends Exception {
    public NotInitializedException(Class notInitedClass, HttpServlet instance){
        super("Object of "+notInitedClass.getCanonicalName()+" class wasn't initialized properly while "+instance.getServletName()+" tried to use it.");
    }
}
