package ado

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import services.WorkManagementService
import  groovy.json.*;
import groovy.json.JsonSlurper;
import groovy.json.JsonBuilder;




public class ApacheHttpClientGet {

	public static void main(String[] args) {
	  try {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		WorkManagementService workManagementService = new WorkManagementService();
		
		//set work item ids
		def String[] ids = ["19","20"]
		def rurl =  "dev.azure.com"
		
		//pass in user token
		def token = ""
		
		//pass in project name
		def project  = "AlmOps"
		
		//pass in ADO token and  encode it to base64
		String authString = Base64.getEncoder().encodeToString((":${token}").getBytes());
		
		//iterate  through work item ids
		for (int i = 0; i < ids.size(); i++) {
			
			def wi = workManagementService.getWorkItem(ids[i], authString)
			def workItemId = wi.id
			def revId = wi.rev
			
			println "work item id : " +  workItemId
			println "revision id : " +  revId
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {
	
		e.printStackTrace();

	  } catch (IOException e) {
	
		e.printStackTrace();
	  }

	}

}
