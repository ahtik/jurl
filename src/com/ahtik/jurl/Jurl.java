package com.ahtik.jurl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Jurl {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		System.out.println("====== JURL -- Java URL Inspector");
		
		System.setProperty("javax.net.debug", "ssl");
		
		if (args.length!=1) {
			System.err.println("Usage: ant jurl URL");
			System.exit(-1);
		}

		String url = args[0];
		
		System.out.println("=== java.* properties ===");
		Iterator<Entry<Object, Object>> it = System.getProperties().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry<java.lang.Object,java.lang.Object>) it
					.next();
			if (entry.getKey().toString().startsWith("java.")) {
				System.out.println(entry.getKey()+"="+entry.getValue());
			}
		}
		
		System.out.println("=== Connecting to "+url+" ===");

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		
			
		HttpResponse response = httpclient.execute(httpget);
		
		System.out.println("Protocol version: "+response.getProtocolVersion());
		System.out.println("Status code: "+response.getStatusLine().getStatusCode());
		System.out.println("Reason: "+response.getStatusLine().getReasonPhrase());
		System.out.println("Status: "+response.getStatusLine().toString());
		System.out.println("=== HEADERS ===");
		for (Header header : response.getAllHeaders()) {
			System.out.println(header.getName()+"="+header.getValue());
		}		
		

		System.out.println("=== EOF ===");
		System.exit(0);
	}

}
