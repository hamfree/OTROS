package com.autentia.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;


public class ACharsetDetector {
	
	private final String fileName;
	
	private String detectedCharset = "";

	public ACharsetDetector(String path) {
		fileName = path;
	}
	
	public boolean isDetectedCharset() {
		return StringUtils.isNotBlank(detectedCharset);
	}
	
	public void detect() throws IOException {
		File file = new File(fileName);
		checkPreconditions(file);
		List<String> results = startDetection(file);
		detectedCharset = mergeResults(results);
	}

	private String mergeResults(List<String> results) {
		String valueToReturn = "";
		int index = 0;
		for(String result : results) {
			if(StringUtils.isNotBlank(result)) {
				valueToReturn = result + "(" + index + ")"; 
				break;
			}
			index++;
		}
		return valueToReturn;
		
	}

	private List<String> startDetection(File file) {
		final List<String> results = new ArrayList<String>();
		
		final List<Callable<String>> tasks = prepareTask(file);		
		final List<Future<String>> futureResults;
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			futureResults = executor.invokeAll(tasks);
			
			for (Future<String> result : futureResults) {
	            results.add(result.get()); // Prints "myResult" after 2 seconds.
	        }
			
	        executor.shutdown();
			
		} catch (InterruptedException e) {
			results.add("");
		} catch (ExecutionException e) {
			results.add("");
		}
		
		
		return results;
	}

	private List<Callable<String>> prepareTask(File file) {
		final List<Callable<String>> tasks = new ArrayList<Callable<String>>();
		
		final DetectorUsingJUniversalChardet detectorUsingJUniversal = new DetectorUsingJUniversalChardet();
		detectorUsingJUniversal.setFile(file);
		tasks.add(detectorUsingJUniversal);
		
		final DetectorUsingJavaNioCharset detectorUsingCharset = new DetectorUsingJavaNioCharset();
		detectorUsingCharset.setFile(file);
		tasks.add(detectorUsingCharset);
		
		return tasks;
		
	}

	private void checkPreconditions(File file) throws IOException {
		if(!file.exists()) {
			throw new FileNotFoundException(fileName + " does not exist.");
		}
		
		if(file.isDirectory()) {
			throw new IOException(fileName + " is a directory.");
		}
	}

	public String getDetectedCharset() {
		return detectedCharset;
	}

}
