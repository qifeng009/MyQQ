package com.common;

import java.util.Hashtable;

public class LinkPort {
	public static Hashtable<Integer,TwoPortMsg> hash = new Hashtable<Integer,TwoPortMsg>();
	public static Hashtable<Integer,Boolean> isfirst = new Hashtable<Integer,Boolean>();
	public static int mixstore[] = new int[20];
	public static int count = 0;
}
