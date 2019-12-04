package com.autentia.utils;

import java.io.IOException;

public class Main 
{
	private final static int NO_ARGS = 1;
	
    public static void main( String[] args )
    {
    	
    	if(args.length <= 0) {
    		System.out.println("Usage: aCharsetDetector {files}");
    		System.exit(NO_ARGS);
    	}
    	
//    	String[] files = new String[] { "/Users/fjarroyo/Desktop/pruebas_charset/files/mac-extended-ascii.txt" 
//    			, "/Users/fjarroyo/Desktop/pruebas_charset/files/mac-os-roman.txt"
//    			, "/Users/fjarroyo/Desktop/pruebas_charset/files/windows-latino-1.txt"
//    			, "/Users/fjarroyo/Desktop/pruebas_charset/files/utf8.txt" };
    	
    	    	
    	for(final String path : args){
    		try {
    			
				final ACharsetDetector charsetDetector = new ACharsetDetector(path);
				charsetDetector.detect();
				if(charsetDetector.isDetectedCharset()) {
					System.out.println(path + ": " + charsetDetector.getDetectedCharset());
					continue;
				}
				System.out.println(path + ": Charset is not detected.");
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
    	}
    	
        
    }
}
