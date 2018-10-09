/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.model;


/**
 *
 * @author obsidiam
 */
public class NotificationModel {
    private int id;
    private String name,description,whoAdded,mail;
    private Priority priority;
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setWhoAdded(String whoAdded){
        this.whoAdded = whoAdded;
    }
    
    public String getWhoAdded(){
        return this.whoAdded;
    }
    
    public void setMail(String mail){
        this.mail = mail;
    }
    
    public String getMail(){
        return this.mail;
    }
    
    public void setPriority(Priority priority){
        this.priority = priority;
    }
    
    public Priority getPriority(){
        return this.priority;
    }
    
    public enum Priority {
        HIGH,
        MEDIUM,
        LOW;
    }
}
