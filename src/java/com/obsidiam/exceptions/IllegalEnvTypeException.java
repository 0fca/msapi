/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.exceptions;

/**
 *
 * @author obsidiam
 */
public class IllegalEnvTypeException extends Exception{
    public IllegalEnvTypeException(Class refferingClass){
        super("Object of "+refferingClass.getCanonicalName()+" was trying to reach inaccessible config constant due to environment type.");
    }
}
