package com.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
	final static int portNumber = 8080;
	final static String hostName = "127.0.0.1";
    private static BufferedReader  input   = null;
    private static PrintWriter out     = null;
    private static String question = "What Questions can you answer?";
	
	public static void main(String[] args) {
		try {
			Socket serverSocket = new Socket(hostName, portNumber);
			System.out.println("Hey I am client and I will automatically fire questions");
			out    = new PrintWriter(serverSocket.getOutputStream());
			
			out.write(question);
			out.flush();
			serverSocket.shutdownOutput();
			input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			String line = "";
			StringBuilder response = new StringBuilder();
			while((line = input.readLine())!=null) {
				response.append(line);
			}
			String[] ques = response.toString().split(",");
			System.out.println("\n------Server says that it can answer following questions:\n");
			for(String que : ques) {
				System.out.println(que);
			}
			System.out.println("\n------Type question one by one as it is to get the answer:\n");
			out.close();
			input.close();
			serverSocket.close();
			Scanner sc = new Scanner(System.in);
			String ch = ques.length>0?"":"n";
			while(!"n".equals(ch)) {
				serverSocket = new Socket(hostName, portNumber);
				String q = sc.nextLine();
				out    = new PrintWriter(serverSocket.getOutputStream());
				out.write(q);
				out.flush();
				serverSocket.shutdownOutput();
				
				input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				line = "";
				response = new StringBuilder();
				while((line = input.readLine())!=null) {
					response.append(line);
				}
				System.out.println("Server replied: "+response);
				System.out.println("\n Ask more?(y/n):");
				ch = sc.nextLine();
				out.close();
				input.close();
				serverSocket.close();
			}
//			System.out.println("Response from server:"+response);
//			if(response.length()!=0) {
//				String[] ques = response.toString().split(",");
//				for(String que : ques) {
//					line = "";
//					out.write(que);
//					
//					input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//					response = new StringBuilder();
//					while((line = input.readLine())!=null) {
//						response.append(line);
//					}
//					System.out.println("Server says: "+response);
//				}
//				out.close();
//				input.close();
//				
//			} else {
//				System.out.println("Server is not ready to answer any questions");
//			}
//			out.close();
//			input.close();
		} catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
}
