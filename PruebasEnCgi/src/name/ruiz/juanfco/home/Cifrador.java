package name.ruiz.juanfco.home;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author hamfree
 *
 */
public class Cifrador {
	/**
	 * 
	 */
	private byte[] raw;

	/**
	 * 
	 * @param raw
	 */
	public Cifrador(byte[] raw) {
		super();
		this.raw = raw;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws GeneralSecurityException
	 */
	public byte[] encrypt(String key, String value) throws GeneralSecurityException {
		raw = key.getBytes(Charset.forName("UTF-8"));
		if (raw.length != 16) {
			throw new IllegalArgumentException("Tamaño no válido de clave.");
		}

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
		return cipher.doFinal(value.getBytes(Charset.forName("UTF-8")));
	}

	/**
	 * 
	 * @param key
	 * @param encrypted
	 * @return
	 * @throws GeneralSecurityException
	 */
	public String decrypt(String key, byte[] encrypted) throws GeneralSecurityException {
		raw = key.getBytes(Charset.forName("UTF-8"));
		if (raw.length != 16) {
			throw new IllegalArgumentException("Tamaño no válido de clave.");
		}
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
		byte[] original = cipher.doFinal(encrypted);

		return new String(original, Charset.forName("UTF-8"));
	}

	/**
	 * @return the raw
	 */
	public byte[] getRaw() {
		return raw;
	}

	/**
	 * @param raw
	 *            the raw to set
	 */
	public void setRaw(byte[] raw) {
		this.raw = raw;
	}
}
