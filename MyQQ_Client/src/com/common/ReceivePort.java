package com.common;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

import javax.swing.JOptionPane;

public class ReceivePort {
	
	
	public static int receivePort;
	
	public ReceivePort() throws IOException{
		try {
			DatagramSocket socket = getRangePort();  
			receivePort = socket.getLocalPort();
			
		} catch (SocketException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int getPort() throws IOException{
	    int port = 6666;
		try {
			DatagramSocket socket = getRangePort();  
			port= socket.getLocalPort();
			
		} catch (SocketException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return port;
	}
    public static int getReceivePort(){
    	return receivePort;
    }
	public static DatagramSocket getRandomPort() throws SocketException {
		DatagramSocket s = new DatagramSocket(0);
		return s;
	}
	public static DatagramSocket getRangePort() throws IOException {
		
		int ports[] = new int[20];
		for(int i = 0; i < 20; i++){
			Random random=new Random();
			int temp = 5000+random.nextInt(5000);
			while(temp < 5000 || temp >9999){
				temp = 5000+random.nextInt(5000);			
			}
			ports[i] = temp;
		}
		for (int port : ports) {
	        try {
	        	DatagramSocket socket = new DatagramSocket(port);
	        	if(socket.getLocalPort() == receivePort)
	        		continue;
	            return socket;
	        } catch (IOException ex) {
	            continue; // try next port
	        }
	    }

	    // if the program gets here, no port in the range was found
	    throw new IOException("no free port found");
	}
}
