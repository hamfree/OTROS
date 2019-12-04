package com.autentia.utils;

import java.io.File;
import java.util.concurrent.Callable;

abstract class Detector implements Callable<String> {

	protected File file = null;
	
	public String call() throws Exception {
		if(file == null) {
			throw new IllegalStateException("setFile must be called before executeProcessor.");
		}
		
		return detectCharset();
	}
	
	void setFile(File file) {
		this.file = file;
	}
	
	abstract String detectCharset();
	
	
}
