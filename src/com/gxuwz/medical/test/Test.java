package com.gxuwz.medical.test;


public class Test {
	
	public static void main(String[] args) {
		  String str = "{T1348647853363:abcdefgh}";
	        if (str.startsWith("{")) {
	            str = str.substring(1);
	        }
	        if (str.endsWith("}")) {
	            str = str.substring(0,str.length() - 1);
	        }
	        System.out.println(str);
			
	}
	
}
