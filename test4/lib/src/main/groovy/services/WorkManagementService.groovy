package services

import java.io.BufferedReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPatch;

import org.apache.http.entity.StringEntity;

import  groovy.json.*;
import groovy.json.JsonSlurper;
import groovy.json.JsonBuilder;
import groovy.util.logging.Slf4j;


class WorkManagementService {
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	def rurl =  "dev.azure.com"
	
	def getWorkItem(String id, authString) {
		
		
		def jsonObject
		HttpGet getRequest = new HttpGet(
				"https://${rurl}/mla092177/_apis/wit/workitems/${id}?api-version=7.1");
				//"https://dev.azure.com/mla092177/Sandbox/_apis/wit/workitems/2?api-version=7.1");
		
		//Request headers
		getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("Authorization", "Basic " + authString);
		HttpResponse response = httpClient.execute(getRequest);
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
						 new InputStreamReader((response.getEntity().getContent())));
		
		System.out.println("Work item data.... \n");
		
		String output;
		while ((output = br.readLine()) != null) {
		def jsonSlurper = new JsonSlurper()
		jsonObject = jsonSlurper.parseText(output)

		}
		return jsonObject
	}
	
	def createWorkItem(String project, type, field, val, field2, val2, field3, val3, authString) {
		
		def ts = '$'
		def jsonObject
		HttpPost postRequest = new HttpPost(
				"https://${rurl}/mla092177/${project}/_apis/wit/workitems/${ts}${type}?api-version=7.1");
			//POST https://dev.azure.com/{organization}/{project}/_apis/wit/workitems/${type}?api-version=7.1	
			
		//Request headers
		postRequest.addHeader("accept", "application/json-patch+json");
		postRequest.addHeader("Authorization", "Basic " + authString);
		//body
		//final  String json = '[{"op": "add","path": "/fields/System.Title","value": ' + "${title2}" + "}]"
		
		def body = []
		
		 body.add([ op: 'add', path: "${field}", value: "${val}"])
		 body.add([ op: 'add', path: "${field2}", value: "${val2}"])
		 body.add([ op: 'add', path: "${field3}", value: "${val3}"])
		 //format body into json formatting
		 String sbody = new JsonBuilder(body).toPrettyString()
		//format body and content type
		final StringEntity entity = new StringEntity(sbody);
		postRequest.setEntity(entity);
		postRequest.setHeader("Accept", "application/json-patch+json");
		postRequest.setHeader("Content-type", "application/json-patch+json");
		
		//attempt to create work item
		HttpResponse response = httpClient.execute(postRequest);
	
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
						 new InputStreamReader((response.getEntity().getContent())));
		
		
		
		String output;
		while ((output = br.readLine()) != null) {
		def jsonSlurper = new JsonSlurper()
		jsonObject = jsonSlurper.parseText(output)

		}
		return jsonObject
	}
	
	def updateWorkItem(String project, id, field, field2, val, val2, authString) {
		
		def jsonObject
		HttpPatch patchRequest = new HttpPatch(
				"https://${rurl}/mla092177/${project}/_apis/wit/workitems/${id}?api-version=7.1");
		//PATCH https://dev.azure.com/{organization}/{project}/_apis/wit/workitems/{id}?api-version=7.1
		//Request headers
		patchRequest.addHeader("accept", "application/json-patch+json");
		patchRequest.addHeader("Authorization", "Basic " + authString);
		
		def body = []
		
		 body.add([ op: 'add', path: "${field}", value: "${val}"])
		 body.add([ op: 'add', path: "${field2}", value: "${val2}"])
		 //format body into json formatting
		 String sbody = new JsonBuilder(body).toPrettyString()
		
		//final  String json = '[{"op": "add","path": ' + "${field2}" +',"value": ' + "${val2}" + "}]"
		
		//format body and content type
		final StringEntity entity = new StringEntity(sbody);
		patchRequest.setEntity(entity);
		patchRequest.setHeader("Accept", "application/json-patch+json");
		patchRequest.setHeader("Content-type", "application/json-patch+json");
		
		//attempt to create work item
		HttpResponse response = httpClient.execute(patchRequest);
	
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
						 new InputStreamReader((response.getEntity().getContent())));
		
		String output;
		while ((output = br.readLine()) != null) {
		def jsonSlurper = new JsonSlurper()
		jsonObject = jsonSlurper.parseText(output)

		}
		return jsonObject
	}
}
