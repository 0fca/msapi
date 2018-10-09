/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obsidiam.controller.helper;

import com.obsidiam.controller.util.ApiLogger;
import com.obsidiam.controller.util.ConfigHandler;
import com.obsidiam.exceptions.IllegalEnvTypeException;
import com.obsidiam.model.NotificationModel;
import com.obsidiam.model.OperationResultModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DatabaseHelper {
    private static Connection sqliteConnection;
    private static volatile DatabaseHelper helperInstance = new DatabaseHelper();
    private QueryFormatter queryFormmater = new QueryFormatter();
    
    private DatabaseHelper(){
        try {
         Class.forName("org.sqlite.JDBC");
         
         sqliteConnection = DriverManager.getConnection("jdbc:sqlite:"+ConfigHandler.getInstance().getDatabasePath());
         ApiLogger.printMessage(new LogRecord(Level.INFO, "Initialized connection to: "+ConfigHandler.getInstance().getDatabasePath()));
      } catch ( ClassNotFoundException | SQLException | IllegalEnvTypeException e ){
         ApiLogger.printMessage(new LogRecord(Level.SEVERE, e.getLocalizedMessage()));
      }
    }
    
    public static synchronized DatabaseHelper getInstance(){
        return helperInstance;
    }
    
    public OperationResultModel<ArrayList<NotificationModel>> getNotificationList(){
        OperationResultModel<ArrayList<NotificationModel>> orm = new OperationResultModel<>();
        
        ArrayList<NotificationModel> notifications = new ArrayList<>();
        if(sqliteConnection != null){
            try {
                Statement stmt =  sqliteConnection.createStatement();
                String tableName = ConfigHandler.getInstance().getNotificationsTableName();
                ResultSet set = stmt.executeQuery(queryFormmater.formatQuery(Query.SELECT, tableName));

                while(set.next()){
                    notifications.add(convertSetToModel(set));
                }
                orm.setData(notifications);
                orm.setSuccess(true);
                orm.setMessage("Successful fetch.");
                stmt.close();
            } catch(SQLException ex) {
                orm.setData(notifications);
                orm.setSuccess(false);
                orm.setMessage("Unsuccessful fetch because of "+ex.getLocalizedMessage());
                ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
            }
        }else{
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, "Connection wasn't initialized properly."));
        }
        return orm;
    }
    
    public OperationResultModel addNotification(NotificationModel mdl){
        OperationResultModel orm = new OperationResultModel();
        try {
            Statement stmt = sqliteConnection.createStatement();
            String tableName = ConfigHandler.getInstance().getNotificationsTableName();
            stmt.executeUpdate(queryFormmater.formatQuery(Query.INSERT, 
                    tableName, 
                    "'"+mdl.getName()+"','"+mdl.getDescription()+"','"+mdl.getWhoAdded()+"','"+mdl.getPriority().name()+"','"+mdl.getMail()+"'"));
            orm.setSuccess(true);
            orm.setMessage("Successfully added notification.");
            stmt.close();
        } catch (SQLException ex) {
            orm.setSuccess(false);
            orm.setMessage("Unsuccessful fetch because of "+ex.getLocalizedMessage());
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
        return orm;
    }
    
    public OperationResultModel editNotification(NotificationModel mdl){
        OperationResultModel orm = new OperationResultModel();
        try {
            Statement stmt =  sqliteConnection.createStatement();
            String tableName = ConfigHandler.getInstance().getNotificationsTableName();
            NotificationModel checkMdl = this.getNotification(mdl.getId()).getData();
            if(checkMdl == null || checkMdl.getWhoAdded().equals("null")){
                orm.setMessage("There is no notification of id "+mdl.getId());
                orm.setSuccess(false);
                return orm;
            }
            stmt.executeUpdate(queryFormmater.formatQuery(Query.UPDATE, 
                    tableName, 
                    "name='"+mdl.getName()+"',description='"+mdl.getDescription()+"',whoAdded='"+mdl.getWhoAdded()+"',priority='"+mdl.getPriority().name()+"',mail='"+mdl.getMail()+"'",
                    String.valueOf(mdl.getId())));
            orm.setSuccess(true);
            orm.setMessage("Successfully edited notification.");
            stmt.close();
        } catch (SQLException ex) {
            orm.setSuccess(false);
            orm.setMessage("Unsuccessful edit query because of "+ex.getLocalizedMessage());
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
        return orm;
    }
    
    public OperationResultModel removeNotification(int id){
        NotificationModel mdl = this.getNotification(id).getData();
        if(mdl.getWhoAdded().equals("null")){
            OperationResultModel orm = new OperationResultModel();
            orm.setMessage("There is no notification of id "+id);
            orm.setSuccess(false);
            return orm;
        }
        mdl.setWhoAdded(null);
        OperationResultModel orm = this.editNotification(mdl);
        orm.setMessage(orm.getSuccess() ? "Successfully removed notification" : "Unsuccessful remove query.");
        return orm;
    }
    
    public OperationResultModel<NotificationModel> getNotification(int id){
        OperationResultModel<NotificationModel> orm = new OperationResultModel<>();
        try {
            Statement stmt = sqliteConnection.createStatement();
            String tableName = ConfigHandler.getInstance().getNotificationsTableName();
            ResultSet set = stmt.executeQuery(queryFormmater.formatQuery(Query.SELECT_WHERE, tableName, String.valueOf(id)));
            NotificationModel mdl = convertSetToModel(set);
            orm.setData(mdl);
            orm.setSuccess(true);
            orm.setMessage("Successfull fetch.");
            stmt.close();
        } catch (SQLException ex) {
            orm.setSuccess(false);
            orm.setMessage("Unsuccessful fetch because of "+ex.getLocalizedMessage());
            ApiLogger.printMessage(new LogRecord(Level.SEVERE, ex.getLocalizedMessage()));
        }
        return orm;
    }

    private NotificationModel convertSetToModel(ResultSet set) throws SQLException {
        NotificationModel mdl = new NotificationModel();
        mdl.setId(set.getInt("id"));
        mdl.setName(set.getObject("name").toString());
        mdl.setDescription(set.getObject("description").toString());
        mdl.setWhoAdded(set.getString("whoAdded"));
        mdl.setMail(set.getString("mail"));
        mdl.setPriority(NotificationModel.Priority.valueOf(set.getString("priority")));
        return mdl;
    }
    
    private class QueryFormatter{
        public String formatQuery(Query q, Object... args){
            String queryToBeFormatted = "";
            
            switch(q){
                case SELECT:
                    queryToBeFormatted = q.getQuery();
                    break;
                case INSERT:
                    queryToBeFormatted = insertQuery(q, args);
                    break;
                case UPDATE:
                    queryToBeFormatted = updateQuery(q, args);
                    break;
                case SELECT_WHERE:
                    queryToBeFormatted = selectWhere(q, args);
                    break;
            }
            
            return String.format(queryToBeFormatted, args);
        }
        
        private String insertQuery(Query q, Object... args){
            return String.format(q.getQuery(), args[0].toString(),"name,description,whoAdded,priority,mail",args[1].toString());
        }
        
        private String updateQuery(Query q, Object... args){
            return String.format(q.getQuery(), args[0],args[1],args[2]);
        }
        
        private String selectWhere(Query q, Object... args){
            return String.format(q.getQuery(), args[0], args[1]);
        }
    }
    
    private enum Query{
        SELECT("SELECT * FROM %s;"),
        INSERT("INSERT INTO %s(%s) VALUES(%s)"),
        UPDATE("UPDATE %s SET %s WHERE id='%s';"),
        SELECT_WHERE("SELECT * FROM %s WHERE id='%s'");
        
        private String query;
        
        Query(String inQuery){
            this.query = inQuery;
        }
        
        public String getQuery(){
            return this.query;
        }
    }
}
