package de.chrissx.alts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AltCryptography {
	
	/**
	 * 
	 * @param a random string to use for key-creation
	 * @return The key
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static SecretKeySpec createKey(String a) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("SHA-256").digest(a.getBytes(StandardCharsets.UTF_8)), 32), "AES");
	}
	
	static String altToCsv(Alt a) {
		return a.getEmail() + ";:;" + a.getPassword();
	}
	
	static Alt csvToAlt(String csv) {
		String[] splt = csv.split(";:;");
		return new Alt(splt[0], splt[1]);
	}
	
	static byte[] altsToBase64(List<Alt> alts) {
		if(alts.size() == 0)
			return new byte[0];
		String s = altToCsv(alts.get(0));
		for(int i = 1; i < alts.size(); i++)
			s += "\u000b" + alts.get(i);
		return Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8));
	}
	
	static List<Alt> base64ToAlts(byte[] base64) {
		if(base64.length == 0)
			return new ArrayList<Alt>();
		String[] strs = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8).replace("\r\n", "\u000b").split("\u000b");
		List<Alt> alts = new ArrayList<Alt>();
		for(String s : strs)
			alts.add(csvToAlt(s));
		return alts;
	}
	
	/**
	 * Loads key from the given file.
	 * @param file to load key from
	 * @return the loaded key
	 * @throws IOException
	 */
	public static SecretKeySpec aesLoadKey(Path file) throws IOException {
		return new SecretKeySpec(Base64.getDecoder().decode(Files.readAllBytes(file)), "AES");
	}
	
	/**
	 * Saves key to the given file.
	 * @param key to save
	 * @param file to save the key to
	 * @throws IOException
	 */
	public static void aesSaveKey(SecretKeySpec key, Path file) throws IOException {
		if(!file.toFile().exists())
			Files.createFile(file);
		Files.write(file, Base64.getEncoder().encode(key.getEncoded()));
	}
	
	/**
	 * Encrypts and saves the alts using the given key and file
	 * @param key to use in the encryption
	 * @param alts to encrypt and save
	 * @param file to save to
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static void aesEncrypt(SecretKeySpec key, List<Alt> alts, Path file) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		if(!file.toFile().exists())
			Files.createFile(file);
		Cipher ci = Cipher.getInstance("AES");
		ci.init(Cipher.ENCRYPT_MODE, key);
		Files.write(file, ci.doFinal(altsToBase64(alts)));
	}
	
	/**
	 * Loads and decrypts the alts using the give file and key
	 * @param key to use in the decryption
	 * @param file to load from
	 * @return the decrypted alts
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static List<Alt> aesDecrypt(SecretKeySpec key, Path file) throws IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher ci = Cipher.getInstance("AES");
		ci.init(Cipher.DECRYPT_MODE, key);
		return base64ToAlts(ci.doFinal(Files.readAllBytes(file)));
	}
}