package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	static Map<String,String> questionMap = new HashMap<>();
	static {
		String q1 = "What is your favourite food";
		String q2 = "Do you love to study";
		String q3 = "Are you boring";
		String q4 = "What is your favourite color";
		String qList = q1+","+q2+","+q3+","+q4;
		questionMap.put(q1,"Pizza all the way....");
		questionMap.put(q2, "Studies??Who does that?? :p");
		questionMap.put(q3, "Hell Yes I am boring");
		questionMap.put(q4, "Purple. Orange aint bad either....");
		questionMap.put("What Questions can you answer?", qList);
		//System.out.println(questionMap);
	}
	final static int portNumber = 8080;
	
	public static void main(String[] args) {
		try(ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("Server running at "+portNumber);
			while(true) {
				Socket clientSocket = serverSocket.accept();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String line = "";
				StringBuilder question = new StringBuilder();
				while((line = br.readLine())!=null) {
					question.append(line);
				}
				String que = question.toString().trim();
				System.out.println("Client is asking:"+que+"\n");
				
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
				if(questionMap.containsKey(que)) {
					out.println(questionMap.get(que));
				} else {
					out.println("It is a Secret! I won't tell you!");
				}
				out.close();
				br.close();
			}
//			
		} catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

}
