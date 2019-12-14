package com.autentia.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.mozilla.universalchardet.UniversalDetector;

class DetectorUsingJUniversalChardet extends Detector {


	@Override
	String detectCharset() {

		String encoding;

		try {
			final FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			final UniversalDetector detector = new UniversalDetector(null);
			handleData(fis, detector);
			encoding = getEncoding(detector);
			detector.reset();
			
			fis.close();

		} catch (IOException e) {
			encoding = "";
		}

		return encoding;
	}

	private String getEncoding(UniversalDetector detector) {
		if(detector.isDone()) {
			return detector.getDetectedCharset();
		}
		return "";
	}

	private void handleData(FileInputStream fis, UniversalDetector detector) throws IOException {
		int nread;
		final byte[] buf = new byte[4096];
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
	}

}
