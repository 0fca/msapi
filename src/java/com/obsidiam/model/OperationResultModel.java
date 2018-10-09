/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.model;

public class OperationResultModel<T> {
    private boolean success = false;
    private String message;
    private T data;
    
    public void setSuccess(boolean success){
        this.success = success;
    }
    
    public boolean getSuccess(){
        return this.success;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return this.message;
    }
    
    public void setData(T data){
        this.data = data;
    }
    
    public T getData(){
        return this.data;
    }
    
}
