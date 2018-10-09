package test;

import com.obsidiam.controller.helper.JsonHelper;
import com.obsidiam.model.NotificationModel;
import com.obsidiam.model.OperationResultModel;
import com.obsidiam.model.UserModel;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Before;

public class AssertServletTests {
    private static CloseableHttpClient client;

    @Before
    public void prepareHttpClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, 
            new UsernamePasswordCredentials("user", "user")); 
            client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
    }
    
    @Test
    public void assertLoginServlet() throws IOException{
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/app/login");
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println("Login: "+response.getStatusLine().getStatusCode());
        
        assertTrue(response.getStatusLine().getStatusCode() == 200);
        assertTrue(response.getEntity().getContentLength() != 0);
        byte[] dumm = new byte[(int)response.getEntity().getContentLength()];
        response.getEntity().getContent().read(dumm);
        OperationResultModel orm = JsonHelper.getInstance().deserialize(new String(dumm), OperationResultModel.class);
        assertFalse(orm == null);
    }
    
    @Test
    public void assertListServlet() throws IOException{
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/app/notification/list");
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        
        assertTrue(response.getStatusLine().getStatusCode() == 200);
        assertTrue(response.getEntity().getContentLength() != 0);
        byte[] dumm = new byte[(int)response.getEntity().getContentLength()];
        response.getEntity().getContent().read(dumm);
        OperationResultModel orm = JsonHelper.getInstance().deserialize(new String(dumm), OperationResultModel.class);
        assertFalse(orm == null && ((List<NotificationModel>)orm.getData()).size() > 0);
    }
    
    @Test
    public void assertAddServlet() throws IOException{
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/app/notification/add");

        HttpEntity entity = EntityBuilder.create().setText(JsonHelper.getInstance().serialize(createDummyNotification())).build();
        httpPost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        assertTrue(response.getStatusLine().getStatusCode() == 200);
        assertTrue(response.getEntity().getContentLength() != 0);
    }
    
    @Test
    public void assertRemoveServlet() throws IOException{
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/app/notification/remove");

        HttpEntity entity = EntityBuilder.create().setText("1").build();
        httpPost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        assertTrue(response.getStatusLine().getStatusCode() == 200);
        assertTrue(response.getEntity().getContentLength() != 0);
        
        byte[] dumm = new byte[(int)response.getEntity().getContentLength()];
        response.getEntity().getContent().read(dumm);
        
        OperationResultModel orm = JsonHelper.getInstance().deserialize(new String(dumm), OperationResultModel.class);
        assertTrue(orm != null);
        System.out.println(orm.getMessage());
    }
    
    @Test
    public void assertEditServlet() throws IOException{
        HttpPost httpPost = new HttpPost("http://localhost:8080/v1/app/notification/edit");
        
        HttpEntity entity = EntityBuilder.create().setText(JsonHelper.getInstance().serialize(createDummyNotification())).build();
        httpPost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        assertTrue(response.getStatusLine().getStatusCode() == 200);
        assertTrue(response.getEntity().getContentLength() != 0);
        
        byte[] dumm = new byte[(int)response.getEntity().getContentLength()];
        response.getEntity().getContent().read(dumm);
        
        OperationResultModel orm = JsonHelper.getInstance().deserialize(new String(dumm), OperationResultModel.class);
        assertTrue(orm != null);
        System.out.println(orm.getMessage());
    }
    
    @After 
    public void disposeHttpClient(){
        try {
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(AssertServletTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private final NotificationModel createDummyNotification(){
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setId(17);
        notificationModel.setName("Name");
        notificationModel.setMail("Mail");
        notificationModel.setPriority(NotificationModel.Priority.HIGH);
        notificationModel.setDescription("Description");
        notificationModel.setWhoAdded("obsidiam");
        return notificationModel;
    } 
}
