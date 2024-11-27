package ado

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApacheHttpClientGetCountries {

	public static void main(String[] args) {
	  try {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		def cntries = [:]
		def String[] countries  = ["italy","deutschland"]
		def rurl =  "restcountries.com"
	
		
		for (int i = 0; i < countries.size(); i++) {
			HttpGet getRequest = new HttpGet(
				"https://${rurl}/v3.1/name/${countries[i]}");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
	
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
	
			BufferedReader br = new BufferedReader(
							 new InputStreamReader((response.getEntity().getContent())));
			def output;
			System.out.println("Output from Server .... \n");
			
			while ((output = br.readLine()) != null) {
			System.out.println(output);
		  }
	
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {
	
		e.printStackTrace();

	  } catch (IOException e) {
	
		e.printStackTrace();
	  }

	}

}
