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

public class ApacheHttpClientPatch {

	public static void main(String[] args) {
	  try {

		  DefaultHttpClient httpClient = new DefaultHttpClient();
		  WorkManagementService workManagementService = new WorkManagementService();
		  
		  //set work item ids
		  //def String[] wtypes = ["Bug","Epic"]
		  def String[] ids = ["22","23","24"]
		  def rurl =  "dev.azure.com"
		  
		  //pass in user token
		  def token = "7w2u5qsl44gh5so6ny7v6ybmdmdgc4z772bjjsdo3q6jnddwxdza"
		  
		  //pass in project name
		  def project  = "AlmOps"
		  
		  
		  //fields to update
		  def  field  = "/fields/Microsoft.VSTS.Common.ValueArea"
		  def field2  =  "/fields/System.History"
		  def val = "Architectural"
		  def val2 = "making  a comment"
		  //pass in ADO token and  encode it to base64
		  String authString = Base64.getEncoder().encodeToString((":${token}").getBytes());
	
		
		//iterate  through work item ids
		for (int i = 0; i < ids.size(); i++) {
			
			
			def wi = workManagementService.updateWorkItem(project, ids[i], field, field2, val, val2, authString)
			
			
			println "work item id : " +  ids[i] + " has been updated"

		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {
	
		e.printStackTrace();

	  } catch (IOException e) {
	
		e.printStackTrace();
	  }

	}

}
