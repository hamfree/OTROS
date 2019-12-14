package com.autentia.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.UnmappableCharacterException;
import java.util.ArrayList;
import java.util.List;


class DetectorUsingJavaNioCharset extends Detector {

	private static final String CHARSET_ISO_8859_15 = "ISO-8859-15";

	private static final String CHARSET_MAC_ROMAN = "MacRoman";
	
	private List<String> avaliablesCharsets = new ArrayList<String>();
	
	private byte[] onlyMacRomanBytes = new byte[] { (byte) 0x81, (byte) 0x8d, (byte) 0x8f, (byte) 0x90, (byte) 0x9D };

	public DetectorUsingJavaNioCharset() {
		avaliablesCharsets.add(CHARSET_ISO_8859_15);
		avaliablesCharsets.add(CHARSET_MAC_ROMAN);
	}
	
	@Override
	String detectCharset() {

		discardEncodings();
		
		for (String charsetName : avaliablesCharsets) {
			final Charset charset = detectCharset(file, Charset.forName(charsetName));
			if (charset != null) {
				return charset.displayName();
			}
		}
		return "";
	}

	private void discardEncodings() {
		try {
			final BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
			final byte[] buffer = new byte[512];
			
			while (input.read(buffer) != -1) {
				for(byte mByte : buffer) {
					checkIfByteIsOnlyInMacRoman(mByte);
				}
			}
			
			input.close();
		} catch (IOException ioException) {
			return;
		}
	}

	private void checkIfByteIsOnlyInMacRoman(byte mByte) {
		for(int index = 0; index < onlyMacRomanBytes.length; index++) {
			if(onlyMacRomanBytes[index] == mByte) {
				avaliablesCharsets.remove(CHARSET_ISO_8859_15);
			}
		}
	}

	private Charset detectCharset(File f, Charset charset) {
		CharsetDecoder decoder = prepareCharsetDecoder(charset);
		return tryingCharset(f, decoder) ? charset : null;
	}

	private CharsetDecoder prepareCharsetDecoder(Charset charset) {
		return charset.newDecoder().reset();
	}
	
	private boolean tryingCharset(File f, CharsetDecoder decoder) {
		try {
			final BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));
			final byte[] buffer = new byte[512];
			boolean identified = false;
			
			while ((input.read(buffer) != -1) && (!identified)) {
				identified = identify(buffer, decoder);
			}
	
			input.close();
			return identified;
		} catch (IOException ioException) {
			return false;
		}
	}


	private boolean identify(byte[] bytes, CharsetDecoder decoder) {
		try {
			decoder.decode(ByteBuffer.wrap(bytes));
		} catch (CharacterCodingException e) {
			return false;
		}
		return true;
	}

}
