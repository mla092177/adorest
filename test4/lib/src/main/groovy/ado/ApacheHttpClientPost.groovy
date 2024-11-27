package ado

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import services.WorkManagementService

public class ApacheHttpClientPost {

	public static void main(String[] args) {
	  try {

		  DefaultHttpClient httpClient = new DefaultHttpClient();
		  WorkManagementService workManagementService = new WorkManagementService();
		  
		  //set work item ids
		  //def String[] wtypes = ["Bug","Epic"]
		  def String[] wtypes = ["Feature","Epic","Bug"]
		  def rurl =  "dev.azure.com"
		  
		  //pass in user token
		  def token = "7w2u5qsl44gh5so6ny7v6ybmdmdgc4z772bjjsdo3q6jnddwxdza"
		  
		  //pass in project name
		  def project  = "AlmOps"
		  
		  //pass in ADO token and  encode it to base64
		  String authString = Base64.getEncoder().encodeToString((":${token}").getBytes());
	
		  def field  = "/fields/System.AreaPath"
		  def field2  =  "/fields/System.History"
		  def field3  =  "/fields/System.Title"
		  def val = "AlmOps\\Astro"
		  def val2 = "making  a comment multi value test"
		  def val3 = "New Work Item"
		
		//iterate  through work item ids
		for (int i = 0; i < wtypes.size(); i++) {
			
			//add work item type to title
			def title = val3 + " " + wtypes[i]
			def wi = workManagementService.createWorkItem(project, wtypes[i], field, val, field2, val2, field3, title, authString)
			
			def workItemId = wi.id
			def revId = wi.rev
			
			println "work item id : " +  workItemId
			println "work item type : " +  wtypes[i]
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {
	
		e.printStackTrace();

	  } catch (IOException e) {
	
		e.printStackTrace();
	  }

	}

}
