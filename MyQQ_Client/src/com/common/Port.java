package com.common;

import java.util.Hashtable;

import com.tools.ClientToServerThread;

public class Port {
	public static int commonPort;
	public static Hashtable<Integer,Integer> hash = new Hashtable<Integer,Integer>();
	public static Hashtable<Integer,ClientToServerThread> comm = new Hashtable<Integer,ClientToServerThread>();
	
}
